package com.example.listdemo.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.listdemo.R;
import com.example.listdemo.activities.DetailActivity;
import com.example.listdemo.adaptor.DataModelAdapter;
import com.example.listdemo.dto.Resource;
import com.example.listdemo.model.DataModel;
import com.example.listdemo.room.repository.AboutCanadaRepository;
import com.example.listdemo.viewmodel.ListViewModel;

import java.util.List;

public class ListFragment extends Fragment {

    private ListViewModel mViewModel;
    private DataModelAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayout llProgress;
    private AboutCanadaRepository aboutCanadaRepository;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutCanadaRepository = new AboutCanadaRepository(getContext());
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
        mAdapter = new DataModelAdapter(getContext(), new DataModelAdapter.DataModelClickListener() {
            @Override
            public void onClick(DataModel dataModel) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("data", dataModel);
                startActivity(intent);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        llProgress.setVisibility(View.VISIBLE);
        aboutCanadaRepository.getAboutCanadas().observe(getViewLifecycleOwner(), new Observer<Resource<List<DataModel>>>() {
            @Override
            public void onChanged(Resource<List<DataModel>> listResource) {
                llProgress.setVisibility(View.GONE);
                mAdapter.setData(listResource.getData());
            }
        });
    }
}
