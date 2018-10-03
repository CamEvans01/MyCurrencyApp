package com.mycompany.CameronEvansApp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mycompany.CameronEvansApp.R;

import com.mycompany.CameronEvansApp.JavaActivities.ConverterActivity;
import com.mycompany.CameronEvansApp.JavaActivities.CurrencyRateActivity;


public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavViewHelper";

    //method to setup the bottom navigation bar
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.e(TAG, "setupBottomNavigationView: set up NavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);


    }

    //method to switch between activities based on the image selected
    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_fiat:
                        Intent intent1 = new Intent(context, CurrencyRateActivity.class);
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //ACTIVITY_NUM = 0
                        break;
                    case R.id.ic_convert:
                        Intent intent3 = new Intent(context, ConverterActivity.class);
                        context.startActivity(intent3);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out); //ACTIVITY_NUM = 2
                        break;
                }
                return false;
            }
        });
    }
}
