package com.rau.CipherMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DecryptText extends AppCompatActivity {
    private TextView Decrypt_text;
    private EditText unique_id1;
    private Button button2;
    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt_text);
        init();
        db = FirebaseDatabase.getInstance().getReference();
    }
    public void init(){
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Decrypt_text = findViewById(R.id.Decrypt_text);
        unique_id1 = findViewById(R.id.unique_id1);
        //Encrypt_text = findViewById(R.id.Encrypt_text);
        button2 = findViewById(R.id.button2);

    }
    public void OnClickDecrypt(View view){
        if(!TextUtils.isEmpty(unique_id1.getText().toString())){
          AddText();
        }else{
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
    };


// Attach a listener to read the data at your profile reference
    public void AddText(){

            db = FirebaseDatabase.getInstance().getReference(unique_id1.getText().toString());
            db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    com.rau.CipherMessage.message_info msg = dataSnapshot.getValue(com.rau.CipherMessage.message_info.class);
                    Decrypt_text.setText(msg.text);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }

