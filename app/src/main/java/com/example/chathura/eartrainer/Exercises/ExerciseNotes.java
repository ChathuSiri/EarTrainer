package com.example.chathura.eartrainer.Exercises;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chathura.eartrainer.Home;
import com.example.chathura.eartrainer.R;
import com.example.chathura.eartrainer.dataaccess.DataAccess;
import com.example.chathura.eartrainer.logic.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExerciseNotes extends AppCompatActivity {
    DataAccess helper = new DataAccess(this);
    String username;
    String[] chords=new String[5];
    String[] files=new String[5];
    List<sound> chordList = new ArrayList<>();
    List<sound> answers = new ArrayList<>();
    MediaPlayer mediaPlayer;
    int answer;
    boolean answered;
    String answerName ="";
    int marks;
    int count;
    Random random;
    RadioGroup radioGroup;
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_chords);

        Intent i = getIntent();
        username = i.getExtras().getString("user");

        chordList = helper.getNotes(username);

        radioGroup = (RadioGroup)findViewById(R.id.group);
        random = new Random();

        randomize();
        answered = false;
        marks = 0;
        count = 1;

        t=(TextView)findViewById(R.id.textNum);
        t.setText(Integer.toString(count));


    }

    private void randomize(){               //this method will randomize the selections for exercises
        radioGroup.clearCheck();
        answers.clear();
        int MAX =chordList.size();
        int[] num =new int[5];
        num[0] = (int)(Math.random()*MAX);

        while (num[1] == num[0])          //this part take care not have same choice in one exercise when random selecting
        {
            num[1] = (int)(Math.random()*MAX);
        }
        while ((num[2] == num[0]) || (num[2] == num[1]) )
        {
            num[2] = (int)(Math.random()*MAX);
        }
        while ((num[3] == num[0]) || (num[3] == num[1]) || (num[3] == num[2]) )
        {
            num[3] = (int)(Math.random()*MAX);
        }
        while ((num[4] == num[0]) ||
                (num[4] == num[1]) ||
                (num[4] == num[2]) ||
                (num[4] == num[3]) )
        {
            num[4] = (int)(Math.random()*MAX);
        }
        for(int i = 0;i<5;i++){
            answers.add(i,chordList.get(num[i]));
            ((RadioButton) radioGroup.getChildAt(i)).setText(answers.get(i).getName());
        }

        answer = random.nextInt(5);      //this part used to not to have the sam answer for two adjacent exercises

        while (count!=0 && answerName.equals(answers.get(answer).getName())){
            answer = random.nextInt(5);
        }
        answerName=answers.get(answer).getName();
        int resID = getResources().getIdentifier(answers.get(answer).getFile(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
    }

    public void onRadioButtonClicked(View view) {           //when a choice is clicked

        String choice = (String) ((RadioButton) view).getText();

        if(choice==answers.get(answer).getName()){          //if correct answer
            Toast.makeText(ExerciseNotes.this,
                    "Your answer is correct!",
                    Toast.LENGTH_SHORT).show();
            if(!answered)
                marks++;
            t=(TextView)findViewById(R.id.textMarks);
            t.setText("score: "+Integer.toString(marks));

        }else {
            Toast.makeText(ExerciseNotes.this,              //if wrong choice
                    "Oops wrong answer!",
                    Toast.LENGTH_SHORT).show();
        }

        answered = true;
    }

    public void onNextButtonClicked(View view){

        refresh();
    }

    public void refresh(){              //this method generates random exercise each time it called
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        if(answered) {
            radioGroup.clearCheck();

            answered = false;
            count ++;
            if(count<16) {
                randomize();
                Toast.makeText(ExerciseNotes.this,
                        "Next Question",
                        Toast.LENGTH_SHORT).show();
                t=(TextView)findViewById(R.id.textNum);
                t.setText(Integer.toString(count));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mediaPlayer.start();
                    }
                }, 500);

            }
            else {                                  //if 15 exercises are done then go to the marks
                Intent i = new Intent(ExerciseNotes.this, com.example.chathura.eartrainer.marks.class);
                i.putExtra("marks", marks);
                i.putExtra("level",helper.levelnote);
                i.putExtra("exercise","notes");
                i.putExtra("user",username);
                startActivity(i);
                this.finish();
            }

        }
        else
            Toast.makeText(ExerciseNotes.this,
                    "Please answer the question.",
                    Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_chords, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void onPlayButtonClicked(View view) {// to play the audio segment
        try {
            mediaPlayer.reset();
            int resID = getResources().getIdentifier(answers.get(answer).getFile(), "raw", getPackageName());
            mediaPlayer = MediaPlayer.create(this, resID);
            //mediaPlayer.setDataSource(songsList.get(songIndex).get("songPath"));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.start();

    }

    public void onPauseButtonClicked(View view) { //pause the audio if playing
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();

        }
    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent i = new Intent(ExerciseNotes.this,Home.class);
        startActivity(i);
        this.finish();
        return;
    }

    public void onBackButtonClicked(View view) {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent i = new Intent(ExerciseNotes.this,Home.class);
        startActivity(i);
        this.finish();
        return;
    }
}
