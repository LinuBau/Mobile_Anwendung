package com.example.testapi.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 *  Is the Class that is used for all private Massaggies that are send. So Massagies can be send and ressivt by the API
 */
public class Message implements Parcelable {
    private int sender;
    private int receiver;
    private String message;

    // Constructor
    public Message(int sender, int receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    // Getter methods (optional)
    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    // Parcelable implementation
    protected Message(Parcel in) {
        sender = in.readInt();
        receiver = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sender);
        dest.writeInt(receiver);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
