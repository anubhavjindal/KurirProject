package com.anubhavj.kurir.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.infrastructure.Auth;
import com.anubhavj.kurir.services.Account;
import com.squareup.otto.Subscribe;

public class AuthenticationActivity extends BaseActivity{

    private Auth auth;

    public static final String EXTRA_RETURN_TO_ACTIVITY = "EXTRA_RETURN_TO_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_authentication);

        auth = application.getAuth();
        if(!auth.hasAuthToken()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        bus.post(new Account.LoginWithLocalTokenRequest(auth.getAuthToken()));
    }

    @Subscribe
    public void onLoginWithLocalToken(Account.LoginWithLocalTokenResponse response){
        if(!response.didSucceed()){
            Toast.makeText(this,"Please Login again",Toast.LENGTH_SHORT).show();
            auth.setAuthToken(null);
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return;
        }

        Intent intent;
        String returnTo = getIntent().getStringExtra(EXTRA_RETURN_TO_ACTIVITY);
        if(returnTo != null){
            try {
                intent = new Intent(this, Class.forName(returnTo));
            }
            catch (Exception ignored) {
                intent = new Intent(this, MainActivity.class);
            }
        }
        else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
