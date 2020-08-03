package com.vaibhav.covid19.ui.symptoms;

public class Upload {

    private String mname;
    private String mImageUrl;
    private String mphoneNumber;
    private String mEmailID;
    private String mDate;
    private String mSymptoms;
    private String mLatitude;
    private String mLongitude;
    public Upload(){


    }

    public Upload(String emailID, String name, String imageUrl, String phoneNumber, String symptoms,  String date, String latitude, String longitude) {

        if (name.trim().equals("") || phoneNumber.trim().equals("") || symptoms.trim().equals("")  ) {
            name = "No Name";
            emailID="No Email ID";
            phoneNumber = "No phone Number";
            imageUrl="No image";
            symptoms="No symptoms";
            date = "No Date";

        }

        mEmailID = emailID;
        mname = name;
        mImageUrl = imageUrl;
        mphoneNumber = phoneNumber;
        mDate = date;
        mLatitude = latitude;
        mLongitude = longitude;
        mSymptoms = symptoms;


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
    public String getSymptoms(){

        return mSymptoms;

    }

    public void setsymptoms(String symptoms){

        mSymptoms = symptoms;

    }

    public String getEmailid(){

        return mEmailID;

    }

    public void setEmailid(String emailId){

        mEmailID = emailId;

    }

    public String getDate(){

        return mDate;

    }

    public void setDate(String date){

        mDate = date;

    }

    public String getName(){

        return mname;

    }

    public void setName(String name){

        mname = name;

    }
    public String getImageUrl(){

        return mImageUrl;

    }

    public void setImageUrl(String imageUrl){

        mImageUrl = imageUrl;

    }

    public String getPhoneNumber(){

        return mphoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber){

        mphoneNumber= PhoneNumber;

    }

}
