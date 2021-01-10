package com.example.qunlthngtinsch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qunlthngtinsch.model.SachModel;

import java.util.ArrayList;

public class CustomDataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "THI_HOC_KI";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "sach";
    public static final String COL_BOOK_ID = "ma_sach";
    public static final String COL_NAME_BOOK = "ten_sach";
    public static final String COL_PUBLISHING_YEAR_BOOK = "nam_xb";
    public static final String COL_TYPE_BOOK = "loai_sach";

    public CustomDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + DB_TABLE + "("+COL_BOOK_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME_BOOK + " text NOT NULL,"
                + COL_PUBLISHING_YEAR_BOOK + " INTEGER,"
                + COL_TYPE_BOOK + " text NOT NULL"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP table if EXISTS " + DB_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }
    // phuong thuc them 1 ghi chu moi
    public Long addBook(SachModel book){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_NAME_BOOK, book.getNameBook());
        contentValues.put(COL_PUBLISHING_YEAR_BOOK, book.getPublishingYear());
        contentValues.put(COL_TYPE_BOOK, book.getTypeBook());

        Long res =  db.insert(DB_TABLE,null, contentValues);

        db.close();
        return res;
    }

    public int updateBook(SachModel book){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_NAME_BOOK, book.getNameBook());
        contentValues.put(COL_PUBLISHING_YEAR_BOOK, book.getPublishingYear());
        contentValues.put(COL_TYPE_BOOK, book.getTypeBook());

        int res =  db.update(DB_TABLE,contentValues,COL_BOOK_ID+ " = ? ", new String[]{
                String.valueOf(book.getId())
        });
        db.close();
        return res;
    }

    public ArrayList<SachModel> getAllBook(){
        String sql = "select * from " + DB_TABLE;
        ArrayList<SachModel> books = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cu = db.rawQuery(sql,null);
        while (cu.moveToNext()){
            SachModel sach = new SachModel(cu.getInt(0),cu.getString(1), cu.getInt(2), cu.getString(3));
            books.add(sach);
        }
        db.close();
        return books;
    }

    public void deleteBook(SachModel book){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,COL_BOOK_ID + " = ?", new String[] {
                String.valueOf(book.getId())
        });
        db.close();
    }
}
