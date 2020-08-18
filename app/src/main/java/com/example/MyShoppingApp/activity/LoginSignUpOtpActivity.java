package com.example.MyShoppingApp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.MyShoppingApp.fragments.LoginFragment;
import com.example.MyShoppingApp.fragments.LoginSignUpChoiceFragment;
import com.example.MyShoppingApp.fragments.OtpFragment;
import com.example.MyShoppingApp.fragments.SignUpFragment;
import com.example.myshoppingapp.R;

public class LoginSignUpOtpActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_LOG_PHONE_NUMBER = 108;
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        //ask permission

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_CALL_LOG) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_PHONE_NUMBERS) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)  {
            // You can use the API that requires the permission.
            //readCallLog();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG) && shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_NUMBERS)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Toast.makeText(this, "We need your call log for better user experience", Toast.LENGTH_SHORT).show();
            }
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG , Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_SMS
                    , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CALL_LOG_PHONE_NUMBER);
        }

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.loginSignUp_fragment_container);

        if( fragment == null){
            fragment = new LoginSignUpChoiceFragment();
            manager.beginTransaction().add(R.id.loginSignUp_fragment_container,fragment).commit();
        }
    }

    public void loadLoginScreen() {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.loginSignUp_fragment_container, loginFragment).addToBackStack(null).commit();
    }
    public void loadSignUpScreen() {
        SignUpFragment signUpFragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.loginSignUp_fragment_container, signUpFragment).addToBackStack(null).commit();
    }

    public void loadOtpFragment() {
        OtpFragment otpFragment = new OtpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.loginSignUp_fragment_container, otpFragment).addToBackStack(null).commit();

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}