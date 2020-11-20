package com.example.bookslog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Frag_shelf extends Fragment implements ShelfAdapter.OnShelfListener {
    private static final String TAG = "tag";
    ShelfAdapter shelfAdapter;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_frag_shelf, container, false);
        recyclerView = fragView.findViewById(R.id.recyclerView);

/*       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        shelfAdapter = new ShelfAdapter(getContext(),this);
        for (int i = 0; i < 10; i++) {
            shelfAdapter.addItem(new Shelf_items("title " + i, "author " + i));
        }
        recyclerView.setAdapter(shelfAdapter);
        return fragView;
    }

    @Override
    public void onShelfClick(int position) {
        Log.d(TAG, "onShelfClick: clicked"+position);
        Intent intent = new Intent(getActivity(),ShelfClickActivity.class);
        startActivity(intent);
    }
}