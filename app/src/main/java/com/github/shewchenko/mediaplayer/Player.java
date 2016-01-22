package com.github.shewchenko.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Player extends ActionBarActivity implements View.OnClickListener {

    MediaPlayer mp;
    ArrayList<File> mySongs;
    Uri u;
    int position;

    SeekBar sb;
    Button btnPV, btnFF, btnPlay, btnFB, btnNxt;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        txt = (TextView) findViewById(R.id.textView2);
        btnPV = (Button) findViewById(R.id.btnPV);
        btnFF = (Button) findViewById(R.id.btnFF);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnFB = (Button) findViewById(R.id.btnFB);
        btnNxt = (Button) findViewById(R.id.btnNxt);
        //sb = (SeekBar) findViewById(R.id.seekBar1);
        btnPV.setOnClickListener((View.OnClickListener) this);
        btnFF.setOnClickListener((View.OnClickListener) this);
        btnPlay.setOnClickListener((View.OnClickListener) this);
        btnFB.setOnClickListener((View.OnClickListener) this);
        btnNxt.setOnClickListener((View.OnClickListener) this);
        //sb.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        onSeekbar();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos", 0);

        u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        //txt.setText("Total Time:" + String.valueOf(mp.getDuration()/1000) + "Seconds");
        updateTime();
    }

    public void onSeekbar(){

        sb = (SeekBar) findViewById(R.id.seekBar1);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int prg;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            prg= progress;
                mp.seekTo(mp.getDuration()*prg/sb.getMax());
                updateTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.seekTo(mp.getDuration()*prg/sb.getMax());
                updateTime();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(mp.getDuration()*prg/sb.getMax());
                updateTime();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //nbtnPV.setOnClickListener((View.OnClickListener) this);oinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btnPlay:
                if (mp.isPlaying()) {
                    mp.pause();
                    btnPlay.setText("||");
                } else {
                    mp.start();
                    btnPlay.setText("|>");
                }
                //txt.setText("Current Time:" + String.valueOf(mp.getCurrentPosition()/1000) + "Seconds");
                updateTime();
                break;
            case R.id.btnFF:
                sb.setProgress(sb.getMax()*(mp.getCurrentPosition()-5000) /mp.getDuration());
                mp.seekTo(mp.getCurrentPosition() - 5000);
                //txt.setText("Current Time:" + String.valueOf((mp.getCurrentPosition() - 5000)/1000)+"Seconds");
                updateTime();
                break;
            case R.id.btnFB:
                sb.setProgress(sb.getMax() * (mp.getCurrentPosition() + 5000) / mp.getDuration());
                mp.seekTo(mp.getCurrentPosition() + 5000);
                //txt.setText("Current Time:" + String.valueOf((mp.getCurrentPosition() + 5000)/1000)+"Seconds");
                updateTime();
                break;
            case R.id.btnNxt:
                sb.setProgress(0);
                //txt.setText("Current Time:" + "0" + "Seconds");
                mp.stop();
                mp.release();
                position = (position + 1) % mySongs.size();
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                updateTime();
                btnPlay.setText("|>");
                break;
            case R.id.btnPV:
                sb.setProgress(0);
                //txt.setText("Current Time:" + "0" + "Seconds");
                mp.stop();
                mp.release();
                position = (position - 1 < 0) ? mySongs.size() - 1 : position - 1;
                u = Uri.parse(mySongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(), u);
                mp.start();
                updateTime();
                btnPlay.setText("|>");
                break;


        }
    }

    private void updateTime() {

        txt.setText("Time-" + (mp.getCurrentPosition()/60000)+" : " + ((mp.getCurrentPosition()/1000)%60)
        +"\nTotal-"+ (mp.getDuration()/60000)+" : " + ((mp.getDuration()/1000)%60));

    }

}
