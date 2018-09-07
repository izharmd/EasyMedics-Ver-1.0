package com.wakeup.xxx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import az.plainpie.PieView;


public class BloodGlucoseActivity extends AppCompatActivity {

    ImageView imv_plush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_glucose);

        initView();

        PieView pieView = (PieView) findViewById(R.id.pieView);
        pieView.setPercentage(67);
        // Update the visibility of the widget text
        pieView.setInnerTextVisibility(View.VISIBLE);

// Change the text of the widget
        pieView.setInnerText("67 %" +"\n" +"IN RANGE");


// Change the text size of the widget
        pieView.setPercentageTextSize(30);
        pieView.setInnerBackgroundColor(getResources().getColor(R.color.white));

// Change the color fill of the bar representing the current percentage
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.colorPrimary));

// Change the color fill of the background of the widget, by default is transparent
        pieView.setMainBackgroundColor(getResources().getColor(R.color.myCustomColor));

// Change the color of the text of the widget
        pieView.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void initView() {

        imv_plush = (ImageView)findViewById(R.id.imv_header);
        imv_plush.setImageResource(R.drawable.ic_plush);
    }
}
