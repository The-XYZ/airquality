package xyz.airquality;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.anton46.stepsview.StepsView;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.Random;

public class RegionDetailActivity extends AppCompatActivity {

    String station;
    int remark;
    BarChart NO2, SO2, CO, O3;
    AppCompatButton cropButton, effectButton;

    KenBurnsView kenBurnsView;
    StepsView stepsView;
    TextView remarkText;


    public  static  int PERMCO = 4;
    public  static  int PERMNO = 12;
    public  static  int PERMOZONE = 180;
    public  static  int PERMNO2 = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);

        cropButton = (AppCompatButton) findViewById(R.id.crop);
        effectButton = (AppCompatButton) findViewById(R.id.Effects);
        stepsView = (StepsView) findViewById(R.id.stepsView);
        remarkText = (TextView) findViewById(R.id.remarkText);

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(RegionDetailActivity.this, Crops.class);
                startActivity(i);
            }
        });
        effectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(RegionDetailActivity.this, Effects.class);
                startActivity(i);
            }
        });

        Intent intent = getIntent();
         station = intent.getStringExtra("Station");
        remark = intent.getIntExtra("Remark",2);

        NO2 = (BarChart) findViewById(R.id.barchartNO2);
        SO2 = (BarChart) findViewById(R.id.barchartSO2);
        CO = (BarChart) findViewById(R.id.barchartCO);
        O3 = (BarChart) findViewById(R.id.barchartO3);

        kenBurnsView = (KenBurnsView) findViewById(R.id.kenburns);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(station);
        toolbar.setSubtitle("");


        stepsView.setLabels(new String[]{"","","",""})
                .setBarColorIndicator(getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(getColorForPollutionLevel(remark))
                .setLabelColorIndicator(getResources().getColor(R.color.orange))
                .setCompletedPosition(remark-1)
                .drawView();

        remarkText.setText(getTextForPollutionLevel(remark));

        loadData();

        switch (station) {
            case "DCE":
                kenBurnsView.setImageResource(R.drawable.image_dce);
                break;
            case "NSIT Dwarka":
                kenBurnsView.setImageResource(R.drawable.image_nsit);
                break;
            case "Mandir Marg":
                kenBurnsView.setImageResource(R.drawable.image_mandir_marg);
        }


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

    private int getColorForPollutionLevel(int number) {
        switch (number) {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.parseColor("#FFA500");
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.RED;
            default:
                return Color.YELLOW;
        }
    }

    private String getTextForPollutionLevel(int number) {
        switch (number) {
            case 1:
                return "Safe";
            case 2:
                return "Moderate";
            case 3:
                return "Unsafe";
            case 4:
                return "Highly unsafe";
            default:
                return "Moderate";
        }
    }
}
