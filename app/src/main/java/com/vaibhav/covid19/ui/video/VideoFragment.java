package com.vaibhav.covid19.ui.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vaibhav.covid19.R;

public class VideoFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_video, container, false);

        TextView t1 = root.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiratcommunications.com"));
                startActivity(browserIntent);
            }
        });

        String frameVideo = "<html><body style='margin:0;padding:0;'><br><iframe width=\"365\" height=\"250\" src=\"https://www.youtube.com/embed/g0XDL5Kh6RE\" frameborder=\"-5\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        WebView displayYoutubeVideo = (WebView) root.findViewById(R.id.videoView);
        WebSettings webSettings = displayYoutubeVideo.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");

        String frameVideo1 = "<html><body style='margin:0;padding:0;'><br><iframe width=\"365\" height=\"250\" src=\"https://www.youtube.com/embed/bPITHEiFWLc\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        WebView displayYoutubeVideo1 = (WebView) root.findViewById(R.id.videoView1);
        WebSettings webSettings1 = displayYoutubeVideo1.getSettings();
        webSettings1.setDomStorageEnabled(true);
        webSettings1.setJavaScriptEnabled(true);
        displayYoutubeVideo1.loadData(frameVideo1, "text/html", "utf-8");

        String frameVideo2 = "<html><body style='margin:0;padding:0;'><br><iframe width=\"365\" height=\"250\" src=\"https://www.youtube.com/embed/6Ooz1GZsQ70\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        WebView displayYoutubeVideo2 = (WebView) root.findViewById(R.id.videoView2);
        WebSettings webSettings2 = displayYoutubeVideo2.getSettings();
        webSettings2.setDomStorageEnabled(true);
        webSettings2.setJavaScriptEnabled(true);
        displayYoutubeVideo2.loadData(frameVideo2, "text/html", "utf-8");

        String frameVideo3 = "<html><body style='margin:0;padding:0;'><br><iframe width=\"365\" height=\"250\" src=\"https://www.youtube.com/embed/qF42gZVm1Bo\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></body></html>";
        WebView displayYoutubeVideo3 = (WebView) root.findViewById(R.id.videoView3);
        WebSettings webSettings3 = displayYoutubeVideo3.getSettings();
        webSettings3.setDomStorageEnabled(true);
        webSettings3.setJavaScriptEnabled(true);
        displayYoutubeVideo3.loadData(frameVideo3, "text/html", "utf-8");

        return root;
    }
}
