package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSON {
    public static <T> List<T> jsonArrToList(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static <T> Set<T> jsonArrToSet(String json) {
        Gson gson = new Gson();
        Type setType = new TypeToken<Set<T>>(){}.getType();
        return gson.fromJson(json, setType);
    }

    public static <T> String stringify(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static <K, V> String stringify(Map<K, List<V>> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static String stringify(String str) {
        return new Gson().toJson(str);
    }
}
