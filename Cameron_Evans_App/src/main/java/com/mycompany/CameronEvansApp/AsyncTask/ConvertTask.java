package com.mycompany.CameronEvansApp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycompany.CameronEvansApp.POJOs.ConvertPojo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConvertTask extends AsyncTask<String,String,String> {
    OkHttpClient client;
    Request request;
    EditText editTxt;
    TextView displayTxt;
    String fromCurrency;
    String toCurrency;
    Context context;
    String number;

    public ConvertTask(EditText editTxt, String fromCurrency, String toCurrency, TextView displayTxt, Context context,String number) {
        this.displayTxt = displayTxt;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.editTxt = editTxt;
        this.context = context;
        this.number = number;
    }


    //putting string values into url, getting json result and creating ConvertPOJO to get values
    protected String doInBackground(String... strings) {

        client = new OkHttpClient();
        request = new Request.Builder().url(String.format(
                "https://forex.1forge.com/1.0.3/convert?from="+fromCurrency+"&to="+toCurrency+"&quantity="+number+"&api_key=61IESGm7bFWcBi4koragWqKBeC9NJ9P6"))
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String body = response.body().string();

                        ConvertPojo cp = new Gson().fromJson(body,ConvertPojo.class);

                        onPostExecute(cp);
                    }

                });

        return null;

    }

    protected void onPostExecute(ConvertPojo cp) {
        try {

                String convertedNumberFinal = String.format("%.2f", cp.getValue())+" "+toCurrency;
                displayTxt.setText(convertedNumberFinal);

            } catch (Exception e) {
                System.out.println(e);

            }
        }


    }


