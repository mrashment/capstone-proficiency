package com.gmail.masonashment.proficiency01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

        // how I want the time to be displayed
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formatter = "oops"; //coming back to this I have no idea why I wrote oops, but I'm assuming it's essential
        // get current time
        Date currentTime = Calendar.getInstance().getTime();

        try {
            // set times so I can compare current time to them later
            noon = timeFormat.parse("12:00:00");
            afternoonEnd = timeFormat.parse("18:00:00");
            earlyMorn = timeFormat.parse("05:45:00");
            // this ensures the current time is in the same format as the others
            formatter = timeFormat.format(currentTime);
            current = timeFormat.parse(formatter);

        } catch(ParseException e) {
            e.printStackTrace();
        }

        // create variable to reference textviews
        TextView timeTextView = findViewById(R.id.timeTextview);
        TextView messageTextView = findViewById(R.id.messageTextview);

        // set the textviews messages
        timeTextView.setText(formatter);

        // check if its the morning
        if(current.equals(earlyMorn) ||
                (current.after(earlyMorn) && current.before(noon))) {
            messageTextView.setText("Good Morning!");
        }
        // afternoon
        else if(current.equals(noon) ||
                (current.after(noon) && current.before(afternoonEnd))) {
            messageTextView.setText("Good Afternoon!");
        }
        // evening
        else {
            messageTextView.setText("Good Evening!");
        }

    }
}
