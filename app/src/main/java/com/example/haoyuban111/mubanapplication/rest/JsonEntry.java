package com.example.haoyuban111.mubanapplication.rest;

import android.util.SparseArray;

import com.example.haoyuban111.mubanapplication.help_class.StringHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public abstract class JsonEntry implements JsonHelper.IJson {

    private final JSONObject _members;

    protected JsonEntry(String json) throws JSONException {
        this(StringHelper.isEmpty(json) ? new JSONObject() : new JSONObject(json));
    }

    protected JsonEntry(JSONObject members) {
        _members = members;
    }

    protected JsonEntry() {
        this(new JSONObject());
    }

    protected void put(String key, Object value) {
        try {
            _members.put(key, value);
        } catch (Exception ex) {
//            LogWriter.debug(ex.getLocalizedMessage());
        }
    }

    protected void put(String key, long value) {
        try {
            _members.put(key, value);
        } catch (Exception ex) {
            //Nothing to do
        }
    }

    protected void put(String key, int value) {
        try {
            _members.put(key, value);
        } catch (Exception ex) {
            //Nothing to do
        }
    }

    protected void put(String key, boolean value) {
        try {
            _members.put(key, value);
        } catch (Exception ex) {
            //Nothing to do
        }
    }

    protected float optFloat(String key) throws Exception {
        return Float.valueOf(_members.optString(key));
    }

    protected int optInt(String key) {
        return _members.optInt(key);
    }

    protected int optInt(String key, int defValue) {
        return _members.optInt(key, defValue);
    }

    protected long optLong(String key, long defValue) {
        return _members.optLong(key, defValue);
    }

    protected boolean optBoolean(String key) {
        return _members.optBoolean(key);
    }

    protected boolean optBoolean(String key, boolean defaultValue) {
        return _members.optBoolean(key, defaultValue);
    }

    public static String optString(String key, JsonEntry entry) {
        if (entry != null) {
            return entry.optString(key);
        }
        return "";
    }

    protected String optString(String key) {
        return _members.optString(key);
    }

    protected String optString(String key, String defaultValue) {
        return _members.optString(key, defaultValue);
    }

    protected String optStringSafe(String key) {
        return _members.isNull(key) ? null : _members.optString(key);
    }

    protected String optStringSafe(String key, String defaultValue) {
        return _members.isNull(key) ? defaultValue : _members.optString(key, defaultValue);
    }

    protected JSONArray optArray(String key) {
        return _members.optJSONArray(key);
    }

    protected JSONObject optObject(String key) {
        return _members.optJSONObject(key);
    }

    protected Object opt(String key) {
        return _members.opt(key);
    }

    protected boolean hasKey(String key) {
        try {
            return _members.has(key);
        } catch (Exception exc) {
            return false;
        }
    }

    public JSONObject toJObject() {
        return _members;
    }

    public int size() {
        return _members.length();
    }

    @Override
    public final String toString() {
        JSONObject object = toJObject();
        return object != null ? object.toString() : new JSONObject().toString();
    }

    protected <T> List<T> getFromArray(String key, IListCreator<T> creator) {
        return JsonHelper.getFromArray(optArray(key), creator);
    }

    protected <T> List<T> getFromMap(String key, IListCreator<T> creator) {
        return JsonHelper.getFromMap(optObject(key), creator);
    }

    protected List<String> getFromArray(String key) {
        return JsonHelper.getListString(optArray(key));
    }

    public interface IListCreator<T> {
        public List<T> newList();

        public T newItem(String key, JSONObject object);
    }

    public interface IListsCreator<T> {
        List<List<T>> newList();

        List<T> newItems(String key, JSONArray array);
    }

    public interface IMapCreator<K, V> {
        Map<K, V> newMap();

        void newEntry(Map<K, V> map, K key, JSONObject object);
    }

    public interface IMapArrayCreator<K, V> {
        Map<K, V> newMap();

        void newEntry(Map<K, V> map, K key, JSONArray object);
    }

    public interface ISparseCreator<V> {
        SparseArray<V> newArray();

        void newEntry(SparseArray<V> array, JSONObject object);
    }

    public interface IInstanceCreator<V> {
        V create(String json) throws JSONException;
        V createDefault();
    }
}
