package com.kopo.pos.pos;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BizSaveTable {

    /**
     * 음식점 테이블들의 메뉴 수량 보존을 위한 Class
     */

    public static HashMap<String, Integer> SaveTable1 = new HashMap<>();
    public static HashMap<String, Integer> SaveTable2 = new HashMap<>();
    public static HashMap<String, Integer> SaveTable3 = new HashMap<>();
    public static HashMap<String, Integer> SaveTable4 = new HashMap<>();
    static ArrayList<BizMenuList> misList = null;

    public BizSaveTable() {

    }//생성자

    public static void puttable(int _tablenum, String _foodname, String _foodquantity, Context context) {
        /**
         * 이름, 수량 받아서 테이블에 삽입
         */
        misList = BizMenuList_manager.getMenuList(context, "");
        switch (_tablenum) {
            case R.id.table_1:
                SaveTable1.put(_foodname, Integer.valueOf(_foodquantity));
                break;
            case R.id.table_2:
                SaveTable2.put(_foodname, Integer.valueOf(_foodquantity));
                break;
            case R.id.table_3:
                SaveTable3.put(_foodname, Integer.valueOf(_foodquantity));
                break;
            case R.id.table_4:
                SaveTable4.put(_foodname, Integer.valueOf(_foodquantity));
                break;
            default:
                break;
        }//switch

//        int foodquantity = Integer.parseInt(_foodquantity);
//        if (SaveTable1.containsKey(foodname)) {
//            int i = SaveTable1.get(foodname) + foodquantity;
//            SaveTable1.put(foodname, i);
//        } else {
//            SaveTable1.put(foodname, foodquantity);
//        }//if&else
    }//puttable

    public static void cleartable(int _tablenum) {
        /**
         * 계산 완료 후 초기화
         */
        switch (_tablenum) {
            case R.id.table_1:
                SaveTable1.clear();
                break;
            case R.id.table_2:
                SaveTable2.clear();
                break;
            case R.id.table_3:
                SaveTable3.clear();
                break;
            case R.id.table_4:
                SaveTable4.clear();
                break;
            default:
                break;
        }//switch

    }//cleartable

    public static int gettotalprice(Context context, int _tablenum) {
        Menu_DB MDB = new Menu_DB(context);
        HashMap<String, Integer> menuprice = MDB.getmenuprice();
        HashMap<String, Integer> rttable = new HashMap<>();

        switch (_tablenum) {
            case R.id.table_1:
                rttable = SaveTable1;
                break;
            case R.id.table_2:
                rttable = SaveTable2;
                break;
            case R.id.table_3:
                rttable = SaveTable3;
                break;
            case R.id.table_4:
                rttable = SaveTable4;
                break;
        }//switch

        Set<String> keys = rttable.keySet();
        int icnt = 0;
        for (String key : keys) {
            if (rttable.containsKey(key)) {
                icnt += menuprice.get(key) * rttable.get(key);
            }//if
        }//for
        return icnt;
    }//gettotalprice

//    public static HashMap<String, Integer> settablemenu(Context context, int _tablenumber) {
//        HashMap<String, Integer> rttable = new HashMap<>();
//        ArrayList<BizMenuList> ismenuList;
//        ismenuList = BizMenuList_manager.getMenuList(context, "");
//        for (int cnt = 0; cnt < ismenuList.size(); cnt++) {
//            if (SaveTable1.containsKey(ismenuList.get(cnt).menu_name)) {
//                rttable.put(ismenuList.get(cnt).menu_name, SaveTable1.get(ismenuList.get(cnt).menu_name));
//            }//if
//        }//for
//        return rttable;
//    }//settablemenu

//    public static void getmenuprice() {
//        int price[];
//    }//getmenuprice

}//class