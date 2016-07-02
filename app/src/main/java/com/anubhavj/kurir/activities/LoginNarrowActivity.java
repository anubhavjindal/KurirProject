package com.anubhavj.kurir.activities;

import android.os.Bundle;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.fragments.LoginFragment;

public class LoginNarrowActivity extends BaseActivity implements LoginFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedState) {
          super.onCreate(savedState);

        setContentView(R.layout.activity_login_narrow);
    }

    @Override
    public void onLoggedIn() {
        setResult(RESULT_OK);
        finish();
    }
}
