package com.example.testapi.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Notice implements Parcelable {
    private String AnzeigeName;
    private String Beschreibung;
    private int erstellerId;

    @Override
    public String toString() {
        return "Notice{" +
                "AnzeigeName='" + AnzeigeName + '\'' +
                ", Beschreibung='" + Beschreibung + '\'' +
                ", erstellerId=" + erstellerId +
                '}';
    }

    public Notice(String beschreibung, String anzeigeName, int erstellerId) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
    }

    protected Notice(Parcel in) {
        AnzeigeName = in.readString();
        Beschreibung = in.readString();
        erstellerId = in.readInt();
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
    }

    // Getter und Setter
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
}