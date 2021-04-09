package com.example.xcargomobile.cargo;

public class Cargo {

    public static Cargo get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String UID;
    private Sender Sender;
    private Receiver Receiver;
    private Information Information;

    public Cargo() {
    }

    public Cargo(String UID, Sender Sender, Receiver Receiver, Information Information) {
        this.UID = UID;
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.Information = Information;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public Sender getSender() {
        return Sender;
    }

    public void setSender(Sender Sender) {
        this.Sender = Sender;
    }

    public Receiver getReceiver() {
        return Receiver;
    }

    public void setReceiver(Receiver Receiver) {
        this.Receiver = Receiver;
    }

    public Information getInformation() {
        return Information;
    }

    public void setInformation(Information Information) {
        this.Information = Information;
    }

    @Override
    public String toString() {
        return this.UID;
    }


}