package com.kopo.pos.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class activity_SetMenu extends Activity {
    adapter_SetMenu adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setmenu);
        setid();
        adapter = new adapter_SetMenu(this);
        lv.setAdapter(adapter);
    }//onCreate

    private void setid() {
        lv = (ListView) findViewById(R.id.list);
    }//setid

    public void btn_add_setup_menu(View view) {
        // +버튼 누르면 메뉴 리스트 1개 추가
        adapter.increse();
    }//btn_add_setup_menu

    public void btn_change_menu(View view) {
        /**
         * 수정버튼 클릭시 "수정" 버튼에 속한 태그
         * IDX : 메뉴명 : 가격
         * 값을 가져와 분리 후
         * activity_change_menu 쪽으로 이동
         **/
        String buffer = (String) view.getTag();
        String[] Data = {"","", ""};
        if (buffer != null) {
            Data = buffer.split(":");
        }//if
        Intent intent = new Intent(getApplicationContext(), activity_change_menu.class);
        intent.putExtra("idx",Data[0]);
        intent.putExtra("Menu",Data[1]);
        intent.putExtra("Price",Data[2]);
        startActivity(intent);
        finish();
    }//btn_change_menu

    public void btn_ac_setmenu_commit(View view) {
        finish();
    }
}//activity