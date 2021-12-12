package com.chinamobile.digitaltwin.websocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WSMap {
    public static ConcurrentMap<String, WSServer> wsmap = new ConcurrentHashMap<>();

    public static void put(String key, WSServer wwserver){
        wsmap.put(key, wwserver);
    }

    public static WSServer get(String key){
        return wsmap.get(key);
    }

    public static void remove(String key){
        wsmap.remove(key);
    }

    public static Collection<WSServer> getValues(){
        return wsmap.values();
    }

}
