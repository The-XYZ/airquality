package xyz.airquality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Effects extends AppCompatActivity {

    TextView effects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEffects);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Effects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        effects  = (TextView) findViewById(R.id.effectDetail);
        effects.setText(getResources().getString(R.string.info_pollutant_risks_CO)+"\n\n\n"+getResources().getString(R.string.info_pollutant_risks_NO2)+"\n\n\n"+getResources().getString(R.string.info_pollutant_risks_O3));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_effects, menu);
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
}
