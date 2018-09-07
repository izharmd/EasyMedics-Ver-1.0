package FragmentView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wakeup.xxx.R;

import az.plainpie.PieView;


/**
 * Created by BWS on 11/07/2018.
 */

public class BloodGlucoseFragment extends Fragment {
    View rootview;
    ImageView imv_header;
    TextView text_headerMain,text_header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_blood_glucose, viewGroup, false);

         initView();

        return rootview;
    }

    private void initView() {

      /*  imv_header = (ImageView)rootview.findViewById(R.id.imv_header);
        text_headerMain = (TextView)rootview.findViewById(R.id.text_headerMain);
        text_header = (TextView)rootview.findViewById(R.id.text_header);

      //  imv_header.setImageResource(R.drawable.ic_plush);
        text_headerMain.setText("Your SUGUR LEVEL");
        text_header.setText("Blood Glucose");
*/



        PieView pieView = (PieView)rootview. findViewById(R.id.pieView);
        pieView.setPercentage(67);
        // Update the visibility of the widget text
        pieView.setInnerTextVisibility(View.VISIBLE);

// Change the text of the widget
        pieView.setInnerText("67 %" +"\n" +"IN RANGE");


// Change the text size of the widget
        pieView.setPercentageTextSize(20);
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));

// Change the color fill of the bar representing the current percentage
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorPrimary));

// Change the color fill of the background of the widget, by default is transparent
        pieView.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));

// Change the color of the text of the widget
        pieView.setTextColor(getResources().getColor(R.color.colorAccent));
    }
}