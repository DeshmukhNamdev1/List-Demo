package com.example.listdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listdemo.R;
import com.example.listdemo.databinding.ActivityDetailBinding;
import com.example.listdemo.model.DataModel;

public class DetailActivity extends AppCompatActivity {

    private DataModel dataModel;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        if (getIntent().getExtras() != null)
            dataModel = getIntent().getParcelableExtra("data");

        if (dataModel.getUrl() != null) {
            Glide
                    .with(this)
                    .load(dataModel.getThumbnailUrl())
                    .centerCrop()
                    .into(binding.ivImage);
            binding.ivImage.setVisibility(View.VISIBLE);
        } else {
            binding.ivImage.setVisibility(View.GONE);
        }
        binding.tvTitle.setText(dataModel.getTitle());
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
