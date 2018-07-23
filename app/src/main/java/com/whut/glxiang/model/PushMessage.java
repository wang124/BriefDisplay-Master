package com.whut.glxiang.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PushMessage {
    @Property(nameInDb = "id")
    @Id(autoincrement = true)
    private Long id;
    private Integer iconCode;//图标编号
    private String title;//标题
    private String content;//内容
    private Integer messageType;//消息类型
    private String receive_time;//接收时间
    private String linkAds;
    private boolean isRead;//是否已读
    private boolean isDelete; //是否显示CheckBox
    private boolean isChecked; //是否选中CheckBox。true:选中，false：未选中
    @Generated(hash = 1587744512)
    public PushMessage(Long id, Integer iconCode, String title, String content,
            Integer messageType, String receive_time, String linkAds,
            boolean isRead, boolean isDelete, boolean isChecked) {
        this.id = id;
        this.iconCode = iconCode;
        this.title = title;
        this.content = content;
        this.messageType = messageType;
        this.receive_time = receive_time;
        this.linkAds = linkAds;
        this.isRead = isRead;
        this.isDelete = isDelete;
        this.isChecked = isChecked;
    }
    @Generated(hash = 1468533071)
    public PushMessage() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIconCode() {
        return this.iconCode;
    }
    public void setIconCode(Integer iconCode) {
        this.iconCode = iconCode;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getMessageType() {
        return this.messageType;
    }
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
    public String getReceive_time() {
        return this.receive_time;
    }
    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }
    public String getLinkAds() {
        return this.linkAds;
    }
    public void setLinkAds(String linkAds) {
        this.linkAds = linkAds;
    }
    public boolean getIsRead() {
        return this.isRead;
    }
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
    public boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    public boolean getIsDelete() {
        return this.isDelete;
    }
    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
