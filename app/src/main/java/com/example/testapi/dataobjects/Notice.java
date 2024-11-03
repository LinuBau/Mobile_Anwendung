package com.example.testapi.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import kotlin.Pair;

public class Notice implements Parcelable {
    private String AnzeigeName;
    private String Beschreibung;
    private int erstellerId;
    private long timestamp;

    public final static Comparator<Notice> NoticeDescendingTime = new Comparator<Notice>() {
        @Override
        public int compare(Notice t, Notice t1) {
            return  Long.compare(t1.getTimestamp(),t.getTimestamp());
        }
    };


    @Override
    public String toString() {
        return "Notice{" +
                "AnzeigeName='" + AnzeigeName + '\'' +
                ", Beschreibung='" + Beschreibung + '\'' +
                ", erstellerId=" + erstellerId +
                ", timestamp=" + timestamp +
                '}';
    }

    public Notice(String beschreibung, String anzeigeName, int erstellerId) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
        this.timestamp = System.currentTimeMillis(); // Automatisch aktuellen Timestamp setzen
    }

    // Zusätzlicher Konstruktor mit manuellem Timestamp
    public Notice(String beschreibung, String anzeigeName, int erstellerId, long timestamp) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
        this.timestamp = timestamp;
    }

    protected Notice(Parcel in) {
        AnzeigeName = in.readString();
        Beschreibung = in.readString();
        erstellerId = in.readInt();
        timestamp = in.readLong(); // Timestamp aus Parcel lesen
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(AnzeigeName);
        dest.writeString(Beschreibung);
        dest.writeInt(erstellerId);
        dest.writeLong(timestamp); // Timestamp in Parcel schreiben
    }

    // Bestehende Getter und Setter
    public String getAnzeigeName() {
        return AnzeigeName;
    }

    public void setAnzeigeName(String anzeigeName) {
        AnzeigeName = anzeigeName;
    }

    public int getErstellerId() {
        return erstellerId;
    }

    public void setErstellerId(int erstellerId) {
        this.erstellerId = erstellerId;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    // Neue Getter und Setter für Timestamp
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}