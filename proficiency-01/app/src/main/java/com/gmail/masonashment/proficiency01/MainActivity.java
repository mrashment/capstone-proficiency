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
    private Date current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the current time
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formatter = "oops";
        Date currentTime = Calendar.getInstance().getTime();

        try {
            noon = timeFormat.parse("12:00:00");
            afternoonEnd = timeFormat.parse("18:00:00");
            earlyMorn = timeFormat.parse("05:45:00");
            formatter = timeFormat.format(currentTime);
            current = timeFormat.parse(formatter);

        } catch(ParseException e) {
            e.printStackTrace();
        }

        //create variable to reference textviews
        TextView timeTextView = (TextView) findViewById(R.id.timeTextview);
        TextView messageTextView = (TextView) findViewById(R.id.messageTextview);

        //set the textviews messages
        timeTextView.setText(formatter);
        if(current.equals(earlyMorn) ||
                (current.after(earlyMorn) && current.before(noon))) {
            messageTextView.setText("Good Morning!");
        }
        else if(current.equals(noon) ||
                (current.after(noon) && current.before(afternoonEnd))) {
            messageTextView.setText("Good Afternoon!");
        }
        else {
            messageTextView.setText("Good Evening!");
        }

    }
}
