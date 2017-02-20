package com.lhj.library.util.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/12/19.
 */
public class BigDecimalTools {

    /**
     *  取整的四舍五入
     * @param s
     * @return
     */
    public static int IntRounded(String s){
        return new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     *  保留小数(四舍五入)
     * @param s  指定的字符串
     * @param count 指定的保留位数
     * @return
     */
    public static float FloatRounded(String s,int count){
        if(Float.parseFloat(s)==0){
            return 0;
        }
        return new BigDecimal(s).setScale(count, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     *   保留小数(没有四舍五入)
     * @param dou 指定的数值
     * @param count 指定的保留位数
     * @return
     */
    public static String FloatFloor(double dou,int count){
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(count);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(dou);
    }

}
