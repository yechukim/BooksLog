package com.example.bookslog;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

public class Frag_shelf extends Fragment {
    LinearLayout expandableview;
    TextView showMore;
    CardView cardView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_frag_shelf, container, false);
        expandableview = fragView.findViewById(R.id.expandable_view);
        showMore = fragView.findViewById(R.id.showMore);
        cardView = fragView.findViewById(R.id.cardview_expandable);
        return fragView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showMore(View view){
        if(expandableview.getVisibility() == View.GONE){
            showMore.setText("닫기");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(view.VISIBLE);
        }else {
            showMore.setText("더보기");
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableview.setVisibility(view.GONE);

        }
    }
}