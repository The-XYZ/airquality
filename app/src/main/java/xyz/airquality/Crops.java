package xyz.airquality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Crops extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCrops);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Impact on Crops");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
