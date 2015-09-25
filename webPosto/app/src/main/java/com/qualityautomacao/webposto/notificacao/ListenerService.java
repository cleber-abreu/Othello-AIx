package com.qualityautomacao.webposto.notificacao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.qualityautomacao.webposto.R;
import com.qualityautomacao.webposto.model.Token;
import com.qualityautomacao.webposto.view.NotificacoesActivity;

public class ListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(Integer.parseInt(data.getString("ID", "0")), data.getString("TITULO", ""), data.getString("NOTIFICACAO", ""),
                         new Token(Integer.parseInt(data.getString("UNIDADE_NEGOCIO", "0")),
                                   data.getString("NOME_UNIDADE", ""),
                                   Integer.parseInt(data.getString("USUARIO", "0")),
                                   Integer.parseInt(data.getString("PERFIL", "0")),
                                   Integer.parseInt(data.getString("REDE", "0"))));
    }

    private void sendNotification(final int id, final String titulo, final String notificacao, final Token token) {
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(id, getNotificacao(id, token, titulo, notificacao));
    }

    private PendingIntent getIntentNotificacao(final int id, final Token token) {
        return TaskStackBuilder.create(this)
                .addParentStack(NotificacoesActivity.class)
                .addNextIntent(new Intent(this, NotificacoesActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                .putExtra("TOKEN", token)
                                .putExtra("ID", id))
                .getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_ONE_SHOT);
    }

    private Notification getNotificacao(final int id, final Token token, final String titulo, final String notificacao) {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icone_app_notificacao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icone_app))
                .setContentTitle(titulo)
                .setContentText(notificacao)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(getIntentNotificacao(id, token))
                .build();
    }
}
