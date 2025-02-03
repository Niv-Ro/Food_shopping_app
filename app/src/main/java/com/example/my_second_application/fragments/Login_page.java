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
 * Use the {@link Login_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_page extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static Login_page newInstance(String param1, String param2) {
        Login_page fragment = new Login_page();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_page, container, false);
        Button button_Login = view.findViewById(R.id.log_button);
        Button button_Register_Page = view.findViewById(R.id.log_register_button);

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText logemailEditText = view.findViewById(R.id.log_email);
                EditText logpassEditText = view.findViewById(R.id.log_pass);
                if (!logemailEditText.getText().toString().isEmpty() && !logpassEditText.getText().toString().isEmpty()) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.login(v);
                } else {
                    // Show a message if the email field is empty
                    Toast.makeText(requireContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_Register_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_login_to_register);

            }
        });
        return view;
    }
}