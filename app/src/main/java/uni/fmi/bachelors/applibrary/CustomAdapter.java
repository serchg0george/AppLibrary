package uni.fmi.bachelors.applibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.AppLibraryAdapter> {

    private Context context;
    Activity activity;
    private ArrayList book_id, book_author, book_name, book_pages;

    Animation translate_animation;


    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_author, ArrayList book_name, ArrayList book_pages){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_author = book_author;
        this.book_name = book_name;
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public AppLibraryAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new AppLibraryAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppLibraryAdapter holder, final int position) {
        holder.bookIdTextView.setText(String.valueOf(book_id.get(position)));
        holder.bookAuthorTextView.setText(String.valueOf(book_author.get(position)));
        holder.bookNameTextView.setText(String.valueOf(book_name.get(position)));
        holder.bookPagesTextView.setText(String.valueOf(book_pages.get(position)));
        holder.bookLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateBookActivity.class);
            intent.putExtra("id", String.valueOf(book_id.get(position)));
            intent.putExtra("author", String.valueOf(book_author.get(position)));
            intent.putExtra("name", String.valueOf(book_name.get(position)));
            intent.putExtra("pages", String.valueOf(book_pages.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class AppLibraryAdapter extends RecyclerView.ViewHolder {

        TextView bookIdTextView, bookAuthorTextView, bookNameTextView, bookPagesTextView;
        LinearLayout bookLayout;

        public AppLibraryAdapter(@NonNull View itemView) {
            super(itemView);
            bookIdTextView = itemView.findViewById(R.id.bookIdTextView);
            bookAuthorTextView = itemView.findViewById(R.id.bookAuthorTextView);
            bookNameTextView = itemView.findViewById(R.id.bookNameTextView);
            bookPagesTextView = itemView.findViewById(R.id.bookPagesTextView);
            bookLayout = itemView.findViewById(R.id.bookLayout);
            translate_animation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            bookLayout.setAnimation(translate_animation);
        }
    }
}
