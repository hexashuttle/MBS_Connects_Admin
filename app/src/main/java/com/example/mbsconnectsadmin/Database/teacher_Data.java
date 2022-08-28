package com.example.mbsconnectsadmin.Database;

public class teacher_Data {
    private String name,email,number,image,key;

    teacher_Data()
    {

    }

    public teacher_Data(String name,
                        String email,
                        String number,
                        String image,
                        String key) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = mail;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
