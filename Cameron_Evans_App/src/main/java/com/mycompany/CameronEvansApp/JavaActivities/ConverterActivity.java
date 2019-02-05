package com.mycompany.CameronEvansApp.JavaActivities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mycompany.CameronEvansApp.AsyncTask.ConvertTask;
import com.mycompany.CameronEvansApp.R;
import com.mycompany.CameronEvansApp.Utils.BottomNavigationViewHelper;
import org.apache.commons.lang3.StringUtils;




public class ConverterActivity extends AppCompatActivity {
    private static final String TAG = "ConverterActivity";

    public Context mContext = ConverterActivity.this;

    private static final int ACTIVITY_NUM = 1;

    String fromCurrency;
    String toCurrency;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_activity);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView();

        setupSpinner();


    }

    //getting content from layout, sending to AsyncTask for conversion
    public void convertCurrency(View view){

        EditText numberToConvert = findViewById(R.id.numberToConvert);
        TextView resultText = findViewById(R.id.result);

        fromCurrency = StringUtils.substring(fromCurrency,0,3);

        toCurrency = StringUtils.substring(toCurrency,0,3);
        String number = numberToConvert.getText().toString();

        fromCurrency = StringUtils.substring(fromCurrency,0,3);

        toCurrency = StringUtils.substring(toCurrency,0,3);

         new ConvertTask(numberToConvert, fromCurrency, toCurrency, resultText, mContext,number).execute();


        }


    private void setupSpinner() {
        Spinner spinner1 = findViewById(R.id.fromCurrency);
       Spinner spinner2 = findViewById(R.id.toCurrency);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.currencyArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.currencyArray, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        spinner1.setSelection(5);
        spinner2.setSelection(10);

        spinner1.setOnItemSelectedListener(new fromCurrencySpinnerClass());
        spinner2.setOnItemSelectedListener(new toCurrencySpinnerClass());

    }

    //from currency dropdown
    class fromCurrencySpinnerClass implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            setFromCurrency(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    //to currency dropdown
    class toCurrencySpinnerClass implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            setToCurrency(parent.getItemAtPosition(position).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private void setupBottomNavigationView(){
        //getting BottomNavViewBar from currency_activity
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.BottomNavViewBar);

        //sending bottomNavigationViewEx to BottomNavigationViewHelper in Utils to setup bottom menu
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        //enabling navigation using enableNavigation
        BottomNavigationViewHelper.enableNavigation(mContext,this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);

        menuItem.setChecked(true);

    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
}
