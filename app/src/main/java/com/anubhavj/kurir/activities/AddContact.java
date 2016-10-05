package com.anubhavj.kurir.activities;

import android.os.Bundle;

import com.anubhavj.kurir.R;

public class AddContact extends BaseAuthenticatedActivity {
    public static final String RESULT_CONTACT = "RESULT_CONTACT";

    @Override
    protected void onKurirCreate(Bundle savedState) {
        setContentView(R.layout.activity_add_contact);
    }
}


Bhoomika