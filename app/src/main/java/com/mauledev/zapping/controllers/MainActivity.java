package com.mauledev.zapping.controllers;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mauledev.zapping.R;
import com.mauledev.zapping.model.Channel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ChannelDataAdapter channelDataAdapter;
    String[] channelNamesArray;
    String[] channelNumbersArray;
    TypedArray channelIconsArray;
    String[] channelStreamsArray;
    ArrayList<Channel> channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        channelNamesArray = getResources().getStringArray(R.array.channels_names_array);
        channelNumbersArray = getResources().getStringArray(R.array.channels_numbers_array);
        channelStreamsArray = getResources().getStringArray(R.array.channels_streams_array);
        channelIconsArray = getResources().obtainTypedArray(R.array.channels_icons_array);

        channels = new ArrayList<Channel>(channelNamesArray.length);
        for(int i=0;i<channelNamesArray.length;i++){
            Channel aux = new Channel(i, channelNamesArray[i], channelNumbersArray[i], channelStreamsArray[i], channelIconsArray.getResourceId(i, -1));
            channels.add(aux);
        }
        channelIconsArray.recycle();

        channelDataAdapter = new ChannelDataAdapter(this, channels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(channelDataAdapter);
    }
}
