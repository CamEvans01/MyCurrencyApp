package com.mycompany.CameronEvansApp.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mycompany.CameronEvansApp.R;



public class CurrencyView extends RecyclerView.ViewHolder{
public TextView symbol, price, timestamp;

//constructor
public CurrencyView(@NonNull View itemView) {
        super(itemView);


        //assigning widget items to their UI id
        symbol = (TextView) (itemView).findViewById(R.id.fxSymbol);
        price = (TextView) (itemView).findViewById(R.id.fx_rate);
        timestamp = (TextView) (itemView).findViewById(R.id.fx_timestamp);


        }
}


