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

import com.example.flighttime.model.*;

public class CreateReservation extends AppCompatActivity {
    private Flight selectedFlight = ReserveSeat.selectedFlight;

    public static Reservation res = null;
    int tries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CreateReservati", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);

                if (username.getText().toString().equals("!admiM2") &&
                        password.getText().toString().equals("!admiM2")) {

                    finish();
                }

                String name = username.getText().toString();
                String pw = password.getText().toString();

                FlightDao dao = FlightRoom.getFlightRoom(CreateReservation.this).dao();
                User user = dao.login(name, pw);
                if (user == null) {
                    // unsuccessful login
                    TextView msg = findViewById(R.id.message);
                    msg.setText("User name or password is invalid. Please enter again.");

                    tries += 1;
                    if(tries == 2){
                        tries = 0;

                        // inform user that he used to many tries -> return to main menu
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservation.this);
                        builder.setTitle("To many fails. Try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


                } else {
                    // successful login
                    MainActivity.user = username.getText().toString();
                    CreateReservation.res = new Reservation(MainActivity.user, selectedFlight, ReserveSeat.amountTickets);
                    res.setId((int)dao.addReservation(res));
                    Intent intent = new Intent(CreateReservation.this, Confirm.class);
                    startActivity(intent);
                }

            }
        });

    }

}
