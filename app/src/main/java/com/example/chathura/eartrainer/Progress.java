package com.example.chathura.eartrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Progress extends AppCompatActivity {
    DataAccess helper = new DataAccess(this);
    TextView t;
    ProgressBar p;
    float progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        helper.getUserDetails();

        t=(TextView)findViewById(R.id.textLevelNotes);
        t.setText("level: " + Integer.toString(helper.levelnote));
        t=(TextView)findViewById(R.id.textView1);
        progress = helper.marknote*100/15;
        t.setText( Integer.toString((int)progress)+"%");
        p=(ProgressBar)findViewById(R.id.progressBar1);
        p.setProgress((int)progress);

        t=(TextView)findViewById(R.id.textLevelChords);
        t.setText("level: " + Integer.toString(helper.levelchord));
        t=(TextView)findViewById(R.id.textView2);
        progress = helper.markchord*100/15 ;
        t.setText( Integer.toString((int)progress)+"%");
        p=(ProgressBar)findViewById(R.id.progressBar2);
        p.setProgress((int)progress);

        t=(TextView)findViewById(R.id.textLevelScales);
        t.setText("level: " + Integer.toString(helper.levelscale));
        t=(TextView)findViewById(R.id.textView3);
        progress = helper.markscale*100/15 ;
        t.setText( Integer.toString((int)progress)+"%");
        p=(ProgressBar)findViewById(R.id.progressBar3);
        p.setProgress((int)progress);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Progress.this,Home.class);
        startActivity(i);
        this.finish();
        return;

    }

    public void onItemClicked(View view) {
        if(view.getId()==R.id.Note||view.getId()==R.id.Note1){

            Intent i = new Intent(Progress.this,ProgressView.class);
            i.putExtra("exercise","notes");
            startActivity(i);
        }

        if(view.getId()==R.id.Chord||view.getId()==R.id.Chord1){

            Intent i = new Intent(Progress.this,ProgressView.class);
            i.putExtra("exercise","chords");
            startActivity(i);
        }

        if(view.getId()==R.id.Scale||view.getId()==R.id.Scale1){

            Intent i = new Intent(Progress.this,ProgressView.class);
            i.putExtra("exercise","scales");
            startActivity(i);
        }
    }
}
