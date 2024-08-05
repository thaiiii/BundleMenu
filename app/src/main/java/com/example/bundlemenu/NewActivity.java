package com.example.bundlemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewActivity extends AppCompatActivity {
    EditText txtid, txtname;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getWidget();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempId = Integer.parseInt(txtid.getText().toString());
                String tempName = txtname.getText().toString();
                Person person = new Person(tempId, tempName);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", person);
                intent.putExtra("DATA", bundle);
                setResult(MainActivity.SAVE_NEW_EMPLOYEE, intent);
                finish();
            }
        });
    }

    public void getWidget() {
        txtid = findViewById(R.id.txtid);
        txtname = findViewById(R.id.txtname);
        btnsave = findViewById(R.id.btnsave);
    }
}
