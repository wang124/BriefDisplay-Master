//package com.whut.glxiang.util;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class PushDatabaseHelper extends SQLiteOpenHelper {
//
//    public static final String MESSAGE_TABLE =
//            "(id integer primary key autoincrement,"
//            + "iconCode integer,"
//            + "title text,"
//            + "content text,"
//            + "messageType integer,"
//            + "linkAds text,"
//            + "receive_time varchar(64),"
//            + "isRead boolean,"
//            + "isDelete boolean)";
//
//    private Context mContext;
//
//    public PushDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//        mContext = context;
//    }
//    //该方法没有数据库存在才会执行
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table push_message" + MESSAGE_TABLE);
//        db.execSQL("create table push_message2" + MESSAGE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists push_message");
//        db.execSQL("drop table if exists push_message2");
//        onCreate(db);
//    }
//}
