/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qualityautomacao.webposto.notificacao;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.view.NotificacoesActivity;

public class ListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(data.getInt("ID", 0), data.getString("TITULO", ""), data.getString("NOTIFICACAO", ""),
                         new Token(Integer.parseInt(data.getString("UNIDADE_NEGOCIO", "0")),
                                   data.getString("NOME_UNIDADE", ""),
                                   Integer.parseInt(data.getString("USUARIO", "0")),
                                   Integer.parseInt(data.getString("PERFIL", "0")),
                                   Integer.parseInt(data.getString("REDE", "0"))));
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     */
    private void sendNotification(int id, String titulo, String notificacao, Token token) {
        Intent intent = new Intent(this, NotificacoesActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacoesActivity.class);
        stackBuilder.addNextIntent(intent);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("TOKEN", token);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icone_app_notificacao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icone_app))
                .setContentTitle(titulo)
                .setContentText(notificacao)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id, notificationBuilder.build());
    }
}
