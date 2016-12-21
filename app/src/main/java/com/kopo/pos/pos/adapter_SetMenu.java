package com.kopo.pos.pos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_SetMenu extends BaseAdapter {
    public ArrayList lst_name = new ArrayList();
    public ArrayList lst_price = new ArrayList();
    LayoutInflater inflater;
    Menu_DB menu_db;

    private ArrayList<BizMenuList> ismenuList;

    public adapter_SetMenu(Context context) {
        /**생성자
         * DB관련은 Biz에서 미리 처리
         * menu_db = new Menu_DB(context);
         * setlist(); 부분 삭제
         **/
        ismenuList = BizMenuList_manager.getMenuList(context, "");
        setMenulist();
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }//adapter

    private void setMenulist() {
        //반복
        for (int cnt = 0; cnt < ismenuList.size(); cnt++) {
            String RT_name = ismenuList.get(cnt).menu_name;
            int RT_price = ismenuList.get(cnt).menu_price;
            lst_name.add(cnt, RT_name);
            lst_price.add(cnt, RT_price);
        }//for
    }//setMenulist

    private void setlist() {
        /**
         * setMenulist의 하위 버전
         */
        for (int cnt = 0; ; cnt++) {
            //배열 크기
            if (menu_db.onSelect(cnt) == 0) {
                return;
            }//if
            String RT_name = menu_db.onSelect("name", cnt);
            int RT_price = Integer.parseInt(menu_db.onSelect("price", cnt));
            lst_name.add(cnt, RT_name);
            lst_price.add(cnt, RT_price);
        }//for
    }//setlist

    public void increse() {
        lst_name.add("");
        lst_price.add("0");
        notifyDataSetChanged();
    }//increse


    //아래의 재정의된 모든 메서드를 호출하는 주체는 ListView이다.
    //즉 ListView는 아래의 메서드를 통해 데이터 정보를 반영한다.
    //리스트뷰에게 리스트의 개수를 몇개로 구성해야하는지를 알게해준다.(호출자:ListView)
    @Override
    public int getCount() {
        return lst_name.size();
    }//getCount

    //리스트뷰에게 지정한 postion 변수에 해당하는 순서의 데이터를 추출할 수 있도록 해준다.
    @Override
    public Object getItem(int position) {
        return null;
    }//getItem

    @Override
    public long getItemId(int position) {
        return position;
    }//getItemid

    //리스트뷰는 getView 메서드를 getCount()반환값 만큼 호출하여, 각 item을 구성하게 된다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(R.layout.adapter_setmenu, parent, false);
        } else {
            view = convertView;
        }//if&else
        LinearLayout layout = (LinearLayout) view;
        //레이아웃 객체는 자신이 포함하고 있는 위젯들을 id로 검색할수 있다.
        TextView lv_MenuName = (TextView) layout.findViewById(R.id.lv_MenuName);
        TextView lv_MenuPrice = (TextView) layout.findViewById(R.id.lv_MenuPrice);
        Button btn = (Button) layout.findViewById(R.id.btn_set);
        try {
            lv_MenuName.setText(String.valueOf(lst_name.get(position)));
            lv_MenuPrice.setText(String.valueOf(lst_price.get(position)));
            btn.setTag(position + ":" + lst_name.get(position) + ":" + lst_price.get(position));
        } catch (IndexOutOfBoundsException ioobe) {
            lv_MenuName.setText("");
            lv_MenuPrice.setText("0");
            btn.setTag(position + ":" + "" + ":" + 0);
        }//try&catch
        return layout;
    }//getView
}//class