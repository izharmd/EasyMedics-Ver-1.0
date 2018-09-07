package FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wakeup.xxx.R;
import com.daasuu.ahp.AnimateHorizontalProgressBar;

/**
 * Created by BWS on 21/07/2018.
 */

public class HeartRateFragment extends Fragment {
    View rootview;
    AnimateHorizontalProgressBar heart_progress_bar, sys_progress_bar, dia_progress_bar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_heart_rate, viewGroup, false);

         initView();

        return rootview;
    }

    private void initView() {
        heart_progress_bar = (AnimateHorizontalProgressBar)rootview.findViewById(R.id.heart_progress_bar);
        heart_progress_bar.setMax(100);
        heart_progress_bar.setProgress(92);

        sys_progress_bar = (AnimateHorizontalProgressBar)rootview.findViewById(R.id.sys_progress_bar);
        sys_progress_bar.setMax(100);
        sys_progress_bar.setProgress(85);

        dia_progress_bar = (AnimateHorizontalProgressBar)rootview.findViewById(R.id.dia_progress_bar);
        dia_progress_bar.setMax(100);
        dia_progress_bar.setProgress(78);
    }
}