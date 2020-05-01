package com.example.listdemo.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listdemo.databinding.ProgressListItemBinding;
import com.example.listdemo.databinding.ViewRepoListItemBinding;
import com.example.listdemo.utils.NetworkState;
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
            ProgressListItemBinding binding = ProgressListItemBinding.inflate(LayoutInflater.from(context), parent, false);
            return new NetworkStateItemViewHolder(binding);
        } else {
            ViewRepoListItemBinding binding = ViewRepoListItemBinding.inflate(LayoutInflater.from(context), parent, false);
            return new RepoViewHolder(binding);
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
        private ViewRepoListItemBinding binding;
        private Record repo;

        RepoViewHolder(ViewRepoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            /*itemView.setOnClickListener(v -> {
                if(repo != null) {
                    repoSelectedListener.onRepoSelected(repo);
                }
            });*/
        }

        void bind(Record repo) {
            this.repo = repo;
            binding.tvRepoName.setText(repo.getQuarter());
            binding.tvRepoDescription.setText(repo.getVolumeOfMobileData());
            binding.tvForks.setText(String.valueOf(repo.getId()));

        }
    }


    /*
     * We define A custom ViewHolder for the progressView
     */
    public static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {


        private com.example.listdemo.databinding.ProgressListItemBinding binding;

        public NetworkStateItemViewHolder(ProgressListItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMsg());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
