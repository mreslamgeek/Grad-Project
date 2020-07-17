package com.eslam.speech_to_text_demoapp;

public class ContactModel {

    String Name;
    String PhoneNumber;

    public ContactModel(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;

    }

    public ContactModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

}
