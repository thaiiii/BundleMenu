package com.example.bundlemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText txtid, txtname;
    Button btnsave;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWidget();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        if (bundle != null) {
            person = (Person) bundle.getSerializable("person");
            if (person != null) {
                txtid.setText(String.valueOf(person.getId()));
                txtname.setText(person.getName());
                txtid.setEnabled(false);

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        person.setName(txtname.getText().toString());
                        Intent resultIntent = new Intent();
                        Bundle resultBundle = new Bundle();
                        resultBundle.putSerializable("person", person);
                        resultIntent.putExtra("DATA", resultBundle);
                        setResult(MainActivity.SAVE_EDIT_EMPLOYEE, resultIntent);
                        finish();
                    }
                });
            }
        }
    }

    public void getWidget() {
        txtid = findViewById(R.id.txtid);
        txtname = findViewById(R.id.txtname);
        btnsave = findViewById(R.id.btnsave);
    }
}
