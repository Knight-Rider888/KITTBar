package com.sample.bar.data;

public class ContactBean {

    // 首字母
    private String phonebook_label;
    // 名字
    private String display_name;
    // 电话
    private String phone_number;

    public ContactBean(String phonebook_label, String display_name) {
        this.phonebook_label = phonebook_label;
        this.display_name = display_name;
        // 模拟电话号码
        this.phone_number = "111";
    }

    public ContactBean(String phonebook_label, String display_name, String phone_number) {
        this.phonebook_label = phonebook_label;
        this.display_name = display_name;
        this.phone_number = phone_number;
    }

    public String getPhonebook_label() {
        return phonebook_label;
    }

    public void setPhonebook_label(String phonebook_label) {
        this.phonebook_label = phonebook_label;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
