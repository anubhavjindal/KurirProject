package com.anubhavj.kurir.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.activities.BaseActivity;
import com.anubhavj.kurir.services.entities.Message;

import java.util.ArrayList;

/**
 * Created by anubhavj on 19/10/16.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> implements View.OnClickListener {

    private final LayoutInflater layoutInflater;
    private final BaseActivity activity;
    private final OnMessageClickedListener listener;
    private final ArrayList<Message> messages;

    public MessagesAdapter(BaseActivity activity, OnMessageClickedListener listener) {
        this.activity = activity;
        this.listener = listener;
        messages = new ArrayList<>();
        layoutInflater = activity.getLayoutInflater();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_message, parent, false);
        view.setOnClickListener(this);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.populate(activity,messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onClick(View view) {
        if(view.getTag() instanceof Message)
        {
            Message message = (Message) view.getTag();
            listener.onMessageClicked(message);
        }
    }

    public interface OnMessageClickedListener {
        void onMessageClicked(Message message);
    }
}
