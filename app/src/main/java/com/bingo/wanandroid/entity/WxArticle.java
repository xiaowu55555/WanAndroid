package com.bingo.wanandroid.entity;

public class WxArticle {


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
}
