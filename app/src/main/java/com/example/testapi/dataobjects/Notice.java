package com.example.testapi.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import kotlin.Pair;

/**
 * Main Object in this App is used in the List in ListViewFragment and is used to ressive and send Messagies to the Api
 * Implements Parcelable so it can be given to the Fragemrnt
 */
public class Notice implements Parcelable {
    private String AnzeigeName;
    private String Beschreibung;
    private int erstellerId;
    private String extraData;
    private List<Integer> tags;
    private long timestamp;

    public final static Comparator<Notice> NoticeDescendingTime = new Comparator<Notice>() {
        @Override
        public int compare(Notice t, Notice t1) {
            return Long.compare(t1.getTimestamp(), t.getTimestamp());
        }
    };

    @Override
    public String toString() {
        return "Notice{" +
                "AnzeigeName='" + AnzeigeName + '\'' +
                ", Beschreibung='" + Beschreibung + '\'' +
                ", erstellerId=" + erstellerId +
                ", extraData=" + extraData +
                ", tags=" + tags +
                ", timestamp=" + timestamp +
                '}';
    }

    public Notice(String beschreibung, String anzeigeName, int erstellerId) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
        this.timestamp = System.currentTimeMillis();
        this.tags = new ArrayList<>(); // Initialize empty tags list
    }

    public Notice(String beschreibung, String anzeigeName, int erstellerId,String extraData, long timestamp) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
        this.extraData = extraData;
        this.timestamp = timestamp;
        this.tags = new ArrayList<>(); // Initialize empty tags list
    }

    // New constructor with tags
    public Notice(String beschreibung, String anzeigeName, int erstellerId,String extraData, List<Integer> tags) {
        this.Beschreibung = beschreibung;
        this.AnzeigeName = anzeigeName;
        this.erstellerId = erstellerId;
        this.extraData = extraData;
        this.timestamp = System.currentTimeMillis();
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    protected Notice(Parcel in) {
        AnzeigeName = in.readString();
        Beschreibung = in.readString();
        erstellerId = in.readInt();
        extraData = in.readString();
        timestamp = in.readLong();
        tags = new ArrayList<>();
        in.readList(tags, Integer.class.getClassLoader()); // Read tags from parcel
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return erstellerId == notice.erstellerId
                && timestamp == notice.timestamp
                && Objects.equals(AnzeigeName, notice.AnzeigeName)
                && Objects.equals(Beschreibung, notice.Beschreibung)
                && Objects.equals(extraData, notice.extraData)
                && Objects.equals(tags, notice.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AnzeigeName, Beschreibung, erstellerId, extraData, tags, timestamp);
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
        dest.writeString(extraData);
        dest.writeLong(timestamp);
        dest.writeList(tags); // Write tags to parcel
    }


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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Integer> getTags() {
        return new ArrayList<>(tags); // Return a copy to prevent external modification
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>();
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    // Convenience methods for tag manipulation
    public void addTag(Integer tag) {
        if (tag != null && !tags.contains(tag)) {
            tags.add(tag);
        }
    }
    public void removeTag(Integer tag) {
        tags.remove(tag);
    }

    public boolean hasTag(Integer tag) {
        return tags.contains(tag);
    }
}