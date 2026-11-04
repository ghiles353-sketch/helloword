package com.livan.carbitlab;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public final class MediaTestService extends Service {
    private static final String TAG = "CarbitLab";
    private static final String CHANNEL = "carbitlab_media";
    private static final int NOTIFICATION_ID = 5101;
    private MediaSession session;
    private MediaPlayer player;

    @Override public void onCreate() {
        super.onCreate();
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(new NotificationChannel(CHANNEL, "CarbitLab test media", NotificationManager.IMPORTANCE_LOW));

        session = new MediaSession(this, "CarbitLabHelloWorld");
        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        session.setMetadata(new MediaMetadata.Builder()
            .putString(MediaMetadata.METADATA_KEY_TITLE, "HELLO WORLD")
            .putString(MediaMetadata.METADATA_KEY_ARTIST, "AA Lab Livan X3 Pro")
            .putString(MediaMetadata.METADATA_KEY_ALBUM, "CarbitLink protocol test")
            .putLong(MediaMetadata.METADATA_KEY_DURATION, 4000)
            .build());
        session.setCallback(new MediaSession.Callback() {
            @Override public void onPlay() { if (player != null) player.start(); publish(PlaybackState.STATE_PLAYING); }
            @Override public void onPause() { if (player != null && player.isPlaying()) player.pause(); publish(PlaybackState.STATE_PAUSED); }
            @Override public void onStop() { stopSelf(); }
        });
        session.setActive(true);
        publish(PlaybackState.STATE_PLAYING);

        Intent launch = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, launch, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this, CHANNEL)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("HELLO WORLD")
            .setContentText("AA Lab Livan X3 Pro — test media")
            .setContentIntent(pi)
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setStyle(new Notification.MediaStyle().setMediaSession(session.getSessionToken()))
            .build();
        startForeground(NOTIFICATION_ID, notification);
        player = MediaPlayer.create(this, R.raw.test_tone);
        if (player != null) {
            player.setVolume(0.1f, 0.1f);
            player.setLooping(true);
            player.start();
        }
        Log.i(TAG, "HELLO WORLD media session active");
    }

    private void publish(int state) {
        if (session == null) return;
        session.setPlaybackState(new PlaybackState.Builder()
            .setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE | PlaybackState.ACTION_STOP)
            .setState(state, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 1.0f)
            .build());
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override public void onDestroy() {
        if (player != null) { player.release(); player = null; }
        if (session != null) { session.setActive(false); session.release(); session = null; }
        super.onDestroy();
        Log.i(TAG, "HELLO WORLD media session stopped");
    }

    @Override public IBinder onBind(Intent intent) { return null; }
}
