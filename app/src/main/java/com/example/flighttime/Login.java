package com.example.flighttime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttime.model.FlightDao;
import com.example.flighttime.model.FlightRoom;
import com.example.flighttime.model.User;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState){
        Log.d("login", "oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        final Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText userN = findViewById(R.id.user_name);
                EditText pass = findViewById(R.id.password);
                String name = userN.getText().toString();
                String password = pass.getText().toString();

                if (name.equals("admin2") && password.equals("admin2")){
                    // administrator user
                    MainActivity.user = userN.getText().toString();
                    Intent intent = new Intent(Login.this, Logs.class);
                    startActivity(intent);
                    finish();

                }
                FlightDao dao = FlightRoom.getFlightRoom(Login.this).dao();
                User user = dao.login(name,password);
                if (user== null){
                    // user not found
                    TextView msg = findViewById(R.id.message);
                    msg.setText("Username or Password is incorrect");
                }else {
                    AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }
            }
        });
    }
}
