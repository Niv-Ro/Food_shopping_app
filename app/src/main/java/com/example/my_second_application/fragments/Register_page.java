package com.example.my_second_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_second_application.R;
import com.example.my_second_application.activitys.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentThree.
     */
    // TODO: Rename and change types and number of parameters
    public static Register_page newInstance(String param1, String param2) {
        Register_page fragment = new Register_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_page, container, false);
        Button button_Register = view.findViewById(R.id.reg_register_button);

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText regNameEditText = view.findViewById(R.id.reg_Name);
                EditText regEmailEditText = view.findViewById(R.id.reg_email);
                EditText regPassEditText = view.findViewById(R.id.reg_pass);
                EditText regRePassEditText = view.findViewById(R.id.reg_pass_approve);
                EditText regPhoneEditText = view.findViewById(R.id.reg_phone);
                if (!regEmailEditText.getText().toString().isEmpty() && !regPassEditText.getText().toString().isEmpty() && !regRePassEditText.getText().toString().isEmpty() && !regPhoneEditText.getText().toString().isEmpty() && !regNameEditText.getText().toString().isEmpty()){
                    if(regPassEditText.getText().toString().equals(regRePassEditText.getText().toString())) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.register(v);
                    }
                    else {
                        Toast.makeText(requireContext(), "Passwords should be the same", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(requireContext(), "All fields should be filled", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}