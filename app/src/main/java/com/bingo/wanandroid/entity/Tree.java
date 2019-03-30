package com.bingo.wanandroid.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

public class Tree extends SectionEntity<TreeChild> {
    private long id;
    private String name;
    List<TreeChild> children;

    public Tree(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public Tree(TreeChild treeChild) {
        super(treeChild);
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

    public List<TreeChild> getChildren() {
        return children;
    }

    public void setChildren(List<TreeChild> children) {
        this.children = children;
    }
}
