package com.example.bookslog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.net.URI;

public class Frag_best extends Fragment {
    Button btn_goSite;
    Intent siteIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("베스트셀러");
        View fragView = inflater.inflate(R.layout.fragment_frag_best, container, false);
        btn_goSite = fragView.findViewById(R.id.btn_goSite);
        btn_goSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://book.interpark.com/display/collectlist.do?_method=bestsellerHourNew&smid1=ranking&smid2=book");//베스트 셀러 사이
                siteIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(siteIntent);
            }
        });
        return  fragView;
    }


}