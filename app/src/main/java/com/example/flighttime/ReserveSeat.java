package com.example.flighttime;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttime.model.Flight;
import com.example.flighttime.model.FlightRoom;

import java.util.ArrayList;
import java.util.List;

public class ReserveSeat extends AppCompatActivity {
    public static List<Flight> flights = new ArrayList<Flight>();
    public static Flight selectedFlight = null;
    public static int amountTickets = 0;
    Adapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Log.d("SearchActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserveseat);


        Button return_main_button = findViewById(R.id.return_button);
        return_main_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("SearchActivity", "onClick return called");
                finish();
            }
        });

        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("SearchActivity", "onClick search called");
                EditText from = findViewById(R.id.from_city);
                EditText to = findViewById(R.id.to_city);
                EditText no_tickets = findViewById(R.id.no_tickets);

                //Check if input is empty
                if(from.getText().toString().isEmpty() || to.getText().toString().isEmpty() ||
                        no_tickets.getText().toString().isEmpty()){
                    Log.d("SearchActivity","something is empty.");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
                    builder.setTitle("Please fill out the form correctly.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ReserveSeat.this,
                                    ReserveSeat.class);
                            finish();
                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }

                /*LAB* Check if tickets between 1 and 7 */
                int amountTickets = Integer.parseInt(no_tickets.getText().toString());

                if(amountTickets > 0 && amountTickets <= 7){
                    flights = FlightRoom.getFlightRoom(ReserveSeat.this).dao().
                            searchFlight(from.getText().toString(),
                                    to.getText().toString(),amountTickets);
                    if(flights.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
                        builder.setTitle("There are no flights available.");
                        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ReserveSeat.this,
                                        MainActivity.class);

                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                    ReserveSeat.amountTickets = amountTickets;
                    // notify recycler view that list of flights has changed
                    adapter.notifyDataSetChanged();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
                    builder.setTitle("Due to ticket restriction, you can only buy up to 7 tickets.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ReserveSeat.this,
                                    ReserveSeat.class);

                            startActivity(intent);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager( new LinearLayoutManager(this));
        adapter = new Adapter();
        rv.setAdapter( adapter );

    }

    private class Adapter  extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(ReserveSeat.this);
            return new  ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            holder.bind(flights.get(position));
        }

        @Override
        public int getItemCount() { return flights.size(); }

    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item, parent, false));
        }

        public void bind(final Flight f ) {
            TextView item = itemView.findViewById(R.id.item_id);
            item.setText(f.toString());

            // make the item clickable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("onClick2","f is: \n" + f.toString());
                    selectedFlight = f;
                    Log.d("onClick2","selected Flight is: \n" + selectedFlight.toString());
                    flights.clear();
                    Intent intent = new Intent(ReserveSeat.this, CreateReservation.class);
                    finish();
                    startActivity(intent);
                }
            });


        }
    }

}
