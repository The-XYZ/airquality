package xyz.airquality;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by naman on 27/09/15.
 */
public class ImproveQualityFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_improve_airquality, container, false);

        return rootView;
    }
}
