package com.rau.CipherMessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity  extends AppCompatActivity  {
    private EditText Text_Message;
    private TextView unique_id, Encrypt;
    private Button button;
    private DatabaseReference db;
    public String message_id;
    private String[] Encrypt_Decrypt = {"Encrypt","Decrypt"};
    private AutoCompleteTextView autoCompleteTxt;
    private ArrayAdapter<String> adapterItems;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadAd();
        init();
        DropMenu2();
    }
    public void init(){
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Text_Message = findViewById(R.id.Text_Message);
        unique_id = findViewById(R.id.unique_id);
        Encrypt = findViewById(R.id.Encrypt);
        button = findViewById(R.id.button);
        db = FirebaseDatabase.getInstance().getReference("Texts");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


    }
    public void OnClickSave(View view) throws Exception{
        com.rau.CipherMessage.CryptMessage crm = new com.rau.CipherMessage.CryptMessage();
        message_id = RandomStringUtils.randomAlphabetic(10);
        String s = Text_Message.getText().toString();
        com.rau.CipherMessage.message_info msg = new com.rau.CipherMessage.message_info(s, crm.SHA(s));
        Map<String, Object> updates = new HashMap<>();
        String encrypt = crm.SHA(Text_Message.getText().toString());
        updates.put(message_id, msg);

        if(!TextUtils.isEmpty(Text_Message.getText().toString())){
            db = FirebaseDatabase.getInstance().getReference(message_id);
            db.setValue(msg);
            Encrypt.setText(encrypt);
            unique_id.setText(message_id);
        }else{
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }
    }



    public void NewActivity(){
        Intent i  = new Intent(this, DecryptText.class);
        startActivity(i);
    }
   /* public void DropMenu(){
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Item " + text, Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    public void DropMenu2(){
        autoCompleteTxt = findViewById(R.id.auto_complete_txt2);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, Encrypt_Decrypt);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                if(text == "Decrypt"){
                    //loadAd();
                    NewActivity();
                }
            }
        });
    }
    /*public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                "ca-app-pub-1254435158776473/8474001796",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.show(MainActivity.this);
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        mInterstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");

                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.

                                        Log.d("TAG", "The ad was shown.");

                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;

                        /*String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                               MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }*/
}











