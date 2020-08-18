package com.example.MyShoppingApp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MyShoppingApp.interfaces.JsonPlaceHolder;
import com.example.MyShoppingApp.model.SignUpPOJO;
import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.activity.LoginSignUpOtpActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText emailBiz,nameBiz,phoneBiz;
    private JsonPlaceHolder jsonPlaceHolder;
    public static final String EXTRA_MESSAGE = "user_id";
    private Button signup_submit_button;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

        View view =inflater.inflate(R.layout.fragment_sign_up, container, false);
        nameBiz = view.findViewById(R.id.nameBiz);
        phoneBiz = view.findViewById(R.id.phoneBiz);
        emailBiz = view.findViewById(R.id.emailBiz);
        signup_submit_button = view.findViewById(R.id.signup_submit_button);

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

        signup_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUser();
            }
        });
        return view;


    }
    private void submitUser() {
        final SignUpPOJO post = new SignUpPOJO(phoneBiz.getText().toString(),nameBiz.getText().toString() , emailBiz.getText().toString());
        Call<SignUpPOJO> call = jsonPlaceHolder.submitUser(post);//we send this to the server
        call.enqueue(new Callback<SignUpPOJO>() {

            @Override
            public void onResponse(Call<SignUpPOJO> call, Response<SignUpPOJO> response) {
                if(!response.isSuccessful()){

                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUpPOJO postResponse = response.body();
                LoginSignUpOtpActivity loginSignUpOtpActivity = (LoginSignUpOtpActivity) getActivity();
                loginSignUpOtpActivity.setUser_id(postResponse.getUser_id());
                loginSignUpOtpActivity.loadOtpFragment();
               /* Intent intent = new Intent(getContext(), OtpActivity.class);
                String message = postResponse.getUser_id();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/
            }

            @Override
            public void onFailure(Call<SignUpPOJO> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
