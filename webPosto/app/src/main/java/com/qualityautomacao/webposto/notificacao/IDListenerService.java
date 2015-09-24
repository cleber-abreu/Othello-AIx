package com.qualityautomacao.webposto.notificacao;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class IDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
