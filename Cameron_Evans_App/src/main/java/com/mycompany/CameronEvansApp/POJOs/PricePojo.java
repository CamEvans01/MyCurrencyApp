package com.mycompany.CameronEvansApp.POJOs;

import com.mycompany.CameronEvansApp.Model.Price;

public class PricePojo {

        private int timestamp;

        private Price price = new Price();

        public void setTimestamp(int timestamp){
        this.timestamp = timestamp;
        }
        public int getTimestamp(){
        return this.timestamp;
        }
        public void setPrice(Price price){
        this.price = price;
        }
        public Price getPrice(){
        return this.price;
        }

}
