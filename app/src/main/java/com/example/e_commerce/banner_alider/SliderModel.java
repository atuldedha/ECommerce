package com.example.e_commerce.banner_alider;

public class SliderModel {

    String banneer;
    String backgroundColor;

    public SliderModel(String banneer, String backgroundColor) {
        this.banneer = banneer;
        this.backgroundColor = backgroundColor;
    }

    public String getBanneer() {
        return banneer;
    }

    public void setBanneer(String banneer) {
        this.banneer = banneer;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
