package com.htcs.kehaoduo.bs.websocket;

import java.util.Observable;

/**
 * @author zhaokaiyuan
 * @create 2017-11-15 10:48
 **/
public class CanStopObservable extends Observable {


    public void setCanStop(String sessionId) {
        setChanged();
        notifyObservers(sessionId);
    }
}
