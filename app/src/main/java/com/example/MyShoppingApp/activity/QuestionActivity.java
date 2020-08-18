package com.example.MyShoppingApp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.MyShoppingApp.fragments.QuestionFragment;
import com.example.MyShoppingApp.fragments.SubCategoryFragment;
import com.example.myshoppingapp.R;

public class QuestionActivity extends AppCompatActivity {

    private String categoryId;
    private String subCategoryId;


    public String getCategoryId() {
        return categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        categoryId = intent.getStringExtra("category_id");
        subCategoryId = intent.getStringExtra("subcategory_id");

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.question_fragment_container);

        if( fragment == null){
            fragment = new QuestionFragment();
            manager.beginTransaction().add(R.id.question_fragment_container,fragment).commit();
        }

    }


    public void loadNextScreen() { //change this
        SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.category_fragment_container, subCategoryFragment).addToBackStack(null).commit();
    }

}