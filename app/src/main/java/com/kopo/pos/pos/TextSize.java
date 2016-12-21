package com.kopo.pos.pos;

public class TextSize {
    public static void getTextSize(float _scale) {
        float dip_b, dip_m, dip_s;
        float scale;
        scale = _scale;
        dip_b = 13.0f;
        MainActivity.pixel_b = (int) (dip_b * scale + 0.5f);
        dip_m = 10.0f;
        MainActivity.pixel_m = (int) (dip_m * scale + 0.5f);
        dip_s = 6.0f;
        MainActivity.pixel_s = (int) (dip_s * scale + 0.5f);
    }//setTextSize
}//class