package uni.fmi.bachelors.applibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    RecyclerView bookRecyclerView;
    FloatingActionButton addButton;

    DatabaseHelper dbHelper;
    ArrayList<String> book_id, book_author, book_name, book_pages;
    CustomAdapter customAdapter;

    private static final String TAG = "BookActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        if(isServicesOK()){
            init();
        }

        dbHelper = new DatabaseHelper(BookActivity.this);
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_name = new ArrayList<>();
        book_pages = new ArrayList<>();



        storeDataInArrays();

        customAdapter = new CustomAdapter(BookActivity.this, this, book_id, book_author, book_name, book_pages);
        bookRecyclerView.setAdapter(customAdapter);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(BookActivity.this));

    }

    private void init(){
        FloatingActionButton bookGpsButton = findViewById(R.id.bookGpsButton);
        bookGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, GpsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = dbHelper.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_author.add(cursor.getString(1));
                book_name.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.library_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete ALL data?");
        builder.setMessage("Are you confirm deletion ALL data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(BookActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(BookActivity.this, BookActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(BookActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make requests
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(BookActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "We can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}