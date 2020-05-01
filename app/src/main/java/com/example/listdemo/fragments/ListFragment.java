package com.example.listdemo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdemo.R;
import com.example.listdemo.viewmodel.ListViewModel;

public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    private ListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private LinearLayout llProgress;
    private FeedListAdapter mAdapter;

   /* public static ListFragment newInstance() {
        return new ListFragment();
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        llProgress = (LinearLayout) view.findViewById(R.id.llProgress);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        setupListUpdate();
    }


    private void setupListUpdate() {
        llProgress.setVisibility(View.VISIBLE);
        mAdapter = new FeedListAdapter(getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        llProgress.setVisibility(View.VISIBLE);
        mViewModel.getArticleLiveData().observe(getViewLifecycleOwner(), records -> {
            llProgress.setVisibility(View.GONE);
            mAdapter.submitList(records);
        });
        mViewModel.getNetworkState().observe(this, networkState -> {
            mAdapter.setNetworkState(networkState);
        });

    }
}
