package com.proj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainActivity2 extends AppCompatActivity {
    private class Cell{
        int val;
        boolean fixed;
        Button bt;

        public Cell(int initvalue, Context THIS){
            val = initvalue;
            if (val!= 0){
                fixed = true;
            }else{
                fixed =false;

            }
            bt = new Button(THIS);
            if (fixed){ //works because each button has its own fixed value attributed to it initialised values are set to true and can be changed
                bt.setTextColor(Color.BLUE);
                bt.setText(String.valueOf(val));

            }else{
                bt.setTextColor(Color.BLACK);
            }
            bt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (fixed != false) return; //value is not what is
                    val++; //increases the value until the user is satisfied
                    if (val> 9){ //units above 9 revert to one
                        val = 1;
                    }
                    bt.setText(String.valueOf(val));//toString
                    if (IsComplete() == true){
                        t.setText("");
                        if (check() == true){
                            t.setText("Congratualtions the board has been solved");
                        }else{
                            t.setText("There is a repeated digit");
                        }
                    }else{
                        t.setText("Puzzle Remains incomplete");
                    }
                }
            });


            }

    }

    Cell[][] table;
    String inp;
    TableLayout tl;
    LinearLayout lay;
    TextView t;
    Button chng;
    boolean boardchange = false;

    boolean IsComplete(){
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){

                if (table[x][y].val==0){
                    return false;
                }

            }

        }
        return true;//returns at the end of checking if there is no 0's
    }

    boolean correctrs(int x1, int x2, int y1, int y2){
        boolean[] check = new boolean[10];

        for (int x = 1; x <=9; x++){
            check[x] = false;
        }
        for (int i = x1; i < x2; i++){
            for (int j = y1; j < y2; j++){
                int z = table[i][j].val;
                if (z != 0){
                    if (check[z] == false) {
                        return false;
                    }
                    check[z] = true;
                }

            }


        }
        return true;
    }

    Boolean check(){

        for (int x =0; x<9; x++){
            if (correctrs(x, 0, x, 9) == false){
                return false;
            }
            for (int y = 0; y < 9; y++){
                if (correctrs(0, y, 9, y) == false) {return false;}
            }
        }
        for (int x = 0; x< 3; x++){
            for (int y = 0; y<3;y++){
                if (correctrs(3*x, 3*y, 3*x+3, 3*y+3) == false){
                    return false;
                }
            }
        }
        return true;
    }
    //}

    //s}




    @Override
    protected void onCreate(Bundle savedInstanceState) { //the idea of button creation and table was provided by
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

         boardgen(boardchange);
         boardchange = true;
        chng.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardgen(boardchange);
                if (boardchange){
                    boardchange = false;
                }
                if (!boardchange){
                    boardchange = true;
                }
            }
        });

         //ActionBar actionBar = getActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);

        //inp =txtboard();


    }

    public void boardgen(boolean board){
        String inp;
        if (board == false){
        inp ="702000580"+
                "000000706"+
                "000000020"+
                "059280603"+
                "060009008"+
                "208050001"+
                "000008000"+
                "390001047"+
                "070900000";}
        else{
            inp ="040000179"+
                    "002008054"+
                    "006005008"+
                    "080070910"+
                    "050090030"+
                    "019060040"+
                    "300400700"+
                    "570100200"+
                    "928000060";}


        String[] indob = new String[86]; //creates and array to store String values for the board pieces
        for (int i = 0; i< 81; i++){
            indob[i] = ""+ inp.charAt(i);
        }
        //String[] split = inp.split(" ");
        table = new Cell[9][9];
        tl = new TableLayout(this);
        for (int x = 0; x<9; x++){
            TableRow tr = new TableRow(this);
            for (int y = 0; y<9; y++){

                //String s= split[x*9+y];
                String s = indob[x*9+y];
                Character c = s.charAt(0);
                int initval = Character.getNumericValue(c);
                table[x][y] = new Cell(initval, this);
                tr.addView(table[x][y].bt);
            }
            tl.addView(tr);
        }
        tl.setShrinkAllColumns(true); //both methods used to shrink or stretch the buttons to match screen size
        tl.setStretchAllColumns(true);
        t = new TextView(this);
        chng = new Button(this);
        chng.setText("change board");
        lay = new LinearLayout(this);

        lay.addView(tl);
        lay.addView(t);
        lay.addView(chng);
        lay.setOrientation(LinearLayout.VERTICAL);

        setContentView(lay);
    }

    // Board from textfile errors occured which ruined initial programing and redoing of project so this idea is scrapped for time being
    public static String txtboard(){ //converts the textfile puzzle to an array for a new board
        String txboard = "";

        try {
            File myObj = new File("puzzle.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();


                txboard+= data;


            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred. File has either been damaged or cannot be found");
            e.printStackTrace();
        }

        return txboard;
    }
}