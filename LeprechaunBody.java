package com.example.junit.model;

import java.util.HashMap;
import java.util.Map;

public class LeprechaunBody {
    private Map<String, String> mElements = new HashMap<String , String>();
    private String name, val;

    public Map<String, String> getmElements() {
        return mElements;
    }

    public void setmElements(Map<String, String> mElements) {
        this.mElements = mElements;
    }

    public void addField(String name, String val){
        this.name=name;
        this.val=val;
        mElements.put(name,val);
    }
}
