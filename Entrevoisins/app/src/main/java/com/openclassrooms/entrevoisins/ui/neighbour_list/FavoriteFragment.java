package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_details.NeighbourDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements MyNeighbourFavoriteRecyclerViewAdapter.OnNeighbourClickListener {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavorites;
    private RecyclerView mRecyclerView;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_neighbour_favorite, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        mFavorites = mApiService.getFavorites();
        MyNeighbourFavoriteRecyclerViewAdapter adapter = new MyNeighbourFavoriteRecyclerViewAdapter(mFavorites, this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDeleteNeighbour(Neighbour neighbour) {
        mApiService.deleteFavorite(neighbour);
        initList();
        Context supContext = getContext();
        CharSequence text = "Favoris supprimé";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(supContext, text, duration);
        toast.show();
    }

    @Override
    public void onNeighbourClicked(Neighbour neighbour) {
        Intent intent = new Intent(getContext(), NeighbourDetailsActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("KEY_NEIGHBOUR", neighbour);
        intent.putExtras(args);
        startActivity(intent);
    }

    @Subscribe
    public void onParentNeighbourDeleted(DeleteNeighbourEvent event) {
        initList();
    }
}
