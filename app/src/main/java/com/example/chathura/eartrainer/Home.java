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

        ContactAdapter ca = new ContactAdapter(createList());
        recList.setAdapter(ca);

        final GestureDetector mGestureDetector = new GestureDetector(Home.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        recList.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());



                if(child!=null && mGestureDetector.onTouchEvent(motionEvent)){
                    if(recyclerView.getChildAdapterPosition(child)==0){
                        Intent i = new Intent(Home.this,ExerciseNotes.class);
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==1){
                        Intent i = new Intent(Home.this,ExerciseChords.class);
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==2){
                        Intent i = new Intent(Home.this,ExerciseScales.class);
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==3){
                        Intent i = new Intent(Home.this,Progress.class);
                        i.putExtra("user",username);
                        startActivity(i);
                    }
                    if(recyclerView.getChildAdapterPosition(child)==4){
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

    private List<ContactInfo> createList() {

        List<ContactInfo> result = new ArrayList<ContactInfo>();

        ContactInfo c1 = new ContactInfo();
        c1.setTitle("Notes");
        c1.setDescription("Identify Notes");
        c1.setImage(R.drawable.image5);
        result.add(c1);

        ContactInfo c3 = new ContactInfo();
        c3.setTitle("Chords");
        c3.setDescription("Identify Chords");
        c3.setImage(R.drawable.image4);
        result.add(c3);

        ContactInfo c4 = new ContactInfo();
        c4.setTitle("Scale");
        c4.setDescription("Identify Scales");
        c4.setImage(R.drawable.image2);
        result.add(c4);

        ContactInfo c5 = new ContactInfo();
        c5.setTitle("Progress");
        c5.setDescription("See your Progress");
        c5.setImage(R.drawable.image7);
        result.add(c5);

        ContactInfo c2 = new ContactInfo();
        c2.setTitle("Help");
        c2.setDescription("Some Information");
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

