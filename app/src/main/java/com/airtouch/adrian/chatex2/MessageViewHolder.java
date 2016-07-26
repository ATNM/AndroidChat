package com.airtouch.adrian.chatex2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by atnm1 on 26/07/16.
 */
public class MessageViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout bubbleView;
    public LinearLayout messageContainer;
    public TextView messageTextView;
    public TextView nameTextView;


    public MessageViewHolder(View v){
        super(v);

        bubbleView = (LinearLayout) v.findViewById(R.id.bubble);
        messageContainer = (LinearLayout) v.findViewById(R.id.message_container);
        messageTextView = (TextView) v.findViewById(R.id.messageTextView);
        nameTextView = (TextView) v.findViewById(R.id.nameTextView);

    }


}
