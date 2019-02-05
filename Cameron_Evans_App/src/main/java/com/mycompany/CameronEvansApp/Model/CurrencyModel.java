package com.mycompany.CameronEvansApp.Model;

public class CurrencyModel {
    public String symbol;
    public float rate;

    //Model of cards in CurrencyRateActivity
    public CurrencyModel() {

    }

    public CurrencyModel(String symbol, float rate) {
        this.symbol = symbol;
        this.rate = rate;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getRate() {
        return rate;
    }


}