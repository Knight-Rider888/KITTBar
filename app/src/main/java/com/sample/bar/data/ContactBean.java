package com.sample.bar.data;

public class ContactBean {

    // recyclerview 的多布局
    private int type;
    // 首字母
    private char initial;
    // 名字
    private String name;

    public ContactBean(char initial, String name) {
        this.initial = initial;
        this.name = name;
        this.type = 1;
    }

    public ContactBean(char initial) {
        this.initial = initial;
        this.type = 0;
    }

    public int getType() {
        return type;
    }

    public char getInitial() {
        return initial;
    }

    public String getName() {
        return name;
    }
}
