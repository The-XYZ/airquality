package xyz.airquality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.google.maps.android.ui.IconGenerator;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naman on 27/09/15.
 */
public class HomeFragment extends Fragment {

    private GoogleMap mMap;
    private RecyclerView mRecyclerView;
    Spinner spinner;
    StationsAdapter stationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_home, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

        spinner = (Spinner) rootView.findViewById(R.id.spinner_nav);
        spinner.setVisibility(View.VISIBLE);
        final List<String> list = new ArrayList<String>();
        list.add("DELHI");
        list.add("GUJARAT");
        list.add("ARUNACHAL PRADESH");
        list.add("PUDUCHERRY");
        list.add("JHARKHAND");
        list.add("HARYANA");
        list.add("MANIPUR");
        list.add("GOA");
        list.add("MEGHALAYA");
        list.add("CHHATTISGARH");
        list.add("LAKSHADWEEP");
        list.add("KERALA");
        list.add("TAMIL NADU");
        list.add("RAJASTHAN");
        list.add("DELHI");
        list.add("UTTAR PRADESH");
        list.add("NAGALAND");
        list.add("MAHARASHTRA");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String state = list.get(i).toLowerCase();
                String stateToMatch = state.substring(0, 1).toUpperCase() + state.substring(1);
                loadStationFromParse(stateToMatch);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);

        stationsAdapter = new StationsAdapter(getActivity(), new ArrayList<ParseObject>());
        mRecyclerView.setAdapter(stationsAdapter);

        return rootView;


    }

    private void loadStationFromParse(final String state) {

        ParseQuery query = ParseQuery.getQuery("StateRemark");
        query.whereEqualTo("State", state);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    stationsAdapter.updateDataset(objects);
                    createHeatMap(state, objects);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addToMap(LatLng position, String title, int pollutionLevel) {

        IconGenerator ig = new IconGenerator(getActivity());

        switch (pollutionLevel) {
            case 1:
                ig.setStyle(IconGenerator.STYLE_GREEN);
                break;
            case 2:
                ig.setStyle(IconGenerator.STYLE_ORANGE);
                break;
            case 3:
                ig.setStyle(IconGenerator.STYLE_PURPLE);
                break;
            case 4:
                ig.setStyle(IconGenerator.STYLE_RED);
                break;
            default:
                ig.setStyle(IconGenerator.STYLE_RED);
                break;

        }

        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();

        markerOptions.position(position);
        markerOptions.title(title);
//        markerOptions.icon(BitmapDescriptorFactory
//                .fromBitmap(ig.makeIcon(String.valueOf(pollutionLevel))));
        mMap.addMarker(markerOptions);

//        moveMapToPosition(6f, position);

    }


    private void createHeatMap(String state, List<ParseObject> stations) {
        List<StatePollutionObject> states = new ArrayList<>();
        for (ParseObject object : stations) {
            states.add(new StatePollutionObject(state, object.getParseGeoPoint("latlon2").getLatitude(), object.getParseGeoPoint("latlon2").getLongitude(), object.getNumber("Remark").intValue()));
            addToMap(new LatLng(object.getParseGeoPoint("latlon2").getLatitude(),object.getParseGeoPoint("latlon2").getLongitude()),object.getString("Station"),object.getNumber("Remark").intValue());
        }

        List<WeightedLatLng> pointList = new ArrayList<WeightedLatLng>();
        for (StatePollutionObject statesPollutionObject : states) {
            pointList.add(
                    new WeightedLatLng(
                            new LatLng(statesPollutionObject.latitude,
                                    statesPollutionObject.longitude),
                            (statesPollutionObject.level)));
        }
        if (!pointList.isEmpty()) {
            HeatmapTileProvider provider =
                    new HeatmapTileProvider.Builder()
                            .weightedData(pointList)
                                    .radius(50)
                            .build();
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
        }

        moveMapToPosition(10f, new LatLng(stations.get(0).getParseGeoPoint("latlon2").getLatitude(), stations.get(0).getParseGeoPoint("latlon2").getLongitude()));
    }

    private void moveMapToPosition(float ratio, LatLng position) {
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(position, ratio);
        mMap.animateCamera(cameraPosition);

    }
}
