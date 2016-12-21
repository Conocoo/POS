package com.kopo.pos.pos;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_PopupPrice extends Activity implements View.OnClickListener {
    //미사용
    TextView mtxt_view_meat, mtxt_view_rice, mtxt_view_alcohol;
    int mcounter_meat = 0;
    int mcounter_rice = 0;
    int mcounter_alcohol = 0;

    ArrayList<String> isList = new ArrayList<>();

    Button mbtn_ubtn_meat, mbtn_dbtn_meat;
    Button mbtn_ubtn_rice, mbtn_dbtn_rice;
    Button mbtn_ubtn_alcohol, mbtn_dbtn_alcohol;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popupprice);
        setdeclare();
        setlistener();
    }

    private void setlistener() {
        mbtn_dbtn_meat.setOnClickListener(this);
        mbtn_ubtn_meat.setOnClickListener(this);
        mbtn_dbtn_rice.setOnClickListener(this);
        mbtn_ubtn_rice.setOnClickListener(this);
        mbtn_dbtn_alcohol.setOnClickListener(this);
        mbtn_ubtn_alcohol.setOnClickListener(this);
    }

    private void setdeclare() {
        mtxt_view_meat = (TextView) findViewById(R.id.txt_view_meat);
        mtxt_view_rice = (TextView) findViewById(R.id.txt_view_rice);
        mtxt_view_alcohol = (TextView) findViewById(R.id.txt_view_alcohol);
        mbtn_ubtn_meat = (Button) findViewById(R.id.btn_Ucounter_meat);
        mbtn_dbtn_meat = (Button) findViewById(R.id.btn_Dcounter_meat);
        mbtn_ubtn_rice = (Button) findViewById(R.id.btn_Ucounter_rice);
        mbtn_dbtn_rice = (Button) findViewById(R.id.btn_Dcounter_rice);
        mbtn_ubtn_alcohol = (Button) findViewById(R.id.btn_Ucounter_alcohol);
        mbtn_dbtn_alcohol = (Button) findViewById(R.id.btn_Dcounter_alcohol);
    }

    public void btn_finish(View view) {
        Intent intent = new Intent();
        isList.add("고기,6000," + mcounter_meat);
        isList.add("밥,1500," + mcounter_rice);
        isList.add("술,4000," + mcounter_alcohol);
        String szValue = "";
        for (int i = 0; i < isList.size(); i++) {
            szValue += isList.get(i) + ":";
        }
        intent.putExtra("meat_list", szValue);
        intent.putExtra("pay_ok", "pay_no");
        intent.putExtra("finish_ok", "finish_ok");
        intent.putExtra("continue_ok", "continue_no");
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void btn_Cancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        this.finish();
    }

    public void btn_pay(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra("pay_ok", "pay_ok");
        intent.putExtra("finish_ok", "finish_no");
        intent.putExtra("continue_ok", "continue_no");
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Dcounter_meat:
                if (mcounter_meat == 0) {
                    return;
                }
                mcounter_meat = mcounter_meat - 1;
                break;
            case R.id.btn_Ucounter_meat:
                mcounter_meat = mcounter_meat + 1;
                break;
            case R.id.btn_Dcounter_rice:
                if (mcounter_rice == 0) {
                    return;
                }
                mcounter_rice = mcounter_rice - 1;
                break;
            case R.id.btn_Ucounter_rice:
                mcounter_rice = mcounter_rice + 1;
                break;
            case R.id.btn_Dcounter_alcohol:
                if (mcounter_alcohol == 0) {
                    return;
                }
                mcounter_alcohol = mcounter_alcohol - 1;
                break;
            case R.id.btn_Ucounter_alcohol:
                mcounter_alcohol = mcounter_alcohol + 1;
                break;
        }
        mtxt_view_meat.setText(Integer.toString(mcounter_meat));
        mtxt_view_rice.setText(Integer.toString(mcounter_rice));
        mtxt_view_alcohol.setText(Integer.toString(mcounter_alcohol));
    }

}