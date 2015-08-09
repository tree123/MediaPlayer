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

import java.io.File;
import java.util.ArrayList;

public class Player extends ActionBarActivity implements View.OnClickListener {

    MediaPlayer mp;
    ArrayList<File> mySongs;
    Uri u;

    SeekBar sb;
    Button btnPV, btnFF, btnPlay, btnFB, btnNxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPV = (Button) findViewById(R.id.btnPV);
        btnFF = (Button) findViewById(R.id.btnFF);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnFB = (Button) findViewById(R.id.btnFB);
        btnNxt = (Button) findViewById(R.id.btnNxt);

        btnPV.setOnClickListener((View.OnClickListener) this);
        btnFF.setOnClickListener((View.OnClickListener) this);
        btnPlay.setOnClickListener((View.OnClickListener) this);
        btnFB.setOnClickListener((View.OnClickListener) this);
        btnNxt.setOnClickListener((View.OnClickListener) this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        int position = b.getInt("pos",0);

        u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
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

    }
}
