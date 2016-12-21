package com.kopo.pos.pos;

public class BizCheckResvation {
    /**
     * 이름 예약시간 예약좌석 인원 접수시각
     */

    public int nidx;
    public String nname;
    public String nrestime;
    public int nresseat;
    public int nrespeople;
    public String ntime;

    public BizCheckResvation(String _idx, String _name, String _restime, String _resseat, String _respeople, String _time) {
        /**
         * BizCheckResvation생성자
         * arrayList안에 들어갈 객체의 각 인자들 설정
         */
        nidx = Integer.parseInt(_idx);
        nname = _name;
        nrestime = _restime;
        nresseat = Integer.parseInt(_resseat);
        nrespeople = Integer.parseInt(_respeople);
        ntime = _time;

    }//BizCheckResvation

}//class
