package com.example.jeevan.swiggy.models;

/**
 * Created by jeevan on 7/15/17.
 */

public class Ocassion {
    String ocassion;
    int drawable;
    String desc;

    public Ocassion(String ocassion, int drawable, String desc) {
        this.ocassion = ocassion;
        this.drawable = drawable;
        this.desc = desc;
    }

    public String getOcassion() {
        return ocassion;
    }

    public void setOcassion(String ocassion) {
        this.ocassion = ocassion;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
