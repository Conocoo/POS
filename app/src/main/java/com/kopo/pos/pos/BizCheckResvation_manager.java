package com.kopo.pos.pos;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BizCheckResvation_manager {
    static final String SERVER_GET_RESERVE_PAGE = "/Myeong_dong/Korea_food/Perfect/reserve/reservation_reserve.php";

    public static ArrayList<BizCheckResvation> BizCheckResvation_manager() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }//네트워크 익셉션 방지
        ArrayList<BizCheckResvation> bizCheckResvations = new ArrayList<>();

        StringBuilder jsonHtml = new StringBuilder();
        try {
            // 연결 url 설정
            URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_GET_RESERVE_PAGE);
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
                        jsonHtml.append(line).append("\n");
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
                BizCheckResvation inList = new BizCheckResvation(
                        jo.getString("idx"),
                        jo.getString("id"),
                        jo.getString("reservetime"),
                        jo.getString("seat"),
                        jo.getString("person"),
                        jo.getString("time")
                );
                bizCheckResvations.add(inList);
            }//for
        } catch (JSONException e) {
            e.printStackTrace();
        }//try&catch
        return bizCheckResvations;
    }//getMenuList
}//class

//    static class phpDown extends AsyncTask<String, Integer, String> {
//        @Override
//        public String doInBackground(String... urls) {
//            StringBuilder jsonHtml = new StringBuilder();
//            try {
//                // 연결 url 설정
//                URL url = new URL(MainActivity.SERVER_ADDRESS + SERVER_GET_RESERVE_PAGE);
//                // 커넥션 객체 생성
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                // 연결되었으면.
//                if (conn != null) {
//                    conn.setConnectTimeout(10000);
//                    conn.setUseCaches(false);
//                    // 연결되었음 코드가 리턴되면.
//                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                        for (; ; ) {
//                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
//                            String line = br.readLine();
//                            if (line == null) break;
//                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
//                            jsonHtml.append(line + "\n");
//                        }//for
//                        br.close();
//                    }//if
//                    conn.disconnect();
//                }//if
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }//try&catch
//            return jsonHtml.toString();
//        }//doInBackground
//        public void onPostExecute(String str) {
//            try {
//                JSONObject root = new JSONObject(str);
//                JSONArray ja = root.getJSONArray("results");
//                for (int i = 0; i < ja.length(); i++) {
//                    JSONObject jo = ja.getJSONObject(i + 1);
//                    idx.add(jo.getString("idx"));
//                    restime.add(jo.getString("reservetime"));
//                    istv += jo.getString("idx");
//                    istv += jo.getString("reservetime");
//                    istv += "\n";
//                }//for
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }//try&catch
//            textView.setText(istv);
//        }//onPostExecute
//    }//phpDown