package com.mycompany.CameronEvansApp.AsyncTask;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import org.patriques.AlphaVantageConnector;
import org.patriques.ForeignExchange;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.exchange.CurrencyExchange;
import org.patriques.output.exchange.data.CurrencyExchangeData;

public class ConvertTask extends AsyncTask<String,String,String> {

EditText editTxt;
TextView displayTxt;
String fromCurrency;
String toCurrency;

    public ConvertTask(EditText editTxt, String fromCurrency, String toCurrency,TextView displayTxt) {
        this.displayTxt = displayTxt;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.editTxt = editTxt;
    }


    protected String doInBackground(String... strings) {
        String apiKey = "DIMTJEJLW41A1SW4";
        int timeout = 3000;
        double conversionRate = 0;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        ForeignExchange foreignExchange = new ForeignExchange(apiConnector);

        try {
            CurrencyExchange currencyExchange = foreignExchange.currencyExchangeRate(fromCurrency, toCurrency);
            CurrencyExchangeData currencyExchangeData = currencyExchange.getData();

            conversionRate = currencyExchangeData.getExchangeRate();

        } catch (AlphaVantageException e) {
            System.out.println(e);
            System.out.println("something went wrong");
        }


        onPostExecute(conversionRate);

        return null;
    }

    protected  void onPostExecute(double result){

        try {
            double number = Double.parseDouble(editTxt.getText().toString());
            double convertedNumber = number * result;
            String convertedNumberFinal = String.valueOf(convertedNumber);
            displayTxt.setText(convertedNumberFinal);
        }
        catch (Exception e){
            System.out.println(e);
            double convertedNumber = 0;
            displayTxt.setText(String.valueOf(convertedNumber));
        }
    }


}
