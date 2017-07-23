package com.english.rockGod.pc.web.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public interface Transaction extends Message{
    Transaction addChild(Message var1);

    List<Message> getChildren();

    long getDurationInMicros();

    long getDurationInMillis();

    boolean hasChildren();

    boolean isStandalone();
}
