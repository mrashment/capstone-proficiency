package com.gmail.masonashment.proficiency01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Date noon;
    private Date afternoonEnd;
    private Date earlyMorn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the current time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();

        try {
            noon = timeFormat.parse("12:00:00");
            afternoonEnd = timeFormat.parse("18:00:00");
            earlyMorn = timeFormat.parse("05:45:00");

        } catch(ParseException e) {
            e.printStackTrace();
        }

        //create variable to reference textview
        TextView messageTextView = (TextView) findViewById(R.id.messageTextview);

        //set the textviews message
        if(currentTime.equals(earlyMorn) ||
                (currentTime.after(earlyMorn) && currentTime.before(noon))) {
            messageTextView.setText("Good Morning!");
        }
        else if(currentTime.equals(noon) ||
                (currentTime.after(noon) && currentTime.before(afternoonEnd))) {
            messageTextView.setText("Good Afternoon!");
        }
        else {
            messageTextView.setText("Good Evening!");
        }

    }
}
