package com.vaibhav.covid19.ui.symptoms;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vaibhav.covid19.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SymptomsFragment extends Fragment {

    private ImageView photoView;
    private Button resetPhotoButton;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText numberEditText;
    private Button checkInButton;
    private Button resetButton;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;

    private Upload upload;
    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private String downloadURlPhoto;
    private Uri ImageURL;

    //private Session session;
    private ProgressDialog progressDialog;

    private ArrayList<String> Namee = new ArrayList<String>();

    private int EnterKey=0;

    private String latitude, longitude;

    //private Paragraph p3;
    protected LocationManager locationManager;

    private TextView viewSiteId;

    private CheckBox fever;
    private CheckBox fatigue;
    private CheckBox sore_throat;
    private CheckBox dry_cough;
    private CheckBox joint_muscle_pain;
    private CheckBox shortness_of_breath;
    private CheckBox lack_of_hunger;
    private EditText anyOther;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_symptoms, container, false);

        TextView t1 = root.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiratcommunications.com"));
                startActivity(browserIntent);
            }
        });

        photoView = (ImageView) root.findViewById(R.id.photoView);
        resetPhotoButton = (Button) root.findViewById(R.id.resetPhotoButton);
        nameEditText =  (EditText) root.findViewById(R.id.nameEditText);
        emailEditText = (EditText) root.findViewById(R.id.emailEditText);
        numberEditText = (EditText) root.findViewById(R.id.numberEditText);
        fever = (CheckBox) root.findViewById(R.id.fever);
        fatigue =  (CheckBox) root.findViewById(R.id.fatigue);
        sore_throat =  (CheckBox) root.findViewById(R.id.sore_throat);
        dry_cough =  (CheckBox) root.findViewById(R.id.dry_cough);
        joint_muscle_pain =  (CheckBox) root.findViewById(R.id.joint_muscle_pain);
        shortness_of_breath =  (CheckBox) root.findViewById(R.id.shortness_of_breath);
        lack_of_hunger =  (CheckBox) root.findViewById(R.id.lack_of_hunger);
        anyOther = (EditText) root.findViewById(R.id.anyOther);

        submitButton= (Button) root.findViewById(R.id.submitButton);

        isStoragePermissionGranted();
        getLoc();

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        resetPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoView.setImageResource(R.drawable.ic_camera_alt_black_24dp);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldEntry()){
                    StringBuffer result = new StringBuffer();
                if (fever.isChecked()) {
                    result.append("Fever,");
                }
                if (fatigue.isChecked()) {
                    result.append("Fatigue,");
                }
                if (sore_throat.isChecked()) {
                    result.append("Sore Throat,");
                }
                if (dry_cough.isChecked()) {
                    result.append("Dry Cough,");
                }
                if (joint_muscle_pain.isChecked()) {
                    result.append("Joint Muscle Pain,");
                }
                if (sore_throat.isChecked()) {
                    result.append("Sore Throat,");
                }
                if (shortness_of_breath.isChecked()) {
                    result.append("Shortness Of Breath,");
                }
                if (lack_of_hunger.isChecked()) {
                    result.append("Lack Of Hunger,");
                }
                if (!(TextUtils.isEmpty(anyOther.getText().toString().trim()))) {
                    result.append(anyOther.getText().toString().trim());
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Symptoms");

                upload = new Upload(emailEditText.getText().toString().trim(), nameEditText.getText().toString().trim(), ImageURL.toString(), numberEditText.getText().toString().trim(), result.toString(), dateFormat.format(date).toString(), latitude, longitude);

                String uploadID = mDatabaseRef.push().getKey();

                mDatabaseRef.child(uploadID).setValue(upload);

                nameEditText.setText("");
                emailEditText.setText("");
                numberEditText.setText("");
                photoView.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                anyOther.setText("");
                Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_LONG).show();
            }
            }
        });

        return root;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                1
        );
    }

    private boolean fieldEntry() {

        if(TextUtils.isEmpty(nameEditText.getText().toString().trim())){
            Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(emailEditText.getText().toString().trim())){
            Toast.makeText(getActivity(), "Enter a Email ID", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(numberEditText.getText().toString().trim())){
            Toast.makeText(getActivity(), "Enter the Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(ImageURL.toString())){
            Toast.makeText(getActivity(), "Click a Photo", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getLoc() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }
        final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                // getting location of user
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                // do something with Latlng
                //Toast.makeText(getActivity(), latitude, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                // do something
            }

            @Override
            public void onProviderDisabled(String provider) {
                // notify user "GPS or Network provider" not available
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 500, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);



//        Intent intent = new Intent(this,formFragment.class);
//        //Toast.makeText(this, "temp Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
//        Log.i("temp Latitude:",String.valueOf(location.getLatitude()));
//        intent.putExtra("Latitude",String.valueOf(location.getLatitude()));
//        intent.putExtra("Longitude",String.valueOf(location.getLongitude()));
//        startActivity(intent);
    }

    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
                cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoView.setImageBitmap(photo);
            EnterKey=1;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("photo");
            final UploadTask uploadTask = reference.putBytes(imageBytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ImageURL = uri;
                        }
                    });
                }
            });

            try {
                FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Documents/sample1.png");
                photo.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
//ever, fatigue, sore throat, dry (unproductive) cough, coughing up slime, joint/muscle pain, shortness of breath, lack of hunger. Initial symptoms may also include headache, chills, dizziness, nasal congestion, abdominal pain, diarrhoea, nausea, and vomiting