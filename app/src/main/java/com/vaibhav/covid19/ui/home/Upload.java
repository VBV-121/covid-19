package com.vaibhav.covid19.ui.home;

public class Upload {

    private String mname;
    private String mCity;
    private String mState;
    private String mCountry;
    private String mNumber;
    private String mLatitude;
    private String mLongitude;
    private String mAddress;
    private String mMacAdd;

    public Upload(){


    }

    public Upload(String name, String Address, String city, String state, String country, String number, String Latitude, String Longitude, String macAdd) {

        if ( name.trim().equals("") || Address.trim().equals("") || city.trim().equals("") || state.trim().equals("") ||country.trim().equals("") ||number.trim().equals("") || Latitude.trim().equals("") || Longitude.trim().equals("")|| macAdd.trim().equals("")) {
            name = "No Name";
            Address = "No Address";
            city = "No City";
            state="No state";
            country ="No Country";
            number= "No Class";
            Latitude = "No Latitude";
            Longitude = "No Longitude";
            macAdd = "No Mac Address";

        }

        mname = name;
        mAddress =Address;
        mCity = city;
        mState = state;
        mCountry = country;
        mNumber = number;
        mLatitude = Latitude;
        mLongitude = Longitude;
        mMacAdd = macAdd;
    }


    public String getName(){

        return mname;

    }

    public void setName(String name){

        mname = name;

    }

    public String getAddress(){

        return mAddress;

    }

    public void setAddress(String Address){

        mAddress = Address;

    }

    public String getCity(){

        return mCity;

    }

    public void setCity(String city){

        mCity = city;

    }

    public String getState(){

        return mState;

    }

    public void setState(String state){

        mState = state;

    }

    public String getCountry(){

        return mCountry;

    }

    public void setCountry(String country){

        mCountry = country;

    }

    public String getNumber(){

        return mNumber;

    }

    public void setNumber(String number){

        mNumber = number;

    }


    public String getLatitude(){

        return mLatitude;

    }

    public void setLatitude(String Latitude){

        mLatitude = Latitude;

    }


    public String getLongitude(){

        return mLongitude;

    }

    public void setLongitude(String Longitude){

        mLongitude = Longitude;

    }

    public String getMacAdd(){

        return mMacAdd;

    }

    public void setMacAdd(String macAdd){

        mMacAdd = macAdd;

    }

}
