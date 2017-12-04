package example.cerki.osuhub;

import java.util.HashMap;

/**
 * Created by cerki on 03-Dec-17.
 */

public class Score extends  HashMap<String,String>{
    public int getAsInt(String key) {
        return Integer.parseInt(get(key));
    }
}
