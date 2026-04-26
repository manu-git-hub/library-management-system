package com.airtribe.java.Entity;

import com.airtribe.java.Enum.ReservationStatus;

public class Reservation {
    private String patronId;
    private String isbn;
    private ReservationStatus status;

    public Reservation(String patronId, String isbn) {
        this.patronId = patronId;
        this.isbn = isbn;
        this.status = ReservationStatus.ACTIVE;
    }

    public String getPatronId() { return patronId; }
    public void complete() { status = ReservationStatus.COMPLETED; }
}
