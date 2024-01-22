package com.example.junit.service;


import com.example.junit.model.Response;

public class ActClose {
    public Response buildLepResp(Response response){
        System.out.println("before parse : "+response.getBody().getmElements());
        Parser.parseMsg(response);

        System.out.println("after parse : "+ response.getBody().getmElements());
        return  response;
    }
}
