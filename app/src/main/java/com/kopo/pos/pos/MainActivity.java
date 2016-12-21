package com.kopo.pos.pos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final int RESULT_CLEAR = 3;
    static final String SERVER_ADDRESS = "http://192.168.0.9";
    public static int MAX_TABLE_NUMBER = 4;


    public static int pixel_b, pixel_m, pixel_s;
    public static int requestcode_check_reservation = 9999;
    //Table
    public static Button Table_1, Table_2, Table_3, Table_4;
    static String[] TableTag_1 = new String[4];
    static String[] TableTag_2 = new String[4];
    static String[] TableTag_3 = new String[4];
    static String[] TableTag_4 = new String[4];
    final int EAT_OR_EMPTY = 0;
    final int DB_IDX = 1;
    final int TOTAL_PRICE = 2;
    final int FLAG = 3;

    BizConnectPHP cPHP = new BizConnectPHP();
    Context context;
    //날짜
    int mYear, mMonth, mDate, mHour, mMin;
    /////화면 구성/////
    LinearLayout LL_main_main;
    DisplayMetrics dm;
    float scale;
    /////텍스트뷰들
    TextView tv_main_main;
    TextView tv_main_today, Day_now, tv_main_manager, tv_main_machine;
    TextView tv_main_set_menu, tv_main_table_info, tv_main_table_all, tv_main_table_all_num, tv_main_table_eat, now_table, tv_main_reservation;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        context = this;
        setdate();
        setid();
        setTag();
        setlistener();
        getTextSize();
        setTextSize();
        LinearLayout.LayoutParams lpLinearLayout_Property = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        LL_main_main.setLayoutParams(lpLinearLayout_Property);

        Day_now.setText(String.valueOf(mYear) + " - " + String.valueOf(mMonth) + " - " + String.valueOf(mDate));
        Table_1.setBackgroundColor(Color.rgb(94, 94, 94));
        Table_2.setBackgroundColor(Color.rgb(94, 94, 94));
        Table_3.setBackgroundColor(Color.rgb(94, 94, 94));
        Table_4.setBackgroundColor(Color.rgb(94, 94, 94));
    }//onCreate


    private void setdate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDate = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR);
        mMin = c.get(Calendar.MINUTE);
    }//setdate

    private void setid() {
        dm = new DisplayMetrics();
        LL_main_main = (LinearLayout) findViewById(R.id.LL_main_main);

        Table_1 = (Button) findViewById(R.id.table_1);
        Table_2 = (Button) findViewById(R.id.table_2);
        Table_3 = (Button) findViewById(R.id.table_3);
        Table_4 = (Button) findViewById(R.id.table_4);

        tv_main_main = (TextView) findViewById(R.id.tv_main_main);

        tv_main_today = (TextView) findViewById(R.id.tv_main_today);
        Day_now = (TextView) findViewById(R.id.Day_now);
        tv_main_manager = (TextView) findViewById(R.id.tv_main_manager);
        tv_main_machine = (TextView) findViewById(R.id.tv_main_machine);

        tv_main_set_menu = (TextView) findViewById(R.id.tv_main_set_menu);
        tv_main_table_info = (TextView) findViewById(R.id.tv_main_table_info);
        tv_main_table_all = (TextView) findViewById(R.id.tv_main_table_all);
        tv_main_table_all_num = (TextView) findViewById(R.id.tv_main_table_all_num);
        tv_main_table_eat = (TextView) findViewById(R.id.tv_main_table_eat);
        now_table = (TextView) findViewById(R.id.now_table);
        tv_main_reservation = (TextView) findViewById(R.id.tv_main_reservation);
    }//setid

    private void setlistener() {
        Table_1.setOnClickListener(this);
        Table_2.setOnClickListener(this);
        Table_3.setOnClickListener(this);
        Table_4.setOnClickListener(this);
    }//setlistener

    private void getTextSize() {
        scale = getResources().getDisplayMetrics().density;
        TextSize.getTextSize(scale);
    }//setTextSize

    private void setTextSize() {
        //BIG
        tv_main_main.setTextSize(pixel_b);
        //MID
        tv_main_today.setTextSize(pixel_m);
        Day_now.setTextSize(pixel_m);
        tv_main_manager.setTextSize(pixel_m);
        tv_main_machine.setTextSize(pixel_m);
        //SMALL
        tv_main_set_menu.setTextSize(pixel_s);
        tv_main_table_info.setTextSize(pixel_s);
        tv_main_table_all.setTextSize(pixel_s);
        tv_main_table_all_num.setTextSize(pixel_s);
        tv_main_table_eat.setTextSize(pixel_s);
        now_table.setTextSize(pixel_s);
        Table_1.setTextSize(pixel_m);
        Table_2.setTextSize(pixel_m);
        Table_3.setTextSize(pixel_m);
        Table_4.setTextSize(pixel_m);
        tv_main_reservation.setTextSize(pixel_s);
    }//setTextSize

    private void setTag() {
        /**
         * 테이블 기본 TAG 설정
         * INDEX 0 = 식사중 판별 (0 = 공석, 1 = 식사중)
         * INDEX 1 = 식사중일 경우 서버 DB IDX
         * INDEX 2 = 총 금액
         * INDEX 3 = 서버에 전송할 Flag
         * new String[]{"0", "-1", "0", "0"};
         */
        TableTag_1 = new String[]{"0", "-1", "0", "0"};
        TableTag_2 = new String[]{"0", "-1", "0", "0"};
        TableTag_3 = new String[]{"0", "-1", "0", "0"};
        TableTag_4 = new String[]{"0", "-1", "0", "0"};
        Table_1.setTag(TableTag_1);
        Table_2.setTag(TableTag_2);
        Table_3.setTag(TableTag_3);
        Table_4.setTag(TableTag_4);
    }//setTag

    @Override
    public void onClick(View v) {
        /**
         * 테이블에만 달려있음
         * 누르면 테이블 ID값과 함께 인텐트
         */
        Intent intent = new Intent(this, activity_MenuPan.class);
        intent.putExtra("selectedTable", v.getId());
        startActivityForResult(intent, v.getId());
    }//onClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * Intent
         * requestCode  : 구분자
         * resultCode   : RESULT_OK와 같은 성공, 실패 구분 값
         */
        super.onActivityResult(requestCode, resultCode, data);
        int totalprice;
        if (resultCode == RESULT_CANCELED) {
            /**
             * 취소 버튼 클릭시 바로 작동
             * onActivityResult문 break;
             */
            return;
        }//if
        if (requestCode == requestcode_check_reservation) {
            String seat = data.getStringExtra("seat");
            int table = Integer.parseInt(seat) % 10000;
            int idx = Integer.parseInt(seat) / 10000;
            switch (table) {
                case 1:
                    TableTag_1[DB_IDX] = String.valueOf(idx);
                    TableTag_1[FLAG] = "2";
                    TableTag_1[EAT_OR_EMPTY] = "1";
                    Table_1.setTag(TableTag_1);
                    Table_1.setText("예약자 착석\n주문 대기");
                    Table_1.setBackgroundColor(Color.rgb(200, 200, 200));
                    Log.i("LOG", "LOG : idx = " + idx);
                    cPHP.BizFirstConnect(TableTag_1[DB_IDX], 1, TableTag_1[FLAG], TableTag_1[TOTAL_PRICE]);
                    break;
                case 2:
                    TableTag_2[DB_IDX] = String.valueOf(idx);
                    TableTag_2[FLAG] = "2";
                    TableTag_2[EAT_OR_EMPTY] = "1";
                    Table_2.setTag(TableTag_2);
                    Table_2.setText("예약자 착석\n주문 대기");
                    Table_2.setBackgroundColor(Color.rgb(200, 200, 200));
                    Log.i("LOG", "LOG : idx = " + idx);
                    cPHP.BizFirstConnect(TableTag_2[DB_IDX], 2, TableTag_2[FLAG], TableTag_2[TOTAL_PRICE]);
                    break;
                case 3:
                    TableTag_3[DB_IDX] = String.valueOf(idx);
                    TableTag_3[FLAG] = "2";
                    TableTag_3[EAT_OR_EMPTY] = "1";
                    Table_3.setTag(TableTag_3);
                    Table_3.setText("예약자 착석\n주문 대기");
                    Table_3.setBackgroundColor(Color.rgb(200, 200, 200));
                    Log.i("LOG", "LOG : idx = " + idx);
                    cPHP.BizFirstConnect(TableTag_3[DB_IDX], 3, TableTag_3[FLAG], TableTag_3[TOTAL_PRICE]);
                    break;
                case 4:
                    TableTag_4[DB_IDX] = String.valueOf(idx);
                    TableTag_4[FLAG] = "2";
                    TableTag_4[EAT_OR_EMPTY] = "1";
                    Table_4.setTag(TableTag_4);
                    Table_4.setText("예약자 착석\n주문 대기");
                    Table_4.setBackgroundColor(Color.rgb(200, 200, 200));
                    Log.i("LOG", "LOG : idx = " + idx);
                    cPHP.BizFirstConnect(TableTag_4[DB_IDX], 4, TableTag_4[FLAG], TableTag_4[TOTAL_PRICE]);
                    break;
            }//switch
            return;
        }//requestCode가 check_reservation에서 넘어올때
        switch (requestCode) {
            case R.id.table_1:
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * Table_1
                         * 주문
                         */
                        //주문 메뉴 표기
                        Set<String> keys = BizSaveTable.SaveTable1.keySet();
                        String settext = "";
                        Table_1.setText("");
                        Log.d("tag", "LOG : keys" + String.valueOf(keys.size()));
                        int icnt = 0;
                        for (String key : keys) {
                            settext += key;
                            settext += " : ";
                            settext += String.valueOf(BizSaveTable.SaveTable1.get(key));
                            if (icnt < keys.size() - 1) {
                                settext += "\n";
                            }//if
                            icnt++;
                        }//for
                        //총 금액 표기
                        settext += "\n";
                        totalprice = BizSaveTable.gettotalprice(this, R.id.table_1);
                        settext += totalprice;
                        settext += "원";
                        if (TableTag_1[EAT_OR_EMPTY] == "0") {
                            cPHP.BizFirstConnect(TableTag_1[DB_IDX], 1, TableTag_1[FLAG], TableTag_1[TOTAL_PRICE]);
                            //int i = Get_IDX.Get_IDX(1);
                            //Log.e("LOG", "idx=" + i);
                        }//if 만약 이사람이 예약자가 아닌, 첫사람이라면
                        TableTag_1[EAT_OR_EMPTY] = "1";
                        TableTag_1[FLAG] = "2";
                        TableTag_1[TOTAL_PRICE] = String.valueOf(totalprice);
                        cPHP.BizFirstConnect(TableTag_1[DB_IDX], 1, TableTag_1[FLAG], TableTag_1[TOTAL_PRICE]);
                        Table_1.setTag(TableTag_1);
                        Table_1.setText(settext);
                        Table_1.setBackgroundColor(Color.rgb(200, 200, 200));
                        break;
                    case RESULT_CLEAR:
                        /**
                         * Table_1
                         * 결재
                         */
                        Table_1.setText("");
                        TableTag_1[FLAG] = "3";
                        cPHP.BizFirstConnect(TableTag_1[DB_IDX], 1, TableTag_1[FLAG], TableTag_1[TOTAL_PRICE]);
                        totalprice = 0;
                        TableTag_1 = new String[]{"0", "-1", "0", "0"};
                        Table_1.setTag(TableTag_1);
                        Table_1.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    case 4:
                        /**
                         * 주문 취소
                         */
                        Table_1.setText("");
                        totalprice = 0;
                        TableTag_1 = new String[]{"0", "-1", "0", "0"};
                        Table_1.setTag(TableTag_1);
                        Table_1.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    default:
                        break;
                }//switch
                break;
            //Table_1
            case R.id.table_2:
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * Table_2
                         * 주문
                         */
                        //주문 메뉴 표기
                        Set<String> keys = BizSaveTable.SaveTable2.keySet();
                        String settext = "";
                        Table_2.setText("");
                        Log.d("tag", "LOG : keys" + String.valueOf(keys.size()));
                        int icnt = 0;
                        for (String key : keys) {
                            settext += key;
                            settext += " : ";
                            settext += String.valueOf(BizSaveTable.SaveTable2.get(key));
                            if (icnt < keys.size() - 1) {
                                settext += "\n";
                            }//if
                            icnt++;
                        }//for
                        //총 금액 표기
                        settext += "\n";
                        totalprice = BizSaveTable.gettotalprice(this, R.id.table_2);
                        settext += totalprice;
                        settext += "원";
                        TableTag_2[FLAG] = "2";
                        TableTag_2[TOTAL_PRICE] = String.valueOf(totalprice);
                        cPHP.BizFirstConnect(TableTag_2[DB_IDX], 2, TableTag_2[FLAG], TableTag_2[TOTAL_PRICE]);
                        Table_2.setTag(TableTag_2);
                        Table_2.setText(settext);
                        Table_2.setBackgroundColor(Color.rgb(200, 200, 200));
                        break;
                    case RESULT_CLEAR:
                        /**
                         * Table_2
                         * 결재
                         */
                        Table_2.setText("");
                        //결제 완료시 idx,flag,총금액 전송
                        TableTag_2[FLAG] = "3";
                        cPHP.BizFirstConnect(TableTag_2[DB_IDX], 2, TableTag_2[FLAG], TableTag_2[TOTAL_PRICE]);
                        totalprice = 0;
                        TableTag_2 = new String[]{"0", "-1", "0", "0"};
                        Table_2.setTag(TableTag_2);
                        Table_2.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    case 4:
                        /**
                         * 주문 취소
                         */
                        Table_2.setText("");
                        totalprice = 0;
                        TableTag_2 = new String[]{"0", "-1", "0", "0"};
                        Table_2.setTag(TableTag_2);
                        Table_2.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    default:
                        break;
                }//switch
                break;
            //Table_2
            case R.id.table_3:
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * table_3
                         * 주문
                         */
                        //주문 메뉴 표기
                        Set<String> keys = BizSaveTable.SaveTable3.keySet();
                        String settext = "";
                        Table_3.setText("");
                        Log.d("tag", "LOG : keys" + String.valueOf(keys.size()));
                        int icnt = 0;
                        for (String key : keys) {
                            settext += key;
                            settext += " : ";
                            settext += String.valueOf(BizSaveTable.SaveTable3.get(key));
                            if (icnt < keys.size() - 1) {
                                settext += "\n";
                            }//if
                            icnt++;
                        }//for
                        //총 금액 표기
                        settext += "\n";
                        totalprice = BizSaveTable.gettotalprice(this, R.id.table_3);
                        settext += totalprice;
                        settext += "원";
                        TableTag_3[FLAG] = "2";
                        TableTag_3[TOTAL_PRICE] = String.valueOf(totalprice);
                        cPHP.BizFirstConnect(TableTag_3[DB_IDX], 3, TableTag_3[FLAG], TableTag_3[TOTAL_PRICE]);
                        Table_3.setTag(TableTag_3);
                        Table_3.setText(settext);
                        Table_3.setBackgroundColor(Color.rgb(200, 200, 200));
                        break;
                    case RESULT_CLEAR:
                        /**
                         * table_3
                         * 결재
                         */
                        Table_3.setText("");
                        //결제 완료시 idx,flag,총금액 전송
                        TableTag_3[FLAG] = "3";
                        cPHP.BizFirstConnect(TableTag_3[DB_IDX], 3, TableTag_3[FLAG], TableTag_3[TOTAL_PRICE]);
                        totalprice = 0;
                        TableTag_3 = new String[]{"0", "-1", "0", "0"};
                        Table_3.setTag(TableTag_3);
                        Table_3.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    case 4:
                        /**
                         * 주문 취소
                         */
                        Table_3.setText("");
                        totalprice = 0;
                        TableTag_3 = new String[]{"0", "-1", "0", "0"};
                        Table_3.setTag(TableTag_3);
                        Table_3.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    default:
                        break;
                }//switch
                break;
            //table_3
            case R.id.table_4:
                switch (resultCode) {
                    case RESULT_OK:
                        /**
                         * table_4
                         * 주문
                         */
                        //주문 메뉴 표기
                        Set<String> keys = BizSaveTable.SaveTable4.keySet();
                        String settext = "";
                        Table_4.setText("");
                        Log.d("tag", "LOG : keys" + String.valueOf(keys.size()));
                        int icnt = 0;
                        for (String key : keys) {
                            settext += key;
                            settext += " : ";
                            settext += String.valueOf(BizSaveTable.SaveTable4.get(key));
                            if (icnt < keys.size() - 1) {
                                settext += "\n";
                            }//if
                            icnt++;
                        }//for
                        //총 금액 표기
                        settext += "\n";
                        totalprice = BizSaveTable.gettotalprice(this, R.id.table_4);
                        settext += totalprice;
                        settext += "원";
                        TableTag_4[FLAG] = "2";
                        TableTag_4[TOTAL_PRICE] = String.valueOf(totalprice);
                        cPHP.BizFirstConnect(TableTag_4[DB_IDX], 4, TableTag_4[FLAG], TableTag_4[TOTAL_PRICE]);
                        Table_4.setTag(TableTag_4);
                        Table_4.setText(settext);
                        Table_4.setBackgroundColor(Color.rgb(200, 200, 200));
                        break;
                    case RESULT_CLEAR:
                        /**
                         * table_4
                         * 결재
                         */
                        Table_4.setText("");
                        //결제 완료시 idx,flag,총금액 전송
                        TableTag_4[FLAG] = "3";
                        cPHP.BizFirstConnect(TableTag_4[DB_IDX], 4, TableTag_4[FLAG], TableTag_4[TOTAL_PRICE]);
                        totalprice = 0;
                        TableTag_4 = new String[]{"0", "-1", "0", "0"};
                        Table_4.setTag(TableTag_4);
                        Table_4.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    case 4:
                        /**
                         * 주문 취소
                         */
                        Table_4.setText("");
                        totalprice = 0;
                        TableTag_4 = new String[]{"0", "-1", "0", "0"};
                        Table_4.setTag(TableTag_4);
                        Table_4.setBackgroundColor(Color.rgb(94, 94, 94));
                        break;
                    default:
                        break;
                }//switch
                break;
            //table_4
            default:
                break;
        }//switch
        /**
         * 식사중 테이블 체크
         */
        int NOW_TABLE_NUMBER;
        String[] i = new String[4];
        String[] j;
        j = (String[]) Table_1.getTag();
        i[0] = j[0];
        j = (String[]) Table_2.getTag();
        i[1] = j[0];
        j = (String[]) Table_3.getTag();
        i[2] = j[0];
        j = (String[]) Table_4.getTag();
        i[3] = j[0];
        NOW_TABLE_NUMBER = Integer.parseInt(i[0]) + Integer.parseInt(i[1]) + Integer.parseInt(i[2]) + Integer.parseInt(i[3]);
        now_table.setText(String.valueOf(NOW_TABLE_NUMBER));
        cPHP.BizConnectPHP(NOW_TABLE_NUMBER, MAX_TABLE_NUMBER);


        switch (resultCode) {
            case RESULT_OK:
                /**
                 * 주문
                 */
                Toast.makeText(this, "주문 완료", Toast.LENGTH_SHORT).show();
                break;
            case RESULT_CLEAR:
                /**
                 * 결재
                 */
                Toast.makeText(this, "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                /**
                 * 주문 취소
                 */
                Toast.makeText(this, "결재가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }//switch


    }//onActivityResult

    public void btn_set_menu(View view) {
        Intent intent = new Intent(MainActivity.this, activity_SetMenu.class);
        startActivity(intent);
    }//btn_set_menu

    public void btn_mainactivity_help(View view) {
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        //해상도 구하는 방법
//        float widthDp = dm.widthPixels / dm.density;
//        String str_ScreenSize = "The Android Screen is: " + dm.widthPixels + " x " + dm.heightPixels + "\n" + "Width DP : " + widthDp;
//        Toast.makeText(this, str_ScreenSize, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "식도락 팀의 APP입니다.", Toast.LENGTH_SHORT).show();
    }//btn_mainactivity_help

    public void btn_main_finish(View view) {
        finish();
    }//btn_main_finish

    public void btn_main_check_respeople(View view) {
        // 서버가 없는 관계로 실행 불가.
        Toast.makeText(this, "서버가 없는 관계로 실행이 불가합니다.", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(MainActivity.this, activity_check_reservation.class);
        //startActivityForResult(intent, requestcode_check_reservation);
    }//btn_main_check_respeople

    public void btn_mainactivity_sell(View view) {
        //매출 현황
        Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show();
    }//btn_mainactivity_sell

    public void btn_mainactivity_who_make_it(View view) {
        startActivity(new Intent(this, who_make_it.class));
    }//btn_mainactivity_who_make_it

//        /*
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.table_1:
//                    if (Table_1.getTag() == "0") {
//                        table_selected = 0;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    } else if (Table_1.getTag() == "1") {
//                        table_selected = 0;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    }
//                    break;
//                case R.id.table_2:
//                    if (Table_2.getTag() == "0") {
//                        table_selected = 1;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    } else if (Table_2.getTag() == "1") {
//                        table_selected = 1;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    }
//                    break;
//                case R.id.table_3:
//                    if (Table_3.getTag() == "0") {
//                        table_selected = 2;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    } else if (Table_3.getTag() == "1") {
//                        table_selected = 2;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    }
//                    break;
//                case R.id.table_4:
//                    if (Table_4.getTag() == "0") {
//                        table_selected = 3;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    } else if (Table_4.getTag() == "1") {
//                        table_selected = 3;
//                        startActivityForResult(new Intent(this, activity_PopupPrice.class), REQUEST);
//                    }
//                    break;
//                default:
//                    break;
//            }//switch
//        }//onClick
//
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            //캔슬 누를시 바로 리턴-----------------
//            if (resultCode == RESULT_CANCELED) {
//                table_selected = -1;
//                return;
//            }
//            //계산 관련 변수 선언--------------------
//            String szValue = data.getStringExtra("meat_list");
//            String[] szData = {"", "", "", ""};
//            if (szValue != null) {
//                szData = szValue.split(":");
//            }
//            String szTemp = "";
//            String setText = "";
//            int pPrice = 0;
//            int pEa = 0;
//            String pCode = "";
//            int itotalPrice = 0;
//            //추가 결제시 창------------------------
//            String continue_ok = data.getStringExtra("continue_ok");
//            if (continue_ok.equals("continue_ok")) {
//                String[] addData = addValue[table_selected].split("/");
//                addValue[table_selected] = "";
//                setText = "";
//                for (int j = 1; j < addData.length; j++) {
//                    String[] addTmp = addData[j].split(":");
//                    String addCode = addTmp[0];
//                    szTemp = szData[j];
//                    int addPrice = Integer.parseInt(addTmp[1]);
//                    int addEa = Integer.parseInt(addTmp[2]);
//                    if (szTemp.length() > 0) {
//                        String[] isTemp = szTemp.split(",");
//                        if (Integer.parseInt(isTemp[2]) != 0) {
//                            pCode = isTemp[0];
//                            pPrice = Integer.parseInt(isTemp[1]);
//                            pEa = Integer.parseInt(isTemp[2]);
//                            if (pCode.equals(addCode)) {
//                                pEa += addEa;
//                            }
//                            int iPrice = pPrice * pEa;
//                            itotalPrice += iPrice;
//                        }//if
//                        addValue[table_selected] += pCode + ":" + String.valueOf(pPrice) + ":" + String.valueOf(pEa);
//                        setText += pCode + " " + String.valueOf(pEa) + "개";
//                        setText += "\n";
//                    }//if
//                    addValue[table_selected] += "/";
//                }//for
//                setText += "total : " + itotalPrice;
//            }//if
//            //결재 창-------------------------------
//            String payment = data.getStringExtra("pay_ok");
//            if (payment.equals("pay_ok")) {
//                Toast.makeText(getApplicationContext(), "완료", Toast.LENGTH_LONG).show();
//                switch (table_selected) {
//                    case 0:
//                        Table_1.setText("");
//                        Table_1.setTag("0");
//                        Table_1.setBackgroundColor(Color.rgb(94, 94, 94));
//                        addValue[table_selected] = "";
//                        break;
//                    case 1:
//                        Table_2.setText("");
//                        Table_2.setTag("0");
//                        Table_2.setBackgroundColor(Color.rgb(94, 94, 94));
//                        addValue[table_selected] = "";
//                        break;
//                    case 2:
//                        Table_3.setText("");
//                        Table_3.setTag("0");
//                        Table_3.setBackgroundColor(Color.rgb(94, 94, 94));
//                        addValue[table_selected] = "";
//                        break;
//                    case 3:
//                        Table_4.setText("");
//                        Table_4.setTag("0");
//                        Table_4.setBackgroundColor(Color.rgb(94, 94, 94));
//                        addValue[table_selected] = "";
//                        break;
//                    default:
//                        break;
//                }//switch
//                table_selected = -1;
//                return;
//            }//if
//            //--------------------------------------
//            //-----------계산식
//            String finish_ok = data.getStringExtra("finish_ok");
//            if (finish_ok.equals("finish_ok")) {
//                for (int i = 0; i < szData.length; i++) {
//                    szTemp = szData[i];
//                    if (szTemp.length() > 0) {
//                        String[] isTemp = szTemp.split(",");
//                        if (Integer.parseInt(isTemp[2]) != 0) {
//                            pCode = isTemp[0];
//                            pPrice = Integer.parseInt(isTemp[1]);
//                            pEa = Integer.parseInt(isTemp[2]);
//                            int iPrice = pPrice * pEa;
//                            itotalPrice += iPrice;
//                            addValue[table_selected] += pCode + ":" + String.valueOf(pPrice) + ":" + String.valueOf(pEa);
//                            setText += pCode + " " + String.valueOf(pEa) + "개";
//                            setText += "\n";
//                        }//if
//                        addValue[table_selected] += "/";
//                    }//if
//                }//for
//                // Toast.makeText(getApplicationContext(), "확인용", Toast.LENGTH_SHORT).show();
//                setText += "total : " + itotalPrice;
//            }
//            //선택 테이블 색상 변경 & 태그 변경 & 선택 메뉴 표기--------------------------------------
//            switch (table_selected) {
//                case 0:
//                    Table_1.setText(setText);
//                    Table_1.setTag("1");
//                    Table_1.setBackgroundColor(Color.rgb(200, 200, 200));
//                    break;
//                case 1:
//                    Table_2.setText(setText);
//                    Table_2.setTag("1");
//                    Table_2.setBackgroundColor(Color.rgb(200, 200, 200));
//                    break;
//                case 2:
//                    Table_3.setText(setText);
//                    Table_3.setTag("1");
//                    Table_3.setBackgroundColor(Color.rgb(200, 200, 200));
//                    break;
//                case 3:
//                    Table_4.setText(setText);
//                    Table_4.setTag("1");
//                    Table_4.setBackgroundColor(Color.rgb(200, 200, 200));
//                    break;
//                default:
//                    break;
//            }//switch
//            table_selected = -1;
//        }//onActivityResult
//        */

}//Main