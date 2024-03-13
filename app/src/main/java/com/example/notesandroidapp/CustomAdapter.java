package com.example.notesandroidapp;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    ArrayList<NotesModel> data;

    CustomAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.note_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
        holder.idTextView.setText(String.valueOf(data.get(position).id));
        holder.titleTextView.setText(data.get(position).title);
        holder.contentTextView.setText(data.get(position).content);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create a bundle and set data
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(data.get(position).id));
                bundle.putString("title", data.get(position).title);
                bundle.putString("content", data.get(position).content);
                bundle.putString("heading", "Update");
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                addNoteFragment.setArguments(bundle);


                // Get FragmentManager
                FragmentManager fragmentManager = null;
                if (view.getContext() instanceof FragmentActivity) {
                    fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                } else {
                    Log.e("MyAdapter", "Unable to get FragmentManager: Context is not a FragmentActivity");
                    return; // Return early if unable to get FragmentManager
                }

                // Replace the current fragment with AddNoteFragment
                if (fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, addNoteFragment);
                    fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    fragmentTransaction.commit();
                } else {
                    Log.e("MyAdapter", "FragmentManager is null");
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a bundle and set data
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(data.get(position).id));
                bundle.putString("title", data.get(position).title);
                bundle.putString("content", data.get(position).content);
                ShowNoteFragment showNoteFragment = new ShowNoteFragment();
                showNoteFragment.setArguments(bundle);

                // Get FragmentManager
                FragmentManager fragmentManager = null;
                if (view.getContext() instanceof FragmentActivity) {
                    fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                } else {
                    return; // Return early if unable to get FragmentManager
                }

                // Replace the current fragment with AddNoteFragment
                if (fragmentManager != null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, showNoteFragment);
                    fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    fragmentTransaction.commit();
                } else {
                    Log.e("MyAdapter", "FragmentManager is null");
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure want to delete?")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext());
                                dbHelper.deleteNote(data.get(position).id); // Assuming id is the primary key of your contact
                                data.remove(position); // Remove the item from your list
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView,idTextView;
        ImageView update, delete;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            cardView = itemView.findViewById(R.id.cardView);
            update = itemView.findViewById(R.id.updateeButton);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
