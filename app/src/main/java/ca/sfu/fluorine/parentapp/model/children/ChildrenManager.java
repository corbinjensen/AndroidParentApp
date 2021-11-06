package ca.sfu.fluorine.parentapp.model.children;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *  Manages and manipulate children database.
 *
 *  This class follow the singleton design pattern
 *  A context of is needed to get the instance of this class
 */
public class ChildrenManager {
    private static ChildrenManager instance;
    private SharedPreferences preferences;
    private static final Gson gson = new Gson();
    private static final Type CHILDREN_LIST = new TypeToken<ArrayList<Child>>(){}.getType();

    private static final String KEY = "children";
    private static final String LAST_CHILD = "last_child";

    private List<Child> children = new ArrayList<>();

    private ChildrenManager() {}

    public static ChildrenManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChildrenManager();
        }
        instance.preferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return instance;
    }

    public List<Child> getChildren() {
        loadChildrenFromPreferences();
        return children;
    }

    public Child getChildByIndex(int index) {
        if (index < 0 || index >= children.size()) return null;
        return children.get(index);
    }

    public void addChild(String fName, String lName) {
        Child child = new Child(fName,lName);
        children.add(child);
        saveChildrenToPreferences();
    }


    public void modifyChild(int id, String fName, String lName) {
        children.set(id, new Child(fName, lName));
        saveChildrenToPreferences();
    }

    public void deleteChild(int id) {
        int lastChildId = getLastChildId();
        if (lastChildId == id) {
            preferences.edit().remove(LAST_CHILD).apply();
        } else if (lastChildId > id) {
            saveLastChildId(--lastChildId);
        }
        children.remove(id);
        saveChildrenToPreferences();
    }

    private void saveChildrenToPreferences() {
        String jsonData = gson.toJson(children);
        preferences.edit().putString(KEY, jsonData).apply();
    }

    private void loadChildrenFromPreferences() {
        children.clear();
        String jsonData = preferences.getString(KEY, "[]");
        children = gson.fromJson(jsonData, CHILDREN_LIST);
    }

    public int getLastChildId() {
        return preferences.getInt(LAST_CHILD, -1);
    }

    public void saveLastChildId(int childId) {
        preferences.edit().putInt(LAST_CHILD, childId).apply();
    }
}
