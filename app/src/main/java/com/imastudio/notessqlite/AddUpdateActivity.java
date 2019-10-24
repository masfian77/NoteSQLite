package com.imastudio.notessqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imastudio.notessqlite.database.NotaHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateActivity extends AppCompatActivity {

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;

    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;

    public static int RESULT_DELETE = 301;

    public boolean isUpdate = false;

    public static String EXTRA_POSITION = "extra_position";
    public static String EXTRA_NOTE = "extra_note";

    EditText edtTitle, edtDescription;
    Button btnSave;

    Note note;
    NotaHelper notaHelper;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);
        btnSave = findViewById(R.id.btn_save);

        notaHelper = new NotaHelper(this);
        notaHelper.open();

        note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isUpdate = true;
        }

        String actionBarTitle, btnTitle;
        if (isUpdate) {
            actionBarTitle = "Update data";
            btnTitle = "Update";

            edtTitle.setText(note.getTitle());
            edtDescription.setText(note.getDescription());

        } else {
            actionBarTitle = "Create data";
            btnTitle = "Create";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        btnSave.setText(btnTitle);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                    Toast.makeText(AddUpdateActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                }else {
                    Note newNote = new Note();
                    newNote.setTitle(title);
                    newNote.setDescription(description);

                    Intent intent = new Intent();
                    if (isUpdate) {
                        newNote.setDate(note.getDate());
                        newNote.setId(note.getId());
                        notaHelper.updateNota(newNote);

                        intent.putExtra(EXTRA_POSITION, position);
                        setResult(RESULT_UPDATE, intent);
                        finish();

                    }else {
                        newNote.setDate(currentDate());
                        notaHelper.insertNota(newNote);
                        setResult(RESULT_ADD);
                        finish();
                    }
                }
            }
        });
    }
    private String currentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (isUpdate){
            getMenuInflater().inflate(R.menu.menu_delete, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_delete){
            notaHelper.deleteNota(note.getId());
            setResult(RESULT_DELETE);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
