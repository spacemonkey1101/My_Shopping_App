package com.example.MyShoppingApp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MyShoppingApp.activity.LocationService;
import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.activity.LoginSignUpOtpActivity;
import com.example.MyShoppingApp.interfaces.JsonPlaceHolder;
import com.example.MyShoppingApp.model.OtpPOJO;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JsonPlaceHolder jsonPlaceHolder;
    private EditText otpNumber;
    private Button otpButton,resendOtpButton;


    public OtpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtpFragment newInstance(String param1, String param2) {
        OtpFragment fragment = new OtpFragment();
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

        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        otpNumber = view.findViewById(R.id.otpNumber);
        otpButton = view.findViewById(R.id.otpButton);
        resendOtpButton = view.findViewById(R.id.resendOtpButton);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)

                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wofo24.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });

        resendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp();
            }
        });

        return view;
    }

    public void resendOtp() {
        LoginSignUpOtpActivity loginSignUpOtpActivity = (LoginSignUpOtpActivity) getActivity();
        String user_id = loginSignUpOtpActivity.getUser_id();
        Call<OtpPOJO> call = jsonPlaceHolder.resendOtp(Integer.parseInt(user_id));
        call.enqueue(new Callback<OtpPOJO>() {
            @Override
            public void onResponse(Call<OtpPOJO> call, Response<OtpPOJO> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<OtpPOJO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void verifyOtp() {
        LoginSignUpOtpActivity loginSignUpOtpActivity = (LoginSignUpOtpActivity) getActivity();
        String user_id = loginSignUpOtpActivity.getUser_id();
        Call<OtpPOJO> call = jsonPlaceHolder.verifyOtp(Integer.parseInt(user_id), new OtpPOJO(Integer.parseInt(otpNumber.getText().toString())));
        call.enqueue(new Callback<OtpPOJO>() {
            @Override
            public void onResponse(Call<OtpPOJO> call, Response<OtpPOJO> response) {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                Log.d("response otp", "onResponse: " + response.body().getMesage());
                if (response.body().getMesage().equalsIgnoreCase("Successful")) {
                    Intent intent1 = new Intent(getContext(), LocationService.class);
                    intent1.putExtra("auth_token", response.body().getAccess());
                    startActivity(intent1);
                }
            }

            @Override
            public void onFailure(Call<OtpPOJO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}