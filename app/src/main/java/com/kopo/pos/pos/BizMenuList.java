package com.kopo.pos.pos;

public class BizMenuList {

    /**
     * TABLE NAME : menulist
     * idx INTEGER PRIMARY KEY AUTOINCREMENT
     * menu_name VARCHAR(10)
     * menu_price INTEGER
     */

    public int idx;
    public String menu_name;
    public int menu_price;

    public BizMenuList(String _Idx, String _menu_name, String _menu_price) {
        /**
         * BizMenuList생성자
         * arrayList안에 들어갈 객체의 각 인자들 설정
         */
        idx = Integer.parseInt(_Idx);
        menu_name = _menu_name;
        menu_price = Integer.parseInt(_menu_price);

    }//BizMenuList

}//BizMenuList

