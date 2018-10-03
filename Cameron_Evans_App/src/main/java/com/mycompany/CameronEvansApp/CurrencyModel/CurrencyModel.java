package com.mycompany.CameronEvansApp.CurrencyModel;

public class CurrencyModel {
    public String symbol;
    public String price;
    public String timestamp;


    //Model of the currencyModel cards
    public CurrencyModel(String symbol) {

    }

    public CurrencyModel(String symbol, String rate, String timestamp) {
        this.symbol = symbol;
        this.price = rate;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}