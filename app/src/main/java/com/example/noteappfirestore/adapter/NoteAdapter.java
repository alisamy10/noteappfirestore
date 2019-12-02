package com.example.noteappfirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappfirestore.R;
import com.example.noteappfirestore.database.model.NoteModel;

import java.util.List;
import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public interface OnClick {

        void onItemClick(int pos, NoteModel noteModel);

    }
    private OnClick onClick;


    private List<NoteModel> noteModels = new ArrayList<>();

    public NoteAdapter(List<NoteModel> noteModels) {
        this.noteModels = noteModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NoteModel note = noteModels.get(position);
        holder.title.setText(note.getTitle());
        holder.txt_desc.setText(note.getDes());
     //   holder.txt_time.setText(note.formatTime());
       // holder.txt_date.setText(note.formatDate());
        if(onClick!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(position,note);
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return noteModels == null ? 0 : noteModels.size();
    }

    public void setList(List<NoteModel> noteModels) {
        this.noteModels = noteModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title , txt_desc , txt_date , txt_time ;

        ImageView memoryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            txt_desc=itemView.findViewById(R.id.desc);
            //txt_date=itemView.findViewById(R.id.txt_date);
            //txt_time=itemView.findViewById(R.id.txt_time);
            //memoryImage=itemView.findViewById(R.id.image);
        }

    }
}
