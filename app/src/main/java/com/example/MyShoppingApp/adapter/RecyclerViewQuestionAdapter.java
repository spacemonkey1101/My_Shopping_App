package com.example.MyShoppingApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshoppingapp.R;
import com.example.MyShoppingApp.interfaces.RecyclerViewClickListener;
import com.example.MyShoppingApp.model.OptionsPOJO;

import java.util.List;

public class RecyclerViewQuestionAdapter extends RecyclerView.Adapter<RecyclerViewQuestionAdapter.ViewHolder> {
    List<String> questions;
    List<List<OptionsPOJO>> choices;
    Context context;
    LayoutInflater layoutInflater;
    private static RecyclerViewClickListener itemListener;

    public RecyclerViewQuestionAdapter(List<String> questions, List<List<OptionsPOJO>> choices, Context context, RecyclerViewClickListener itemListener) {
        this.questions = questions;
        this.choices = choices;
        this.context = context;
        this.itemListener = itemListener;
        layoutInflater = layoutInflater.from(context);
    }



    @NonNull
    @Override
    public RecyclerViewQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = layoutInflater.inflate(R.layout.question_layout, parent , false);
        return new RecyclerViewQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewQuestionAdapter.ViewHolder holder, int position) {
        holder.questionTextView.setText(questions.get(position));
        List<OptionsPOJO> options = choices.get(position);

        for( OptionsPOJO optionsPOJO : options) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(optionsPOJO.getChoice());
            holder.questionRadioGroup.addView(radioButton);

        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView;
        private RadioGroup questionRadioGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question_textview);
            questionRadioGroup = itemView.findViewById(R.id.question_radio_group);
        }
    }
}
