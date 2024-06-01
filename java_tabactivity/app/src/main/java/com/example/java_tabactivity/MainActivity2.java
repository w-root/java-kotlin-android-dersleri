package com.example.java_tabactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java_tabactivity.ui.main.PageViewModel;

public class MainActivity2 extends AppCompatActivity {

    EditText text;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text = findViewById(R.id.etName);
        textView = findViewById(R.id.tv);
        MainActivity2ViewModel pageViewModel = new ViewModelProvider(this).get(MainActivity2ViewModel.class);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pageViewModel.setStr(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pageViewModel.getValue().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });

    }
}