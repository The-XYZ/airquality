package xyz.airquality;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by laavanye on 27/9/15.
 */
public class ApplicationWrapper extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, getString(R.string.i),
                getString(R.string.p));
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
