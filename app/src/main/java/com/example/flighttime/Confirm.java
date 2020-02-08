package com.example.flighttime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttime.model.Flight;
import com.example.flighttime.model.FlightDao;
import com.example.flighttime.model.FlightRoom;
import com.example.flighttime.model.LogRecord;

import java.util.Date;

public class Confirm  extends AppCompatActivity {
    FlightDao dao = FlightRoom.getFlightRoom(Confirm.this).dao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Confirmation", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        String information = CreateReservation.res.toString();

        //Show booking info
        TextView summary = findViewById(R.id.summary);
        summary.setText(information);

        //Confirm booking info
        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //update flight capacity
                Flight updateFlight = ReserveSeat.selectedFlight;
                updateFlight.setCapacity(updateFlight.getCapacity()-ReserveSeat.amountTickets);
                dao.updateFlight(updateFlight);
                //Log the reservation
                //write a record to Log table with message that reservation is confirmed.
                //  include username (but not password) in the message.
                Date now = new Date();
                LogRecord rec = new LogRecord(now, LogRecord.TYPE_RESERVATION,
                        MainActivity.user, CreateReservation.res.getLog());
                dao.addLogRecord(rec);

                //Go back to main menu
                Intent intent = new Intent(Confirm.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //if declined delete reservation
        Button decline = findViewById(R.id.decline);
        decline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //delete res from db
                dao.deleteReservation(CreateReservation.res);
                //go back to main menu
                Intent intent = new Intent(Confirm.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
