package com.example.flighttime;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttime.model.FlightRoom;
import com.example.flighttime.model.LogRecord;

import java.util.List;


public class Logs extends AppCompatActivity {
    private ViewLogAdapter adapter;
    private List<LogRecord> records;
    @Override
    protected void onCreate (Bundle savedInstanceState ){
        Log.d("Logs"," on create called ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);



        // get all records from database
        records= FlightRoom.getFlightRoom(this).dao().getAllLogRecords();

        if (records.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(Logs.this);
            builder.setTitle("No logs at the moment ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();;
        }
        Button main_menu = findViewById(R.id.confirm);
        main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ViewLogActivity", "onClick return called");

                AlertDialog.Builder builder = new AlertDialog.Builder(Logs.this);
                builder.setTitle("Do you want to add a new flight?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Logs.this, AddFlight.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     Intent intent = new Intent(Logs.this, MainActivity.class);
                     startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // create view for listing of all users
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager( new LinearLayoutManager(this));
        adapter = new ViewLogAdapter();
        rv.setAdapter(adapter);
    }
    private class ViewLogAdapter  extends RecyclerView.Adapter<ItemHolder> {
        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(Logs.this);
            return new ItemHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            holder.bind(records.get(position));
        }
        @Override
        public int getItemCount() { return records.size(); }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item , parent, false));
        }

        public void bind(LogRecord rec) {
            TextView item = itemView.findViewById(R.id.item_id);
            item.setText(rec.toString());
        }
    }

}
