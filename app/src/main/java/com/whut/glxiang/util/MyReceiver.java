package com.whut.glxiang.util;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.whut.glxiang.R;
import com.whut.glxiang.activity.DisplayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
//未读消息角标
//import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by liuyezhen,wanglexin on 2018/6/27.
 */

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    public static final String KEY_MSG = "jpush_msg";
    private NotificationManager nm;
    private int iconCode;//图标编号
    private String title;//推送标题
    private String content;//推送内容
    private int messageType;//消息类型
    private String linkAds;//跳转链接地址
    private String extra;//额外字段
    private String receiveTime;//接收消息时间
    //private PushDatabaseHelper pdbHelper;
    private LocalBroadcastManager localBroadcastManager;
    private int count = 0;//未读消息数量

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
      //  pdbHelper = new PushDatabaseHelper(context, "message_record.db", null, 1);

        //设置默认图标
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(context,
                R.layout.customer_notitfication_layout,
                R.id.icon,
                R.id.title,
                R.id.text);
        builder.layoutIconDrawable = R.mipmap.ic_launcher;
        JPushInterface.setDefaultPushNotificationBuilder(builder);

        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            content = bundle.getString(JPushInterface.EXTRA_ALERT);
            extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            //创建JSON解析对象(两条规则的体现:大括号用JSONObject,注意传入数据对象),obj.后面有各种数据类型,根据对象来选择使用的数据类型
            JSONObject obj = null;
            try {
                obj = new JSONObject(extra);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                messageType = obj.getInt("messageType");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                linkAds = obj.getString("linkAds");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                iconCode = obj.getInt("iconCode");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //接收消息时间
            Date date = new Date();//获得系统当前的时间
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            receiveTime = sDateFormat.format(date);
            DatabaseOperation.insertMessage(iconCode,title,content,messageType,linkAds,receiveTime,false,false);
//            SQLiteDatabase db = pdbHelper.getWritableDatabase();
//            //实例化常量值
//            ContentValues values = new ContentValues();
//            //添加图标
//            values.put("iconCode", iconCode);
//            //添加标题
//            values.put("title", title);
//            //添加消息
//            values.put("content", content);
//            //添加消息类型
//            values.put("messageType", messageType);
//            //添加url
//            values.put("linkAds", linkAds);
//            //添加date
//            values.put("receive_time", receiveTime);
//            //是否已读
//            values.put("isRead", false);
//            //是否需要删除
//            values.put("isDelete", false);
//            switch (messageType){
//                case 1:
//                    db.insert("push_message", null, values);//安全简报国际民航
//                    break;
//                case 2:
//                    db.insert("push_message2", null, values);//安全简报国际民航
//                    break;
//                    default:
//                        db.insert("push_message", null, values);//安全简报国际民航
//                        break;
//            }
//            db.close();
            /**
             * 扫描数据库，获取未读消息数量
             */
//            Cursor cursor = db.query("push_message", null, "isRead=?", new String[]{"0"},
//                    null, null, null);
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    count++;
//                }
//                cursor.close();
//                ShortcutBadger.applyCount(context, count); //显示app图标右上角消息数量
//            }
            /**
             * 向PushActivity发送广播，更新消息列表
             */
            localBroadcastManager = LocalBroadcastManager.getInstance(context);//获取实例
            Intent intent1 = new Intent("com.whut.glxiang.adapter.MyRecyclerAdapter.LOCAL_BROADCAST");
            localBroadcastManager.sendBroadcast(intent1);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            openNotification(context, bundle);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);

//        SQLiteDatabase db = pdbHelper.getWritableDatabase();
//        //实例化常量值
//        ContentValues values = new ContentValues();
//        values.put("isRead",true);
//        db.update("push_message",values,"title=? and content=?",new String[]{title,content});
//        db.close();

//        String myValue = "";
//        try {
//            JSONObject extrasJson = new JSONObject(extras);
//            myValue = extrasJson.optString("myKey");
//        } catch (Exception e) {
//            Log.w(TAG, "Unexpected: extras is not a valid json", e);
//            return;
//        }
//
        JSONObject obj = null;
        try {
            obj = new JSONObject(extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            messageType = obj.getInt("messageType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            linkAds = obj.getString("linkAds");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            iconCode = obj.getInt("iconCode");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        switch (messageType) {
            case 1:
                Intent intent1 = new Intent(context, DisplayActivity.class);
                intent1.putExtra("messageType", 1);
                intent1.putExtra("title", title);
                intent1.putExtra("content", content);
                context.startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(context, DisplayActivity.class);
                intent2.putExtra("messageType", 2);
                intent2.putExtra("title", title);
                intent2.putExtra("content", content);
                context.startActivity(intent2);
                break;
            default:
                Toast.makeText(context, "未知的推送消息类型！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}