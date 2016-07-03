package com.anubhavj.kurir.activities;

import android.os.Bundle;

import com.anubhavj.kurir.R;

public class ContactActivity extends BaseAuthenticatedActivity {
    public static final String EXTRA_USER_DETAILS = "EXTRA_USER_DETAILS";

    @Override
    protected void onKurirCreate(Bundle savedState) {
        setContentView(R.layout.activity_contact);
    }
}
