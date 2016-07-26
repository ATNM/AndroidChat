
package com.airtouch.adrian.chatex2;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.Api;

import java.util.Arrays;

import Model.User;
import Util.API;
import Util.Constants;

public class ChatFragment extends Fragment {

    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private Button mSendButton;
    private EditText mMessageEditText;
    private ProgressBar mProgressBar;


    private FirebaseRecyclerAdapter<Message, MessageViewHolder> mFIrebaseAdapter;
    private String mChatKey;
    private static User mOtherUser;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(User otherUser) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        mOtherUser = otherUser;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public boolean setupChatKey() {
        if (Model.user == null || mOtherUser == null) {
            Toast.makeText(getActivity(), "There was a problem setting up the users", Toast.LENGTH_LONG)
                 .show();
            return false;
        }

        // chat key?
        // user1:user2
        // the order of user 1 and 2 is given by alphabetically sorted ids

        final String[] chatUsers = new String[]{
            Model.user.getId(), mOtherUser.getId()
        };

        Arrays.sort(chatUsers);
        mChatKey = chatUsers[0] + ":" + chatUsers[1];

        return true;
    }

    public void setupViews(){
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);

        mProgressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) getView().findViewById(R.id.messageRecyclerView);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mMessageEditText = (EditText) getView().findViewById(R.id.messageEditText);
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // enable the text button
                if(s.toString().trim().length() > 1){
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSendButton = (Button) getView().findViewById(R.id.sendButton);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(mMessageEditText.getText().toString(), Model.user.getId(), mOtherUser.getId());

                API.getInstance().getDatabaseReference()
                            .child(Constants.CHATS_CHILD)
                            .child(mChatKey)
                            .child(Constants.MESSAGES_CHILD)
                            .push().setValue(message);

                mMessageEditText.setText("");
            }
        });

    }


}
