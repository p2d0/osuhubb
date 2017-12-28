package example.cerki.osuhub.List;

import android.os.Bundle;

import example.cerki.osuhub.API.ApiDatabase.ApiDatabase;
import example.cerki.osuhub.API.POJO.User;
import example.cerki.osuhub.GenericRecyclerFragment;
import example.cerki.osuhub.PlayerFragment.PlayerFragment;
import example.cerki.osuhub.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("unchecked")
public class ListFragment extends GenericRecyclerFragment<User>{
    @Override
    protected int getEndlessScrollPageSize() {
        return 0;
    }

    @Override
    protected void onItemClick(User item) {
        android.support.v4.app.Fragment fragment = PlayerFragment.newInstance(item.getUserId(), item.getUsername());
        getFragmentManager().beginTransaction().add(R.id.content_main, fragment)
                .addToBackStack("stack")
                .commit();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_player_list;
    }

    @Override
    public void initDataDatabase() {
        Single.fromCallable(()-> ApiDatabase.getInstance().userDao().getAll())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((items)->{
                    onUpdate(items);
                    updateData();
                });
    }

    @Override
    public void updateData() {
        new Task(this::onUpdate).loadUsers();
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        new Task(getAdapter()::onLoadMoreComplete).loadUsersFromPage(currentPage+1);
    }
}
