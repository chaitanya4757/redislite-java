package com.redislite.store;
import java.util.HashMap;
public class Store {
    private HashMap<String, String> data = new HashMap<>();

    public void set(String key, String value){
        data.put(key, value);
    }
    public String get(String key){
        return data.get(key);
    }
    public boolean exists(String key){
        return data.containsKey(key);
    }
    public boolean delete(String key){
        if(data.containsKey(key)){
            data.remove(key);
            return true;
        }
        else{
            return false;
        }
    }

}
