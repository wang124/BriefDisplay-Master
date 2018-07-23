package com.whut.glxiang.util;

import com.whut.glxiang.application.MyApplication;
import com.whut.glxiang.model.PushMessage;
import com.whut.glxiang.model.PushMessageDao;

public class DatabaseOperation {

    public static void insertMessage(int iconCode,String title,String content,int messageType,String linkAds,String receiveTime,Boolean isRead,Boolean isDelete){
        PushMessageDao pushMessageDao = MyApplication.getInstance().getmDaoSession().getPushMessageDao();
        PushMessage pushMessage = new PushMessage();
        pushMessage.setIconCode(iconCode);
        pushMessage.setTitle(title);
        pushMessage.setContent(content);
        pushMessage.setMessageType(messageType);
        pushMessage.setLinkAds(linkAds);
        pushMessage.setReceive_time(receiveTime);
        pushMessage.setIsRead(isRead);
        pushMessage.setIsDelete(isDelete);
        pushMessageDao.insert(pushMessage);
    }
}
