package uni.fmi.bachelors.applibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    EditText editTextBookAuthor, editTextName, editTextPages;
    Button addBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        editTextBookAuthor = findViewById(R.id.editTextUpdateBookAuthor);
        editTextName = findViewById(R.id.editTextUpdateBookName);
        editTextPages = findViewById(R.id.editTextUpdateBookPages);
        addBookButton = findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(AddBookActivity.this);
                dbHelper.addBook(editTextBookAuthor.getText().toString().trim(),
                        editTextName.getText().toString().trim(),
                        Integer.valueOf(editTextPages.getText().toString().trim()));
                Intent intent = new Intent(AddBookActivity.this, BookActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}