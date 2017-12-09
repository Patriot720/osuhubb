package example.cerki.osuhub;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by cerki on 09-Dec-17.
 */

public class NewDatabase {
    private static final String DATABASE_NAME = "new-db" ;
    private static NewDb newDb;
    private NewDatabase() {

    }
    public static void newInstance(Context context){
        newDb = Room.databaseBuilder(context, NewDb.class, DATABASE_NAME).build();
    }
    public static NewDb getInstance(){
        return newDb;
    }
}
