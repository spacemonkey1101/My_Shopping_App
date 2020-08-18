package com.example.MyShoppingApp.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyShoppingApp.interfaces.JsonPlaceHolder;
import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.activity.QuestionActivity;
import com.example.MyShoppingApp.model.OptionsPOJO;
import com.example.MyShoppingApp.model.QuestionPOJO;

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
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private JsonPlaceHolder jsonPlaceHolder;
    private List<String> questionsList;
    private List<QuestionPOJO> questions;
    private List<List<OptionsPOJO>> optionsList ;
    private List<QuestionPOJO> initialQuestions;
    private static int counter = 0;
    private Button prevBtn,nextBtn,submitBtn;
    private boolean nextQuestion = false;
    private boolean nextQuestionNotFound;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view =inflater.inflate(R.layout.fragment_question, container, false);
       // recyclerView = view.findViewById(R.id.question_recycler_view);

        questionsList = new ArrayList<>();
        optionsList = new ArrayList<>();
        initialQuestions = new ArrayList<>();

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

        final QuestionActivity questionActivity = (QuestionActivity) getActivity();
        Call<List<QuestionPOJO>> call = jsonPlaceHolder.getQuestions(questionActivity.getSubCategoryId());

        //execute the call and get response
        call.enqueue(new Callback<List<QuestionPOJO>>() {//enque method makes a new thread
            @Override
            public void onResponse(Call<List<QuestionPOJO>> call, Response<List<QuestionPOJO>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

                questions = response.body();
                for(QuestionPOJO question : questions){
                    questionsList.add(question.getQuestion());
                    optionsList.add(question.getOptions());
                    //initRecyclerView();
                    if(question.getParent_question() == null){
                        initialQuestions.add(question);
                    }
                }
                prevBtn = view.findViewById(R.id.prevButton);
                nextBtn =view.findViewById(R.id.nextButton);
                submitBtn = view.findViewById(R.id.questionSubmitButton);

                renderQuestion(initialQuestions.get(counter) , view);

                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonVisibility();
                        renderQuestion(initialQuestions.get(--counter) , view);

                    }
                });

                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonVisibility();
                        renderQuestion(initialQuestions.get(++counter) , view);
                    }
                });

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuestionActivity questionActivity1 = (QuestionActivity) getActivity();
                        questionActivity1.loadNextScreen();
                    }
                });
            }

            private void buttonVisibility() {
                if(counter == 0){
                    prevBtn.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.VISIBLE);
                } else if ( counter >0 && counter< initialQuestions.size()){
                    nextBtn.setVisibility(View.VISIBLE);
                    prevBtn.setVisibility(View.VISIBLE);
                } else if( counter == initialQuestions.size()){
                    prevBtn.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<List<QuestionPOJO>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to receive Questions", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }




    private void renderQuestion(QuestionPOJO questionPOJO, final View view) {
        CardView cardView = view.findViewById(R.id.question_card_view);
        TextView questionTxtView=view.findViewById(R.id.question_textview);
        RadioGroup questionRadioGroup = view.findViewById(R.id.question_radio_group);
        questionRadioGroup.removeAllViews();

        questionTxtView.setText(questionPOJO.getQuestion());

        if (questionTxtView.getParent() != null) {
            ((ViewGroup) questionTxtView.getParent()).removeView(questionTxtView); // <- fix
        }
        cardView.addView(questionTxtView);
        if (questionRadioGroup.getParent() != null) {
            ((ViewGroup) questionRadioGroup.getParent()).removeView(questionRadioGroup); // <- fix
        }
        cardView.addView(questionRadioGroup);
        for (int i = 0; i < questionPOJO.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(questionPOJO.getOptions().get(i).getChoice());
            questionRadioGroup.addView(radioButton);
        }

        questionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(radioButtonId);
                String optionSelected = String.valueOf(radioButton.getText());
                nextQuestionNotFound = true;
                for(QuestionPOJO questionPOJO : questions){
                    if(questionPOJO.getParent_option().equalsIgnoreCase(optionSelected))
                    {
                        renderQuestion(questionPOJO,view);
                        nextQuestionNotFound = false;

                    }
                }
                if(nextQuestionNotFound){
                    submitBtn.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.GONE);
                    prevBtn.setVisibility(View.GONE);

                }
            }
        });
        
    }

    public QuestionPOJO getQuestionFromId(String id){
        for(QuestionPOJO questionPOJO: questions){
            if(questionPOJO.getId().equalsIgnoreCase(id) ){
                return questionPOJO;
            }
        }
        return null;
    }

   /* private  void initRecyclerView(){
        RecyclerViewQuestionAdapter recyclerViewAdapter = new RecyclerViewQuestionAdapter(questionsList,optionsList ,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    @Override
    public void recyclerViewListClicked(View v, int position){
        CategorySubcategoryActivity categorySubcategoryActivity = (CategorySubcategoryActivity) getActivity();
        categorySubcategoryActivity.setSubCategoryId(String.valueOf(position+1));

        Intent intent = new Intent(getContext(), QuestionActivity.class);
        intent.putExtra("category_id" ,  categorySubcategoryActivity.getCategoryId());
        intent.putExtra("subcategory_id" , categorySubcategoryActivity.getSubCategoryId());
        startActivity(intent);
    }*/
}