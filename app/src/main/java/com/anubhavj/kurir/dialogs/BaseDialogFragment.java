package com.anubhavj.kurir.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;

import com.anubhavj.kurir.infrastructure.KurirApplication;
import com.squareup.otto.Bus;

public abstract class BaseDialogFragment extends DialogFragment{
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
