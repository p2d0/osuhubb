package example.cerki.osuhub.Feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import example.cerki.osuhub.Feed.FeedItemFragment.OnListFragmentInteractionListener;
import example.cerki.osuhub.Mods;
import example.cerki.osuhub.R;
import example.cerki.osuhub.Score;
import example.cerki.osuhub.Util;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFeedItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFeedItemRecyclerViewAdapter.ViewHolder> {

    private final List<FeedItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyFeedItemRecyclerViewAdapter(List<FeedItem> items, Context mContext, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_feeditem, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedItem feedItem = mValues.get(viewHolder.getAdapterPosition());
                mListener.onListFragmentInteraction(feedItem);
            }
        });
        return viewHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        FeedItem item = mValues.get(position);
        holder.mPerformance.setText(String.format("%sPP", item.score.getAsInt(Score.PP)));
        holder.mAccuracy.setText(Util.getAccuracyString(item.score));
        holder.mMissCount.setText(String.format("%sxMiss", item.score.get(Score.MISS)));
        holder.mCombo.setText(String.format("%s/%s", item.score.get(Score.COMBO), item.beatmap.get(Beatmap.MAX_COMBO)));
        holder.mMods.setText(Mods.parseFlags(item.score.get(Score.MODS)));
        holder.mUsername.setText(item.username);
        holder.mMapName.setText(String.format("%s[%s]",item.beatmap.get(Beatmap.MAP_NAME),item.beatmap.get(Beatmap.DIFFICULTY_NAME)));
        holder.mStarRate.setText(String.format("%.2f",item.beatmap.getAsDouble(Beatmap.STAR_RATING))); // TODO Calculate for MODS
        Util.setImageFromAsset(mContext,holder.mRank,item.score.get(Score.RANK) + ".png"); // Todo :thinking:
        holder.mRelativeDate.setText(item.relativeDate);
        Glide.with(holder.mView)
                .load(item.coverUrl)
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .transition(withCrossFade())
                .into(holder.mCover);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FeedItem mItem;
        public TextView mPerformance;
        public TextView mCombo;
        public TextView mUsername;
        public TextView mMapName; // TODO bad naming it's map_name + difficulty name
        public TextView mMissCount;
        public TextView mMods;
        public TextView mAccuracy;
        public TextView mStarRate;
        public TextView mRelativeDate;
        public ImageView mCover;
        public ImageView mRank;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPerformance = view.findViewById(R.id.pp);
            mCombo = view.findViewById(R.id.combo);
            mUsername = view.findViewById(R.id.username);
            mMapName = view.findViewById(R.id.map_name);
            mMissCount = view.findViewById(R.id.miss_count);
            mMods = view.findViewById(R.id.mods);
            mAccuracy = view.findViewById(R.id.acc);
            mStarRate = view.findViewById(R.id.star_rate);
            mRelativeDate = view.findViewById(R.id.relative_date);
            mCover = view.findViewById(R.id.cover);
            mRank = view.findViewById(R.id.rank);
        }

    }
}
