package com.gmail.masonashment.proficiency04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.masonashment.proficiency04.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView resultTextView;
    private EditText numberEditText;
    private TextView operationTextView;
    private TextView warningTextView;
    private TextView responseTextView;
    private Button translateButton;
    private Button button0;
    private Button button2;
    private Button button4;
    private Button button6;
    private Button button8;
    private Button buttonAdd;
    private Button buttonDivide;
    private Button buttonEquals;
    private Button buttonClear;

    private Double operand1;
    private Double nullValue;
    private String pendingOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username = getIntent().getStringExtra("username");
        Toast.makeText(this,"Hello, "+username,Toast.LENGTH_LONG).show();

        // initialize all the buttons
        resultTextView = findViewById(R.id.resultTextView);
        numberEditText = findViewById(R.id.numberEditText);
        operationTextView = findViewById(R.id.operationTextView);
        warningTextView = findViewById(R.id.warningTextView);
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
                if (pendingOperation != null && pendingOperation.equals("=")) {
                    operand1 = nullValue;
                }
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
                warningTextView.setText("");
                operand1 = nullValue;
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
                operationTextView.setText(operation);
                try {
                    Double doubleValue = Double.valueOf(value);
                    // value == number in editText, operation == symbol on button
                    performOperation(doubleValue, operation);
                } catch (NumberFormatException e) {
                    numberEditText.setText("");
                }
                pendingOperation = operation;
            }
        };

        buttonAdd.setOnClickListener(operationListener);
        buttonDivide.setOnClickListener(operationListener);
        buttonEquals.setOnClickListener(operationListener);

        // new stuff for proficiency 3 -------------------------------------------------------------
        translateButton = findViewById(R.id.translateButton);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "translate buttonclick: executing MagicPOST");
                MagicPOST magicPOST = new MagicPOST();
                magicPOST.execute(resultTextView.getText().toString());
            }
        });


    }

    private void performOperation(Double value, String operation) {
        // check whether there is a number stored already
        if (operand1 == null) {
            operand1 = value;
        } else {
            // if the last operation was =, we want to reset so this new operation is the pending operation
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            //perform the pending operation
            switch (pendingOperation) {
                case "=": // this means they pressed = without giving us an arithmetic operation
                    operand1 = value;
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "/":
                    if (value == 0) {
                        numberEditText.setText("");
                        warningTextView.setText("Divisions by 0 will be ignored!");

                    } else {
                        operand1 /= value;
                    }
                    break;
            }
        }
        resultTextView.setText(operand1.toString());
        numberEditText.setText("");
    }

    class MagicPOST extends AsyncTask<String, Void, String> {
        private static final String TAG = "MagicPOST";

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: arg is " + s);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            String status = "false";
            String message = "empty";
            if (s != null) {
                String[] splitResponse = s.split(",");
                status = splitResponse[0].split(":")[1]; // should be in the form of "status":true, so grabs the second value
                message = splitResponse[2].split(":")[1];

                responseTextView = findViewById(R.id.responseTextView);
                responseTextView.setText(s);
            }

            builder.setTitle(status.equals("true") ? "Successful" : "Unsuccessful");
            builder.setMessage(message);
            builder.setCancelable(true);
            builder.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts");
            String urlString = "http://cgi.sice.indiana.edu/~examples/info-i494/api/index.php/magic-number";
            int parsedResult = Double.valueOf(strings[0]).intValue(); // because the server said it had to be an int
            String data = "team=59&number=" + parsedResult;
            byte[] postData = data.getBytes();
            StringBuilder sb = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                Log.d(TAG, "doInBackground: connection opened");

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputPost = new BufferedOutputStream(httpURLConnection.getOutputStream());
                outputPost.write(postData);
                outputPost.flush();
                outputPost.close();

                Log.d(TAG, "doInBackground: connecting");
                httpURLConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    Log.d(TAG, "doInBackground: line is " + line);
                    sb.append(line + '\n');
                }
                reader.close();

            } catch (MalformedURLException e) {
                System.err.println("MalformedURLException : " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IOException : " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (sb != null) {
                Log.d(TAG, "doInBackground: returning " + sb.toString());
                return sb.toString();
            } else {
                return null;
            }
        }
    }

}
