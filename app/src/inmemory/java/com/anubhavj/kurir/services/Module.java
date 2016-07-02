package com.anubhavj.kurir.services;

import android.util.Log;

import com.anubhavj.kurir.infrastructure.KurirApplication;

public class Module {
    public static void register(KurirApplication application){
        new InMemoryAccountService(application);
    }
}
