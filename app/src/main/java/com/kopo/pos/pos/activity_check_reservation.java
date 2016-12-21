package com.kopo.pos.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class activity_check_reservation extends Activity {
    adapter_check_reservation adapter;
    ListView listView;
    TextView tv_ac_idx, tv_ac_name, tv_ac_restime, tv_ac_resseat, tv_ac_respeople, tv_ac_time, tv_ac_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reservation);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }//네트워크 익셉션 방지
        setid();
        adapter = new adapter_check_reservation(this);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        setTextSize();
    }//onCreate

    private void setid() {
        tv_ac_idx = (TextView) findViewById(R.id.tv_ac_idx);
        tv_ac_name = (TextView) findViewById(R.id.tv_ac_name);
        tv_ac_restime = (TextView) findViewById(R.id.tv_ac_restime);
        tv_ac_resseat = (TextView) findViewById(R.id.tv_ac_resseat);
        tv_ac_respeople = (TextView) findViewById(R.id.tv_ac_respeople);
        tv_ac_time = (TextView) findViewById(R.id.tv_ac_time);
        tv_ac_delete = (TextView) findViewById(R.id.tv_ac_delete);
    }//setid

    private void setTextSize() {
        tv_ac_idx.setTextSize(MainActivity.pixel_m);
        tv_ac_name.setTextSize(MainActivity.pixel_m);
        tv_ac_restime.setTextSize(MainActivity.pixel_m);
        tv_ac_resseat.setTextSize(MainActivity.pixel_m);
        tv_ac_respeople.setTextSize(MainActivity.pixel_m);
        tv_ac_time.setTextSize(MainActivity.pixel_m);
        tv_ac_delete.setTextSize(MainActivity.pixel_m);
    }//setTextSize

    public void btn_ac_delete_respeople(View view) {
        BizConnectPHP cPHP = new BizConnectPHP();
        int DB_idx = (Integer) view.getTag() % 10000;
        int List_idx = (Integer) view.getTag() / 10000;
        cPHP.BizResDelete(DB_idx);
        adapter.Change_List(List_idx);
    }//btn_ac_delete_respeople

    public void btn_res_finish(View view) {
        finish();
    }//btn_res_finish

    public void btn_ac_seatdown(View view) {
        Intent intent = new Intent();
        intent.putExtra("seat", String.valueOf(view.getTag()));
        setResult(RESULT_OK, intent);
        finish();
    }//btn_ac_seatdown

}//class