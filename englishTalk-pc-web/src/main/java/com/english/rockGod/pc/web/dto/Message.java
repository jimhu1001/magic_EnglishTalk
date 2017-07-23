package com.english.rockGod.pc.web.dto;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface Message {
    String SUCCESS = "0";

    void addData(String var1);

    void addData(String var1, Object var2);

    void complete();

    Object getData();

    String getName();

    String getStatus();

    long getTimestamp();

    String getType();

    boolean isCompleted();

    boolean isSuccess();

    void setStatus(String var1);

    void setStatus(Throwable var1);
}
