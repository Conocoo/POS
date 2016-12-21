package com.kopo.pos.pos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_MenuPan_set_quantity extends Activity implements View.OnClickListener {
    /**
     * 메뉴판에서 각 메뉴 설정 버튼 클릭시 이동
     **/
    TextView tv_ac_quantity, tv_ac_menu_quantity_menuname;
    Button btn_ac_set_quantity;
    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupan_set_quantity);
        setid();
        setlistener();
        intent = getIntent();
        tv_ac_menu_quantity_menuname.setText(intent.getStringExtra("Menu"));
        tv_ac_quantity.setText("0");
    }//onCreate

    private void setlistener() {
        /**
         * 객체 Listener
         */
        btn_ac_set_quantity.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
    }//setlistener

    private void setid() {
        /**
         * JAVA & XML 객체 연동
         */
        tv_ac_quantity = (TextView) findViewById(R.id.tv_ac_quantity);
        tv_ac_menu_quantity_menuname = (TextView) findViewById(R.id.tv_ac_menu_quantity_menuname);
        btn_0 = (Button) findViewById(R.id.btn_ac_setquantity_0);
        btn_1 = (Button) findViewById(R.id.btn_ac_setquantity_1);
        btn_2 = (Button) findViewById(R.id.btn_ac_setquantity_2);
        btn_3 = (Button) findViewById(R.id.btn_ac_setquantity_3);
        btn_4 = (Button) findViewById(R.id.btn_ac_setquantity_4);
        btn_5 = (Button) findViewById(R.id.btn_ac_setquantity_5);
        btn_6 = (Button) findViewById(R.id.btn_ac_setquantity_6);
        btn_7 = (Button) findViewById(R.id.btn_ac_setquantity_7);
        btn_8 = (Button) findViewById(R.id.btn_ac_setquantity_8);
        btn_9 = (Button) findViewById(R.id.btn_ac_setquantity_9);
        btn_ac_set_quantity = (Button) findViewById(R.id.btn_ac_set_quantity);
    }//setid

    public void btn_ac_set_quantity_clear(View view) {
        /**
         * C클릭시 수량 0개로 초기화
         */
        tv_ac_quantity.setText("0");
    }//btn_ac_set_quantity_clear

    @Override
    public void onClick(View v) {
        int selected_btn = v.getId();
        switch (selected_btn) {
            case R.id.btn_ac_setquantity_0:
                if (btn_ac_set_quantity.getText() != null) {
                    tv_ac_quantity.append("0");
                }//if
                break;
            case R.id.btn_ac_setquantity_1:
                tv_ac_quantity.append("1");
                break;
            case R.id.btn_ac_setquantity_2:
                tv_ac_quantity.append("2");
                break;
            case R.id.btn_ac_setquantity_3:
                tv_ac_quantity.append("3");
                break;
            case R.id.btn_ac_setquantity_4:
                tv_ac_quantity.append("4");
                break;
            case R.id.btn_ac_setquantity_5:
                tv_ac_quantity.append("5");
                break;
            case R.id.btn_ac_setquantity_6:
                tv_ac_quantity.append("6");
                break;
            case R.id.btn_ac_setquantity_7:
                tv_ac_quantity.append("7");
                break;
            case R.id.btn_ac_setquantity_8:
                tv_ac_quantity.append("8");
                break;
            case R.id.btn_ac_setquantity_9:
                tv_ac_quantity.append("9");
                break;
            case R.id.btn_ac_set_quantity:
                String name = String.valueOf(tv_ac_menu_quantity_menuname.getText());
                String quantity = String.valueOf(tv_ac_quantity.getText());
                intent.putExtra("name", name);
                intent.putExtra("quantity", quantity);
                Log.d("LOG", "LOG : putExtra(name) = " + name);
                Log.d("LOG", "LOG : putExtra(quantity) = " + quantity);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }//Switch
    }//onClick

    public void result_canceled(View view) {
        setResult(RESULT_CANCELED, intent);
        finish();
    }//result_canceled
}//class