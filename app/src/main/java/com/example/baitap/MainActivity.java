package com.example.baitap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    FloatingActionButton btnAddContact;
    public static ArrayList<Contact> contacts = new ArrayList<>();
    ContactAdapter adapter;
    public DataBase dataBase = new DataBase(this);

    private static final int MY_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map();
        //add một số dữ liệu nhất định vào database

//        dataBase.addContact(new Contact("ta quang huy", "0386356200"));
//        dataBase.addContact(new Contact("do huong giang", "0337030504"));
//        dataBase.addContact(new Contact("anh van", "0123455678"));
        contacts = (ArrayList<Contact>) dataBase.getAll();
        adapter = new ContactAdapter(this, contacts);
        lvContact.setAdapter(adapter);

        //đăng ký sự kiện menu cho list view contact
        registerForContextMenu(lvContact);
        AddData();
    }

    //đăng ký menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //lấy vị trí note hiện tại
        final Contact selectedContact = (Contact) this.lvContact.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.menu_edit:
                //todo chuyển sang màn hình sửa
                Intent intent = new Intent(this, EditActivity.class);
                //name trong putExtra là tên của lớp đối tượng
                intent.putExtra("contact", selectedContact);

                // Start AddEditNoteActivity, (with feedback).
                this.startActivityForResult(intent,MY_REQUEST_CODE);
                return true;

            case R.id.menu_sort:
                //sắp xếp lại list theo tên tăn dần
                Collections.sort(contacts);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_delete:
//                Toast.makeText(this, "delete contact", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setTitle("Xóa dữ liệu").setMessage("bạn có muốn xóa?")
                        .setPositiveButton("có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase.delete(selectedContact);
                                contacts.remove(selectedContact);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("không", null)
                        .show();
                return true;

        }
        return true;

    }

    private void Map(){
        lvContact = (ListView) findViewById(R.id.lvContact);
        btnAddContact = (FloatingActionButton) findViewById(R.id.btnAdd);
    }

    private void AddData(){
        btnAddContact.setOnClickListener(v -> {
            Intent i = new Intent(this, AddAcitvity.class);
            this.startActivityForResult(i, MY_REQUEST_CODE);
        });
    }

    //hàm refresh lại lv sau khi trả data từ màn hình kia sang
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.contacts.clear();
                List<Contact> list = dataBase.getAll();
                this.contacts.addAll(list);


                // Notify the data change (To refresh the ListView).
                this.adapter.notifyDataSetChanged();
            }
        }
    }


}