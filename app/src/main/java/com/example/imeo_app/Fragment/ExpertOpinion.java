package com.example.imeo_app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imeo_app.R;


public class ExpertOpinion extends Fragment {

    public ExpertOpinion() {
        // Required empty public constructor
    }

    public static ExpertOpinion newInstance() {
        ExpertOpinion fragment = new ExpertOpinion();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expert_opinion, container, false);
        return view;
    }
}