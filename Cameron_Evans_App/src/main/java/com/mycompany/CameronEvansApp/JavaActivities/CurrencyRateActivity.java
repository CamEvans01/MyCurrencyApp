package com.mycompany.CameronEvansApp.JavaActivities;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.mycompany.CameronEvansApp.CurrencyModel.CurrencyModel;


import com.mycompany.CameronEvansApp.R;
import com.mycompany.CameronEvansApp.Utils.BottomNavigationViewHelper;

import com.mycompany.CameronEvansApp.Adapter.CurrencyAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrencyRateActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private Context mContext = CurrencyRateActivity.this;

    private static final int ACTIVITY_NUM = 0;

    //Arraylist of CurrencyModels
    List<CurrencyModel> items = new ArrayList<>();
    CurrencyAdapter adapter;
    RecyclerView recyclerView;

    OkHttpClient client;
    Request request;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_activity);
        Log.d(TAG, "onCreate: Starting");
        setupBottomNavigationView();

        swipeRefreshLayout = findViewById(R.id.rootLayout);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirst10Rates(0);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                loadFirst10Rates(0);
                setupAdapter();
            }
        });

        recyclerView = findViewById(R.id.fxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        setupAdapter();

        setupBottomNavigationView();


        }

    private void setupAdapter() {
        adapter = new CurrencyAdapter(recyclerView, CurrencyRateActivity.this,items);
        recyclerView.setAdapter(adapter);
    }


    private void loadFirst10Rates(int i) {
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format(
                "https://forex.1forge.com/1.0.3/quotes?pairs=EURUSD,USDJPY,GBPUSD,USDCHF,AUDUSD,USDCAD,NZDUSD,USDMXN&api_key=61IESGm7bFWcBi4koragWqKBeC9NJ9P6"))
                .build();
        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String body = response.body().string();
                        Gson gson = new Gson();

                        final List<CurrencyModel> newItems = gson.fromJson(body,new TypeToken<List<CurrencyModel>>(){}.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                adapter.updateData(newItems);
                            }
                        });

                    }

                });
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing((false));
        }
    }


    // BottomNavigationViewEX Setup

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up");
        //getting BottomNavViewBar from currency_activity
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.BottomNavViewBar);

        //sending bottomNavigationViewEx to BottomNavigationViewHelper in Utils to setup bottom menu
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        //enabling navigation using enableNavigation method. Sending context, activity, and bottomnavigationviewex
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);

        menuItem.setChecked(true);


    }


}
