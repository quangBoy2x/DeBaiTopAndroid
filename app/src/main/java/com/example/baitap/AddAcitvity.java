package com.example.baitap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAcitvity extends AppCompatActivity {

    Button btnBack, btnAdd;
    EditText edtId, edtName, edtNum;
    DataBase dataBase = new DataBase(this);
    private boolean needRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acitvity);
        Map();

        //khi bấm thoát trở về màn hình cũ
        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        //xử lí khi bấm edit có thể là thêm , sửa
        btnAdd.setOnClickListener(v -> {
            //validate đã
            //1. nếu các edt rỗng
            if(edtId.getText().toString().equals("") || edtNum.getText().toString().equals("") ||
                    edtName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),
                        "không để trống", Toast.LENGTH_LONG).show();
                return;
            }else{
                boolean checkExist = false;
                //validate neu data dinh them la trung id
                int id = Integer.valueOf(edtId.getText().toString());
                String name = edtName.getText().toString();
                String num = edtNum.getText().toString();
                for (Contact a:
                   MainActivity.contacts  ) {
                    if(a.getId() == id){
                        checkExist = true;
                        Toast.makeText(this,"bi trung id", Toast.LENGTH_SHORT).show();
                    }
                }
                if(checkExist == false){
                    Contact a = new Contact(id, name, num);
                    dataBase.addContact(a);
                }
                this.needRefresh = true;
                this.onBackPressed();
            }

        });
    }

    private void Map(){
        btnBack = (Button) findViewById(R.id.btnBack);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtName = (EditText) findViewById(R.id.edtName);
        edtId = (EditText) findViewById(R.id.edtID);
        edtNum = (EditText) findViewById(R.id.edtNum);
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        data.putExtra("needFresh", needRefresh);

        this.setResult(Activity.RESULT_OK, data);

        super.finish();
    }
}