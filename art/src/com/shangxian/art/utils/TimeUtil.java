package com.shangxian.art.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data=new Date();
        return format.format(data);
    }

    public static String getyyyymmddhh(){
        long time=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        Date data=new Date(time);
        return format.format(data);
    }

    public static String getCurrentData(){
        long time=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date data=new Date(time);
        return format.format(data);
    }

    public static String getCurrentDay(){
        long time=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("MM-dd");
        Date data=new Date(time);
        return format.format(data);
    }

    public static String getCurrentDaychina(){
        long time=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
        Date data=new Date(time);
        return format.format(data);
    }

    public static String getCurrentWeek(){
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
        }
        return "";
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }
    public static String getTime_yun(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年\nMM月dd日");
        return format.format(new Date(time));
    }
    public static String getTime_yun_h(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }
    public static String getTime_yun_y(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(new Date(time));
    }
    public static String getTime_yun_n(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyy年MM月dd日");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static String getSecAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(new Date(time));
    }

    public static String getMinAndHH(long time){
        //long diff = time.getTime() - d2.getTime();//这样得到的差值是微秒级别
        long days = time / (1000 * 60 * 60 * 24);
        long hours = (time - days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (time - days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

        if (days <= 0) {
            if (hours <= 0) {
                if (minutes <= 0) {
                    return "小于一分钟";
                }
                return minutes + "分钟";
            }
            return hours + "小时" + minutes + "分钟";
        }
        return days + "天" + hours + "小时" + minutes + "分钟";
    }

    /** 获取聊天时间：因为sdk的时间默认到秒故应该乘1000
     * @Title: getChatTime
     * @Description: TODO
     * @param @param timesamp
     * @param @return
     * @return String
     * @throws
     */
    public static String getChatTime(long timesamp) {
        long clearTime = timesamp*1000;
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);
        int temp = Integer.parseInt(sdf.format(today))
                - Integer.parseInt(sdf.format(otherDay));

        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(clearTime);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(clearTime);
                break;
            case 2:
                result = "前天 " + getHourAndMin(clearTime);
                break;

            default:
                result = getTime(clearTime);
                break;
        }

        return result;
    }

    public static String getTimen(String time){
        if (time.contains("年")){
            int index = time.indexOf("年");
            time  = time.replace("年", "年\n");
        }
        return time;
    }

}
