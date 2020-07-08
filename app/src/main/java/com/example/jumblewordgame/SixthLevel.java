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

public class SixthLevel extends AppCompatActivity {
    private TextView showText,titleText;
    private EditText enterInput;
    private Button submit,nextGame;
    private String jumbleString;
    private int count=0;
    private int moveNext=0;
    private AlertDialog dialog;
    private  int points=0;
    private String []cityName={"Myna","Canary","Parrot","Crow","Dove","Pigeon","Heron","Crane","Goose","macaw"};
    private String[]jumbleArray=new String[cityName.length];
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth_level);
        showText=findViewById(R.id.show_text);
        enterInput=findViewById(R.id.enter_input);
        submit=findViewById(R.id.submit);
        nextGame=findViewById(R.id.next_game);
        titleText=findViewById(R.id.title_text);
        preferences=getSharedPreferences("preference",MODE_PRIVATE);
        final boolean isNext=  preferences.getBoolean("sixthLevel",false);
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

                                    titleText.setVisibility(View.GONE);

                                    showText.setText("Game is finished");
                                    submit.setText("Play again");
                                    submit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
startActivity(new Intent(SixthLevel.this,SixthLevel.class));

                                            finish();
                                        }
                                    });
                                    nextGame.setVisibility(View.VISIBLE);
                                    nextGame.setText("Play from start");
                                    nextGame.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            SharedPreferences.Editor editor=preferences.edit();
                                            editor.putBoolean("firstLevel",false);
                                            editor.putBoolean("secondLevel",false);
                                            editor.putBoolean("thirdLevel",false);
                                            editor.putBoolean("fourthLevel",false);
                                            editor.putBoolean("fifthLevel",false);
                                            editor.apply();
                                       startActivity(new Intent(SixthLevel.this,MainActivity.class));
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