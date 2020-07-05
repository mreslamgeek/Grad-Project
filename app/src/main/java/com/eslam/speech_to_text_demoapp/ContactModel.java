package com.eslam.speech_to_text_demoapp;

public class ContactModel {

    String Name;
    String PhoneNumber;
    String ImgUri;

    public ContactModel(String name, String phoneNumber, String imgUri) {
        Name = name;
        PhoneNumber = phoneNumber;
        ImgUri = imgUri;
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

    public String getImgUri() {
        return ImgUri;
    }

    public void setImgUri(String imgUri) {
        ImgUri = imgUri;
    }
}
