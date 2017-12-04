package example.cerki.osuhub.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import example.cerki.osuhub.List.Fragment.OnListFragmentInteractionListener;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static example.cerki.osuhub.Columns.*;
import static example.cerki.osuhub.Columns.PC;
import static example.cerki.osuhub.Columns.PP;
import static example.cerki.osuhub.Columns.RANK;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Player} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<Player> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    RecyclerAdapter(Context context, List<Player> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    void replaceData(final List<Player> players){
        final List<Player> oldData = new ArrayList<>(mValues);
        mValues.clear();

        if (players != null) {
            mValues.addAll(players);
        }

        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldData.size();
            }

            @Override
            public int getNewListSize() {
                return mValues.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldData.get(oldItemPosition).equals(mValues.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldData.get(oldItemPosition).equals(mValues.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.players_top_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Player player = mValues.get(position);
        holder.mItem = player;
        holder.mUsernameView.setText(player.getUsername());
        setImage(holder.mCountryImage,player.getCountry());
        holder.mPerformanceView.setText(player.getString(PP));
        holder.mAccuracyView.setText(String.format("%s%%", player.getString(ACC)));
        holder.mPlaycountView.setText(player.getString(PC));
        holder.mRankView.setText(String.format("#%s", player.getString(RANK)));

        Util.showPlayerDifference(holder.mPerformanceDifference,holder.mPerformanceArrow,player.getDifferenceDouble(PP));
        Util.showPlayerDifference(
                holder.mAccuracyDifference,
                holder.mAccuracyArrow,
                player.getDifferenceDouble(ACC)
        );
        Util.showPlayerDifference(
                holder.mPlaycountDifference,
                holder.mPlaycountArrow,
                player.getDifferenceDouble(PC)
        );
        Util.showPlayerDifference(
                holder.mRankDifference,
                holder.mRankArrow,
                 player.getDifferenceDouble(RANK)
        );

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
    private void setImage(ImageView destination, String source) {
        try {
            InputStream open = mContext.getAssets().open(source);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            destination.setImageBitmap(bitmap);
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUsernameView;
        public final TextView mRankView;
        public final TextView mPerformanceView;
        public final TextView mAccuracyView;
        public final TextView mPlaycountView;
        public final ImageView mCountryImage;

        public final TextView mPerformanceDifference;
        public final ImageView mPerformanceArrow;
        public final TextView mAccuracyDifference;
        public final ImageView mAccuracyArrow;
        public final TextView mPlaycountDifference;
        public final ImageView mPlaycountArrow;
        public final TextView mRankDifference;
        public final ImageView mRankArrow;

        public Player mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsernameView = view.findViewById(R.id.item_username);
            mRankView = view.findViewById(R.id.item_rank);
            mAccuracyView = view.findViewById(R.id.item_acc);
            mPerformanceView = view.findViewById(R.id.item_pp);
            mPlaycountView = view.findViewById(R.id.item_pc);
            mCountryImage = view.findViewById(R.id.item_country_image);

            mPerformanceDifference = view.findViewById(R.id.item_pp_difference);
            mPerformanceArrow = view.findViewById(R.id.item_pp_arrow);
            mAccuracyDifference = view.findViewById(R.id.item_acc_difference);
            mAccuracyArrow = view.findViewById(R.id.item_acc_arrow);
            mPlaycountDifference = view.findViewById(R.id.item_pc_difference);
            mPlaycountArrow = view.findViewById(R.id.item_pc_arrow);
            mRankDifference = view.findViewById(R.id.item_rank_difference);
            mRankArrow = view.findViewById(R.id.item_rank_arrow);
        }

    }
}
