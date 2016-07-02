package com.anubhavj.kurir.activities;

import android.os.Bundle;
import com.anubhavj.kurir.R;
import com.anubhavj.kurir.views.MainNavDrawer;

public class MainActivity extends BaseAuthenticatedActivity {

    @Override
    protected void onKurirCreate(Bundle savedState) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Inbox");
        setNavDrawer(new MainNavDrawer(this));
    }
}
