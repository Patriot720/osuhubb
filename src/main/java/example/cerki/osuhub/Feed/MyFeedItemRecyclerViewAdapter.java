package example.cerki.osuhub.Feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.cerki.osuhub.Feed.FeedItemFragment.OnListFragmentInteractionListener;
import example.cerki.osuhub.R;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<FeedItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFeedItemRecyclerViewAdapter(List<FeedItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FeedItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }
}
