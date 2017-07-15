package com.example.jeevan.swiggy.models;

import com.example.jeevan.swiggy.R;

/**
 * Created by jeevan on 7/15/17.
 */

public class Occasion {
    long id;
    String occasion;
    int drawable;
    String desc;
    String[] terms;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable() {
        if (occasion == null) return;
        if (occasion.equalsIgnoreCase("Hangout")) drawable = R.drawable.occasion_hangout;
        if (occasion.equalsIgnoreCase("Regulars")) drawable = R.drawable.occasion_chilling;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getTerms() {
        return terms;
    }

    public void setTerms(String[] terms) {
        this.terms = terms;
    }
}
