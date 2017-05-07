package com.example.haoyuban111.mubanapplication.rest;

import android.text.TextUtils;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class JsonHelper {

    public interface IJson {
        JSONObject toJObject() throws JSONException;
    }

    public static <T> List<T> getFromArray(JSONArray array, JsonEntry.IListCreator<T> creator) {
        List<T> result = creator.newList();
        if (array != null) {
            final int length = array.length();
            for (int i = 0; i < length; i++) {
                final JSONObject object = array.optJSONObject(i);
                if (object != null) {
                    final T item = creator.newItem(null, object);
                    if (item != null)
                        result.add(item);
                }
            }
        }
        return result;
    }

    public static <T extends IComposite<T>> List<T> getComposite(JSONArray array, JsonEntry.IListCreator<T> creator) {
        List<T> result = creator.newList();
        if (array != null) {
            final int length = array.length();
            T parent = null;
            for (int i = 0; i < length; i++) {
                final JSONObject object = array.optJSONObject(i);
                if (object != null) {
                    final T item = creator.newItem(null, object);
                    if (item != null) {
                        if (item.isParent()) {
                            parent = item;
                            result.add(item);
                        } else {
                            if (parent != null) {
                                parent.addSubItem(item);
                            } else {
                                result.add(item);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static <T> List<List<T>> getFromArrays(JSONArray array, JsonEntry.IListsCreator<T> creator) {
        List<List<T>> result = creator.newList();
        if (array != null) {
            final int length = array.length();
            for (int i = 0; i < length; i++) {
                final JSONArray subArray = array.optJSONArray(i);
                if (subArray != null) {
                    final List<T> items = creator.newItems(null, subArray);
                    if (items != null)
                        result.add(items);
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getFromMap(JSONObject object, JsonEntry.IListCreator<T> creator) {
        List<T> result = creator.newList();
        if (object != null) {
            final Iterator<String> iterator = object.keys();
            while (iterator.hasNext()) {
                final String itemKey = iterator.next();
                final JSONObject jItem = object.optJSONObject(itemKey);
                if (jItem != null) {
                    final T item = creator.newItem(itemKey, jItem);
                    if (item != null)
                        result.add(item);
                }
            }
        }
        return result;
    }


    public static List<String> getListString(JSONArray array) {
        List<String> result = new ArrayList<String>();
        if (array != null) {
            final int length = array.length();
            for (int i = 0; i < length; i++) {
                final String item = array.optString(i);
                if (item != null) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    public static List<Integer> getListInteger(JSONArray array) {
        List<Integer> result;
        if (array != null) {
            final int length = array.length();
            result = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                result.add(array.optInt(i));
            }
        } else {
            result = new ArrayList<Integer>(0);
        }
        return result;
    }

    public static int[] getArrayInteger(JSONArray array) {
        int[] result;
        if (array != null) {
            final int length = array.length();
            result = new int[length];
            for (int i = 0; i < length; i++) {
                result[i] = array.optInt(i);
            }
        } else {
            result = new int[0];
        }
        return result;
    }

    public static String[] getArrayString(JSONArray array) {
        String[] result;
        if (array != null) {
            final int length = array.length();
            result = new String[length];
            for (int i = 0; i < length; i++) {
                result[i] = array.optString(i);
            }
        } else {
            result = new String[0];
        }
        return result;
    }

    public static <T extends IJson> JSONArray toArray(Collection<T> items) {
        JSONArray array = new JSONArray();
        try {
            for (T item : items) {
                array.put(item.toJObject());
            }
        } catch (Exception ex) {
//            LogWriter.e(ex);
        }
        return array;
    }

    public static <T extends IJson> JSONObject toObject(Map<String, T> items) throws JSONException {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, T> entry : items.entrySet()) {
            object.put(entry.getKey(), entry.getValue().toJObject());
        }
        return object;
    }

    public static <T> SparseArray<T> getSparseArray(JSONArray array, JsonEntry.ISparseCreator<T> creator) throws JSONException {

        SparseArray<T> result = null;
        int length;
        if (array != null && (length = array.length()) > 0) {
            result = creator.newArray();
            for (int i = 0; i < length; i++) {
                creator.newEntry(result, array.getJSONObject(i));
            }
        }
        return result;
    }

    public static <V> Map<Integer, V> getMap(JSONArray array, JsonEntry.IMapCreator<Integer, V> creator) throws JSONException {

        Map<Integer, V> result = null;
        int length;
        if (array != null && (length = array.length()) > 0) {
            result = creator.newMap();
            for (int i = 0; i < length; i++) {
                creator.newEntry(result, i, array.getJSONObject(i));
            }
        }
        return result;
    }

    public static <V> Map<String, V> getMapString(JSONArray array, JsonEntry.IMapCreator<String, V> creator) throws JSONException {
        Map<String, V> result = null;
        int length;
        if (array != null && (length = array.length()) > 0) {
            result = creator.newMap();
            for (int i = 0; i < length; i++) {
                creator.newEntry(result, "", array.getJSONObject(i));
            }
        }
        return result;
    }

    public static <V> Map<String, V> getMapItem(JSONObject object, JsonEntry.IMapCreator<String, V> creator) {
        Map<String, V> result = creator.newMap();

        if (object != null) {
            try {
                creator.newEntry(result, object.getString("userID"), object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static <V> Map<String, V> getMap(JSONObject object, JsonEntry.IMapCreator<String, V> creator) {
        Map<String, V> result = creator.newMap();

        if (object != null) {
            final Iterator keys = object.keys();
            while (keys.hasNext()) {
                final String next = (String) keys.next();
                creator.newEntry(result, next, object.optJSONObject(next));
            }
        }
        return result;
    }

    public static Map<String, String> getMap(JSONObject object) {
        Map<String, String> result = new HashMap<String, String>();
        if (object != null) {
            final Iterator keys = object.keys();
            while (keys.hasNext()) {
                final String next = (String) keys.next();
                result.put(next, object.optString(next));
            }
        }
        return result;
    }

    public static Map<String, Integer> getMapInt(JSONObject object) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        if (object != null) {
            final Iterator keys = object.keys();
            while (keys.hasNext()) {
                final String next = (String) keys.next();
                result.put(next, object.optInt(next));
            }
        }
        return result;
    }

    public static <V> Map<String, V> getMapArrays(JSONObject object, JsonEntry.IMapArrayCreator<String, V> creator) {
        Map<String, V> result = creator.newMap();

        if (object != null) {
            final Iterator keys = object.keys();
            while (keys.hasNext()) {
                final String next = (String) keys.next();
                creator.newEntry(result, next, object.optJSONArray(next));
            }
        }
        return result;
    }

    public static <T> T newInstance(String json, JsonEntry.IInstanceCreator<T> creator) {
        T result;
        if (TextUtils.isEmpty(json)) {
            result = creator.createDefault();
        } else {
            try {
                result = creator.create(json);
            } catch (Exception ex) {
//                LogWriter.e(ex);
                result = creator.createDefault();
            }
        }
        return result;
    }
}

