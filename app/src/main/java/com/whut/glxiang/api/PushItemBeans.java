package com.whut.glxiang.api;

public class PushItemBeans {
    private Integer id;
    private Integer iconCode;//图标编号
    private String title;//标题
    private String content;//内容
    private Integer messageType;//消息类型
    private String receive_time;//接收时间
    private String linkAds;
    private boolean isRead;//是否已读
    private boolean isShow; //是否显示CheckBox
    private boolean isChecked; //是否选中CheckBox。true:选中，false：未选中

    public PushItemBeans(Integer id, Integer iconCode, String title, String content, Integer messageType,
                         String receive_time, String linkAds, boolean isRead, boolean isShow, boolean isChecked) {
        this.id = id;
        this.iconCode = iconCode;
        this.title = title;
        this.content = content;
        this.messageType = messageType;
        this.receive_time = receive_time;
        this.linkAds = linkAds;
        this.isRead = isRead;
        this.isShow = isShow;
        this.isChecked = isChecked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIconCode() {
        return iconCode;
    }

    public void setIconCode(Integer iconCode) {
        this.iconCode = iconCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getLinkAds() {
        return linkAds;
    }

    public void setLinkAds(String linkAds) {
        this.linkAds = linkAds;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
