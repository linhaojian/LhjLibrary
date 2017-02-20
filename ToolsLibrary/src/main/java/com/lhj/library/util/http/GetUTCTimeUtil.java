package com.lhj.library.util.http;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/11/30.
 */
public class GetUTCTimeUtil {

    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;

    /**
     * 将指定的时间转为UTC
     * @param datetime
     * @return
     */
    public static String getUTCTimeStr(String datetime) {
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance() ;
        try {
            cal.setTime(format.parse(datetime));
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day) ;
        UTCTimeBuffer.append(" ").append(hour).append(":").append(minute) ;
        try{
            return format.format(format.parse(UTCTimeBuffer.toString())) ;
        }catch(ParseException e)
        {
            e.printStackTrace() ;
        }
        return "" ;
    }

    /**
     *  utc转为本地时间
     * @param utcTime
     * @return
     */
    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    /**
     *  将云端指定格式的gmt时间转为本地时间
     * @param gmttime
     * @return
     * @throws ParseException
     */
    public static String CloudTimeToLocalTime(String gmttime) {
        String locattime = "";
        SimpleDateFormat fm=new SimpleDateFormat("EEE,d MMM yyyy hh:mm:ss", Locale.ENGLISH);
        SimpleDateFormat fm1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = fm.parse(gmttime);
            locattime = fm1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locattime;
    }


}
