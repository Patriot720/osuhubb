package example.cerki.osuhub.List;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cerki on 30-Nov-17.
 */


public class Player {
    private int id;
    private String username;
    private String country;
    private boolean activity;
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;
    private Map<String,Double> comparable;
    // TODO Add difference

    public Player(int id) {
        this.id = id;
        this.comparable = new HashMap<>();
    }

    public Player(String user_id) {
        this(Integer.parseInt(user_id.replaceAll("[^0-9]","")));
    }

    public int getId(){
        return this.id;
    }
    public void set(String key, Double value){
        this.comparable.put(key,value);
    }
    public void set(String key,String value){
        this.comparable.put(key,Double.valueOf(value.replaceAll("[^0-9.]","")));
    }
    public String getString(String key) {
        Double val = this.comparable.get(key);
        if(val == null)
            return "";
        if(val % 1 == 0)
            return String.valueOf(val.intValue());
        return String.valueOf(val);
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
