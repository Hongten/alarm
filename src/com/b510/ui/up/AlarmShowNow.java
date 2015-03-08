/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b510.ui.up;

import java.util.Calendar;

/**
 *
 * @author Hongten
 * 主界面区-显示现在的时间
 */
public class AlarmShowNow {

    /**
     * 获得系统的当前时间，并返回
     * @return 返回系统当前时间
     */
    public static String now() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        String current = new String(hour+ ":" + min + ":" + sec);
        return current;
    }
   public static void main(String args[]){
       System.out.println(now());
   }
}
