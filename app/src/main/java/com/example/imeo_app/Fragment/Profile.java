package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.imeo_app.Activity.LogIn;
import com.example.imeo_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
  private TextView txtFullName;
  private AppCompatButton btnExit;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        return fragment;
    }

    public static Fragment newInstance() {
        Profile profile = new Profile();
        return profile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullName = view.findViewById(R.id.txtFullName);
        btnExit = view.findViewById(R.id.btnExit);
        SharedPreferences shared = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        txtFullName.setText(shared.getString("userName", ""));

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setMessage("میخواهید از حساب کاربری خارج شوید؟");

                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(getActivity());
                infoBuilder.setMessage("میخواهید از حساب کاربری خارج شوید؟")
                        .setPositiveButton("بله", (dialog, id) -> exit()).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });

        return view;
    }
    private void exit(){
        SharedPreferences pref = getActivity().getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user", "");
        editor.putString("pass", "");
        editor.commit();
        Intent intent = new Intent(getActivity(), LogIn.class);
        startActivity(intent);
    }
}