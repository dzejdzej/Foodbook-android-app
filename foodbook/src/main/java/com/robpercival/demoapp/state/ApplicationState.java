package com.robpercival.demoapp.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 6/2/2018.
 */

public class ApplicationState {

    private static ApplicationState instance;

    public static String SERVER_IP = "http://192.168.0.12:8080";

    private Map<String, Object> localStorage = new HashMap<String, Object>();

    private ApplicationState() {

    }

    public void setItem(String key, Object value) {

        localStorage.put(key, value);
    }

    public Object getItem(String key) {
        return localStorage.get(key);
    }

    public static ApplicationState getInstance() {
        if(instance == null)
            instance = new ApplicationState();
        return instance;
    }

}
