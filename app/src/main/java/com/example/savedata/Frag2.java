package com.example.savedata;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Frag2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag2, container, false);

        TextView textView = v.findViewById(R.id.TextView);
        SharedPreferences sp = this.getActivity().getSharedPreferences("all", Context.MODE_PRIVATE);
        textView.setText(String.valueOf(sp.getInt("count2",0)));

        return v;
    }

}