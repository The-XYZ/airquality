package xyz.airquality;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class RegionDetailActivity extends AppCompatActivity {

    private PieChart NO2, SO2, CO, O3;
    String station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);

        Intent intent = getIntent();
         station = intent.getStringExtra("station");

        NO2 = (PieChart) findViewById(R.id.piechart1);
        SO2 = (PieChart) findViewById(R.id.piechart2);
        CO = (PieChart) findViewById(R.id.piechart3);
        O3 = (PieChart) findViewById(R.id.piechart4);

        loadDataCO();
        loadDataNO2();
        loadDataO3();
        loadDataSO2();
    }

    @Override
    public void onResume() {
        super.onResume();
        NO2.startAnimation();
        SO2.startAnimation();
        CO.startAnimation();
        O3.startAnimation();
    }

    /*@Override
    public void restartAnimation() {
        NO2.startAnimation();
        SO2.startAnimation();
        CO.startAnimation();
        O3.startAnimation();
    }

    @Override
    public void onReset() {

    }*/

    private void loadDataNO2() {
        NO2.addPieSlice(new PieModel("", 15, Color.parseColor("#FE6DA8")));
        NO2.addPieSlice(new PieModel("", 25, Color.parseColor("#56B7F1")));
        NO2.addPieSlice(new PieModel("", 35, Color.parseColor("#CDA67F")));
        NO2.addPieSlice(new PieModel("", 9, Color.parseColor("#FED70E")));

        NO2.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
            }
        });
    }

    private void loadDataSO2() {
        SO2.addPieSlice(new PieModel("", 10, Color.parseColor("#C7A27C")));
        SO2.addPieSlice(new PieModel("", 35, Color.parseColor("#D65780")));
        SO2.addPieSlice(new PieModel("", 15, Color.parseColor("#576490")));
        SO2.addPieSlice(new PieModel("", 15, Color.parseColor("#C9CAD9")));

        SO2.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
            }
        });
    }

    private void loadDataCO() {
        CO.addPieSlice(new PieModel("", 45, Color.parseColor("#4C061D")));
        CO.addPieSlice(new PieModel("", 15, Color.parseColor("#D17A22")));
        CO.addPieSlice(new PieModel("", 25, Color.parseColor("#B4C292")));
        CO.addPieSlice(new PieModel("", 10, Color.parseColor("#736F4E")));

        CO.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
            }
        });
    }

    private void loadDataO3() {
        O3.addPieSlice(new PieModel("", 35, Color.parseColor("#5B2E48")));
        O3.addPieSlice(new PieModel("", 15, Color.parseColor("#585563")));
        O3.addPieSlice(new PieModel("", 25, Color.parseColor("#73937E")));
        O3.addPieSlice(new PieModel("", 10, Color.parseColor("#CEB992")));

        O3.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_region_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadCO(String state) {

        ParseQuery query = ParseQuery.getQuery("airq");
        query.whereEqualTo("Station", station);
        query.whereEqualTo("Parameter", "Carbon Monoxide(CO)");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, com.parse.ParseException e) {
                if (e==null) {
                    List<ParseObject> sortedList = new ArrayList<ParseObject>();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadOzone(String state) {

        ParseQuery query = ParseQuery.getQuery("airq");
        query.whereEqualTo("Station", station);
        query.whereEqualTo("Parameter", "Carbon Monoxide(CO)");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, com.parse.ParseException e) {
                if (e==null) {
                    List<ParseObject> sortedList = new ArrayList<ParseObject>();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadNO2(String state) {

        ParseQuery query = ParseQuery.getQuery("airq");
        query.whereEqualTo("Station", station);
        query.whereEqualTo("Parameter", "Carbon Monoxide(CO)");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, com.parse.ParseException e) {
                if (e==null) {
                    List<ParseObject> sortedList = new ArrayList<ParseObject>();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
