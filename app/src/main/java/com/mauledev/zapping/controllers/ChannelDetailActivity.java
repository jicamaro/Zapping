package com.mauledev.zapping.controllers;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauledev.zapping.R;


public class ChannelDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView backdrop;
    TextView channelName;
    TextView channelStatus;
    FloatingActionButton fab;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("object");

        toolbar = (Toolbar) findViewById(R.id.activity_channel_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backdrop = (ImageView) findViewById(R.id.activity_channel_detail_backdrop);
        if(Build.VERSION.SDK_INT >= 21){
            backdrop.setImageDrawable(getResources().getDrawable(bundle.getInt("channelIcon"), null));
        }
        else{
            backdrop.setImageDrawable(getResources().getDrawable(bundle.getInt("channelIcon")));
        }
        channelStatus = (TextView) findViewById(R.id.channel_detail_status);
        channelStatus.setText(getResources().getText(bundle.getInt("channelStatus")));

        fab = (FloatingActionButton) findViewById(R.id.channel_detail_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChannelDetailActivity.this, MediaPlayerActivity.class);
                intent.putExtra("channelStream", bundle.getString("channelStream"));
                startActivity(intent);
            }
        });
    }
}
