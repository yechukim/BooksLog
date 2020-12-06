package com.example.bookslog;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private static final String TAG = "search";
    Context context;
    LayoutInflater layoutInflater;
    List<SearchItems> sItems;

    public SearchAdapter(Context context,List<SearchItems>sItems) {
        this.context = context;
        this.sItems = sItems;
    }

    @Override
    public int getCount() {
        return sItems.size();
    }

    @Override
    public SearchItems getItem(int position) {
        return sItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.searched_book_list, parent, false);
        }
        TextView b_title, b_author, b_price;
        ImageView b_cover = convertView.findViewById(R.id.cover);
        b_title = convertView.findViewById(R.id.b_title);
        b_author = convertView.findViewById(R.id.b_author);
        b_price = convertView.findViewById(R.id.b_price);

        SearchItems searchItems = getItem(position);
        b_author.setText(searchItems.getAuthor());
        b_title.setText(searchItems.getTitle());
        b_price.setText(searchItems.getPrice());

        if(searchItems.getImageUrl()==null){
         b_cover.setImageResource(R.drawable.bookwide);
        }
        else{
            Glide.with(context).load(searchItems.getImageUrl()).into(b_cover);
        }

        return convertView;
    }

    public void addItem(String title, String author, String price, String imgUrl) {
        SearchItems mItems = new SearchItems();
        mItems.setAuthor(author);
        mItems.setPrice(price);
        mItems.setTitle(title);
        sItems.add(mItems);
    }
}
