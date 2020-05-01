package com.example.listdemo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.listdemo.adaptor.FeedListAdapter;
import com.example.listdemo.databinding.ListFragmentBinding;
import com.example.listdemo.viewmodel.ListViewModel;

public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    private ListViewModel mViewModel;
    private FeedListAdapter mAdapter;
    private ListFragmentBinding binding;

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
        binding = ListFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        setupListUpdate();
    }


    private void setupListUpdate() {
        binding.llProgress.setVisibility(View.VISIBLE);
        mAdapter = new FeedListAdapter(getContext());
        binding.mRecyclerView.setHasFixedSize(true);
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.llProgress.setVisibility(View.VISIBLE);
        mViewModel.getArticleLiveData().observe(getViewLifecycleOwner(), records -> {
            binding.llProgress.setVisibility(View.GONE);
            mAdapter.submitList(records);
        });
        mViewModel.getNetworkState().observe(this, networkState -> {
            mAdapter.setNetworkState(networkState);
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
