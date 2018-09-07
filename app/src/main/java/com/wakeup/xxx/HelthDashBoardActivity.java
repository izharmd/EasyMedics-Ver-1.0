package com.wakeup.xxx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import FragmentView.FragmentWearable;
import az.plainpie.PieView;

public class HelthDashBoardActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Fragment fragment = null;

    Button btnwearable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helth_dash_board);

        initview();

        clickEvent();

        PieView pieView = (PieView) findViewById(R.id.pieView);
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
        PieView pieViewHeartRate = (PieView) findViewById(R.id.pieViewHeartRate);
        PieView pieViewBloodSugur1 = (PieView) findViewById(R.id.pieViewBloodSugur1);
        PieView pieViewBloodSugur2 = (PieView) findViewById(R.id.pieViewBloodSugur2);

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

    private void clickEvent() {

        btnwearable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new FragmentWearable();
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                } else {
                    Toast.makeText(HelthDashBoardActivity.this, "Error in creating fragment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initview() {

        btnwearable = (Button)findViewById(R.id.btnwearable);
    }
}
