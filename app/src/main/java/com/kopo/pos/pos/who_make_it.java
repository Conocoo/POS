package com.kopo.pos.pos;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class who_make_it extends Activity {
    TextView t_1, t_2, t_3, t_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_make_it);
        t_1 = (TextView) findViewById(R.id.t_1);
        t_2 = (TextView) findViewById(R.id.t_2);
        t_3 = (TextView) findViewById(R.id.t_3);
        t_4 = (TextView) findViewById(R.id.t_4);

        t_1.setTextSize(MainActivity.pixel_b);
        t_2.setTextSize(MainActivity.pixel_m);
        t_3.setTextSize(MainActivity.pixel_m);
        t_4.setTextSize(MainActivity.pixel_m);

    }//onCreate
}//class
