package com.android.bathhack.models;

import android.widget.ImageView;
import android.widget.TextView;

public class AcoladeModel {

    private String image;
    private String text;

    public AcoladeModel(String imageResourse, String text) {
        image = imageResourse;
        this.text = text;
    }

    public String getImage() { return this.image; }
    public String getText() { return this.text; }

}
