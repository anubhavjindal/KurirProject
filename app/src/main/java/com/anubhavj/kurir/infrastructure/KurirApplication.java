package com.anubhavj.kurir.infrastructure;

import android.app.Application;

import com.anubhavj.kurir.services.Module;
import com.squareup.otto.Bus;

public class KurirApplication extends Application {

    private Auth auth;
    private Bus bus;

    public KurirApplication(){
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
        Module.register(this);
    }

    public Auth getAuth() {
        return auth;
    }

    public Bus getBus() { return bus; }
}
