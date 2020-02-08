package com.example.flighttime.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;




@Entity

public class Reservation {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id; //reservation no
    @NonNull
    private String time; //reservation time
    @NonNull
    private String username;
    @NonNull
    private String flightNo;
    @NonNull
    private String departure;
    @NonNull
    private String arrival;
    @NonNull
    private String departureTime;
    @NonNull
    private double total;
    @NonNull
    private int tickets; //amount of tickets


    public Reservation() {}

    @Ignore
    public Reservation(String username, Flight flight, int tickets){
        this.username=username;
        this.time = new java.util.Date().toString();
        this.flightNo = flight.getFlightNo();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.departureTime = flight.getDepartureTime();
        this.total = tickets * flight.getPrice();
        this.tickets = tickets;
    }

    @Override
    public String toString(){
        String d = "";
        if(username.length() > 0 ) d = "Username: " + username;
        d += "\nFlight No: " + flightNo +
                "\nDeparture: " + departure + " " + departureTime +
                "\nArrival: " + arrival +
                "\nNumber of Tickets: " + tickets;
        d+= "\nReservation Number: " + id;

        d+= "\nTotal Cost: $" + total;
        return d;
    }

    public String getLog() {
        return "Flight No: " + flightNo +
                "\nDeparture: " + departure + " " + departureTime +
                "\nArrival: " + arrival +
                "\nNumber of Tickets: " + tickets +
                "\nReservation number: " + id +
                "\nTotal Cost: $" + total;

    }

    public String getCancelLog() {
        return  "Reservation number: " + id +
                "\nFlight No: " + flightNo +
                "\nDeparture: " + departure + " " + departureTime +
                "\nArrival: " + arrival +
                "\nNumber of Tickets: " + tickets;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(@NonNull String flightNo) {
        this.flightNo = flightNo;
    }

    @NonNull
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(@NonNull String departure) {
        this.departure = departure;
    }

    @NonNull
    public String getArrival() {
        return arrival;
    }

    public void setArrival(@NonNull String arrival) {
        this.arrival = arrival;
    }

    @NonNull
    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(@NonNull String departureTime) {
        this.departureTime = departureTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }
}
