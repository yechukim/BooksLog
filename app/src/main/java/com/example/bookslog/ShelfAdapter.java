package com.example.bookslog;

import android.content.Context;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ViewHolder> {
    Context context;
    ArrayList<Shelf_items> shelfItems = new ArrayList<>();

    public ShelfAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {//몇개냐
        return shelfItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //뷰 홀더가 만들어지는 시점에 자동으로 호출
        //아이템을 위한 뷰 홀더 객체가 만들어질 때 호출된다.
        //뷰홀더가 재사용될 수 있으면 호출 안됨
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.shelf_items, parent, false);
        return new ViewHolder(itemView); //뷰를 담고 있는 뷰 홀더 객체를 만들어서 리턴..
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //뷰홀더라는게 바인될 시점이다.
        Shelf_items item = shelfItems.get(position);
        holder.bookTitle.setText(item.getBookTitle());
        holder.author.setText(item.getAuthor());
        holder.bookDetails.setText(item.getDetails());
        holder.itemView.setTag(position);
        holder.showMore.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (holder.expandable.getVisibility() == View.GONE) {
                    holder.showMore.setText("닫기");
                    holder.expandable.setVisibility(v.VISIBLE);
                } else {
                    holder.showMore.setText("더보기");
                    holder.expandable.setVisibility(v.GONE);
                }
            }
        });
    }

    public void addItem(Shelf_items item) {
        shelfItems.add(item);
    }

    public Shelf_items getItem(int position) {
        return shelfItems.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle, author, bookDetails;
        LinearLayout expandable;
        TextView showMore;

        public ViewHolder(@NonNull View itemView) {//뷰를 파라미터로 받음
            // 뷰홀더는 뷰를 담고 있다. 그 뷰가 전달됨! 그 안에 들어있는 차일드 뷰가 요기 들어가 있다..?
            //데이터와 뷰를 매칭해줘야 한다.
            super(itemView);
            expandable = itemView.findViewById(R.id.expandable_view);
            showMore = itemView.findViewById(R.id.showMore);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            author = itemView.findViewById(R.id.author);
            bookDetails = itemView.findViewById(R.id.bookDetails);

        }

    }
}
