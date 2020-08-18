package com.example.MyShoppingApp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.MyShoppingApp.interfaces.JsonPlaceHolder;
import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.activity.CategorySubcategoryActivity;
import com.example.MyShoppingApp.adapter.RecyclerViewCategoryAdapter;
import com.example.MyShoppingApp.interfaces.RecyclerViewClickListener;
import com.example.MyShoppingApp.model.CategoryPOJO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements RecyclerViewClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private JsonPlaceHolder jsonPlaceHolder;
    private GridLayoutManager layoutManager;
    private List<String> titles;
    private List<String> images;
    private List<CategoryPOJO> categories;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.category_recycler_view);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        setApiCall();
        return view;
    }
    private void setApiCall() {
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
        Call<List<CategoryPOJO>> call = jsonPlaceHolder.getCategories();

        //execute the call and get response
        call.enqueue(new Callback<List<CategoryPOJO>>() {//enque method makes a new thread
            @Override
            public void onResponse(Call<List<CategoryPOJO>> call, Response<List<CategoryPOJO>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

                 categories = response.body();
                for(CategoryPOJO categoryPOJO : categories){
                    images.add(categoryPOJO.getWallpaper());
                    titles.add(categoryPOJO.getCategory());
                }
                initRecyclerView();

            }

            @Override
            public void onFailure(Call<List<CategoryPOJO>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void initRecyclerView(){
        RecyclerViewCategoryAdapter recyclerViewAdapter = new RecyclerViewCategoryAdapter(titles,images,getContext(),this);
        layoutManager = new GridLayoutManager(getContext(), 2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    @Override
    public void recyclerViewListClicked(View v, int position){
        CategorySubcategoryActivity categorySubcategoryActivity = (CategorySubcategoryActivity) getActivity();
        categorySubcategoryActivity.setCategoryId(String.valueOf(position+1));
        categorySubcategoryActivity.loadSubCategoryScreen();
    }
}