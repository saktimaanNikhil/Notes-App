package com.example.notesandroidapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesandroidapp.databinding.FragmentAddNoteBinding;
import com.example.notesandroidapp.databinding.FragmentHomeBinding;


public class AddNoteFragment extends Fragment {
    FragmentAddNoteBinding binding;
    MyDatabaseHelper myDB;
    NotesModel model;
    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        model = new NotesModel();
        myDB = new MyDatabaseHelper(getContext());

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.saveButton.setVisibility(View.VISIBLE);
                myDB.addNote(binding.titleEditText.getText().toString().trim(),
                        binding.contentEditText.getText().toString().trim());

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new HomeFragment());
//        fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                fragmentTransaction.commit();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            binding.updateButton.setVisibility(View.VISIBLE);
            String id = bundle.getString("id");
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            String heading = bundle.getString("heading");

            Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
            // Ensure that addNoteHeading is initialized in your layout XML file
            binding.addNoteHeading.setText(heading);
            binding.titleEditText.setText(title);
            binding.contentEditText.setText(content);
        }else {
            binding.updateButton.setVisibility(View.GONE);
        }

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String id = bundle.getString("id");
                String title = binding.titleEditText.getText().toString().trim();
                String content = binding.contentEditText.getText().toString().trim();



                myDB.updateContact(id, title, content);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }


}