package com.example.bookslog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Frag_best extends Fragment {
    Button btn_goSite;
    GridView gridView;
    BestAdapter adapter;
    List<Best_items> bList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.fragment_frag_best, container, false);
        btn_goSite = fragView.findViewById(R.id.btn_goSite);
        btn_goSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://book.naver.com/bestsell/bestseller_list.nhn");
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(siteIntent);
            }
        });

        gridView = fragView.findViewById(R.id.gridView);
        bList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 10; i++) {
            bList.add(new Best_items("title# " +i, "author# "+i, R.drawable.bcover));
        }
        adapter = new BestAdapter(getContext(), bList);
        gridView.setAdapter(adapter);

        return fragView;
    }

    public class BestAdapter extends BaseAdapter {
        Context context;
        List<Best_items> bestItemsList;
        LayoutInflater layoutInflater;

        public BestAdapter(Context context, List<Best_items> bestItemsList) {
            this.context = context;
            this.bestItemsList = bestItemsList;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return bestItemsList.size();
        }

        @Override
        public Object getItem(int position) {
            return bestItemsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.best_items, null);
            }
            ImageView bCover = convertView.findViewById(R.id.bestItemCover);
            TextView bTitle = convertView.findViewById(R.id.bestItemTitle);
            TextView bAuthor = convertView.findViewById(R.id.bestItemAuthor);

            Best_items bestItems = bestItemsList.get(position);

            bCover.setImageResource(bestItems.getCover());
            bTitle.setText(bestItems.getTitle());
            bAuthor.setText(bestItems.getAuthor());

            return convertView;
        }
    }
}