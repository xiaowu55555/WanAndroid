package com.bingo.wanandroid.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class WxArticle implements Parcelable {




    /**
     * courseId : 13
     * id : 421
     * name : 互联网侦察
     * order : 190010
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */

    private long courseId;
    private long id;
    private String name;
    private String order;
    private long parentChapterId;
    private boolean userControlSetTop;
    private int visible;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public long getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(long parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.courseId);
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.order);
        dest.writeLong(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
    }

    public WxArticle() {
    }

    protected WxArticle(Parcel in) {
        this.courseId = in.readLong();
        this.id = in.readLong();
        this.name = in.readString();
        this.order = in.readString();
        this.parentChapterId = in.readLong();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readInt();
    }

    public static final Creator<WxArticle> CREATOR = new Creator<WxArticle>() {
        @Override
        public WxArticle createFromParcel(Parcel source) {
            return new WxArticle(source);
        }

        @Override
        public WxArticle[] newArray(int size) {
            return new WxArticle[size];
        }
    };
}
