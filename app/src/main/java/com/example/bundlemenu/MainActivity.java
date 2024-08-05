package com.example.bundlemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_EMPLOYEE = 1;
    public static final int EDIT_EMPLOYEE = 2;
    public static final int SAVE_NEW_EMPLOYEE = 3;
    public static final int SAVE_EDIT_EMPLOYEE = 4;

    ArrayList<Person> list;
    ArrayAdapter<Person> adapter;
    ListView listView;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        list.add(new Person(1, "John Doe"));
        list.add(new Person(2, "Jane Smith"));
        list.add(new Person(3, "Mike Johnson"));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Thêm");
        menu.add(0, 1, 0, "Sửa");
        menu.add(0, 2, 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: // Thêm
                Intent intentAdd = new Intent(MainActivity.this, NewActivity.class);
                startActivityForResult(intentAdd, NEW_EMPLOYEE);
                return true;
            case 1: // Sửa
                Intent intentEdit = new Intent(MainActivity.this, EditActivity.class);
                Person person = list.get(selectedPosition);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", person);
                intentEdit.putExtra("DATA", bundle);
                startActivityForResult(intentEdit, EDIT_EMPLOYEE);
                return true;
            case 2: // Xóa
                new AlertDialog.Builder(this)
                        .setMessage("Bạn có chắc muốn xóa không?")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            list.remove(selectedPosition);
                            adapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras().getBundle("DATA");
            if (bundle != null) {
                Person person = (Person) bundle.getSerializable("person");
                if (person != null) {
                    if (requestCode == NEW_EMPLOYEE && resultCode == SAVE_NEW_EMPLOYEE) {
                        list.add(person);
                    } else if (requestCode == EDIT_EMPLOYEE && resultCode == SAVE_EDIT_EMPLOYEE) {
                        list.set(selectedPosition, person);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}