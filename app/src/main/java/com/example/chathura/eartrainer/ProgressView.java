package com.example.chathura.eartrainer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ProgressView extends AppCompatActivity {

    RelativeLayout layout;
    DataAccess helper = new DataAccess(this);
    String exercise = "chords";
    int size;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_view);
        layout=(RelativeLayout) findViewById(R.id.layout);

        helper.getUserDetails();
        Intent i = getIntent();
        exercise = i.getExtras().getString("exercise");

        BarChart chart = (BarChart) findViewById(R.id.chart);
        BarDataSet set =getDataSet();
        BarData data = new BarData(getXAxisValues(size), set);
        chart.setData(data);
        chart.setDescription(exercise);
        chart.setDescriptionColor(Color.WHITE);
        chart.setDescriptionPosition(185, 35);
        chart.setScaleY(0.93f);
        chart.setDescriptionTextSize(40);
        chart.animateY(1000);
        chart.invalidate();
        //chart.setDescription("");    // Hide the description
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setAxisMaxValue(15);

        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.setDrawValueAboveBar(true);

        //chart.setBackgroundColor(Color.BLACK);
        //chart.setTouchEnabled(false);
        //chart.setScaleEnabled(false);
        chart.setScaleYEnabled(false);
        //chart.setDragEnabled(false);
        chart.setPinchZoom(false);
        int ResourceId = this.getResources().getIdentifier("guitar", "drawable", this.getPackageName());
        //chart.setBackgroundResource(ResourceId);
        chart.setDrawGridBackground(false);

    }

    private BarDataSet getDataSet() {
        ArrayList<BarEntry> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        valueSet1 = helper.getDataSet(exercise);
        size = valueSet1.size();
        BarDataSet dataset = new BarDataSet(valueSet1, null);
        dataset.setColor( Color.parseColor("#f58233") );
        dataset.setBarShadowColor(Color.parseColor("#f58233"));

        return dataset;

    }

    private ArrayList<String> getXAxisValues(int length) {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("2");

        for(int i =0;i<length;i++){
            xAxis.add("2");
        }
        return xAxis;
    }

   /* @Override
    public void onBackPressed() {
        Intent i = new Intent(ProgressView.this,Progress.class);
        startActivity(i);
        this.finish();
        return;

    }*/
}

