package com.project.jojosbizzarematchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    private static MediaPlayer player;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        play(R.raw.morio, false);

        player = MediaPlayer.create(this, R.raw.stardust);
        player.setLooping(true);
        player.start();

        btn = findViewById(R.id.newGame);
        btn.setOnClickListener(this::onClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    public void onClick(View v){
        btn.setOnClickListener(null);
        play(R.raw.konodioda, true);
        while (player.isPlaying()){

        }
        if(!player.isPlaying()){
            player.release();
            Intent intent = new Intent(HomeScreen.this, MainActivity.class);
            HomeScreen.this.startActivity(intent);
            this.finish();
        }
    }

    public void play(int soundID, boolean suppress){
        if(suppress){
            player.release();
        }
        player = MediaPlayer.create(this, soundID);
        player.start();
    }
}