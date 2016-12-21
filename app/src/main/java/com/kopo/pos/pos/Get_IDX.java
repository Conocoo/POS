package com.kopo.pos.pos;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Get_IDX {
    static final String SERVER_GET_IDX_PAGE = "/Myeong_dong/Korea_food/Perfect/reserve/reservation_eating.php";

    public static int Get_IDX(int _table) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }//네트워크 익셉션 방지
        HashMap<Integer, Integer> getTable = new HashMap<>();
        StringBuilder jsonHtml = new StringBuilder();
        try {
            // 연결 url 설정
            URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_GET_IDX_PAGE);
            // 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 연결되었으면.
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                // 연결되었음 코드가 리턴되면.
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    for (; ; ) {
                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                        String line = br.readLine();
                        if (line == null) break;
                        // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                        jsonHtml.append(line + "\n");
                    }//for
                    br.close();
                }//if
                conn.disconnect();
            }//if
        } catch (Exception ex) {
            ex.printStackTrace();
        }//try&catch
        try {
            JSONObject root = new JSONObject(jsonHtml.toString());
            JSONArray ja = root.getJSONArray("results");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                //getTable.put(tablenum,IDX);
                getTable.put(
                        jo.getInt("seat"),
                        jo.getInt("idx")
                );
            }//for
        } catch (JSONException e) {
            e.printStackTrace();
        }//try&catch

        return getTable.get(String.valueOf(_table));
    }//getMenuList

}//class