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

public class adapter_check_reservation extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<BizCheckResvation> bizCheckResvations;

    public adapter_check_reservation(Context context) {
        //생성자
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        bizCheckResvations = BizCheckResvation_manager.BizCheckResvation_manager();
    }//adapter

    public void Change_List(int _idx) {
        bizCheckResvations.remove(_idx);
        notifyDataSetInvalidated();
    }//Change_List

    @Override
    public int getCount() {
        return bizCheckResvations.size();
    }//getCount

    @Override
    public Object getItem(int position) {
        return null;
    }//getItem

    @Override
    public long getItemId(int position) {
        return 0;
    }//getItemId

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(R.layout.adapter_check_reservation, parent, false);
        } else {
            view = convertView;
        }
        LinearLayout layout = (LinearLayout) view;
        //레이아웃 객체는 자신이 포함하고 있는 위젯들을 id로 검색할수 있다.
        TextView nidx = (TextView) layout.findViewById(R.id.nidx);
        TextView nname = (TextView) layout.findViewById(R.id.nname);
        TextView nrestime = (TextView) layout.findViewById(R.id.nrestime);
        TextView nresseat = (TextView) layout.findViewById(R.id.nresseat);
        TextView nrespeople = (TextView) layout.findViewById(R.id.nrespeople);
        TextView ntime = (TextView) layout.findViewById(R.id.ntime);
        Button ndelete = (Button) layout.findViewById(R.id.ndelete);
        Button nseatdown = (Button) layout.findViewById(R.id.nseatdown);
        //=====================================================================
        nidx.setText(String.valueOf(position+1));
        nname.setText(bizCheckResvations.get(position).nname);
        nrestime.setText(bizCheckResvations.get(position).nrestime);
        nresseat.setText(String.valueOf(bizCheckResvations.get(position).nresseat));
        nrespeople.setText(String.valueOf(bizCheckResvations.get(position).nrespeople));
        ntime.setText(bizCheckResvations.get(position).ntime);
        //=====================================================================
        nidx.setTextSize(MainActivity.pixel_m);
        nname.setTextSize(MainActivity.pixel_m);
        nrestime.setTextSize(MainActivity.pixel_m);
        nresseat.setTextSize(MainActivity.pixel_m);
        nrespeople.setTextSize(MainActivity.pixel_m);
        ntime.setTextSize(MainActivity.pixel_m);
        ntime.setText(bizCheckResvations.get(position).ntime);
        nseatdown.setTag(bizCheckResvations.get(position).nresseat + 10000 * bizCheckResvations.get(position).nidx);
        ndelete.setTag(bizCheckResvations.get(position).nidx + 10000 * position);
        return layout;
    }//getView
}//class