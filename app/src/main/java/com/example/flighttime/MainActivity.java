//Rodrigo Espinoza
// this app is airline app that can be used to book flights and mange flights



package com.example.flighttime;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttime.model.FlightRoom;

public class MainActivity extends AppCompatActivity {
    public static String user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check database
        FlightRoom.getFlightRoom(MainActivity.this).loadData(this);
        Button creat_account_button = findViewById(R.id.create_account);
        creat_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Main","onclick for account called");
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
            }
        });
        Button managebutton = findViewById(R.id.manage_system);
        managebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // call the ShowUser Activity
                Log.d("MainActivity", "onClick for manage system is called");
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });
        Button reserve_seat_button = findViewById(R.id.reserve_seat);
        reserve_seat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // call the ShowUser Activity
                Log.d("MainActivity", "onClick for reserve seat called");
                Intent intent = new Intent(MainActivity.this, ReserveSeat.class);
                startActivity(intent);

            }
        });
        Button cancel_reservation_button = findViewById(R.id.cancel_reservation);
        cancel_reservation_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // call the ShowUser Activity
                Log.d("MainActivity", "onClick for cancel reservation called");
                Intent intent = new Intent(MainActivity.this, Cancel.class);
                startActivity(intent);

            }
        });


    }
}
