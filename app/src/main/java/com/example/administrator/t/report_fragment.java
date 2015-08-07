package com.example.administrator.t;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class report_fragment extends android.support.v4.app.Fragment {



    ArrayList<Double> yList;

    // fake database
    static final Double[] record = new Double[]{2.0,4.4,2.2,3.1,1.1,2.2,3.1,1.1,4.4,
            1.1,4.4,2.2,7.7,5.2,4.9,7.1,1.1,4.4,2.2,7.7,5.2,4.9,3.1,1.1,4.4,2.2,3.1,1.1,4.4,1.1};

    static final Double[] record_total = new Double[]{7.8,6.9,6.4,6.3,5.8,5.76,5.2,5.1,4.2,4.0,
            3.6,3.3,3.2,2.6,2.3,2.1,1.3,1.0,0.8,0.2,0.1,0.05};

    View rootview;

    RadioGroup radiogroup1,radiogroup2;
    RadioButton single, total, week, month,year;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.report, container, false);

        LineGraphicView tu = new LineGraphicView(this.getActivity());

        yList = new ArrayList<Double>();
        for(int i=0; i<7;i++) {
            yList.add(record[6-i]);
        }

        ArrayList<String> xRawDatas = new ArrayList<String>();
        for(int i=1; i<8; i++) {
            xRawDatas.add("Day"+i);
        }

        tu.setData(yList, xRawDatas, 8, 2);
        LinearLayout linearlayout = (LinearLayout)rootview.findViewById(R.id.report);
        linearlayout.addView(tu);

         //get radio groups & buttons
        radiogroup1=(RadioGroup)rootview.findViewById(R.id.radiogroup1);
        radiogroup2=(RadioGroup)rootview.findViewById(R.id.radiogroup2);
        single=(RadioButton)rootview.findViewById(R.id.single);
        total=(RadioButton)rootview.findViewById(R.id.total);
        week=(RadioButton)rootview.findViewById(R.id.week);
        month=(RadioButton)rootview.findViewById(R.id.month);
        year=(RadioButton)rootview.findViewById(R.id.year);

         //set default for radio choice
        single.setChecked(true);
        week.setChecked(true);

        // onclick method for group1
        addListenerRadioGroup1();
        // onclick method for group2
        addListenerRadioGroup2();

        return rootview;
    }


    private void addListenerRadioGroup1(){

        radiogroup1=(RadioGroup)rootview.findViewById(R.id.radiogroup1);
        single=(RadioButton)rootview.findViewById(R.id.single);
        total=(RadioButton)rootview.findViewById(R.id.total);

        // set onClick method for radiogroup1
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // text for testing purpose
//                TextView result = (TextView) rootview.findViewById(R.id.text1);

//                 access graph and clear
                LinearLayout linearlayout = (LinearLayout) rootview.findViewById(R.id.report);
                linearlayout.removeAllViews();
                LineGraphicView tu = new LineGraphicView(getActivity());

                // detect radiogroup2 status, obtain size of input data list
                int duration = 7;
                if (radiogroup2.getCheckedRadioButtonId() == week.getId()) {
                    duration = 7;
                } else if (radiogroup2.getCheckedRadioButtonId() == month.getId()) {
                    duration = 13;
                } else {
                    duration = 20;
                }

                //actions for button total
                if (checkedId == total.getId()) {
//                  result.setText("" + total.getText() + ((RadioButton) rootview.findViewById(radiogroup2.getCheckedRadioButtonId())).getText());
                    yList = new ArrayList<Double>();
                    for(int i=0; i<duration;i++) {
                        yList.add(record_total[duration-1-i]);
                    }

                    ArrayList<String> xRawDatas = new ArrayList<String>();
                    for(int i=1; i<(duration+1); i++) {
                        xRawDatas.add("Day"+i);
                    }

                    tu.setData(yList, xRawDatas, 8, 2);
                    linearlayout.addView(tu);

                } else {

//                    result.setText("" + single.getText() + ((RadioButton) rootview.findViewById(radiogroup2.getCheckedRadioButtonId())).getText());

                    yList = new ArrayList<Double>();
                    for (int i = 0; i < duration; i++) {
                        yList.add(record[duration - 1 - i]);
                    }

                    ArrayList<String> xRawDatas = new ArrayList<String>();
                    for (int i = 1; i < (duration + 1); i++) {
                        xRawDatas.add("Day" + i);
                    }

                    tu.setData(yList, xRawDatas, 8, 2);
                    linearlayout.addView(tu);
                }
            }
        });

    }

    private void addListenerRadioGroup2(){

        radiogroup2=(RadioGroup)rootview.findViewById(R.id.radiogroup2);
        week=(RadioButton)rootview.findViewById(R.id.week);
        month=(RadioButton)rootview.findViewById(R.id.month);
        year=(RadioButton)rootview.findViewById(R.id.year);

        radiogroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //TextView result = (TextView) rootview.findViewById(R.id.text1);
                // access graph and clear
                LinearLayout linearlayout = (LinearLayout)rootview.findViewById(R.id.report);
                linearlayout.removeAllViews();
                LineGraphicView tu = new LineGraphicView(getActivity());

                int duration = 7;

                if (checkedId == week.getId()) {

                    duration = 7;
                    //result.setText("" + ((RadioButton) rootview.findViewById(radiogroup1.getCheckedRadioButtonId())).getText() + week.getText() + duration);
                    if(radiogroup1.getCheckedRadioButtonId() == single.getId()) {

                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);

                    }else {

                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record_total[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);
                    }

                } else if (checkedId == month.getId()) {
                    duration = 13;
//                    result.setText("" + ((RadioButton) rootview.findViewById(radiogroup1.getCheckedRadioButtonId())).getText() + month.getText() + duration);
                    if(radiogroup1.getCheckedRadioButtonId() == single.getId()) {

                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);

                    }else {
                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record_total[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);
                    }
                } else {
                    duration = 20;
//                    result.setText("" + ((RadioButton) rootview.findViewById(radiogroup1.getCheckedRadioButtonId())).getText() + year.getText() + duration);
                    if(radiogroup1.getCheckedRadioButtonId() == single.getId()) {

                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);

                    }else {
                        yList = new ArrayList<Double>();
                        for(int i=0; i<duration;i++) {
                            yList.add(record_total[duration-1-i]);
                        }

                        ArrayList<String> xRawDatas = new ArrayList<String>();
                        for(int i=1; i<(duration+1); i++) {
                            xRawDatas.add("Day"+i);
                        }

                        tu.setData(yList, xRawDatas, 8, 2);
                        linearlayout.addView(tu);
                    }
                }
            }
        });
    }

}
