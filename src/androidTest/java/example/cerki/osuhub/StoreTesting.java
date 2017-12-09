package example.cerki.osuhub;

import com.nytimes.android.external.fs3.FileSystemPersister;
import com.nytimes.android.external.fs3.filesystem.FileSystemFactory;
import com.nytimes.android.external.store3.base.Parser;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.util.ParserException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import example.cerki.osuhub.List.Player;
import io.reactivex.Single;
import okio.BufferedSource;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;

public class StoreTesting {
    @Test
    public void store() throws Exception {
        Store<Player,String> store = StoreBuilder.<String,BufferedSource,Player>parsedWithKey()
                .fetcher(new OsuAPI()::getBufferedSourceAsync)
                .persister(FileSystemPersister.create(FileSystemFactory.create(getTargetContext().getFilesDir()),path->path))
                .parser(new PlayerParser())
                .open();
        Single<Player> single = store.get("5187234");
        assertEquals(single.blockingGet().getUsername(),"Tanaka Aiko");
    }
    static class PlayerParser implements Parser<BufferedSource,Player>{

        @Override
        public Player apply(BufferedSource bufferedSource) throws ParserException {
            try {
                String s = bufferedSource.readUtf8();
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject != null)
                return new Player(jsonObject);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return new Player();
        }
    }
}


