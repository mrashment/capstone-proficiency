package com.gmail.masonashment.proficiency05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText emailEditText, fnameEditText, lnameEditText, passwordEditText, verifyEditText;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        warningTextView = findViewById(R.id.warningTextView);
        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        fnameEditText = findViewById(R.id.fnameEditText);
        lnameEditText = findViewById(R.id.lnameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        verifyEditText = findViewById(R.id.verifyEditText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String fname = fnameEditText.getText().toString();
                String lname = lnameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String verify = verifyEditText.getText().toString();
                if(password.length() > 8) {
                    if(password.equals(verify)) {
                        registerUser(email, fname, lname, password);
                    }
                    else {
                        warningTextView.setText("Please make sure passwords match.");
                    }
                }
                else {
                    warningTextView.setText("Password must be over 8 characters.");
                }
            }
        });
    } // onCreate

    private void registerUser(String email, String fname, String lname, String password) {
        RegisterThread thread = new RegisterThread();
        thread.execute();
    }

    class RegisterThread extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPostExecute(String[] s) {
            String response = s[0].trim();
            switch(response) {
                case "User already exists.":
                    warningTextView.setText("Email already in use.");
                    break;
                case "Failure":
                    warningTextView.setText("Something went wrong. (SQL)");
                    break;
                case "Success":
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("fname",s[1]);
                    startActivity(intent);
                    break;
                default:
                    warningTextView.setText("Something went wrong. (Server)");
            }
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String urlString = "http://cgi.sice.indiana.edu/~mashment/itp5_register.php";
            String data = "email=" + strings[0] + "&fname=" + strings[1] + "&lname=" + strings[2] + "&password=" + strings[3];
            byte[] postData = data.getBytes();
            StringBuilder sb = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputPost = new BufferedOutputStream(httpURLConnection.getOutputStream());
                outputPost.write(postData);
                outputPost.flush();
                outputPost.close();


                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
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
                String[] values = new String[] {sb.toString(),strings[1]};
                return values;
            } else {
                return null;
            }
        }
    }
}
