package com.example.chathura.eartrainer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.chathura.eartrainer.Exercises.ExerciseChords;
import com.example.chathura.eartrainer.Exercises.ExerciseNotes;
import com.example.chathura.eartrainer.Exercises.ExerciseScales;
import com.example.chathura.eartrainer.logic.CardAdapter;
import com.example.chathura.eartrainer.logic.CardInfo;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = "default";

        RecyclerView recList = (RecyclerView) findViewById(R.id.my_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        CardAdapter ca = new CardAdapter(createList());
        recList.setAdapter(ca);

        final GestureDetector mGestureDetector = new GestureDetector(Home.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        recList.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() { //in this segement touch events addded to cardlist items
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());



                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    if(recyclerView.getChildAdapterPosition(child)==0){
                        Intent i = new Intent(Home.this,ExerciseNotes.class);   //Card that leads to notes exercises when pressed
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==1){
                        Intent i = new Intent(Home.this,ExerciseChords.class);  //Card that leads to chords exercises when pressed
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==2){
                        Intent i = new Intent(Home.this,ExerciseScales.class);  //Card that leads to chords exercises when pressed
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==3){
                        Intent i = new Intent(Home.this,Progress.class);        //Card that leads to progress view when pressed
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==4){         ////Card that leads to help info when pressed
                        Intent i = new Intent(Home.this,Play.class);
                        i.putExtra("user",username);
                        startActivity(i);
                    }

                    return true;

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    private List<CardInfo> createList() { //in this method cards are created for the recycler view

        List<CardInfo> result = new ArrayList<CardInfo>();

        CardInfo c1 = new CardInfo();     //Card that leads to notes exercises when pressed
        c1.setTitle("Notes");
        c1.setDescription("");
        c1.setImage(R.drawable.image5);
        result.add(c1);

        CardInfo c3 = new CardInfo();       //Card that leads to chords exercises when pressed
        c3.setTitle("Chords");
        c3.setDescription("");
        c3.setImage(R.drawable.image4);
        result.add(c3);

        CardInfo c4 = new CardInfo();       //Card that leads to scales exercises when pressed
        c4.setTitle("Scale");
        c4.setDescription("");
        c4.setImage(R.drawable.image2);
        result.add(c4);

        CardInfo c5 = new CardInfo();       //Card that leads to progressvies when pressed
        c5.setTitle("Progress");
        c5.setDescription("");
        c5.setImage(R.drawable.image7);
        result.add(c5);

        CardInfo c2 = new CardInfo();       ////Card that leads to help info when pressed
        c2.setTitle("Help");
        c2.setDescription("");
        c2.setImage(R.drawable.image1);
        result.add(c2);

        return result;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

