package com.example.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SoundPool spNotes;
    private int[] sounds;
    private Button bA;
    private Button bB;
    private Button bC;
    private Button bD;
    private Button bE;
    private Button bF;
    private Button bG;
    private Button bG_sharp;
    private Button bA_sharp;
    private Button bC_sharp;
    private Button bD_sharp;
    private Button bF_sharp;
    private TextView tvRate;
    private Button bIncrease;
    private Button bDecrease;
    private float rate = (float)1.0;
    private int rate_show = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spNotes = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        wire();
        setListeners();
        loadSounds();
        tvRate.setText("" + rate_show);
    }
    //region wiring and loading
    public void wire(){
        bA = findViewById(R.id.bA);
        bB = findViewById(R.id.bB);
        bC = findViewById(R.id.bC);
        bD = findViewById(R.id.bD);
        bE = findViewById(R.id.bE);
        bF = findViewById(R.id.bF);
        bG = findViewById(R.id.bG);
        bG_sharp = findViewById(R.id.bG_sharp);
        bA_sharp = findViewById(R.id.bA_sharp);
        bC_sharp = findViewById(R.id.bC_sharp);
        bD_sharp = findViewById(R.id.bD_sharp);
        bF_sharp = findViewById(R.id.bF_sharp);
        tvRate = findViewById(R.id.tvRate);
        bIncrease = findViewById(R.id.bIncrease_rate);
        bDecrease = findViewById(R.id.bDecrease_rate);
    }

    private void setListeners(){
        bA.setOnClickListener(this);
        bB.setOnClickListener(this);
        bC.setOnClickListener(this);
        bD.setOnClickListener(this);
        bE.setOnClickListener(this);
        bF.setOnClickListener(this);
        bG.setOnClickListener(this);
        bG_sharp.setOnClickListener(this);
        bA_sharp.setOnClickListener(this);
        bC_sharp.setOnClickListener(this);
        bD_sharp.setOnClickListener(this);
        bF_sharp.setOnClickListener(this);
        bIncrease.setOnClickListener(this);
        bDecrease.setOnClickListener(this);
    }

    private void loadSounds(){
        sounds = new int[12];
        sounds[0] = R.raw.scalea;
        sounds[1] = R.raw.scaleb;
        sounds[2] = R.raw.scalec;
        sounds[3] = R.raw.scaled;
        sounds[4] = R.raw.scalee;
        sounds[5] = R.raw.scalef;
        sounds[6] = R.raw.scaleg;
        sounds[7] = R.raw.scalegs;
        sounds[8] = R.raw.scalebb;
        sounds[9] = R.raw.scalecs;
        sounds[10] = R.raw.scaleds;
        sounds[11] = R.raw.scalefs;
        for(int i = 0; i < sounds.length; i++){
            sounds[i] = spNotes.load(getApplicationContext(), sounds[i], 1);
        }
      }
    //endregion
    @Override
    protected void onDestroy() {
        super.onDestroy();
        spNotes.release();
    }

    @Override
    public void onClick(View view) {
        int index = -1;
        switch(view.getId()) {
            case R.id.bA: index = 0; break;
            case R.id.bB: index = 1; break;
            case R.id.bC: index = 2; break;
            case R.id.bD: index = 3; break;
            case R.id.bE: index = 4; break;
            case R.id.bF: index = 5; break;
            case R.id.bG: index = 6; break;
            case R.id.bG_sharp: index = 7; break;
            case R.id.bA_sharp: index = 8; break;
            case R.id.bC_sharp: index = 9; break;
            case R.id.bD_sharp: index = 10; break;
            case R.id.bF_sharp: index = 11; break;
            case R.id.bIncrease_rate:
                if(rate < 1.9) {
                    rate += 0.1;
                    rate_show++;
                }
                tvRate.setText("" + rate_show);
                break;
            case R.id.bDecrease_rate:
                if(rate > 0.1) {
                    rate -= 0.1;
                    rate_show--;
                }
                tvRate.setText("" + rate_show);
                break;
        }
        if(index != -1) {
            spNotes.play(sounds[index], 1, 1, 1, 0, rate);
        }
    }
}
