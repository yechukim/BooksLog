package com.example.bookslog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookslog.persistence.ShelfDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Frag_shelf extends Fragment implements ShelfAdapter.OnShelfListener,
        View.OnClickListener {
    ShelfDatabase db;

    private static final String TAG = "tag";

    //UI components
    RecyclerView mRecyclerView;
    FloatingActionButton fab;

    //vars
    private ArrayList<Shelf_items> mShelf = new ArrayList<>();
    ShelfAdapter mShelfAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = Room.databaseBuilder(getContext(),ShelfDatabase.class,"shelf_db").allowMainThreadQueries().build();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_frag_shelf, container, false);
        mRecyclerView = fragView.findViewById(R.id.recyclerView);

        //fab
        fab = fragView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mShelfAdapter = new ShelfAdapter(getContext(), this, mShelf);
        mRecyclerView.setAdapter(mShelfAdapter);
        setHasOptionsMenu(true);


        return fragView;
    }


    // 프래그먼트마다 액션바 다르게 구성..
    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //카드뷰 클릭
    @Override
    public void onShelfClick(int position) {
        Intent intent = new Intent(getActivity(), ShelfClickActivity.class);
        intent.putExtra("selected_book", mShelf.get(position));
        startActivity(intent);
    }

    // 새로 기록하기
    @Override
    public void onClick(View v) {
        fab.setImageResource(R.drawable.write_selected);
        Intent intent = new Intent(getActivity(), ShelfClickActivity.class);
        startActivity(intent);
    }

    private void deleteBooks(Shelf_items book) {
        mShelf.remove(book);
        mShelfAdapter.notifyDataSetChanged();
        db.getShelfDao().delete(book);
        Toast.makeText(getActivity(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
    }

    //스와이프하여 삭제
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {//오른쪽으로 스와이프해서 삭제
        //이건 드래그하고 싶을 때
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteBooks(mShelf.get(viewHolder.getAdapterPosition()));
        }
    };
}
