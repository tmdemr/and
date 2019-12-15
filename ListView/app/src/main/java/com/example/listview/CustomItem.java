package com.example.listview;

public class CustomItem {
    String id, pass, desc, etc;
    CustomItem(String _id, String _pass, String _desc, String _etc){
        id = _id;
        pass = _pass;
        desc = _desc;
        etc = _etc;
    }

    public String getDesc() {
        return desc;
    }

    public String getEtc() {
        return etc;
    }

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }
}
