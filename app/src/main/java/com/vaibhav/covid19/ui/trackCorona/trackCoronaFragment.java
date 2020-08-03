package com.vaibhav.covid19.ui.trackCorona;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vaibhav.covid19.R;

public class trackCoronaFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trackcorona, container, false);

        Button viewButton;
        ImageView viewPhotoTrack;

        TextView t1 = root.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiratcommunications.com"));
                startActivity(browserIntent);
            }
        });

        viewButton = (Button) root.findViewById(R.id.viewButton);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trackcorona.live/map"));
                startActivity(browserIntent);
            }
        });

        viewPhotoTrack = root.findViewById(R.id.viewPhotoTrack);
        viewPhotoTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trackcorona.live/map"));
                startActivity(browserIntent);
            }
        });


        return root;
    }
}
