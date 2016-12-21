package com.kopo.pos.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class activity_change_menu extends Activity {
    /**
     * activity_SetMenu에서 넘어옴
     * INTENT를 받아 IDX,메뉴,가격을 TEXTVIEW로 출력
     * 사용자는 EDITTEXT에
     **/
    TextView tv_change_name, tv_change_value;
    EditText et_change_name, et_change_value;
    Menu_DB menu_db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_menu);
        setid();
        tv_change_name.setText(intent.getStringExtra("Menu"));
        tv_change_value.setText(intent.getStringExtra("Price"));
    }//onCreate

    private void setid() {
        tv_change_name = (TextView) findViewById(R.id.tv_change_name);
        tv_change_value = (TextView) findViewById(R.id.tv_change_value);
        et_change_name = (EditText) findViewById(R.id.et_change_name);
        et_change_value = (EditText) findViewById(R.id.et_change_value);
        menu_db = new Menu_DB(this);
        intent = getIntent();
    }//setid

    public void btn_Cancel_change_menu(View view) {
        finish();
    }//btn_Cancel_change_menu


    public void btn_db_update_menulist(View view) {
        BizMenuList isMenu;
        String _Query = String.format("where idx = %s", intent.getStringExtra("idx"));
        isMenu = BizMenuList_manager.getMenuList_Select(_Query);
        isMenu.menu_name = intent.getStringExtra("Menu");
        BizMenuList_manager.setMenuList_Update(isMenu);
        Intent intent = new Intent(getApplicationContext(), activity_SetMenu.class);
        startActivity(intent);
        finish();
    }//btn_db_update_menulist

    public void btn_db_update_menu(View view) {
        menu_db.update_menu(
                Integer.parseInt(intent.getStringExtra("idx")),
                et_change_name.getText().toString(),
                et_change_value.getText().toString());
        Intent intent = new Intent(getApplicationContext(), activity_SetMenu.class);
        startActivity(intent);
        finish();
    }//btn_db_update_menu
}//activity_change_menu