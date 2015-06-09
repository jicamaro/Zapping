package com.mauledev.zapping.controllers;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauledev.zapping.R;
import com.mauledev.zapping.model.Channel;

import java.util.ArrayList;

public class ChannelDataAdapter extends RecyclerView.Adapter<ChannelDataAdapter.ChannelDataViewHolder> {

    private Context context;
    private ArrayList<Channel> list;
    private Activity activity;

    public static class ChannelDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView channelCard;
        private TextView channelName;
        private TextView channelNumber;
        private TextView channelStatus;
        private ImageView channelImage;
        private OnRowListener channelListener;

        public ChannelDataViewHolder(View itemView) {
            super(itemView);
            channelCard = (CardView) itemView.findViewById(R.id.channel_row_detail_card);
            channelImage = (ImageView) itemView.findViewById(R.id.channel_row_detail_image);
            channelName = (TextView) itemView.findViewById(R.id.channel_row_detail_title);
            channelStatus = (TextView) itemView.findViewById(R.id.channel_row_detail_status);
            channelNumber = (TextView) itemView.findViewById(R.id.channel_row_detail_number);
            channelCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            channelListener.onRowClickListener(v);
        }
    }

    public ChannelDataAdapter(Activity activity, ArrayList<Channel> list) {
        this.list = list;
        this.activity = activity;
        this.context = activity;
    }

    @Override
    public ChannelDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_row_layout, null);
        return new ChannelDataViewHolder(v){

            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                CardView channelCard = (CardView) v.findViewById(R.id.channel_row_detail_card);
                ImageView channelImage = (ImageView) v.findViewById(R.id.channel_row_detail_image);
                TextView channelName = (TextView) v.findViewById(R.id.channel_row_detail_title);
                TextView channelStatus = (TextView) v.findViewById(R.id.channel_row_detail_status);
                TextView channelNumber = (TextView) v.findViewById(R.id.channel_row_detail_number);

                Intent intent = new Intent(context, ChannelDetailActivity.class);
                Bundle bundle = new Bundle();

                Channel aux = list.get((Integer) channelCard.getTag());
                bundle.putString("channelName", aux.getName());
                bundle.putInt("channelStatus", aux.getStatus());
                bundle.putString("channelNumber", aux.getChannel());
                bundle.putInt("channelIcon", aux.getIcon());
                bundle.putString("channelStream", aux.getStreamHttp());
                intent.putExtra("object", bundle);
                if(Build.VERSION.SDK_INT >= 21){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, channelImage, "channelImage");
                    context.startActivity(intent, options.toBundle());
                }
                else{
                    context.startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onBindViewHolder(ChannelDataViewHolder holder, int position) {
        holder.channelName.setText(list.get(position).getName());
        holder.channelNumber.setText(list.get(position).getChannel());
        holder.channelStatus.setText(list.get(position).getStatus());
        holder.channelCard.setTag(list.get(position).getId());
        if(Build.VERSION.SDK_INT >= 21){
            holder.channelImage.setImageDrawable(context.getResources().getDrawable(list.get(position).getIcon(), null));
        }
        else{
            holder.channelImage.setImageDrawable(context.getResources().getDrawable(list.get(position).getIcon()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnRowListener {
        void onRowClickListener(View v);
    }
}
