package com.example.charann.songdetection_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button msend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myapplication2-3ec91.firebaseio.com/");

        msend = (Button) findViewById(R.id.b1);

        msend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference refchild = ref.child("Value");
                refchild.setValue(Math.random());
            }
        });
    }
}
