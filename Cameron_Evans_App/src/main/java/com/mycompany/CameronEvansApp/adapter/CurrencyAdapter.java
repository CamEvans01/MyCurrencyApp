package com.mycompany.CameronEvansApp.adapter;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycompany.CameronEvansApp.R;

import com.mycompany.CameronEvansApp.CurrencyModel.CurrencyModel;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity activity;

    //ArrayList to hold CurrencyModel objects
    List<CurrencyModel> items;

    int lastVisibleItem;

    //constructor
    public CurrencyAdapter(RecyclerView recyclerView, Activity activity, List<CurrencyModel> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    //abstract method
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rate_card_layout,parent,false);
        return new CurrencyView(view);

    }
    //abstract method
    //when Binding the content to the cards, format the text this way
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CurrencyModel item = items.get(position);
        CurrencyView holderItem = (CurrencyView)holder;

        Timestamp ts=new Timestamp(System.currentTimeMillis());
        Date date=new Date(ts.getTime());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String timestampDateString = df.format(date);

        double currencyNumber = Double.parseDouble(item.getPrice());
        String currencyRate = String.format("%,.3f",currencyNumber);

        holderItem.symbol.setText(item.getSymbol());
        holderItem.price.setText(currencyRate);
        holderItem.timestamp.setText(timestampDateString);

    }

    //abstract method
    //returns the number of currencyModels in the list
    @Override
    public int getItemCount() {
        return items.size();
    }


    //refreshes the data
    public void updateData(List<CurrencyModel> fx_models){
        this.items = fx_models;
        notifyDataSetChanged();
    }
}
