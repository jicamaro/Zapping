package com.mauledev.zapping.model;

import com.mauledev.zapping.R;

public class Channel {

    private Integer id;
    private String name;
    private String channel;
    private int status;
    private String streamHttp;
    private int icon;

    public Channel(int id, String name, String channel, String streamHttp, int iconId) {
        this.id = id;
        this.name = name;
        this.channel = channel;
        this.streamHttp = streamHttp;
        this.icon = iconId;
        this.status = R.string.channel_detail_status_offline;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }

    public String getStreamHttp() {
        return streamHttp;
    }

    public int getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }
}
