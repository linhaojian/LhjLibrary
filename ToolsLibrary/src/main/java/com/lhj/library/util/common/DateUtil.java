package com.lhj.library.util.common;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/8/10.
 */
public class DateUtil {

    /**
     *   获取月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysForDate(int year,int month){
        int dayCount;
        Calendar cl=Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,month-1);
        dayCount=cl.getActualMaximum(Calendar.DATE);
        return dayCount;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param startDate
     * @param endDate
     * @return List
     * @throws ParseException
     */
    public static ArrayList<String> getDatesBetweenTwoDate(String startDate, String endDate) throws ParseException {
        ArrayList<String> lDate = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse(startDate);//定义起始日期
        Date d2 = sdf.parse(endDate);//定义结束日期
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        while(dd.getTime().before(d2)){//判断是否到结束日期
            String str = sdf.format(dd.getTime());
            lDate.add(str);// 把结束时间加入集合
            dd.add(Calendar.DAY_OF_MONTH, 1);
        }
        lDate.add(sdf.format(dd.getTime()));
        return lDate;
    }

    /**
     *   将指定时间加上一分钟的时间
     * @param dateTime
     * @return
     */
    public static String timeAdd1Min(String dateTime){//yyy MM dd HH mm
        String newdateTime="";
        try {
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long s=sf.parse(dateTime).getTime();
            s=s+(60*1000);
            newdateTime=sf.format(new Date(s));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newdateTime;
    }

    /**
     *   获取指定时间段里面的所有时间(yyyy-MM-dd HH:mm)
     * @param dataS
     * @param dataE
     * @return
     */
    public static ArrayList<String> dataTimes(String dataS,String dataE) {
        ArrayList<String> list=new ArrayList<String>();
        if(dataS.equals("")||dataE.equals("")){
            return list;
        }
        try {
            list.add(dataS);
            String nextT=dataS;
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long starttimelong=sf.parse(nextT).getTime();
            long eimelong=sf.parse(dataE).getTime();
            while(starttimelong<eimelong){
                nextT=timeAdd1Min(nextT);
                starttimelong=sf.parse(nextT).getTime();
                list.add(nextT);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取设备采用的时间制式(12小时制式或者24小时制式)
     * 注意:
     * 在模拟器上获取的时间制式为空
     */
    public static int getTime12or24(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        String time_12_24 = Settings.System.getString(contentResolver,Settings.System.TIME_12_24);
        int time = 24;
        if(time_12_24!=null&&"24".equals(time_12_24)){
            time = 24;
        }

        if(time_12_24!=null&&"12".equals(time_12_24)){
            time = 12;
        }
        return time;
    }

    /**
     *  根据指定的格式：返回当前系统时间
     * @param paren
     * @return
     */
    public static String getPhoneDateTime(String paren){
        SimpleDateFormat sf = new SimpleDateFormat(paren);
        return sf.format(new Date());
    }

    /**
     * 计算日期 --  天
     * @param paren
     * @param date
     * @param count
     * @return
     */
    public static String calculateDate(String paren,String date,int count){
        SimpleDateFormat sf = new SimpleDateFormat(paren);
        GregorianCalendar gc=new GregorianCalendar();
        try{
            gc.setTime(sf.parse(date));
            gc.add(GregorianCalendar.DATE,count);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sf.format(gc.getTime());
    }

    /**
     *  通过指定日期获取周的第一天日期与最后一天日期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDate(int year,int month,int day) {
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar(year, month-1, day);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        Calendar calFirstDayInThisWeek = (Calendar)cal.clone();

        calFirstDayInThisWeek.add(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_WEEK)-dayOfWeek);

        Calendar calLastDayInThisWeek = (Calendar)cal.clone();

        calLastDayInThisWeek.add(Calendar.DATE,cal.getActualMaximum(Calendar.DAY_OF_WEEK)-dayOfWeek);

        return f.format(calFirstDayInThisWeek.getTime())+":"+f.format(calLastDayInThisWeek.getTime());
    }

    /**
     *  获取指定日期的周，所有日期
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static ArrayList<String> dateToWeek(String datestr) throws ParseException{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date mdate = sf.parse(datestr);
        int b=mdate.getDay();
        Date fdate ;
        ArrayList<String> list = new ArrayList<String>();
        Long fTime=mdate.getTime()-b*24*3600000;
        for(int a=0;a<7;a++){
            fdate= new Date();
            fdate.setTime(fTime+(a*24*3600000));
            String dt = sf.format(fdate);

            list.add(dt);
        }
        return list;
    }

    /**
     *  获取指定日期的月，所有日期
     * @param datestr
     * @return
     * @throws ParseException
     */
    public static ArrayList<String> getAllTheDateOftheMonth(String datestr) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse(datestr);
        ArrayList<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while(cal.get(Calendar.MONTH) == month){
            list.add(sf.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

}
