package com.whut.glxiang.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.whut.glxiang.model.DaoMaster;
import com.whut.glxiang.model.DaoSession;

public class MyApplication extends Application {
    private static MyApplication instance;
    private static String sessionID = "";
    private static int taskCount = 0;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static int getTaskCount(){
        return taskCount;
    }

    public static void setTaskCount(int taskCount){
        MyApplication.taskCount = taskCount;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        setDatabase();
    }


    /**
     * 设置greenDao
     * 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
     * 并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
     * 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
     * 所以，在正式的项目中还应该做一层封装，来实现数据库的安全升级。
     */

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this,"pushMessage_db",null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
    public static MyApplication getInstance(){
        return instance;
    }
    public static String getSessionID(){
        return sessionID;
    }
    public static void setSessionID(String sessionID){
        MyApplication.sessionID = sessionID;
    }
}
