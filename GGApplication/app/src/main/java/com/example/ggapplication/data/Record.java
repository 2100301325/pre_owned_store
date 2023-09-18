package com.example.ggapplication.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {
    private String id;
    private String appKey;
    @SerializedName("content")
    private String content;

    public int getAppIsShare() {
        return appIsShare;
    }

    public String getTuserId() {
        return tuserId;
    }

    public void setTuserId(String tuserId) {
        this.tuserId = tuserId;
    }

    public void setAppIsShare(int appIsShare) {
        this.appIsShare = appIsShare;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String gettUserId() {
        return tUserId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public void settUserId(String tUserId) {
        this.tUserId = tUserId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String tUserId;
    @SerializedName("imageCode")
    private String imageCode;

    @SerializedName("price")

    private double price;
    private String addr;
    private int typeId;
    private String typeName;
    private int status;
    private String createTime;
    private String username;
    private String avatar;
    @SerializedName("imageUrlList")
    private List<String> imageUrlList;
    private int appIsShare;
    private String tuserId;
    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", appKey='" + appKey + '\'' +
                ", price='" + price + '\'' +
                // 将你想要显示的属性逐个添加进来
                '}';
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    private String goodsDescription;

}
