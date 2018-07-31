package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Field to store the result text
    TextView rollResult;
    TextView txtScore;

    //Field to hold the score
    int score;

    //Random generator for the dice
    Random rand;

    //Values for the dice values
    int dice1;
    int dice2;
    int dice3;

    //Array list for the dice values
    ArrayList<Integer> dice;

    //Array lit to hold the die images
    ArrayList<ImageView> diceImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        //Set initial score
        score = 0;

        //Create greeting message
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!", Toast.LENGTH_LONG).show();

        //Assign the widgets to the apps activity views
        rollResult = (TextView) findViewById(R.id.text_rollResult);
        txtScore = (TextView) findViewById(R.id.text_scoreMessage);
        rand = new Random();

        //Create array list for dice values
        dice = new ArrayList<Integer>();

        ImageView die1Image = (ImageView) findViewById(R.id.im_die1);
        ImageView die2Image = (ImageView) findViewById(R.id.im_die2);
        ImageView die3Image = (ImageView) findViewById(R.id.im_die3);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);
    }

    public void rollDice(View v){
        //Roll dice
        dice1 = rand.nextInt(6)+1;
        dice2 = rand.nextInt(6)+1;
        dice3 = rand.nextInt(6)+1;

        //Build array with the dice values
        dice.clear();
        dice.add(dice1); dice.add(dice2); dice.add(dice3);

        String strMessage;
        int dScore=0;
        //Update the score and create score message
        if (dice1 == dice2 && dice1 == dice3) {
            dScore = dice1* 100;
            strMessage = "You rolled triples. you score " + dScore + " points.";
        }else if(dice1 == dice2 || dice1 == dice3 || dice2 == dice3){

            if (dice1 == dice2 || dice1 == dice3) {
                dScore = dice1 * 10;
            }else {
                dScore = dice2 * 10;
            }

            strMessage = "You rolled a pair. You scored " + dScore + " points.";
        }else
            strMessage = "Keep trying.";

        score = score + dScore;
        String strScore = "Score: " + score;

        for (int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "die_"+dice.get(dieOfSet) + ".png";
            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream,null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);

            }   catch (IOException e){
                e.printStackTrace();;
            }

        }

        //Update the app to display the roll message
        rollResult.setText(strMessage);
        txtScore.setText(strScore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
