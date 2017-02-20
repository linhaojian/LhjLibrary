package com.lhj.library.util.common;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/19.
 */
public class MetricTools {

    /**
     *  公里 To 英里
     * @param km
     * @return
     */
    public static double km2mile(double km){
        return km * 0.63;
    }

    /**
     *  英里 To 公里
     * @param mile
     * @return
     */
    public static double mile2km(double mile){
        return mile/0.63;
    }

    /**
     *  厘米 To 英寸
     * @param cm
     * @return
     */
    public static double cm2inch(double cm){
        return cm*0.4;
    }

    /**
     *  英寸 To 厘米
     * @param inch
     * @return
     */
    public static double inch2cm(double inch){
        return inch*2.5;
    }

    /**
     *  公斤 To 磅
     * @param kg
     * @return
     */
    public static double kg2pound(double kg){
        return kg*2.2;
    }

    /**
     *  磅 To 公斤
     * @param pound
     * @return
     */
    public static double pound2kg(double pound){
        return pound/2.2;
    }


}
