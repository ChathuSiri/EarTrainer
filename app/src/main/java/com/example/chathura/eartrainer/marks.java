package com.example.chathura.eartrainer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chathura.eartrainer.dataaccess.DataAccess;

public class marks extends AppCompatActivity {
    ProgressBar bar ;
    int marks;
    int level;
    String exercise;
    private Handler handler;
    private runner runner;
    TextView t;
    DataAccess helper = new DataAccess(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {    //this activity shows marks for the exercise
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        Intent i = getIntent();
        marks = i.getIntExtra("marks", 0);
        level = i.getIntExtra("level", 0);
        exercise = i.getExtras().getString("exercise");
        t=(TextView)findViewById(R.id.textNum);
        t.setText(Integer.toString(marks));
        handler = new Handler();
        bar.setProgress(marks);                              //with a progress bar
        t=(TextView)findViewById(R.id.textMessage);
        t.setText(setMessage(marks));                        // and a motivational message
        helper.setMarks(exercise,marks,level);

    }

    public void onProgressClicked(View view) {
        Intent i = new Intent(marks.this,Progress.class);

        startActivity(i);
        this.finish();
        return;
    }

    private String setMessage(int marks){       //A message will be changed according to users marks
        String message = "Just Wow!!";
        if(marks<3){
            return "Newbie..";
        }
        if(marks<7){
            return "Not bad for a beginner";
        }
        if(marks<11){
            return "That's some Progress!";
        }
        if(marks<14){
            return "Bravo..!!";
        }

        return message;
    }

    public class runner implements Runnable{

        int num;
        @Override
        public void run() {
            num = bar.getProgress();
            num++;
            bar.setProgress(marks);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marks, menu);
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
        Intent i = new Intent(marks.this,Home.class);
        startActivity(i);
        this.finish();
        return;

    }


}
