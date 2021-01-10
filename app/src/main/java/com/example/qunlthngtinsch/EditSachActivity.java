package com.example.qunlthngtinsch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qunlthngtinsch.model.SachModel;

public class EditSachActivity extends AppCompatActivity {

        private EditText edtNameFood;
        private EditText edtPriceFood;
        private Button btnDelete;
        private Button btnSaveBook;
        private RadioGroup rgTypeFood;
        private boolean isUpdate = false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_sach);
            init();
            Intent getI = getIntent();
            SachModel updateBook = null;
            updateBook = (SachModel) getI.getSerializableExtra("book");
            if(updateBook != null){
                edtNameFood.setText(updateBook.getNameBook());
                edtPriceFood.setText(String.valueOf(updateBook.getPublishingYear()));
                isUpdate = true;
            }

            btnDelete.setOnClickListener(v -> {
                edtNameFood.setText("");
                edtPriceFood.setText("");
            });
            SachModel finalUpdateBook = updateBook;
            btnSaveBook.setOnClickListener((view) ->{
                String nameFood = edtNameFood.getText().toString();
                String priceFood = edtPriceFood.getText().toString();
                RadioButton typeFood ;
                String typeString = "";
                if(rgTypeFood.getCheckedRadioButtonId() == -1){

                }else {
                    int selectedId = rgTypeFood.getCheckedRadioButtonId();
                    typeFood = (RadioButton)findViewById(selectedId);
                    typeString = typeFood.getText().toString();
                }
                CustomDataBaseHelper db = new CustomDataBaseHelper(EditSachActivity.this);

                if(isUpdate){
                    SachModel book = new SachModel(finalUpdateBook.getId(),nameFood, Integer.parseInt(priceFood), typeString );
                    update(book, db);
                    Intent returnIntent = new Intent();
                    setResult(Activity.DEFAULT_KEYS_SEARCH_GLOBAL,returnIntent);
                    finish();

                }
                else {
                    if (nameFood.isEmpty() || priceFood.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Không được để trống",Toast.LENGTH_LONG).show();
                    }else{
                        SachModel bookSave = new SachModel(nameFood, Integer.parseInt(priceFood), typeString );

                        save(bookSave,db);
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                }
            });
        }

        private boolean save(SachModel foods, CustomDataBaseHelper db ){
            db.addBook(foods);
            db.close();
            return false;
        }
        private boolean update(SachModel foods, CustomDataBaseHelper db){
            int row = db.updateBook(foods);
            Toast.makeText(getBaseContext(), " "  + row, Toast.LENGTH_LONG).show();
            db.close();
            return false;
        }
        private void init() {
            edtNameFood = findViewById(R.id.edtNameBook);
            edtPriceFood = findViewById(R.id.edtPublishingYear);
            btnDelete = findViewById(R.id.btn_delete);
            btnSaveBook = findViewById(R.id.btn_save);
            rgTypeFood = findViewById(R.id.type_book);
        }
    }