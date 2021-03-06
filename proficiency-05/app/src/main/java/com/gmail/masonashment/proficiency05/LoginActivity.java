package com.gmail.masonashment.proficiency05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gmail.masonashment.proficiency05.MainActivity;
import com.gmail.masonashment.proficiency05.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private TextView responseTextView;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        responseTextView = findViewById(R.id.responseTextView);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(usernameEditText.getText().toString(),passwordEditText.getText().toString());
            }
        });

    }

    private void loginUser(String username, String password) {
        LoginThread loginThread = new LoginThread();
        loginThread.execute(username,password);

    }

    class LoginThread extends AsyncTask<String,Void,String[]> {
        @Override
        protected void onPostExecute(String[] s) {
            String response = s[0].trim();
            switch(response) {
                case "Invalid Email":
                    responseTextView.setText("User not found.");
                    break;
                case "Invalid Password":
                    responseTextView.setText("Incorrect password.");
                    break;
                case "success":
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",s[1]);
                    startActivity(intent);
                    break;
                default:
                    responseTextView.setText("Something went wrong.");
            }
        }

        @Override
        protected String[] doInBackground(String... strings) {
            String urlString = "http://cgi.sice.indiana.edu/~mashment/itp5_login.php";
            String data = "email=" + strings[0] + "&password=" + strings[1];
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
                String[] values = new String[] {sb.toString(),strings[0]};
                return values;
            } else {
                return null;
            }
        }
    }
}
