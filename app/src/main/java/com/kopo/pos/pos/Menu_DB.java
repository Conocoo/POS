package com.kopo.pos.pos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class Menu_DB extends SQLiteOpenHelper {
    SQLiteDatabase sqlDB = this.getWritableDatabase();

    public Menu_DB(Context context) {
        /**
         * 2번째 인자 DB 파일명
         * 4번째 인자 DB 버전으로 처음에는 1로 지정한다.
         */
        super(context, "pos.db", null, 1);
    }//생성자

    // 테이블 생성 - 테이블이 없을 때 1회 호출
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE menulist (idx INTEGER PRIMARY KEY AUTOINCREMENT, menu_name VARCHAR(10), menu_price INTEGER);"
            );
            db.execSQL(
                    "INSERT INTO menulist(menu_name,menu_price) values('삼겹살',7000);"
            );
            db.execSQL(
                    "INSERT INTO menulist(menu_name,menu_price) values('밥',2000);"
            );
            db.execSQL(
                    "INSERT INTO menulist(menu_name,menu_price) values('술',3000);"
            );
        } catch (SQLException e) {
            Log.i("LOG : ", "DB 생성문 에러");
        } finally {
            Log.i("LOG : ", "DB 생성 완료");
        }//try&catch&finally
    }//onCreate

    // 테이블 삭제 후 다시 생성
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS menulist");
            onCreate(db);
        }//VerSion
    }//upgrade

    public int onSelect(int cnt) {
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM menulist", null);
        if (cnt >= cursor.getCount()) {
            cursor.close();
            return 0;
        }//다 저장 후에 또 반복문 실행시 리턴
        cursor.close();
        return 1;
    }//onselest_int

    @SuppressLint("LongLogTag")
    public String onSelect(String check, int cnt) {
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM menulist", null);
        if (check == "idx") {
            cursor.moveToPosition(cnt);
            Log.i("LOG getIDX : ", String.valueOf(cursor.getInt(0)));
            String idx = String.valueOf(cursor.getInt(0));
            cursor.close();
            return idx;
        }//if_idx
        if (check == "name") {
            cursor.moveToPosition(cnt);
            Log.i("LOG getNAME : ", cursor.getString(1));
            String name = cursor.getString(1);
            cursor.close();
            return name;
        }//if_name
        if (check == "price") {
            cursor.moveToPosition(cnt);
            Log.i("LOG getPRICE : ", String.valueOf(cursor.getInt(2)));
            String price = Integer.toString(cursor.getInt(2));
            cursor.close();
            return price;
        }//if_price
        return check;
    }//onSelect_String

    public HashMap<String, Integer> getmenuprice() {
        /**
         * 메뉴들의 가격만을 저장
         */
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM menulist", null);
        HashMap<String, Integer> menuprice = new HashMap<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String key = cursor.getString(1);
            int value = cursor.getInt(2);
            menuprice.put(key, value);
        }//for
        cursor.close();
        return menuprice;
    }//getmenuprice

    public Cursor getSelect(String _Query) {
        Cursor cursor;
        cursor = sqlDB.rawQuery(_Query, null);
        return cursor;
    }//getSelect

    public String DBExecuteSQL(String _query) {
        String szError = "";
        try {
            sqlDB.execSQL(_query);
            Log.i("LOG : ", "INSERT OR UPDATE 완료");
            Log.i("LOG : ", _query);
        } catch (SQLException exception) {
            Log.i("LOG : ", "INSERT OR UPDATE 실패");
            Log.i("LOG : ", _query);
            Log.i("LOG : ", String.valueOf(exception));
        }//try&catch
        return szError;
    }//DBExecuteSQL

    public void update_menu(int idx, String menu, String value) {
        int price = Integer.parseInt(value);
        int id = idx + 1;
        Cursor cursor;
        String szQuery;
        cursor = sqlDB.rawQuery("SELECT * FROM menulist where idx = " + id + ";", null);
        Log.i("LOG : ", "GET COUNT : " + String.valueOf(cursor.getCount()));
        if (0 == cursor.getCount()) {
            szQuery = String.format("INSERT INTO menulist(menu_name,menu_price) values('%s',%d);", menu, price);
            Log.i("LOG : ", "INSERT 시작");
        } else {
            szQuery = String.format("update menulist set menu_name = '%s', menu_price = %d where idx = %d;", menu, price, id);
            Log.i("LOG : ", "UPDATE 시작");
        }//if&else

        try {
            sqlDB.execSQL(szQuery);
            Log.i("LOG : ", "INSERT OR UPDATE 완료");
            Log.i("LOG : ", szQuery);
        } catch (SQLException exception) {
            Log.i("LOG : ", "INSERT OR UPDATE 실패");
            Log.i("LOG : ", szQuery);
        }//try&catch

//        String testQuery = String.format("insert into menulist (menu_name,menu_price) values('우삼겹',5000);");
//        Log.i("로그 확인 : ", testQuery);
//        sqlDB.execSQL(testQuery);
//        Log.i("로그 확인 : ", "시작");
//        ContentValues values = new ContentValues();
//        values.put("menu_name",menu);
//        //values.put("menu_price", price);
//        sqlDB.update("menulist", values, "_id = ?", new String[]{String.valueOf(idx + 1)});
//        Log.i("로그 확인 : ", "통과");
    }//update_menu
}//class