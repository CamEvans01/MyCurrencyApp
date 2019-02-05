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
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mycompany.CameronEvansApp.Adapter.CurrencyAdapter;
import com.mycompany.CameronEvansApp.Model.CurrencyModel;
import com.mycompany.CameronEvansApp.POJOs.PricePojo;
import com.mycompany.CameronEvansApp.R;
import com.mycompany.CameronEvansApp.Utils.BottomNavigationViewHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UnknownFormatConversionException;
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
                loadRates(0);
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                items.clear();
                loadRates(0);
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


    //method to retrieve rates from url
    private void loadRates(int i) throws UnknownFormatConversionException {
        try {
            client = new OkHttpClient();
            request = new Request.Builder().url(String.format(
                    "https://fxmarketapi.com/apilive?api_key=a2GKHmHO-kPcYrhdSHw-&currency=EURUSD,USDJPY,GBPUSD,USDCHF,AUDUSD,USDCAD,NZDUSD,USDMXN"))
                    .build();
            swipeRefreshLayout.setRefreshing(true);
            client.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {


                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String body = response.body().string();

                            //ArrayList to hold CurrencyModel objects
                            ArrayList<CurrencyModel> c1 = new ArrayList<>(8);


                            PricePojo p1 = new Gson().fromJson(body, PricePojo.class);

                            //Adding content to the models with PricePojo value as price
                            c1.add(0,new CurrencyModel("EURUSD",p1.getPrice().getEURUSD()));
                            c1.add(1,new CurrencyModel("USDJPY",p1.getPrice().getUSDJPY()));
                            c1.add(2,new CurrencyModel("GBPUSD",p1.getPrice().getGBPUSD()));
                            c1.add(3,new CurrencyModel("USDJPY",p1.getPrice().getUSDJPY()));
                            c1.add(4,new CurrencyModel("USDCHF",p1.getPrice().getUSDCHF()));
                            c1.add(5,new CurrencyModel("AUDUSD",p1.getPrice().getAUDUSD()));
                            c1.add(6,new CurrencyModel("NZDUSD",p1.getPrice().getNZDUSD()));
                            c1.add(7,new CurrencyModel("USDMXN",p1.getPrice().getUSDMXN()));



                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                adapter.updateData(c1);
                                }
                            });

                        }

                    });
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing((false));
            }
        } catch (UnknownFormatConversionException e) {
            throw new RuntimeException(e);
        }
    }


    // BottomNavigationViewEX Setup

    private void setupBottomNavigationView(){

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
