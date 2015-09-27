package xyz.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegionDetailActivity extends AppCompatActivity {

    String station;
    BarChart NO2, SO2, CO, O3;



    public  static  int PERMCO = 4;
    public  static  int PERMNO = 12;
    public  static  int PERMOZONE = 180;
    public  static  int PERMNO2 = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);

        Intent intent = getIntent();
         station = intent.getStringExtra("Station");

        NO2 = (BarChart) findViewById(R.id.barchartNO2);
        SO2 = (BarChart) findViewById(R.id.barchartSO2);
        CO = (BarChart) findViewById(R.id.barchartCO);
        O3 = (BarChart) findViewById(R.id.barchartO3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(station);
        toolbar.setSubtitle("");


        loadData();


    }

    @Override
    public void onResume() {
        super.onResume();
        NO2.startAnimation();
        SO2.startAnimation();
        CO.startAnimation();
        O3.startAnimation();
    }


    private void loadData() {
        NO2.addBar(new BarModel(2.3f, 0xFF123456));
        NO2.addBar(new BarModel(2.f,  0xFF343456));
        NO2.addBar(new BarModel(3.3f, 0xFF563456));
        NO2.addBar(new BarModel(1.1f, 0xFF873F56));
        NO2.addBar(new BarModel(2.7f, 0xFF56B7F1));
        SO2.addBar(new BarModel(2.f,  0xFF343456));
        SO2.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        SO2.addBar(new BarModel(4.f,  0xFF1BA4E6));
        SO2.addBar(new BarModel(2.3f, 0xFF123456));
        SO2.addBar(new BarModel(2.f, 0xFF343456));
        CO.addBar(new BarModel(3.3f, 0xFF563456));
        CO.addBar(new BarModel(1.1f, 0xFF873F56));
        CO.addBar(new BarModel(2.7f, 0xFF56B7F1));
        CO.addBar(new BarModel(2.f,  0xFF343456));
        CO.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        O3.addBar(new BarModel(3.4f, 0xFF343456));
        O3.addBar(new BarModel(2.3f, 0xFF563456));
        O3.addBar(new BarModel(04.f, 0xFF1BA4E6));
        O3.addBar(new BarModel(2.8f, 0xFF1FF4AC));
        O3.addBar(new BarModel(1.1f, 0xFF563456));

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

    public int NO() {
        Random r = new Random();
        int Low = 357;
        int High = 450;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int CO() {
        Random r = new Random();
        int Low = 1;
        int High = 8;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int Ozone() {
        Random r = new Random();
        int Low = 12;
        int High = 250;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int N02() {
        Random r = new Random();
        int Low = 12;
        int High = 200;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }


    public String genColor(int a ,int b){
        if(a>b){
            return "#ff0000";
        }else{
            return "#00ff00";
        }

    }
}
