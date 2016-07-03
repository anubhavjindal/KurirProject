package com.anubhavj.kurir.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.anubhavj.kurir.infrastructure.ActionScheduler;
import com.anubhavj.kurir.infrastructure.KurirApplication;
import com.squareup.otto.Bus;

public abstract class BaseFragment extends Fragment {

    protected KurirApplication application;
    protected Bus bus;
    protected ActionScheduler scheduler;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        application = (KurirApplication) getActivity().getApplication();
        scheduler = new ActionScheduler(application);
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        scheduler.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduler.onResume();
    }
}
