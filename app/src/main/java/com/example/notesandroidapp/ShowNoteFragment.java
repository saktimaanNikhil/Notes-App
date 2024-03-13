package com.example.notesandroidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNoteFragment extends Fragment {

TextView titleText, contentText;
    String title, content;
    public ShowNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_note, container, false);

        titleText = view.findViewById(R.id.showTitle);
        contentText = view.findViewById(R.id.showContent);

        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            content = bundle.getString("content");
        } else {
            Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Update UI with the latest data in onResume
        titleText.setText(title);
        contentText.setText(content);
    }
}