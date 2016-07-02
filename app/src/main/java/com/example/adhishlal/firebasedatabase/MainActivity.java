package com.example.adhishlal.firebasedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvMyMessageData;
    EditText etMessage;
    Button btnSend;
    // Write a message to the database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMyMessageData=(TextView)findViewById(R.id.tvMyMsg);
        etMessage=(EditText)findViewById(R.id.etMessage);
        btnSend=(Button)findViewById(R.id.btnSend);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyMessages myMessages=new MyMessages(etMessage.getText().toString(),"Android");
                myRef.push().setValue(myMessages);
                etMessage.setText("");
            }
        });



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String myNames="";
                for (DataSnapshot myChild : dataSnapshot.getChildren()) {
                        myNames=myNames+"\n\n"+ myChild.child("chat").getValue();
                }
                tvMyMessageData.setText(myNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
