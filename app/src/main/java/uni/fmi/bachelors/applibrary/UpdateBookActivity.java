package uni.fmi.bachelors.applibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBookActivity extends AppCompatActivity {

    EditText author_input, name_input, pages_input;
    Button update_button, delete_button;

    String id, author, name, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        author_input = findViewById(R.id.editTextUpdateBookAuthor);
        name_input = findViewById(R.id.editTextUpdateBookName);
        pages_input = findViewById(R.id.editTextUpdateBookPages);
        update_button = findViewById(R.id.updateBookButton);
        delete_button = findViewById(R.id.deleteBookButton);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }

        update_button.setOnClickListener(view -> {
            DatabaseHelper myDB = new DatabaseHelper(UpdateBookActivity.this);
            author = author_input.getText().toString().trim();
            name = name_input.getText().toString().trim();
            pages = pages_input.getText().toString().trim();
            myDB.updateData(id, author, name, pages);
        });
        delete_button.setOnClickListener(view -> {
            confirmDialog();
        });

    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("author")
                && getIntent().hasExtra("name") && getIntent().hasExtra("pages")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            author = getIntent().getStringExtra("author");
            name = getIntent().getStringExtra("name");
            pages = getIntent().getStringExtra("pages");

            //Setting Intent data
            author_input.setText(author);
            name_input.setText(name);
            pages_input.setText(pages);
        }else{
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you confirm deletion" + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateBookActivity.this);
                myDB.deleteOneRow(id);
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
}