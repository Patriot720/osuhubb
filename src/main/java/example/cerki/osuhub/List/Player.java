package example.cerki.osuhub.List;

import android.annotation.SuppressLint;
import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static example.cerki.osuhub.Columns.ACTIVITY;
import static example.cerki.osuhub.Columns.COUNTRY;
import static example.cerki.osuhub.Columns.ID;
import static example.cerki.osuhub.Columns.PP;
import static example.cerki.osuhub.Columns.USERNAME;

/**
 * Created by cerki on 30-Nov-17.
 */


public class Player {
    private int id;
    private String username;
    private String country;
    //comment
    private boolean activity;
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;
     private Map<String,Double> comparable;

     public String getDifferenceString(String key){
         return toString(difference.get(key));
     }

    private Map<String,Double> difference;
    // TODO Add difference
    public void compare(Player p){
        if(p.id != id)
            return;
        for(String val : getKeySet()){
            Double firstVal = p.getDouble(val);
            Double secondVal = getDouble(val);
            difference.put(val,secondVal - firstVal);
        }
    }
    public Double getDifferenceDouble(String key){
        return difference.get(key);
    }
    public Player(int id) {
        this();
        this.id = id;
    }
    public Double getDouble(String key){
        return comparable.get(key);
    }
    public Player(Cursor query){
        this();
        if(query.moveToNext()) {
            int id = query.getInt(query.getColumnIndex(ID));
            String username = query.getString(query.getColumnIndex(USERNAME));
            String country = query.getString(query.getColumnIndex(COUNTRY));
            Boolean activity = query.getInt(query.getColumnIndex(ACTIVITY)) != 0;
            this.id = id;
            this.country = country;
            this.username = username;
            this.activity = activity;
            int startIndex = query.getColumnIndex(PP);
            for (int i = startIndex; i < query.getColumnCount(); i++) {
                String columnName = query.getColumnName(i);
                Double value = query.getDouble(i);
                setComparable(columnName, value);
            }
            query.close();
        }
    }

    public Player(String user_id) {
        this(Integer.parseInt(user_id.replaceAll("[^0-9]","")));
    }


    public Player() {
    comparable = new HashMap<>();
    difference = new HashMap<>();
    activity = ACTIVE;
    }

    public int getId(){
        return this.id;
    }
    public void setComparable(String key, Double value){
        this.comparable.put(key,value);
    }
    public void setComparable(String key, String value){
        this.comparable.put(key,Double.valueOf(value.replaceAll("[^0-9.]","")));
    }
    public String getString(String key) {
        Double val = this.comparable.get(key);
        return toString(val);
    }
    @SuppressLint("DefaultLocale")
    private String toString(Double val) {
        if(val == null)
            return "";
        if(val % 1 == 0)
            return String.valueOf(val.intValue());
        return String.format("%.2f",val);
    }

    public Set<String> getKeySet(){
        return comparable.keySet();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public boolean getActivity() {
        return activity;
    }
}
