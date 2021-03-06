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

public class ExerciseChords extends AppCompatActivity {
    DataAccess helper = new DataAccess(this);
    String username;
    String[] chords=new String[5];
    String[] files=new String[5];
    List<sound> chordList = new ArrayList<>();
    List<sound> answers = new ArrayList<>();
    MediaPlayer mediaPlayer;
    int answer;
    String answerName = "";
    boolean answered;
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

        chordList = helper.getChords("default");

        radioGroup = (RadioGroup)findViewById(R.id.group);
        random = new Random();

        randomize();

        answered = false;
        marks = 0;
        count = 1;

        t=(TextView)findViewById(R.id.textNum);
        t.setText(Integer.toString(count));


    }

    private void randomize(){       //this method randome generate the next execise
        radioGroup.clearCheck();
        answers.clear();
        int MAX =chordList.size();
        int[] num =new int[5];
        num[0] = (int)(Math.random()*MAX);

        while (num[1] == num[0])    //In order to same choices not to be included in the exercise more than once this procedure had to be used
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
        for(int i = 0;i<5;i++){                     //finally completely different five segments will be randomly selected
            answers.add(i,chordList.get(num[i]));
            ((RadioButton) radioGroup.getChildAt(i)).setText(answers.get(i).getName());
        }

        answer = random.nextInt(5);                 //this part takes care of not having the same answer in two adjacent exercise
        while (count!=0 && answerName.equals(answers.get(answer).getName())){
            answer = random.nextInt(5);
        }
        answerName=answers.get(answer).getName();
        int resID = getResources().getIdentifier(answers.get(answer).getFile(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
    }

    public void onRadioButtonClicked(View view) {       // when a choice is clicked

        String choice = (String) ((RadioButton) view).getText();

        if(choice==answers.get(answer).getName()){      //if the choice is correct
            Toast.makeText(ExerciseChords.this,
                    "Your answer is correct!",
                    Toast.LENGTH_SHORT).show();
            if(!answered)
                marks++;
            t=(TextView)findViewById(R.id.textMarks);   //if the choice is wrong
            t.setText("score: "+Integer.toString(marks));

        }else {
            Toast.makeText(ExerciseChords.this,
                    "Oops wrong answer!",
                    Toast.LENGTH_SHORT).show();
        }

        answered = true;
    }

    public void onNextButtonClicked(View view){

        refresh();
    }

    public void refresh(){          //this methods generate random exercises
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        if(answered) {
            radioGroup.clearCheck();

            answered = false;
            count ++;
            if(count<16) {
                randomize();
                Toast.makeText(ExerciseChords.this,
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
            else {                                        //if 15 exercises are generated finish the session and goto show marks
                Intent i = new Intent(ExerciseChords.this, com.example.chathura.eartrainer.marks.class);
                i.putExtra("user",username);
                i.putExtra("level",helper.levelchord);
                i.putExtra("exercise","chords");
                i.putExtra("marks", marks);
                startActivity(i);
                this.finish();
            }

        }
        else
            Toast.makeText(ExerciseChords.this,
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

    public void onPlayButtonClicked(View view) {    //plays the audio file
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

    public void onPauseButtonClicked(View view) { //pause the audio
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();

        }
    }

    @Override
    public void onBackPressed() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent i = new Intent(ExerciseChords.this,Home.class);
        startActivity(i);
        this.finish();
        return;
    }

    public void onBackButtonClicked(View view) {
        if(mediaPlayer.isPlaying())
            mediaPlayer.stop();
        Intent i = new Intent(ExerciseChords.this,Home.class);
        startActivity(i);
        this.finish();
        return;
    }
}
