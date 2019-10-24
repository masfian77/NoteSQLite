package com.imastudio.notessqlite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.imastudio.notessqlite.database.NotaHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNota;
    NotaHelper helper;
    NoteAdapter adapter;
    ArrayList<Note> lisNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvNota = findViewById(R.id.rv_note);
        rvNota.setHasFixedSize(true);
        rvNota.setLayoutManager(new LinearLayoutManager(this));

        helper = new NotaHelper(this);
        helper.open();

        lisNota = new ArrayList<>();

        adapter = new NoteAdapter(this);
        rvNota.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                startActivityForResult(intent, AddUpdateActivity.REQUEST_ADD);
            }
        });
        new LoadNoteAsyncTask().execute();
    }

    private class LoadNoteAsyncTask extends AsyncTask<Void, Void, ArrayList<Note>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (lisNota.size() > 0) {
                lisNota.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);
            lisNota.addAll(notes);
            adapter.setListNota(lisNota);
            adapter.notifyDataSetChanged();
            if (lisNota.size() == 0) {
                Toast.makeText(MainActivity.this, "Empty data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            return helper.getAllNota();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddUpdateActivity.REQUEST_ADD){
            if (resultCode == AddUpdateActivity.RESULT_ADD){
                Toast.makeText(this, "Success create data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsyncTask().execute();
            }
        }
        if (requestCode == AddUpdateActivity.REQUEST_UPDATE){
            if (resultCode == AddUpdateActivity.RESULT_UPDATE){
                Toast.makeText(this, "Success update data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsyncTask().execute();
            }

            if (resultCode == AddUpdateActivity.RESULT_DELETE){
                Toast.makeText(this, "Success delete data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsyncTask().execute();
            }

        }
    }
}