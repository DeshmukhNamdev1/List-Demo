package com.example.listdemo.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdemo.R;
import com.example.listdemo.fragments.util.NetworkState;
import com.example.listdemo.model.Record;


public class FeedListAdapter extends PagedListAdapter<Record, RecyclerView.ViewHolder> {

    /*
     * There are two layout types we define
     * in this adapter:
     * 1. progrss view
     * 2. data view
     */
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;

    /*
     * The DiffUtil is defined in the constructor
     */
    public FeedListAdapter(Context context) {
        super(Record.DIFF_CALLBACK);
        this.context = context;
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_PROGRESS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_list_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
            return new RepoViewHolder(view);
        }
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoViewHolder) {
            ((RepoViewHolder) holder).bind(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    /*
     * Default method of RecyclerView.Adapter
     */
    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        TextView repoNameTextView;
        TextView repoDescriptionTextView;
        TextView forksTextView;
        TextView starsTextView;

        private Record repo;

        RepoViewHolder(View itemView) {
            super(itemView);
            repoNameTextView = itemView.findViewById(R.id.tv_repo_name);
            repoDescriptionTextView = itemView.findViewById(R.id.tv_repo_description);
            forksTextView = itemView.findViewById(R.id.tv_forks);
            starsTextView = itemView.findViewById(R.id.tv_stars);
            /*itemView.setOnClickListener(v -> {
                if(repo != null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });*/
        }

        void bind(Record repo) {
            this.repo = repo;
            repoNameTextView.setText(repo.getQuarter());
            repoDescriptionTextView.setText(repo.getVolumeOfMobileData());
            forksTextView.setText(String.valueOf(repo.getId()));

        }
    }


    /*
     * We define A custom ViewHolder for the progressView
     */
    public static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        TextView errorMsg;
        ProgressBar progressBar;

        public NetworkStateItemViewHolder(View view) {
            super(view);
            errorMsg=view.findViewById(R.id.errorMsg);
            progressBar=view.findViewById(R.id.progressBar);

        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
