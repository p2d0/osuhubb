package example.cerki.osuhub.PlayerFragment.RecentPlays;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import example.cerki.osuhub.API.OsuAPI;
import example.cerki.osuhub.API.POJO.BestScore;
import example.cerki.osuhub.Feed.FeedItem;
import example.cerki.osuhub.MainActivity;
import example.cerki.osuhub.PlayerFragment.TopPlays.PagerRecyclerFragment;
import example.cerki.osuhub.R;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by cerki on 25.12.2017.
 */
@SuppressWarnings("unchecked")
public class RecentScoresFragment extends PagerRecyclerFragment<FeedItem> {

    @Override
    public boolean setListener(MainActivity context) {
        setListener(context::feedFragmentListener);
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_feeditem_list;
    }

    @Override
    public void initDataDatabase() {
        OsuAPI.getApi().getRecentScoresBy(mUserId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(items -> {new RecentScoresTask(this::onUpdate,items).execute(0);setItems(items);});
    }
    private static final String ARG_USER_ID = "user_id";
    private static final java.lang.String ARG_USERNAME = "username";
    private int mUserId = 0;
    private String mUsername;
    @SuppressWarnings("unused")
    public static RecentScoresFragment newInstance(int userId, String username) {
        RecentScoresFragment fragment = new RecentScoresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_USER_ID);
            mUsername = getArguments().getString(ARG_USERNAME);
        }
    }
    @Override
    protected void doAfterViewCreated() {
       FloatingActionButton fab =  getActivity().findViewById(R.id.fab);
       fab.setImageResource(R.drawable.common_full_open_on_phone); // todo change this
        fab.setOnClickListener(view -> {
            removeFailedScores();
            new RecentScoresTask(this::onUpdate,getItems()).execute(0);
        });
    }

    public void removeFailedScores() {
        List<BestScore> items = getItems();
        List<BestScore> newItems = new ArrayList<>();
        for (BestScore item : items)
            if(!item.getRank().equals("F"))
                newItems.add(item);
        setItems(newItems);
    }

    @Override
    public void updateData() {
        initDataDatabase();
    }
}
