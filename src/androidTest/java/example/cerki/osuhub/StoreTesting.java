package example.cerki.osuhub;

import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;

import org.junit.Test;

import example.cerki.osuhub.List.Player;
import io.reactivex.Single;

public class StoreTesting {
    @Test
    public void store() throws Exception {
        Store<Player,String> store = StoreBuilder.<String,Player>key()
                .fetcher(new OsuAPI()::getPlayerAsync)
                .open();
        Single<Player> single = store.get("5187234");
        single.blockingGet();
    }
}