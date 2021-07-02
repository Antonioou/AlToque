package com.altoque.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinResponseModel {

    @SerializedName("code_server")
    String code_server;
    @SerializedName("res_server")
    String res_server;
    @SerializedName("msg_server")
    String msg_server;

    public JoinResponseModel() { }

    public JoinResponseModel(String code_server, String res_server, String msg_server) {
        this.code_server = code_server;
        this.res_server = res_server;
        this.msg_server = msg_server;
    }

    @Override
    public String toString() {
        return "JoinResponseModel{" +
                "code_server='" + code_server + '\'' +
                ", res_server='" + res_server + '\'' +
                ", msg_server='" + msg_server + '\'' +
                '}';
    }

    public String getCode_server() {
        return code_server;
    }

    public void setCode_server(String code_server) {
        this.code_server = code_server;
    }

    public String getRes_server() {
        return res_server;
    }

    public void setRes_server(String res_server) {
        this.res_server = res_server;
    }

    public String getMsg_server() {
        return msg_server;
    }

    public void setMsg_server(String msg_server) {
        this.msg_server = msg_server;
    }
}
