package com.example.MyShoppingApp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.activity.LoginSignUpOtpActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginSignUpChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginSignUpChoiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button loginButton,signUpButton;

    public LoginSignUpChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginSignUpChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginSignUpChoiceFragment newInstance(String param1, String param2) {
        LoginSignUpChoiceFragment fragment = new LoginSignUpChoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_login_sign_up_choice, container, false);
        loginButton = view.findViewById(R.id.logInBusinessAccountButton);
        signUpButton = view.findViewById(R.id.signUpBusinessAccountButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUpOtpActivity loginSignUpOtpActivity = (LoginSignUpOtpActivity) getActivity();
                loginSignUpOtpActivity.loadLoginScreen();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUpOtpActivity loginSignUpOtpActivity = (LoginSignUpOtpActivity) getActivity();
                loginSignUpOtpActivity.loadSignUpScreen();
            }
        });
        return view;
    }
}