package com.gmail.masonashment.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private Button button0;
    private Button button2;
    private Button button4;
    private Button button6;
    private Button button8;
    private Button buttonAdd;
    private Button buttonDivide;
    private Button buttonEquals;
    private Button buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button0);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button6 = findViewById(R.id.button6);
        button8 = findViewById(R.id.button8);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonClear = findViewById(R.id.buttonClear);


    }

}
