package com.gmail.masonashment.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private EditText numberEditText;
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

        // initialize all the buttons
        resultTextView = findViewById(R.id.resultTextView);
        numberEditText = findViewById(R.id.numberEditText);
        button0 = findViewById(R.id.button0);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button6 = findViewById(R.id.button6);
        button8 = findViewById(R.id.button8);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonClear = findViewById(R.id.buttonClear);

        // number will be appended to the numberEditText
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //append the number
                Button button = (Button) view;
                numberEditText.append(button.getText().toString());
            }
        };

        // set on click listeners for numbers
        button0.setOnClickListener(numberListener);
        button2.setOnClickListener(numberListener);
        button4.setOnClickListener(numberListener);
        button6.setOnClickListener(numberListener);
        button8.setOnClickListener(numberListener);

        // clear button erases all text
        View.OnClickListener clearListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberEditText.setText("");
                resultTextView.setText("");
            }
        };
        buttonClear.setOnClickListener(clearListener);

        // listener for the operation buttons
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                String operation = button.getText().toString();
                String value = numberEditText.getText().toString();
                // value == number in editText, operation == symbol on button
                performOperation(value,operation);
            }
        };
    }

    private void performOperation(String value, String operation) {

    }

}
