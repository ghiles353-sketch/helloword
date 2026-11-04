package com.livan.carbitlab;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class MainActivity extends Activity {
    private int dp(int n) { return (int) (n * getResources().getDisplayMetrics().density + .5f); }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(dp(24), dp(24), dp(24), dp(24));
        layout.setBackgroundColor(Color.rgb(14, 26, 43));

        TextView title = new TextView(this);
        title.setText("HELLO WORLD");
        title.setTextSize(34);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        layout.addView(title, new LinearLayout.LayoutParams(-1, dp(105)));

        TextView sub = new TextView(this);
        sub.setText("AA Lab — test CarbitLink\nUne application normale avec raccourci Android et un test de session multimedia.\nAucune fonction de partage d'ecran.");
        sub.setTextColor(Color.LTGRAY);
        sub.setTextSize(16);
        sub.setGravity(Gravity.CENTER);
        layout.addView(sub, new LinearLayout.LayoutParams(-1, dp(150)));

        Button start = new Button(this);
        start.setText("Activer le test media HELLO WORLD");
        start.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 501);
            }
            try {
                Intent intent = new Intent(this, MediaTestService.class);
                startForegroundService(intent);
                Toast.makeText(this, "Session media lancee", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Impossible de demarrer : " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        layout.addView(start, new LinearLayout.LayoutParams(-1, dp(76)));

        Button stop = new Button(this);
        stop.setText("Arreter le test media");
        stop.setOnClickListener(v -> {
            stopService(new Intent(this, MediaTestService.class));
            Toast.makeText(this, "Session media arretee", Toast.LENGTH_SHORT).show();
        });
        layout.addView(stop, new LinearLayout.LayoutParams(-1, dp(72)));

        TextView hint = new TextView(this);
        hint.setText("TEST VEHICULE A L'ARRET UNIQUEMENT\nDans CarbitLink : verifier l'icone AA Lab Hello, puis regarder si HELLO WORLD apparait sans miroir, et tester Musique locale.");
        hint.setTextColor(Color.LTGRAY);
        hint.setTextSize(13);
        hint.setGravity(Gravity.CENTER);
        layout.addView(hint, new LinearLayout.LayoutParams(-1, dp(125)));

        setContentView(layout);
    }
}
