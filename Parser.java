package com.example.junit.service;


import com.example.junit.model.LeprechaunBody;
import com.example.junit.model.Response;

public class Parser {
    public static void parseMsg(Response response){
        LeprechaunBody body = new LeprechaunBody();
        body.addField("ak", "cb");
        response.setBody(body);
    }
}
