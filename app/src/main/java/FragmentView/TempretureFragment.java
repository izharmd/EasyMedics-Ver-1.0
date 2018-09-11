package FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wakeup.easymedics.R;

/**
 * Created by BWS on 21/07/2018.
 */

public class TempretureFragment extends Fragment {
    View rootview;
    ImageView imv_plush;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_tempreture, viewGroup, false);

        // initView();

        return rootview;
    }
}