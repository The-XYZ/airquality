package xyz.airquality;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by championswimmer on 22/3/15.
 */
public class StatePollutionLoader extends AsyncTask<Integer, Void, ArrayList<StatePollutionObject>>{

    Context mContext;
    GoogleMap mMap;

    static ArrayList<StatePollutionObject> statesPollutionObjects;
    static boolean loadedOnce;

    public StatePollutionLoader(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
    }


    @Override
    protected ArrayList<StatePollutionObject> doInBackground(Integer... params) {

        if (loadedOnce && (statesPollutionObjects != null)) {
            return statesPollutionObjects;
        } else {
            try {
                JSONArray jArr = new JSONArray(loadJSONFromAsset());
                statesPollutionObjects = new ArrayList<>(jArr.length());
                for (int i = 0; i < jArr.length(); i++) {
                    Log.d("Trickle", jArr.getJSONObject(i).getString("state"));
                    String state = jArr.getJSONObject(i).getString("state");
                    double latitude;
                    double longitude;
                    try {
                        latitude = jArr.getJSONObject(i).getJSONObject("coords").getDouble("lat");
                        longitude = jArr.getJSONObject(i).getJSONObject("coords").getDouble("lng");
                    } catch (Exception e ) {
                        latitude = 0;
                        longitude = 0;
                    }
                    int level;
                    try {
                        level = jArr.getJSONObject(i).getInt("level");
                    } catch (Exception e) {
                        level = 50;
                    }
                    statesPollutionObjects.add(new StatePollutionObject(
                            state,
                            latitude,
                            longitude,
                            level
                            ));
                }
                loadedOnce = true;
                return statesPollutionObjects;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    @Override
    protected void onPostExecute(ArrayList<StatePollutionObject> statesPollutionObjects) {
        super.onPostExecute(statesPollutionObjects);
        loadPollutionOnMap();

    }

    private void loadPollutionOnMap () {
        if (mMap.getCameraPosition().zoom > 6) {
            for (StatePollutionObject statesPollutionObject : statesPollutionObjects) {
                float distance[] = new float[3];
                Location.distanceBetween(
                        statesPollutionObject.latitude,
                        statesPollutionObject.longitude,
                        mMap.getCameraPosition().target.latitude,
                        mMap.getCameraPosition().target.longitude,
                        distance
                );
                IconGenerator ig = new IconGenerator(mContext);
                if (statesPollutionObject.level > 60) ig.setStyle(IconGenerator.STYLE_RED);
                else if (statesPollutionObject.level > 40) ig.setStyle(IconGenerator.STYLE_ORANGE);
                else ig.setStyle(IconGenerator.STYLE_GREEN);

//                mMap.addCircle(new CircleOptions()
//                        .center(new LatLng(statesPollutionObject.latitude, statesPollutionObject.longitude)));
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(statesPollutionObject.latitude, statesPollutionObject.longitude))
                        .icon(BitmapDescriptorFactory
                                .fromBitmap(ig.makeIcon(String.valueOf(statesPollutionObject.level)))));
                Log.d("lol","here");
            }
        } else {
            // make heatmap otherwise
            List<WeightedLatLng> pointList = new ArrayList<WeightedLatLng>();
            for (StatePollutionObject statesPollutionObject : statesPollutionObjects) {
                pointList.add(
                        new WeightedLatLng(
                                new LatLng(statesPollutionObject.latitude,
                                        statesPollutionObject.longitude),
                                (statesPollutionObject.level)));
            }
            if (!pointList.isEmpty()) {
                HeatmapTileProvider provider =
                        new HeatmapTileProvider.Builder()
                                .weightedData(pointList) //FIXME: this comes to be null sometimes
                                .build();
                mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
            }
        }
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = mContext.getAssets().open("states_pollution.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}