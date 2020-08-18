package com.example.MyShoppingApp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.MyShoppingApp.fragments.CategoryFragment;
import com.example.MyShoppingApp.fragments.SubCategoryFragment;
import com.example.myshoppingapp.R;

public class CategorySubcategoryActivity extends AppCompatActivity {
    String categoryId;
    String subCategoryId;

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_sub);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.category_fragment_container);

        if( fragment == null){
            fragment = new CategoryFragment();
            manager.beginTransaction().add(R.id.category_fragment_container,fragment).commit();
        }
    }

    public void loadSubCategoryScreen() {
        SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.category_fragment_container, subCategoryFragment).addToBackStack(null).commit();
    }


}