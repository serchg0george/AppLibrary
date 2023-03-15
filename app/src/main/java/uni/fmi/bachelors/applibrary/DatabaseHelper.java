package uni.fmi.bachelors.applibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;

    public static final String DATABASE_NAME = "AppLibrary.db";
    public static final int DATABASE_VERSION = 1;

    public static final String BOOKTABLE = "book_table";
    public static final String BOOK_ID = "ID";
    public static final String BOOK_AUTHOR = "BookAuthor";
    public static final String BOOK_NAME = "BookName";
    public static final String BOOK_PAGES = "BookPages";


    DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryBook = "CREATE TABLE " + BOOKTABLE +
                " (" + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BOOK_AUTHOR + " TEXT, " +
                BOOK_NAME + " TEXT, " +
                BOOK_PAGES + " INTEGER);";

        db.execSQL(queryBook);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + BOOKTABLE);
        onCreate(db);
    }

    void addBook(String author, String name, int pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(BOOK_AUTHOR, author);
        contentValues.put(BOOK_NAME, name);
        contentValues.put(BOOK_PAGES, pages);

        long result = db.insert(BOOKTABLE, null, contentValues);
        if(result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + BOOKTABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    void updateData(String row_id, String author, String name, String pages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_AUTHOR, author);
        contentValues.put(BOOK_NAME, name);
        contentValues.put(BOOK_PAGES, pages);

        long result = db.update(BOOKTABLE, contentValues, "ID=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(BOOKTABLE, "ID=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "Something went wrong :c", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + BOOKTABLE);
    }
}
