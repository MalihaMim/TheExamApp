package com.example.studynook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;

public class AlarmSound extends  BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer media = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        media.start();
    }
}