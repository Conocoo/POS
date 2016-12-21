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
import java.util.HashMap;
import java.util.List;

public class adapter_MenuPan extends BaseAdapter {
    public ArrayList lst_name = new ArrayList();
    public ArrayList lst_price = new ArrayList();
    public ArrayList lst_idx = new ArrayList();
    LayoutInflater inflater;
    List<BizMenuList> isMenu;
    Menu_DB menu_db;
    HashMap<String, Integer> Table;

    public adapter_MenuPan(Context context) {
        //생성자
        //menu_db = new Menu_DB(context);
        //setlist();
        isMenu = BizMenuList_manager.getMenuList(context, "");
        setmenulist();
        settablenum();
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }//adapter

    private void settablenum() {
        switch (activity_MenuPan.Selected_Table) {
            case R.id.table_1:
                Table = BizSaveTable.SaveTable1;
                break;
            case R.id.table_2:
                Table = BizSaveTable.SaveTable2;
                break;
            case R.id.table_3:
                Table = BizSaveTable.SaveTable3;
                break;
            case R.id.table_4:
                Table = BizSaveTable.SaveTable4;
                break;
            default:
                break;
        }//switch
    }//settablenum

    public void updatelist() {
        notifyDataSetChanged();
    }//updatelist

    private void setmenulist() {
        for (int cnt = 0; cnt < isMenu.size(); cnt++) {
            int RT_idx = isMenu.get(cnt).idx;
            String RT_name = isMenu.get(cnt).menu_name;
            int RT_price = isMenu.get(cnt).menu_price;
            lst_idx.add(cnt, RT_idx);
            lst_name.add(cnt, RT_name);
            lst_price.add(cnt, RT_price);
        }//for
    }//setlist

    private void setlist() {
        /**
         * 역시 하위버전
         * */
        //무한반복
        for (int cnt = 0; ; cnt++) {
            if (menu_db.onSelect(cnt) == 0) {
                return;
            }//배열 크기 체크
            String RT_name = menu_db.onSelect("name", cnt);
            int RT_price = Integer.parseInt(menu_db.onSelect("price", cnt));
            int RT_idx = Integer.parseInt(menu_db.onSelect("idx", cnt));
            lst_name.add(cnt, RT_name);
            lst_price.add(cnt, RT_price);
            lst_idx.add(cnt, RT_idx);
        }//for
    }//setlist

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
        /**
         * getView
         * 스마트폰에 아이템이 화면에 보여지는 시점에 화면구성을 위해 호출된다.
         * 따라서 내가 보여줄 데이터가 100건이라도 화면에 보여질 리스트의 개수가 13건이면
         * getView호출은 12번을 먼저 호출한 후, 화면에 가려졌다가 다시 보이게 되는 아이템이 발생하면
         * 다시 getView가 발생한다.
         * 따라서 한 아이템당 한번만 인플레이션이 이루어지도록 조건문을 넣어줘야 한다.
         */
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(R.layout.adapter_menupan, parent, false);
        } else {
            view = convertView;
        }
        LinearLayout layout = (LinearLayout) view;
        //레이아웃 객체는 자신이 포함하고 있는 위젯들을 id로 검색할수 있다.
        //---------------------------ID 설정---------------------------------------------------------
        TextView food_name = (TextView) layout.findViewById(R.id.tv_ad_name);
        TextView food_price = (TextView) layout.findViewById(R.id.tv_ad_price);
        TextView food_quantity = (TextView) layout.findViewById(R.id.tv_ad_number);
        TextView food_sum_price = (TextView) layout.findViewById(R.id.tv_ad_sum_price);
        Button btn_setup = (Button) layout.findViewById(R.id.btn_ad_setup);
        //TextView tv_idx_value = (TextView) layout.findViewById(R.id.tv_idx_value);
        //------------------------------------------------------------------------------------------
        //---------------------------TABLE 정보 불러오기----------------------------------------------
        if (Table.containsKey(lst_name.get(position))) {
            int foodquantity = Table.get(lst_name.get(position));
            food_quantity.setText(String.valueOf(foodquantity));    //구입 수량
            food_sum_price.setText(String.valueOf(foodquantity * (int) lst_price.get(position))); //가격 X 구입한 수량
            food_quantity.append("개");
            food_sum_price.append("원");
        } else {
            food_sum_price.setText("0"); //가격 X 구입한 수량
        }//if&else
        //------------------------------------------------------------------------------------------
        food_name.setText((String) lst_name.get(position)); //이름
        food_price.setText(lst_price.get(position) + "원");  //가격
        //tv_idx_value.setText("idx = "+ lst_idx.get(position));
        //btn_setup.setTag(lst_name.get(position)+":"+lst_price.get(position));
        btn_setup.setTag(lst_name.get(position));
        return layout;
    }//getView

}//class