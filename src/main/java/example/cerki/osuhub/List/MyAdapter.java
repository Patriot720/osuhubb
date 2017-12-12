package example.cerki.osuhub.List;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by cerki on 12.12.2017.
 */

public class MyAdapter extends FlexibleAdapter {
    public MyAdapter(@Nullable List items) {
        super(items);
    }

    public MyAdapter(@Nullable List items, @Nullable Object listeners) {
        super(items, listeners);
    }

    @Override
    protected void onLoadMore(int position) {
        int currentPage = getItemCount() / 50 + 1;
        mHandler.post(()->{
            mHandler.removeMessages(LOAD_MORE_COMPLETE);
        });

    }
}
