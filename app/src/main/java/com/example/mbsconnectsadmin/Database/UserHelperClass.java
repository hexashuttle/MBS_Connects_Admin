package com.example.mbsconnectsadmin.Database;

public class UserHelperClass {

    String _name,_email,_password, _designation,_phone;


    public UserHelperClass()
    {}

    public UserHelperClass(String _name, String _email, String _password, String _designation, String _phone) {
        this._name = _name;
        this._email = _email;
        this._password = _password;
        this._designation = _designation;
        this._phone = _phone;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_designation() {
        return _designation;
    }

    public void set_designation(String _designation) {
        this._designation = _designation;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }
}
