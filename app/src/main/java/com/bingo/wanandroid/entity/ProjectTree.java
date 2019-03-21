package com.bingo.wanandroid.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectTree implements Parcelable {



    /**
     * children : []
     * courseId : 13
     * id : 294
     * name : 完整项目
     * order : 145000
     * parentChapterId : 293
     * userControlSetTop : false
     * visible : 0
     */

    private long courseId;
    private long id;
    private String name;
    private long order;
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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
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
        dest.writeLong(this.order);
        dest.writeLong(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
    }

    public ProjectTree() {
    }

    protected ProjectTree(Parcel in) {
        this.courseId = in.readLong();
        this.id = in.readLong();
        this.name = in.readString();
        this.order = in.readLong();
        this.parentChapterId = in.readLong();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readInt();
    }

    public static final Creator<ProjectTree> CREATOR = new Creator<ProjectTree>() {
        @Override
        public ProjectTree createFromParcel(Parcel source) {
            return new ProjectTree(source);
        }

        @Override
        public ProjectTree[] newArray(int size) {
            return new ProjectTree[size];
        }
    };
}
