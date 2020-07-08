package com.example.jumblewordgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
private TextView showText,titleText;
private EditText enterInput;
private Button submit,nextGame;
private String jumbleString;
private int count=0;
private int moveNext=0;
private AlertDialog dialog;
private  int points=0;
private String []cityName={"patna","delhi","mumbai","kanpur","chennai","lucknow","srinagar","punjab","jaipur","Muzaffarpur"};
private String[]jumbleArray=new String[cityName.length];
private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showText=findViewById(R.id.show_text);
        enterInput=findViewById(R.id.enter_input);
        submit=findViewById(R.id.submit);
        nextGame=findViewById(R.id.next_game);
        titleText=findViewById(R.id.title_text);
        preferences=getSharedPreferences("preference",MODE_PRIVATE);
      final boolean isNext=  preferences.getBoolean("firstLevel",false);
        dialog=new AlertDialog.Builder(this).create();
        dialog.setTitle("Result..");


        for(final String getString:cityName)
        {
            jumbleString=getString.substring(getString.length()-2)+
                    getString.substring(1,getString.length()-2)+
                    getString.charAt(0);

            String add=jumbleString.replace("",",");
            add=add.replace(""," ");
            add='['+add.substring(2,add.length()-2)+']';

            jumbleArray[count]=add;

            count++;
        }
     if(submit.getText().toString().equalsIgnoreCase("start"))
     {
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showText.setText(jumbleArray[moveNext]);
                 enterInput.setVisibility(View.VISIBLE);
                 submit.setText("Next");
                 submit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                     try {
                         if(submit.getText().toString().equalsIgnoreCase
                                 ("submit"))
                         {
                             dialog.setMessage("Your correct answers are "+points);
                             dialog.show();
                             enterInput.setVisibility(View.GONE);
                             showText.setVisibility(View.GONE);
                             titleText.setVisibility(View.GONE);
                             submit.setText("Reset Game");
                             submit.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     SharedPreferences.Editor editor=preferences.edit();
                                     editor.putBoolean("firstLevel",false).apply();
                                     startActivity(new Intent(MainActivity.this,MainActivity.class));
                                     finish();

                                 }
                             });
nextGame.setVisibility(View.VISIBLE);
nextGame.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      SharedPreferences.Editor editor=preferences.edit();
      editor.putBoolean("firstLevel",true).apply();
        startActivity(new Intent(MainActivity.this,SecondLevel.class));
        finish();

    }
});
                         }
                         else
                         {
                            operation();
                         }
                         Log.d("tag","movie next = "+moveNext);
                     }
                     catch (ArrayIndexOutOfBoundsException e)
                     {
                         e.printStackTrace();
                     }
                     }
                 });
             }
         });
     }


        if(isNext)
        {
            Log.d("tag",""+isNext);
            startActivity(new Intent(MainActivity.this,SecondLevel.class));
            finish();
        }

    }
    private void operation()
    {
        if(enterInput.getText().toString().equalsIgnoreCase(
                cityName[moveNext]
        ))
        {
            points++;
            Log.d("tag","points "+points);

        }
        moveNext++;
        enterInput.setText("");
        showText.setText(jumbleArray[moveNext]);
        if(moveNext==(cityName.length-1))
        {


            submit.setText("Submit");
            if(enterInput.getText().toString().
                    equalsIgnoreCase(cityName[moveNext]))
            {

                points++;
                enterInput.setText("");
                Log.d("tag","points "+points);
            }
            showText.setText(jumbleArray[moveNext]);
        }
    }
}