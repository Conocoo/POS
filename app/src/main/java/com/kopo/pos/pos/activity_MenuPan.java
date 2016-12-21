package com.kopo.pos.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Set;

public class activity_MenuPan extends Activity implements View.OnClickListener {
    public static int Selected_Table;
    BizSaveTable Table;
    ListView lv_menu;
    Button btn_ac_menupan_ok, btn_ac_menupan_clear, btn_ac_menupan_cancle;
    Intent intent;
    adapter_MenuPan adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupan);
        init();
        setlistener();
    }//onCreate

    private void init() {
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        btn_ac_menupan_ok = (Button) findViewById(R.id.btn_ac_menupan_ok);
        btn_ac_menupan_clear = (Button) findViewById(R.id.btn_ac_menupan_clear);
        btn_ac_menupan_cancle = (Button) findViewById(R.id.btn_ac_menupan_cancle);
        intent = getIntent();
        Selected_Table = intent.getIntExtra("selectedTable", 0);
        Table = new BizSaveTable();
        adapter = new adapter_MenuPan(this);
        lv_menu.setAdapter(adapter);
    }//init

    private void setlistener() {
        btn_ac_menupan_clear.setOnClickListener(this);
        btn_ac_menupan_ok.setOnClickListener(this);
        btn_ac_menupan_cancle.setOnClickListener(this);
    }//setlistener

    public void btn_ad_menupan_setup(View view) {
        /**
         * Adapter 메뉴판에서 각 메뉴 "설정" 클릭시 이동
         */
        //btn_setup.setTag(lst_name.get(position)+":"+lst_price.get(position));
        String name = String.valueOf(view.getTag());
        Intent intent = new Intent(view.getContext(), activity_MenuPan_set_quantity.class);
        //intent.putExtra("구분자", "값");
        intent.putExtra("Menu", name);
        startActivityForResult(intent, view.getId());
    }//btn_ad_menupan_setup

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Intent에서 반환값으로 메뉴판 가격, 수량 수정
         * requestCode  : 구분자
         * resultCode   : RESULT_OK와 같은 성공, 실패 구분 값
         */
        super.onActivityResult(requestCode, resultCode, data);
        String foodname = data.getStringExtra("name");
        String foodquantity = data.getStringExtra("quantity");
        Log.d("LOG", "LOG : foodname =" + foodname);
        Log.d("LOG", "LOG : foodquantity =" + foodquantity);
        switch (resultCode) {
            case RESULT_OK:
                if (Integer.valueOf(foodquantity) == 0) {
                    Log.d("LOG", "LOG : RESULT_OK 통과 했으나, 수량 0개 기각");
                    break;
                }//if
                Table.puttable(Selected_Table, foodname, foodquantity, this);
                Log.d("LOG", "LOG : RESULT_OK 통과");
                break;
            case RESULT_CANCELED:
                Log.d("LOG", "LOG : RESULT_CANCELED 통과");
                break;
            default:
                break;
        }//switch
        adapter.updatelist();
    }//onActivityResult

    @Override
    public void onClick(View v) {

        //기각
        HashMap<String, Integer> tbl = null;
        switch (Selected_Table) {
            case R.id.table_1:
                tbl = BizSaveTable.SaveTable1;
                break;
            case R.id.table_2:
                tbl = BizSaveTable.SaveTable2;
                break;
            case R.id.table_3:
                tbl = BizSaveTable.SaveTable3;
                break;
            case R.id.table_4:
                tbl = BizSaveTable.SaveTable4;
                break;
            default:
                break;
        }//switch
        Set<String> keys = tbl.keySet();

        int settext = 0;

        for (String key : keys) {
            settext += tbl.get(key);
        }//for

        if (settext == 0) {
            setResult(MainActivity.RESULT_CANCELED, intent);
            finish();
        }//if


        switch (v.getId()) {
            case R.id.btn_ac_menupan_cancle:
                setResult(4, intent);
                finish();
                break;
            case R.id.btn_ac_menupan_ok:
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_ac_menupan_clear:
                //해당 테이블 번호가 넘어가야함
                BizSaveTable.cleartable(Selected_Table);
                setResult(MainActivity.RESULT_CLEAR, intent);
                finish();
                break;
            default:
                break;
        }//switch
    }//onClick
}//activity