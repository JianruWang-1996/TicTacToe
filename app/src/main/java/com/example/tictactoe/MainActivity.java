package com.example.tictactoe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean playerTurn = true;

    private int roundCount;

    private int player1Point;
    private int player2Point;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        //if it's not empty then do nothing
        if (!((Button) v).getText().toString().equals("")){
            return;
        }

        if (playerTurn){ //set player1 X
            ((Button) v).setText("x");
        }else { // player2 will be O
            ((Button) v).setText("o");
        }

        roundCount++;

        if(checkForWin()) {
            if (playerTurn) {
                player1Wins();
            }else {
                player2Wins();
            }
        }else if (roundCount == 9) {
            draw();
        }else { //if no winner and no draw then swap the player
            playerTurn = !playerTurn;
        }

}

    private  boolean checkForWin() {
        String[][] field = new String[3][3];

        //go through all the buttons and save them to the string array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //check rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        //check columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        //check diagonal line
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Point++;
        Toast.makeText(this, "Player 1 wins!!" , Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void player2Wins() {
        player2Point++;
        Toast.makeText(this, "Player 2 wins!!" , Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!!" , Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private  void updatePointText() {
        textViewPlayer1.setText("Player 1: " + player1Point);
        textViewPlayer2.setText("Player 2: " + player2Point);
    }

    private void resetBoard() {
        //reset whole button to empty when one player is win
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerTurn = true;
    }

    private  void resetGame() {
        player1Point = 0;
        player2Point = 0;
        updatePointText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("Player1Point", player1Point);
        outState.putInt("Player2Point", player2Point);
        outState.putBoolean("PlayerTurn", playerTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Point = savedInstanceState.getInt("player1Point");
        player2Point = savedInstanceState.getInt("player2Point");
        playerTurn = savedInstanceState.getBoolean("playerTurn");
    }
}