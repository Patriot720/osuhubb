package example.cerki.osuhub;

import com.nytimes.android.external.store3.base.Parser;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.util.ParserException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import example.cerki.osuhub.List.Player;
import okio.BufferedSource;

import static junit.framework.Assert.assertEquals;

/**
 * Created by cerki on 06-Dec-17.
 */

@RunWith(RobolectricTestRunner.class)
public class StoreTest {


        @Test
        public void store() throws Exception {
            Store<Player,String> store = StoreBuilder.<String, BufferedSource,Player>parsedWithKey()
                    .fetcher(new OsuAPI()::getBufferedSourceAsync)
                    .parser(new PlayerParser()) // TODO custom persister;
                    .open();
            Player player = store.get("124493").blockingGet();
            assertEquals(player.getUsername(),"Cookiezi");
        }
        static class PlayerParser implements Parser<BufferedSource,Player> {

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