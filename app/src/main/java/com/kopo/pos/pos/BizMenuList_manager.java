package com.kopo.pos.pos;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class BizMenuList_manager {

    private static Menu_DB isDB;

    public static ArrayList<BizMenuList> getMenuList(Context context, String _Query) {

        ArrayList<BizMenuList> bizMenuList = new ArrayList<>();
        String szQuery = String.format("SELECT idx, menu_name, menu_price FROM menulist %s;", _Query);

        isDB = new Menu_DB(context);
        Cursor cursor;
        cursor = isDB.getSelect(szQuery);

        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);

            BizMenuList isList = new BizMenuList(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            bizMenuList.add(isList);
        }//for

        cursor.close();
        return bizMenuList;

    }//getMenuList

    public static BizMenuList getMenuList_Select(String _idx) {
        BizMenuList bizMenuList = null;
        String szQuery = String.format("SELECT idx, menu_name, menu_price FROM menulist %s;", _idx);

        Cursor cursor;
        cursor = isDB.getSelect(szQuery);

        if (cursor.getCount() > 0) {

            cursor.moveToPosition(0);

            bizMenuList = new BizMenuList(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }//if

        cursor.close();
        return bizMenuList;
    }//getMenuList_Select

//    public static String getMenuList_Count() {
//        return isDB.DBExecuteSQL("select COUNT(*) from menulist;");
//    }//getMenuList_Count

    public static Boolean setMenuList_Update(BizMenuList isMenuList) {
        Boolean bReturn = false;
        String szQuery;

        try {
            szQuery = String.format(
                    "update menulist set menu_name = '%s', menu_price = %d where idx = %d;",
                    isMenuList.menu_name, isMenuList.menu_price, isMenuList.idx
            );
            String szError = isDB.DBExecuteSQL(szQuery);
            if (szError.length() == 0)
                bReturn = true;
        } catch (Exception e) {
            Log.i("LOG : ", "setMenuList_Update 쪽 Exception 에러");
        }//try&catch
        return bReturn;
    }//setMenuList_Update

    public static int getMenuList_price() {
        String szQuery;
        HashMap<String, Integer> Table_price = new HashMap<>();

        try {
            szQuery = "SELECT idx, menu_name, menu_price FROM menulist ;";
            Cursor cursor = isDB.getSelect(szQuery);
            cursor.getCount();
        } catch (Exception e) {
            Log.i("LOG : ", "getMenuList_price 쪽 Exception 에러");
        }//try&catch
        return 1;
    }//getMenuList_price
}//Class