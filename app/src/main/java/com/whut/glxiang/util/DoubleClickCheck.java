package com.whut.glxiang.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fortuner on 2018/1/6.
 */

public class DoubleClickCheck {
    private static long lastClickTime = 0;
    private static long DIFF = 800;//0.8秒内请勿重复点击
    private static int lastButtonId = -1;

    public static long getLastClickTime() {
        return lastClickTime;
    }

    public static void setLastClickTime(long lastClickTime) {
        DoubleClickCheck.lastClickTime = lastClickTime;
    }

    /**
     * 判断两次点击的间隔，如果小于DIFF，则认为是多次无效点击
     * isFastDoubleClick()针对页面内所有点击事件,判断是否快速点击
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于DIFF，则认为是多次无效点击
     * isFastDoubleClick(int buttonId)针对特定按钮,判断是否双击
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于DIFF，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.v("isFastDoubleClick", "短时间内多次点击");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
    public static Date setCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);//年-月-日 时:分:秒
        Date date = new Date(System.currentTimeMillis());
        //String currentTime = simpleDateFormat.format(date);
        return date;
    }

    public static long getDIFF() {
        return DIFF;
    }

    public static void setDIFF(long DIFF) {
        DoubleClickCheck.DIFF = DIFF;
    }
}
