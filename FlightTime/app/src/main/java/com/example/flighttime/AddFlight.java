package com.example.flighttime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttime.model.Flight;
import com.example.flighttime.model.FlightRoom;
import com.example.flighttime.model.LogRecord;
import com.example.flighttime.model.FlightDao;
import java.util.Date;

public class AddFlight  extends AppCompatActivity {
    FlightDao dao = FlightRoom.getFlightRoom(AddFlight.this).dao();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d("CancelReservatiActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addflight);

        final EditText flightNo_ = findViewById(R.id.flight_number_edit);
        final EditText from_ = findViewById(R.id.from_edit);
        final EditText to_ = findViewById(R.id.to_edit);
        final EditText time_ = findViewById(R.id.time_edit);
        final EditText capacity_ = findViewById(R.id.capacity_edit);
        final EditText price_ = findViewById(R.id.price_edit);


        Button addFlight = findViewById(R.id.add_flight);
        addFlight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Parsing Input
                String flightNo = flightNo_.getText().toString();
                String from = from_.getText().toString();
                String to = to_.getText().toString();
                String time = time_.getText().toString();
                int capacity = Integer.parseInt(capacity_.getText().toString());
                double price = Double.parseDouble(price_.getText().toString());

                boolean empty = false;
                if(flightNo.isEmpty() || from.isEmpty() || to.isEmpty() || time.isEmpty() ||
                        capacity_.getText().toString().isEmpty() || price_.getText().toString().isEmpty()){
                    empty = true;
                }
                if(dao.getFlightByFlightNo(flightNo)==null && !empty){
                    Flight addFlight = new Flight(flightNo,from,to,time,capacity,price);
                    addFlight.setId((int)dao.addFlight(addFlight));

                    //Log a new flight
                    Date now = new Date();
                    LogRecord rec = new LogRecord(now, LogRecord.TYPE_NEW_FLIGHT,
                            MainActivity.user, addFlight.toString());
                    dao.addLogRecord(rec);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFlight.this);
                    builder.setTitle("Flight added.");
                    builder.setPositiveButton("Confirm.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddFlight.this,
                                    MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();



                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddFlight.this);
                    builder.setTitle("Flight already exists or you didn't fill out the form correctly.");
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddFlight.this,
                                    MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
    }

}
