package com.imastudio.notessqlite;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ArrayList<Note> listNota;
    Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Note> getListNota() {
        return listNota;
    }

    public void setListNota(ArrayList<Note> listNota) {
        this.listNota = listNota;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, final int position) {

        holder.txtJudul.setText(getListNota().get(position).getTitle());
        holder.txtTanggal.setText(getListNota().get(position).getDate());
        holder.txtDeskripsi.setText(getListNota().get(position).getDescription());

        // todo intent ke AddUpdateActivity, untuk di update / delete
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, AddUpdateActivity.class);
                intent.putExtra(AddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(AddUpdateActivity.EXTRA_NOTE, getListNota().get(position));
                activity.startActivityForResult(intent, AddUpdateActivity.REQUEST_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listNota.size() > 0){
            return listNota.size();
        }
        return listNota.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtJudul, txtTanggal, txtDeskripsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeskripsi = itemView.findViewById(R.id.tv_description);
            txtJudul = itemView.findViewById(R.id.tv_title);
            txtTanggal = itemView.findViewById(R.id.tv_date);
        }
    }
}
