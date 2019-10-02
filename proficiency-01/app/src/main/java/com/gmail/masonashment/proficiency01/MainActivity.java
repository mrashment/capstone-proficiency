package com.gmail.masonashment.proficiency01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the current time
        Date currentTime = Calendar.getInstance().getTime();

        //create variable to reference textview
        TextView messageTextView = (TextView) findViewById(R.id.messageTextview);

        //set the textviews message
        messageTextView.setText(currentTime.toString());

    }
}
