package com.example.bookslog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ViewHolder> {

    Context context;
    ArrayList<Shelf_items> mShelf = new ArrayList<>();
    private OnShelfListener onShelfListener;

    public ShelfAdapter(Context context, OnShelfListener onShelfListener, ArrayList<Shelf_items> mShelf) {
        this.context = context;
        this.onShelfListener = onShelfListener;
        this.mShelf = mShelf;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //뷰 홀더가 만들어지는 시점에 자동으로 호출
        //아이템을 위한 뷰 홀더 객체가 만들어질 때 호출된다.
        //뷰홀더가 재사용될 수 있으면 호출 안됨
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_items, parent, false);
        return new ViewHolder(itemView, onShelfListener); //뷰를 담고 있는 뷰 홀더 객체를 만들어서 리턴..
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        //entry만큼 호출됨.
        holder.bookTitle.setText(mShelf.get(i).getBookTitle());
        holder.author.setText(mShelf.get(i).getAuthor());
        holder.writeDate.setText(mShelf.get(i).getWriteDate());
        holder.itemView.setTag(i);

    }
    @Override
    public int getItemCount() {//몇개냐
        return mShelf.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookTitle, author,writeDate;
        ImageView bcover;
        OnShelfListener onShelfListener;

        public ViewHolder(@NonNull View itemView, OnShelfListener onShelfListener) {//뷰를 파라미터로 받음
            // 뷰홀더는 뷰를 담고 있다. 그 뷰가 전달됨! 그 안에 들어있는 차일드 뷰가 요기 들어가 있다..?
            //데이터와 뷰를 매칭해줘야 한다.
            super(itemView);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.author);
            bcover = itemView.findViewById(R.id.bcover);
            writeDate = itemView.findViewById(R.id.writeDate);
            this.onShelfListener = onShelfListener;
            itemView.setOnClickListener(this);
        }

        //카드뷰를 클릭하여 수정
        @Override
        public void onClick(View v) {
            onShelfListener.onShelfClick(getAdapterPosition());
        }
    }

    public void addItem(Shelf_items item) {
        mShelf.add(item);
    }

    public Shelf_items getItem(int position) {
        return mShelf.get(position);
    }

    public interface OnShelfListener {
        void onShelfClick(int position);
    }

}
