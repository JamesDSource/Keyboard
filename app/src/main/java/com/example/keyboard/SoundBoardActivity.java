package com.example.keyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener {
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
    private Button bScale;
    private Button bSong;
    private Button bRecord;
    private Button bPlay;
    private float rate = (float) 1.0;
    private int rate_show = 0;
    private boolean recording = false;
    private ArrayList<Integer> record = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spNotes = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        wire();
        setListeners();
        sounds = loadSounds();
        tvRate.setText("" + rate_show);
    }

    //region wiring and loading
    public void wire() {
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
        bSong = findViewById(R.id.bSongs);
        bRecord = findViewById(R.id.bRecord);
        bPlay = findViewById(R.id.bPlay);
        bScale = findViewById(R.id.bScale);
    }

    private void setListeners() {
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
        bSong.setOnClickListener(this);
        bRecord.setOnClickListener(this);
        bPlay.setOnClickListener(this);
        bScale.setOnClickListener(this);
    }

    public int[] loadSounds() {
        int[] array = new int[12];
        array[0] = R.raw.scalea;
        array[1] = R.raw.scaleb;
        array[2] = R.raw.scalec;
        array[3] = R.raw.scaled;
        array[4] = R.raw.scalee;
        array[5] = R.raw.scalef;
        array[6] = R.raw.scaleg;
        array[7] = R.raw.scalegs;
        array[8] = R.raw.scalebb;
        array[9] = R.raw.scalecs;
        array[10] = R.raw.scaleds;
        array[11] = R.raw.scalefs;
        for (int i = 0; i < array.length; i++) {
            array[i] = spNotes.load(this, array[i], 1);
        }
        return array;
    }

    //endregion
    public SoundPool getSoundPool() {
        return spNotes;
    }

    public int getSounds(int index) {
        return sounds[index];
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spNotes.release();
    }

    @Override
    public void onClick(View view) {
        int index = -1;
        switch (view.getId()) {
            case R.id.bA:
                index = 0;
                break;
            case R.id.bB:
                index = 1;
                break;
            case R.id.bC:
                index = 2;
                break;
            case R.id.bD:
                index = 3;
                break;
            case R.id.bE:
                index = 4;
                break;
            case R.id.bF:
                index = 5;
                break;
            case R.id.bG:
                index = 6;
                break;
            case R.id.bG_sharp:
                index = 7;
                break;
            case R.id.bA_sharp:
                index = 8;
                break;
            case R.id.bC_sharp:
                index = 9;
                break;
            case R.id.bD_sharp:
                index = 10;
                break;
            case R.id.bF_sharp:
                index = 11;
                break;
            case R.id.bIncrease_rate:
                if (rate_show < 1) {
                    rate += 0.5;
                    rate_show++;
                }
                tvRate.setText("" + rate_show);
                break;
            case R.id.bDecrease_rate:
                if (rate_show > -1) {
                    rate -= 0.5;
                    rate_show--;
                }
                tvRate.setText("" + rate_show);
                break;
            case R.id.bSongs:
                SongPlayer songPlayer = new SongPlayer("song");
                new Thread(songPlayer).start();
                break;
            case R.id.bScale:
                SongPlayer scaler = new SongPlayer("scale");
                new Thread(scaler).start();
                break;
            case R.id.bRecord:
                recording = !recording;
                if(recording) {
                    Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
                    record.clear();
                }
                else {
                    Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bPlay:
                SongPlayer recordingPlayer = new SongPlayer("recording");
                new Thread(recordingPlayer).start();
                break;
        }
        if (index != -1) {
            if(recording) {
                record.add(index);
            }
            spNotes.play(sounds[index], 1, 1, 1, 0, rate);
        }
    }

    class SongPlayer implements Runnable {
        private int[] sounds;
        private String song;

        public SongPlayer(String song) {
            this.song = song;
            sounds = loadSounds();
        }

        @Override
        public void run() {
            switch (song) {
                // a = 0
                // b = 1
                // c = 2
                // d = 3
                // e = 4
                // f = 5
                // g = 6
                //as = 8
                //cs = 9
                //ds = 10
                //fs = 11
                //gs = 7
                case "song":
                    int[][] notes = {
                            {3, 2, 0, 3, 2, 0, 3, 2, 0, 2, 3, 0, 2, 3, 2,
                                0, 5, 2, 3, 1, 1, 1, 1}, // notes
                            {400, 200, 400, 200, 400, 200, 400, 200, 400, 200, 400, 200, 400, 200, 400,
                                200, 400, 200, 400, 200, 400, 200, 400}}; // pause in ms between notes
                    playSong(notes);
                    break;
                case "recording":
                    playSongList(record);
                    break;
                case "scale":
                    int[][] scale = {
                            {0,8,1,2,9,3,10,4,5,11,6,7},
                            {400,400,350,350,300,300,250,300,300,350,400,400}
                    };
                    playSong(scale);
                    break;
            }
        }

        public void playSong(int[][] notes) {
            for (int i = 0; i < notes[0].length; i++) {
                int note = notes[0][i];
                playNote(note, rate);
                try {
                    Thread.sleep(notes[1][i]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void playSongList(ArrayList<Integer> notes) {
            int[][] array = new int[2][notes.size()];
            for (int i = 0; i < notes.size(); i++) {
                array[0][i] = notes.get(i);
                array[1][i] = 300;
            }
            playSong(array);
        }

        public void playNote(int note_index, float rate) {
            getSoundPool().play(sounds[note_index], 1, 1, 1, 0, rate);
        }
    }
}
