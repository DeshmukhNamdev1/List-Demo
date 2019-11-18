package com.example.listdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listdemo.R;
import com.example.listdemo.model.DataModel;

public class DetailActivity extends AppCompatActivity {

    private DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolbar();
        if (getIntent().getExtras() != null)
            dataModel = getIntent().getParcelableExtra("data");
        TextView tvTitle=(TextView) findViewById(R.id.tvTitle);
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);

        if (dataModel.getUrl() != null) {
            Glide
                    .with(this)
                    .load(dataModel.getThumbnailUrl())
                    .centerCrop()
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        tvTitle.setText(dataModel.getTitle());
    }
    private void initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
