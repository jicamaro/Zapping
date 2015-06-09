package com.mauledev.zapping.controllers;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.mauledev.zapping.R;

import java.io.IOException;


public class MediaPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, View.OnClickListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    View mDecorView;
    SurfaceView player;
    SurfaceHolder holder;
    MediaPlayer mediaPlayer;
    String url;
    ImageButton playPause;
    Toolbar toolbar;
    LinearLayout progressLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        mDecorView = getWindow().getDecorView();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        url = intent.getStringExtra("channelStream");

        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        playPause = (ImageButton) findViewById(R.id.play_pause_button);
        playPause.setOnClickListener(this);

        player = (SurfaceView) findViewById(R.id.player);
        holder = player.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(holder);
        try {
            mediaPlayer.setDataSource(this, Uri.parse(url));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        progressLayout.setVisibility(View.GONE);
        mediaPlayer.start();
        if(Build.VERSION.SDK_INT >= 21){
            playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause_white_36dp, null));
        }
        else{
            playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause_white_36dp));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public void onClick(View v) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            if(Build.VERSION.SDK_INT >= 21){
                playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_play_arrow_white_36dp, null));
            }
            else{
                playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_play_arrow_white_36dp));
            }
        }
        else{
            mediaPlayer.prepareAsync();
            progressLayout.setVisibility(View.VISIBLE);
            if(Build.VERSION.SDK_INT >= 21){
                playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause_white_36dp, null));
            }
            else{
                playPause.setImageDrawable(getResources().getDrawable(R.mipmap.ic_pause_white_36dp));
            }
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
