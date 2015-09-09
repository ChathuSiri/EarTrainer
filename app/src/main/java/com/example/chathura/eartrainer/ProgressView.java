package com.example.chathura.eartrainer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.chathura.eartrainer.dataaccess.DataAccess;
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

        //this segment charts attributes will be set according to our requirment
        BarChart chart = (BarChart) findViewById(R.id.chart);
        BarDataSet set =getDataSet();
        BarData data = new BarData(getXAxisValues(size), set);
        chart.setData(data);
        chart.setDescription(exercise);
        chart.setDescriptionColor(Color.WHITE);
        chart.setDescriptionPosition(185, 35);
        chart.setScaleY(0.93f);
        chart.invalidate();
        chart.setDescriptionTextSize(40);
        chart.setDrawGridBackground(false);

        chart.animateY(1000);                       //Y axes attributes
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setAxisMaxValue(15);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setTextColor(Color.WHITE);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);

        chart.getXAxis().setTextColor(Color.WHITE); //X axis attributes
        chart.getXAxis().setDrawLabels(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);

        chart.setDrawValueAboveBar(true);           //Zoom and scale capabilities
        chart.setScaleYEnabled(false);
        chart.setPinchZoom(false);


    }

    private BarDataSet getDataSet() {                       //dataset will be returned barchart
        ArrayList<BarEntry> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        valueSet1 = helper.getDataSet(exercise);
        size = valueSet1.size();
        BarDataSet dataset = new BarDataSet(valueSet1, null);

        dataset.setColor( Color.parseColor("#f58233") );    //bar color changed to orange color
        dataset.setBarShadowColor(Color.parseColor("#f58233"));

        return dataset;

    }

    private ArrayList<String> getXAxisValues(int length) {      //set x axis values
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("2");

        for(int i =0;i<length;i++){
            xAxis.add("2");
        }
        return xAxis;
    }

}

