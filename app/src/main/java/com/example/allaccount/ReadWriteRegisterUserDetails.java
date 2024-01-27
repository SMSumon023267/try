package com.example.allaccount;

public class ReadWriteRegisterUserDetails {
    String nameRegister,emailRegister,dateRegister,passwordRegister;
    public ReadWriteRegisterUserDetails(){

    }

    public String getNameRegister() {
        return nameRegister;
    }

    public void setNameRegister(String nameRegister) {
        this.nameRegister = nameRegister;
    }

    public String getEmailRegister() {
        return emailRegister;
    }

    public void setEmailRegister(String emailRegister) {
        this.emailRegister = emailRegister;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getPasswordRegister() {
        return passwordRegister;
    }

    public void setPasswordRegister(String passwordRegister) {
        this.passwordRegister = passwordRegister;
    }

    public ReadWriteRegisterUserDetails(String nameRegister, String emailRegister, String dateRegister, String passwordRegister) {
        this.nameRegister = nameRegister;
        this.emailRegister = emailRegister;
        this.dateRegister = dateRegister;
        this.passwordRegister = passwordRegister;


    }
}
