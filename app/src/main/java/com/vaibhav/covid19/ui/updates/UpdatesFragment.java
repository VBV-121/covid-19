package com.vaibhav.covid19.ui.updates;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaibhav.covid19.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UpdatesFragment extends Fragment {

    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView0;

    Button viewDataButton;
    ImageView refreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_updates, container, false);

        setHasOptionsMenu(true);

        TextView t1 = root.findViewById(R.id.t1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kiratcommunications.com"));
                startActivity(browserIntent);
            }
        });

        textView = (TextView) root.findViewById(R.id.textView);
        textView1 = (TextView) root.findViewById(R.id.textView1);
        textView2 = (TextView) root.findViewById(R.id.textView2);
        textView0 = (TextView) root.findViewById(R.id.textView0);

        viewDataButton = (Button) root.findViewById(R.id.viewDataButton);

        new doit().execute();

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mohfw.gov.in/"));
                startActivity(browserIntent);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.refresh:
                textView0.setText("Confirmed Cases : Null");
                textView.setText("Active Cases : Null");
                textView1.setText("Cured Cases : Null");
                textView2.setText("Deaths : Null");
                new doit().execute();
                return true;
        }

        return super.onOptionsItemSelected(item); // important line
    }

    public class doit extends AsyncTask<Void,Void,Void>{

        String words;
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://www.mohfw.gov.in/").get();
                words = doc.text();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            int index = words.indexOf("Active Cases");
            index = index-10;
            Log.i("data",words);
            String str = words.substring(index,index+100);
            //textView0.setText(str);
            List<String> list = Arrays.asList(str.split(" "));
            String active="";
            String cured="";
            String death="";
            int tempc=0;
            for(int i=0;i<list.size();i++){
                String temp = list.get(i);
                if(temp.contains("0") || temp.contains("1") || temp.contains("2") || temp.contains("3") ||temp.contains("4") ||temp.contains("5") ||temp.contains("6") ||temp.contains("7") ||temp.contains("8") ||temp.contains("9")){
                    if(tempc==2){
                        death = temp;
                        tempc++;
                    }
                    if(tempc==1){
                        cured = temp;
                        tempc++;
                    }
                    if(tempc==0){
                        active = temp;
                        tempc++;
                    }


                }
            }
            int total = Integer.parseInt(active)+Integer.parseInt(cured)+Integer.parseInt(death);
            textView0.setText("Confirmed Cases : "+String.valueOf(total));
            textView.setText("Active Cases : "+active);
            textView1.setText("Cured Cases : "+cured);
            textView2.setText("Deaths : "+death);
        }
    }
}
//<TextView
//            android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:textSize="30dp"
//                    android:text="Active Cases : " />