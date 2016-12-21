package com.kopo.pos.pos;

import android.util.Log;

import java.net.URL;
import java.net.URLEncoder;


public class BizConnectPHP {
    /**
     * PHP, 즉 서버에 테이블 저장을 위한 클래스
     */
    private final String SERVER_PAGE = "/table_php_yk/showtable_insert.php?";
    private final String SERVER_RES_DELETE = "/Myeong_dong/Korea_food/Perfect/reserve/reservation_pos_delete.php?";
    private final String SERVER_RES_UPDATE = "/Myeong_dong/Korea_food/Perfect/reserve/reservation_pos_update.php?";

    String rasname;
    String nowtablenumber;
    String totaltablenumber;
    String idx;
    String totalprice;

    public void BizConnectPHP(int _nowtable, int _totaltable) {
        rasname = "rasss";
        nowtablenumber = String.valueOf(_nowtable);
        totaltablenumber = String.valueOf(_totaltable);
        Thread thread_update_table_number = null;
        thread_update_table_number = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("LOG", "try문 진입");
                    URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_PAGE + "rasname=" + URLEncoder.encode(rasname, "UTF-8") + "&nowtable=" + URLEncoder.encode(nowtablenumber, "UTF-8") + "&totaltable=" + URLEncoder.encode(totaltablenumber, "UTF-8"));
                    //변수값을 UTF-8로 인코딩하기 위해 URLEncoder를 이용하여 인코딩함
                    Log.e("LOG", "rasname : " + rasname);
                    Log.e("LOG", "nowtablenumber : " + nowtablenumber);
                    Log.e("LOG", "totaltablenumber : " + totaltablenumber);
                    url.openStream(); //웹서버의 DB에 입력
                } catch (Exception e) {
                    e.printStackTrace();
                }//try&catch
            }//run
        });//thread_update_table_number
        thread_update_table_number.start();
    }//BizConnectPHP

    public void BizResDelete(int _idx) {
        /**
         * 예약자를 위한 쓰레드
         */
        idx = String.valueOf(_idx);
        Thread thread_delete = null;
        thread_delete = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_RES_DELETE + "idx=" + URLEncoder.encode(idx, "UTF-8"));
                    url.openStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }//try&catch
            }//run
        });//thread_delete
        thread_delete.start();
    }//BizResDelete

    public void BizFirstConnect(final String DB_IDX, final int _TABLENUM, final String FLAG, final String TOTALPRICE) {
        /**
         * 첫 착석
         * Idx, Table, Flag
         * -Idx 가 -1일시 insert
         * cPHP.BizFirstConnect(TableTag_1[DB_IDX], 1, TableTag_1[FLAG]);
         */
        Thread FirstConnect = null;
        FirstConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_RES_UPDATE + "idx=" + URLEncoder.encode(DB_IDX, "UTF-8") + "&seat=" + _TABLENUM + "&flag=" + URLEncoder.encode(FLAG, "UTF-8") + "&price=" + URLEncoder.encode(TOTALPRICE, "UTF-8"));
                    Log.e("LOG", String.valueOf(url));
                    url.openStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }//try&catch
            }//run
        });//thread_delete
        FirstConnect.start();
    }//BizFirstConnect

    public void BizLastConnect(final String _DBIDX, final String _FLAG, final String _PRICE) {
        /**
         * 계산완료
         * Idx, Flag, Price
         * cPHP.BizLastConnect(TableTag_1[DB_IDX], TableTag_1[FLAG], TableTag_1[TOTAL_PRICE]);
         */
        Thread LastConnect = null;
        LastConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_RES_UPDATE + "idx=" + URLEncoder.encode(_DBIDX, "UTF-8") + "&flag=" + _FLAG + "&price=" + URLEncoder.encode(_PRICE, "UTF-8"));
                    url.openStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }//try&catch
            }//run
        });//thread_delete
        LastConnect.start();
    }//BizLastConnect

//    public int BizGetIdx(int _table) {
//        /**
//         * 비회원들 Idx 리턴
//         */
//        return Get_IDX.Get_IDX(_table);
//    }//BizGetIdx
}//class