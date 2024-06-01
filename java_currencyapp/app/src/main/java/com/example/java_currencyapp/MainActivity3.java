package com.example.java_currencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.java_currencyapp.databinding.ActivityMain3Binding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    ActivityMain3Binding binding;
    List<String> liste = new ArrayList<String>() {{
        add("Hisse Senedi");
        add("Altın");
        add("Döviz");
        add("Kripto Para");
    }};
    String type = "";
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter adapter = new ArrayAdapter(MainActivity3.this, android.R.layout.simple_list_item_1,liste);
        binding.spinner.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(Singleton.getInstance().getCurrency() != null){
            binding.etSelling.setText(String.valueOf(Singleton.getInstance().getCurrency().selling));
            binding.etBuying.setText(String.valueOf(Singleton.getInstance().getCurrency().buying));
            binding.etCurrencyName.setText(String.valueOf(Singleton.getInstance().getCurrency().currencyName));
            binding.etChange.setText(String.valueOf(Singleton.getInstance().getCurrency().change));

        }

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = liste.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addCurrency(View view) {
        String currencyName = binding.etCurrencyName.getText().toString();
        double buying = Double.parseDouble(binding.etBuying.getText().toString());
        double selling = Double.parseDouble(binding.etSelling.getText().toString());
        double change = Double.parseDouble(binding.etChange.getText().toString());

        Currency currency = new Currency("",currencyName,type,buying,selling,change);

        firebaseFirestore.collection("currencies").add(currency).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent view = new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity3.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateCurrency(View view) {
        Map<String,Object> data = new HashMap<>();
        data.put("currencyName",binding.etCurrencyName.getText());
        data.put("selling",Double.parseDouble(binding.etSelling.getText().toString()));
        data.put("buying",Double.parseDouble(binding.etBuying.getText().toString()));
        data.put("change",Double.parseDouble(binding.etChange.getText().toString()));
        data.put("type",type);

        for (Object o: data.values()
             ) {
            System.out.println(o);
        }


        firebaseFirestore.collection("currencies").document(Singleton.getInstance().getCurrency().id)
                        .update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent view = new Intent(MainActivity3.this,MainActivity2.class);
                        startActivity(view);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity3.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteCurrency(View view) {
        firebaseFirestore.collection("currencies").document(Singleton.getInstance().getCurrency().id).
                delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Intent view = new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity3.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}