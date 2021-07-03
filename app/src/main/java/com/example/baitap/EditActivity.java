package com.example.baitap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    EditText edtID, edtName, edtNum;
    Button btnEdit, btnBack;
    private boolean needRefresh;
    private Contact contact;
    DataBase dataBase = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Map();

        //lấy dữ liệu khi người dùng chọn menu sửa
        Intent intent = this.getIntent();
        this.contact = (Contact) intent.getSerializableExtra("contact");
        this.edtID.setText(String.valueOf(contact.getId()));
        this.edtID.setEnabled(false);
        this.edtNum.setText(contact.getPhoneNumber());
        this.edtName.setText(contact.getName());

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        btnEdit.setOnClickListener(v -> {
            String name = this.edtName.getText().toString();
            String num = this.edtNum.getText().toString();
            if(edtName.getText().toString().equals("") ||
                    edtNum.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),
                        "không để trống", Toast.LENGTH_LONG).show();
                return;
            }else{
                    this.contact.setName(name);
                    this.contact.setPhoneNumber(num);
                    dataBase.update(contact);

                this.needRefresh = true;
                this.onBackPressed();
            }
        });
    }

    private void Map(){
        btnBack = (Button) findViewById(R.id.btnEditBack);
        btnEdit = (Button) findViewById(R.id.btnEditEdit);
        edtName = (EditText) findViewById(R.id.edtEditName);
        edtID = (EditText) findViewById(R.id.edtEditID);
        edtNum = (EditText) findViewById(R.id.edtEditNum);
    }
    @Override
    public void finish() {
        Intent data = new Intent();

        data.putExtra("needFresh", needRefresh);

        this.setResult(Activity.RESULT_OK, data);

        super.finish();
    }
}