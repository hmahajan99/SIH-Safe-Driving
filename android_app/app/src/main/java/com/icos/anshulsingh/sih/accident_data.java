package com.icos.anshulsingh.sih;

public class accident_data {
    Double lat,lon,acc_val;

    public accident_data(){


    }
    public accident_data(String lat, String lon, String acc_val) {
        this.lat = Double.valueOf(lat);
        this.lon = Double.valueOf(lon);
        this.acc_val = Double.valueOf(acc_val);
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getAcc_val() {
        return acc_val;
    }

    public void setAcc_val(Double acc_val) {
        this.acc_val = acc_val;
    }
}
