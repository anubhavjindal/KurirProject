package com.anubhavj.kurir.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.anubhavj.kurir.infrastructure.KurirApplication;
import com.squareup.otto.Bus;

public abstract class BaseFragment extends Fragment {

    protected KurirApplication application;
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        application = (KurirApplication) getActivity().getApplication();
        bus = application.getBus();

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
