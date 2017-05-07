package com.example.haoyuban111.mubanapplication.rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EntriesText {

    private final Map<String, String> _entries;

    public EntriesText() {
        _entries = new HashMap<String, String>();
    }

    public EntriesText(String json) throws JSONException {
        this(new JSONObject(json));
    }

    public EntriesText(JSONObject object) {
        _entries = JsonHelper.getMap(object);
    }

    public String get(String key) {
        return _entries.get(key);
    }

    public void put(String key, String value){
        _entries.put(key, value);
    }

    public String getDefault(){
        String item = get(getDefaultKey());
        if(item == null){
            item = creteDefault();
        }
        return item;
    }

    public Map<String, String> getMap(){
        return _entries;
    }

    public String getDefaultKey() {
        return "def";
    }

    protected String creteDefault() {
        return "";
    }

    public void putDefault(String entry){
        put(getDefaultKey(), entry);
    }

    public JSONObject toJObject() throws JSONException {
        return new JSONObject(_entries);
    }
}
