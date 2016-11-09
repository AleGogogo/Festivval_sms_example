package com.example.lyw.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String[] mValues = new String[]
            {
              "Hello!","Android","Button","WE are the world!","happy!",
              "TextView","JNI","android servier","broadcast","wo men shi",
               "la la la"
            };
    private FlowLayout mLayout;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (FlowLayout)findViewById(R.id.id_flowlayout);
        initData();
    }

    private void initData() {
        inflater = LayoutInflater.from(this);
        for (int i =0 ;i< mValues.length;i++){
            TextView tv = (TextView) inflater.inflate(R.layout.tv,mLayout,false);
            tv.setText(mValues[i]);
            mLayout.addView(tv);
        }
    }
}
