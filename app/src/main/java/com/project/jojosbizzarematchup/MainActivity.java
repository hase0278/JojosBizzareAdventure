package com.project.jojosbizzarematchup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] drawableId = {1, 2, 3, 4, 5, 6};
    int[] drawable = {R.drawable.dio, R.drawable.jotaro, R.drawable.kars};
    int dio = 0, jotaro = 0, kars = 0, tileClicked = 0, index, firstTile = 0, secondTile = 0, matches = 0;
    ImageView tile1;
    private MediaPlayer player;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play(R.raw.zawarudo, false);
        for(int x = 0; x < 6; x++){
            Randomizer();
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.reset();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.release();
    }

    public void tileClick(View v) {
        ImageView tile = null;
        int img = 0;
        switch (v.getId()) {
            case R.id.tile1:
                tile = findViewById(R.id.tile1);
                img = drawableId[0];
                tile.setBackgroundResource(drawableId[0]);
                break;
            case R.id.tile2:
                tile = findViewById(R.id.tile2);
                img = drawableId[1];
                tile.setBackgroundResource(drawableId[1]);
                break;
            case R.id.tile3:
                tile = findViewById(R.id.tile3);
                img = drawableId[2];
                tile.setBackgroundResource(drawableId[2]);
                break;
            case R.id.tile4:
                tile = findViewById(R.id.tile4);
                img = drawableId[3];
                tile.setBackgroundResource(drawableId[3]);
                break;
            case R.id.tile5:
                tile = findViewById(R.id.tile5);
                img = drawableId[4];
                tile.setBackgroundResource(drawableId[4]);
                break;
            case R.id.tile6:
                tile = findViewById(R.id.tile6);
                img = drawableId[5];
                tile.setBackgroundResource(drawableId[5]);
                break;
            default:
                break;
        }

        if(firstTile == 0){
            tile1 = findViewById(tile.getId());
            firstTile = img;
            tileClicked++;
            tile1.setOnClickListener(null);
        }else if(secondTile == 0){
            secondTile = img;
            tileClicked++;
        }

        if(tileClicked == 2){
            if(firstTile == secondTile){
                Toast.makeText(this, "Match", Toast.LENGTH_SHORT).show();
                matches++;
                if(matches == 2){
                    play(R.raw.two, true);
                }else if(matches == 1){
                    play(R.raw.one, true);
                }
                tile.setOnClickListener(null);
            }
            else{
                Toast.makeText(this, "Not Match", Toast.LENGTH_SHORT).show();
                play(R.raw.mudamuda, true);
                resetBack(tile1);
                tile.setBackgroundResource(R.drawable.deftile);
            }

            if(matches == 3){
                play(R.raw.threeyareyare, true);
                AlertDialog.Builder alert  = new AlertDialog.Builder(this);
                alert.setMessage("Game Over. All Matches Found.");
                alert.setTitle("Jojo's Bizzare Matchup");
                alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        player.release();
                        matches = 0;
                    }
                });
                alert.setNegativeButton("Start new game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        refresh(findViewById(R.id.refresh));
                    }
                });
                alert.setCancelable(true);
                alert.create().show();

            }
            tileClicked = 0;
            firstTile = 0;
            secondTile = 0;
            tile1 = null;
        }
    }

    public void refresh(View v){
        this.recreate();
    }

    public void homeBtnClick(View v){
        player.release();
        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
        MainActivity.this.startActivity(intent);
        this.finish();
    }

    public void resetBack(ImageView view){
        view.setOnClickListener(this::tileClick);
        view.setBackgroundResource(R.drawable.deftile);
    }

    private void Randomizer(){
        index = rand.nextInt(3);

        if(drawable[index] == drawable[0] && dio != 2){
            RandomizerEx();
            dio++;
        }else if(drawable[index] == drawable[1] && jotaro != 2){
            RandomizerEx();
            jotaro++;
        }else if(drawable[index] == drawable[2] && kars != 2){
            RandomizerEx();
            kars++;
        }else{
            Randomizer();
        }
    }

    private void RandomizerEx(){
        if(drawableId[0] == 1){
            drawableId[0] = drawable[index];
        }else if(drawableId[1] == 2){
            drawableId[1] = drawable[index];
        }else if(drawableId[2] == 3){
            drawableId[2] = drawable[index];
        }else if(drawableId[3] == 4){
            drawableId[3] = drawable[index];
        }else if(drawableId[4] == 5){
            drawableId[4] = drawable[index];
        }else if(drawableId[5] == 6){
            drawableId[5] = drawable[index];
        }
    }

    public void play(int soundID, boolean suppress){
        if(suppress){
            player.reset();
        }
        
        player = MediaPlayer.create(this, soundID);
        player.start();
    }
}