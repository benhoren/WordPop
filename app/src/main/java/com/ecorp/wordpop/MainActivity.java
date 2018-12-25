package com.ecorp.wordpop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changePage(false);
                    return true;
                case R.id.navigation_dashboard:
                    changePage(true);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        bulidPage();
        changePage(false);

        Dictionary.init();


        addWordToRow("a", "א");
        addWordToRow("b", "ב");
        addWordToRow("c", "ג");
        addWordToRow("d", "ד");
        addWordToRow("e", "ה");
        loadWords();
    }

    void loadWords(){
        File file = new File("filename.txt");

        try{
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            System.out.println(st);
    }catch(Exception e){e.printStackTrace();}
}

    void addToFile(String word, String translation){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("filename.txt"), "utf-8"))) {
            writer.write(word+","+translation+'\n');

            writer.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    void openPopUp(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View et1 = inflater.inflate(R.layout.popupwindow,
                (ViewGroup) findViewById(R.id.popupid));

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(et1);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String word = ((TextView) et1.findViewById(R.id.WordField)).getText().toString();
                String tran = ((TextView) et1.findViewById(R.id.TranField)).getText().toString();

                if(!word.isEmpty() && !tran.isEmpty())
                addWordToRow(word, tran);
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();



    }

    void loadQuiz(){

        try{
            av = false;
            Thread.currentThread().sleep(500);
        }catch(Exception e){}

        TextView word = (TextView) testPage.findViewById(R.id.word);

        buttons = new Button[4];
        buttons[0] = (Button) testPage.findViewById(R.id.ans1);
        buttons[1] = (Button) testPage.findViewById(R.id.ans2);
        buttons[2] = (Button) testPage.findViewById(R.id.ans3);
        buttons[3] = (Button) testPage.findViewById(R.id.ans4);


        currentQuiz = Dictionary.quiz();

        String wordd = currentQuiz[0].getWord();
        String answer = currentQuiz[0].getTranslation();

        str = new String[4];
        for (int i = 0; i <4; i++) {
            str[i] = currentQuiz[i].getTranslation();
        }

        index = (int) (Math.random()*3);

        String tmp = str[index];
        str[index] = str[0];
        str[0] = tmp;

        index =0;
         for(int i=0; i<str.length; i++){
             if(str[i].equals(answer))
                 index = i;
         }

        word.setText(wordd);
        buttons[0].setText(str[0]);
        buttons[0].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        buttons[0].setBackgroundColor(Color.WHITE);
//        buttons[0].setTextColor(Color.BLACK);
        buttons[1].setText(str[1]);
        buttons[1].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        buttons[1].setBackgroundColor(Color.WHITE);
//        buttons[1].setTextColor(Color.BLACK);
        buttons[2].setText(str[2]);
        buttons[2].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        buttons[2].setTextColor(Color.BLACK);
//        buttons[2].setBackgroundColor(Color.WHITE);
        buttons[3].setText(str[3]);
        buttons[3].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        buttons[3].setTextColor(Color.BLACK);
//        buttons[3].setBackgroundColor(Color.WHITE);

        av = true;

    }
    Button[] buttons;
    Word[] currentQuiz;
    int index;
    String[] str;
    boolean av;
    void answered(View view){
        if(!av) return;


        final View v = view;


        av = false;

        buttons[0] = (Button) testPage.findViewById(R.id.ans1);
        buttons[1] = (Button) testPage.findViewById(R.id.ans2);
        buttons[2] = (Button) testPage.findViewById(R.id.ans3);
        buttons[3] = (Button) testPage.findViewById(R.id.ans4);
        Button b = (Button)v;
        String stri = b.getText().toString();
         if(!str[index].equals(stri)){
             b.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
         }
        buttons[index].getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);

         Thread t = new Thread(){
             @Override
             public void run(){
                 try {
                     Thread.currentThread().sleep(300);
                 }catch (Exception e){}

                 runOnUiThread(new Runnable() {

                     @Override
                     public void run() {

                         loadQuiz();

                     }
                 });

             }
         };
         t.start();




    }






    void addWordToRow(String word, String tran){
        addToFile(word, tran);
        Dictionary.addWord(word, tran);
        Log.d("DEBUG",Dictionary.size()+"");
        LinearLayout items = (LinearLayout) findViewById(R.id.itemContainer);

        final View child = getLayoutInflater().inflate(R.layout.row, null);

        TextView f = (TextView) child.findViewById(R.id.word);
        TextView s = (TextView) child.findViewById(R.id.tran);

        f.setText(word);
        s.setText(tran);

        items.addView(child);
    }

    /**
     * change the main frame.
     * @param summ true if switch to summary, false if swith to home screen
     */
    protected void changePage(boolean summ){
        FrameLayout frame = (FrameLayout) findViewById(R.id.content);
        if(summ){
            try {
                frame.removeView(homepage);
                frame.addView(testPage);
                loadQuiz();
            }catch (Exception e){e.printStackTrace();}
        }
        if(!summ){
            try{
                frame.removeView(testPage);
                frame.addView(homepage);
            }catch (Exception e){e.printStackTrace();}
        }
    }

    View homepage =null;
    View testPage =null;


    /**
     * build the Summary frame and homepage frame. only on create
     */
    protected void bulidPage(){
        if(testPage == null) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            testPage = inflater.inflate(R.layout.question,
                    (ViewGroup) findViewById(R.id.quiz ));
        }
        if(homepage == null) {
            LayoutInflater inflater = (LayoutInflater)      this.getSystemService(LAYOUT_INFLATER_SERVICE);
            homepage = inflater.inflate(R.layout.activity_words,
                    (ViewGroup) findViewById(R.id.wordsLayout));
        }
    }



}
