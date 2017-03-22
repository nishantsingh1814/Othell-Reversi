package com.example.nish.othello;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Nishant on 2/1/2017.
 */

public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }
    private int player;
    private int row;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private int col;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {

        this.player = player;
        if(player==1) {
            int black= Color.parseColor("#000000");
            setBackgroundResource(R.drawable.black);
        }
        else if(player==2){
            int white= Color.parseColor("#ffffff");
            setBackgroundResource(R.drawable.black);
        }
    }
}
