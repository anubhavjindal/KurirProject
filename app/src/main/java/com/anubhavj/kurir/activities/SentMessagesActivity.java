package com.anubhavj.kurir.activities;

import android.os.Bundle;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.views.MainNavDrawer;

public class SentMessagesActivity extends BaseAuthenticatedActivity{

    @Override
    protected void onKurirCreate(Bundle savedState) {
        setContentView(R.layout.activity_sent_messages);
        setNavDrawer(new MainNavDrawer(this));
    }
}