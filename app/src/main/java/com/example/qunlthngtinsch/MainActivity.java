package com.example.qunlthngtinsch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlthngtinsch.model.SachModel;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final int VIEW_NOTE = 9999;
    private static final int DELETE_NOTE = 9998;
    private static final int UPDATE_NOTE = 9997;
    private static final int CREATE_NOTE = 9996;

    private Button btnAddFoods;
    private ListView lvBooks;
    private int CODE = 11;
    CustomDataBaseHelper booksDB;
    ArrayList<SachModel> books;
    ArrayAdapter<SachModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        booksDB = new CustomDataBaseHelper(MainActivity.this);
        books = booksDB.getAllBook();

        // create adapter
        adapter = new Myadapter();
        lvBooks.setAdapter(adapter);
        registerForContextMenu(lvBooks);
        btnAddFoods.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), EditSachActivity.class);
            startActivityForResult(intent, CODE);

        });
    }

    private void updateListFoods() {
        books = booksDB.getAllBook();
        adapter = new Myadapter();
        lvBooks.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListFoods();
    }

    private void deleteNote(SachModel book) {
        CustomDataBaseHelper db = new CustomDataBaseHelper(this);
        db.deleteBook(book);
        this.books.remove(book);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chose an action");

        menu.add(0, VIEW_NOTE, 0, "View Book");
        menu.add(0, CREATE_NOTE, 1, "Create Book");
        menu.add(0, UPDATE_NOTE, 2, "Update Book");
        menu.add(0, DELETE_NOTE, 3, "Delete Book");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final SachModel selectBook = (SachModel) lvBooks.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case (VIEW_NOTE):
                Toast.makeText(getApplicationContext(), selectBook.getNameBook(), Toast.LENGTH_LONG).show();
                break;
            case (CREATE_NOTE):
                Intent intent1 = new Intent(getApplicationContext(), EditSachActivity.class);
                startActivityForResult(intent1, CODE);
                break;
            case (DELETE_NOTE):
                new AlertDialog.Builder(this)
                        .setMessage(selectBook.getNameBook() + "you are sure delete book?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            deleteNote(selectBook);
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
            case (UPDATE_NOTE):
                Intent intent = new Intent(getApplicationContext(), EditSachActivity.class);
                intent.putExtra("book", (Serializable) selectBook);
                this.startActivityForResult(intent, CODE);
                break;
            default:
                return false;
        }
        return true;
    }

    private void init() {
        btnAddFoods = findViewById(R.id.btnAddBook);
        lvBooks = findViewById(R.id.lvBooks);
    }

    private class Myadapter extends ArrayAdapter<SachModel> {

        public Myadapter() {
            super(MainActivity.this, R.layout.view_books, books);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            SachModel food = books.get(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_books, null);
            TextView name = convertView.findViewById(R.id.txt_name);
            TextView price = convertView.findViewById(R.id.txt_publishing_book);
            ImageView img = convertView.findViewById(R.id.img_book);
            if (food.getTypeBook().equals("Giáo trình")) {
                img.setImageResource(R.drawable.giao_trinh);
            } else {
                img.setImageResource(R.drawable.tieu_thuyet);
            }

            name.setText(food.getNameBook());
            price.setText(String.valueOf(food.getPublishingYear()));
            return convertView;

        }
    }
}