package FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wakeup.xxx.R;

import az.plainpie.PieView;

/**
 * Created by BWS on 21/07/2018.
 */

public class HealthDashBoardFragment extends Fragment {
    View rootview;
    ImageView imv_plush;

    LinearLayout ll_header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_health_dashboard, viewGroup, false);

         initView();

        return rootview;
    }

    private void initView() {

        PieView pieView = (PieView) rootview.findViewById(R.id.pieView);
        pieView.setPercentage(78);
        // Update the visibility of the widget text
        pieView.setInnerTextVisibility(View.VISIBLE);
        // pieView.setPercentageTextSize();

// Change the text of the widget
        pieView.setInnerText("78 %" );


// Change the text size of the widget
        pieView.setPercentageTextSize(30);
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));

// Change the color fill of the bar representing the current percentage
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorPrimary));

// Change the color fill of the background of the widget, by default is transparent
        pieView.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));

// Change the color of the text of the widget
        pieView.setTextColor(getResources().getColor(R.color.colorAccent));


        //PieView pieView = (PieView) findViewById(R.id.pieView);
        PieView pieViewHeartRate = (PieView)rootview. findViewById(R.id.pieViewHeartRate);
        PieView pieViewBloodSugur1 = (PieView)rootview. findViewById(R.id.pieViewBloodSugur1);
        PieView pieViewBloodSugur2 = (PieView)rootview. findViewById(R.id.pieViewBloodSugur2);

        // pieView.setPercentage(67);
        pieViewHeartRate.setPercentage(60);
        pieViewBloodSugur1.setPercentage(16);
        pieViewBloodSugur2.setPercentage(25);

        pieViewHeartRate.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));
        pieViewBloodSugur1.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));
        pieViewBloodSugur2.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));

        // Update the visibility of the widget text
        // pieView.setInnerTextVisibility(View.VISIBLE);
        pieViewHeartRate.setInnerTextVisibility(View.VISIBLE);
        pieViewBloodSugur1.setInnerTextVisibility(View.VISIBLE);
        pieViewBloodSugur2.setInnerTextVisibility(View.VISIBLE);

// Change the text of the widget
        //pieView.setInnerText("78 %");
        pieViewHeartRate.setInnerText("60 %");
        pieViewBloodSugur1.setInnerText("16 %");
        pieViewBloodSugur2.setInnerText("25 %");


// Change the text size of the widget
        // pieView.setPercentageTextSize(15);
        pieViewHeartRate.setPercentageTextSize(15);
        pieViewBloodSugur1.setPercentageTextSize(15);
        pieViewBloodSugur2.setPercentageTextSize(15);


        // pieView.setInnerBackgroundColor(Color.TRANSPARENT);
        pieViewHeartRate.setInnerBackgroundColor(getResources().getColor(R.color.white));
        pieViewBloodSugur1.setInnerBackgroundColor(getResources().getColor(R.color.white));
        pieViewBloodSugur2.setInnerBackgroundColor(getResources().getColor(R.color.white));

        // Change the color fill of the bar representing the current percentage
        //pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorPrimary));
        pieViewHeartRate.setPercentageBackgroundColor(getResources().getColor(R.color.colorAccent));

        pieViewBloodSugur1.setPercentageBackgroundColor(getResources().getColor(R.color.colorAccent));
        pieViewBloodSugur2.setPercentageBackgroundColor(getResources().getColor(R.color.colorAccent));

        pieViewHeartRate.setTextColor(getResources().getColor(R.color.colorPrimary));
        pieViewBloodSugur1.setTextColor(getResources().getColor(R.color.black));
        pieViewBloodSugur2.setTextColor(getResources().getColor(R.color.colorAccent));

    }
}