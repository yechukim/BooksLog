package com.example.bookslog;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Frag_best extends Fragment {
    Button btn_goSite;
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_frag_best, container, false);
        btn_goSite = fragView.findViewById(R.id.btn_goSite);
        btn_goSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mbook.interpark.com/main/");
                Intent siteIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(siteIntent);
            }
        });
/*
        List<BestSellerItems> bestSellerLists = new ArrayList<>();
        bestSellerLists.add(new BestSellerItems("제목","저자","카데고리"));
        gridView = fragView.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(getContext(),bestSellerLists);
        gridView.setAdapter(adapter);
*/

        return fragView;
    }

    class GridAdapter extends BaseAdapter{
        Context context;
        LayoutInflater layoutInflater;
        List<BestSellerItems> bestSellerLists;

        public GridAdapter(Context c, List<BestSellerItems> bestSellerLists) {
            this.context = c;
            this.layoutInflater = LayoutInflater.from(context);
            this.bestSellerLists = bestSellerLists;
        }

        @Override
        public int getCount() {
            return bestSellerLists.size();
        }

        @Override
        public Object getItem(int position) {
            return bestSellerLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(view==null){
                view = layoutInflater.inflate(R.layout.best_items,null);
            }
            ImageView bestItemCover = view.findViewById(R.id.bestItemCover);
            TextView bestItemAuthor = view.findViewById(R.id.bestItemAuthor);
            TextView bestItemTitle = view.findViewById(R.id.bestItemTitle);

            BestSellerItems bestItems = bestSellerLists.get(position);
            bestItemAuthor.setText(bestItems.getAuthor());
            bestItemTitle.setText(bestItems.getTitle());
            bestItemCover.setImageResource(0);
            return null;
        }
    }

}