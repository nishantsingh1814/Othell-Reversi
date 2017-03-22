package com.example.nish.othello;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mainLayout;
    LinearLayout squareLayout;
    LinearLayout rows[];
    MyButton buttons[][];
    static boolean gameOver;
    static int whiteScore;
    static int blackScore;
    TextView textViewWhite;
    TextView textViewBlack;

    final static int PLAYER1=1;
    final static int PLAYER2=2;
    final static int NO_PLAYER=0;

    final static int PLAYER_1_WIN=-1;
    final static int PLAYER_2_WIN=-2;
    final static int INCOMPLETE=-3;
    final static int DRAW=-4;
    static boolean player1Turn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout=(LinearLayout)findViewById(R.id.activity_main);
        squareLayout=(LinearLayout)findViewById(R.id.squareLayout);
        textViewBlack=(TextView)findViewById(R.id.textViewBlack);
        textViewWhite=(TextView)findViewById(R.id.textViewWhite);

        setUpBoard();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.newGame) {
            setUpBoard();
        }
        return true;
    }

    public void setUpBoard(){
        rows=new LinearLayout[8];
        buttons=new MyButton[8][8];
        player1Turn=true;
        gameOver=false;
        squareLayout.removeAllViews();

        for(int i=0;i<8;i++){
            rows[i]=new LinearLayout(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            squareLayout.addView(rows[i]);
        }
        for(int i=0;i<8;i++){
            int temp;
            if(i%2==0){
                temp=0;
            }
            else{
                temp=1;
            }
            for(int j=0;j<8;j++){
                buttons[i][j]=new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                buttons[i][j].setPadding(10, 10, 10, 10);
                buttons[i][j].setTextSize(30);
                buttons[i][j].setRow(i);
                buttons[i][j].setCol(j);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setPlayer(NO_PLAYER);
                buttons[i][j].setOnClickListener(this);
                if(temp%2==0){
                    int black= Color.parseColor("#054205");
                    buttons[i][j].setBackgroundColor(black);
                    temp++;
                }
                else{
                    int white= Color.parseColor("#219621");
                    buttons[i][j].setBackgroundColor(white);
                    temp++;
                }
                rows[i].addView(buttons[i][j]);
            }

        }
        buttons[3][4].setPlayer(PLAYER1);
        buttons[4][3].setPlayer(PLAYER1);
        buttons[3][3].setPlayer(PLAYER2);
        buttons[4][4].setPlayer(PLAYER2);

        buttons[3][4].setBackgroundResource(R.drawable.black);
        buttons[4][3].setBackgroundResource(R.drawable.black);
        buttons[3][3].setBackgroundResource(R.drawable.white);
        buttons[4][4].setBackgroundResource(R.drawable.white);
        whiteScore=2;
        blackScore=2;
        textViewBlack.setTextSize(20);
        textViewWhite.setTextSize(20);
        textViewWhite.setText("WHITE= "+whiteScore);
        textViewBlack.setText("BLACK= "+blackScore);

        showHints(PLAYER1,PLAYER2);


    }

    public ArrayList getHints(int current, int next){
        ArrayList<Coordinate> hint=new ArrayList<>();
        for(int i=0;i<8;i++) {
            for (int j=0;j<8;j++){
                if (buttons[i][j].getPlayer()==next){
                    int m=i,n=j;
                    if (i-1>=0&&j-1>=0&&(buttons[i-1][j-1].getPlayer()==NO_PLAYER||buttons[i-1][j-1].getText()=="*")){
                        while(i<7&&j<7&&buttons[i][j].getPlayer()==next){
                            i++;
                            j++;
                        }
                        if(i<=7&&j<=7&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m-1,n-1));
                        }
                    }
                    i=m;
                    j=n;
                    if(i-1>=0&&(buttons[i-1][j].getPlayer()==NO_PLAYER|| buttons[i-1][j].getText()=="*")){
                        while(i<7&&buttons[i][j].getPlayer()==next) {
                            i++;
                        }
                        if(i<=7&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m-1,j));
                        }
                    }
                    i=m;
                    if(i-1>=0&&j+1<=7&&(buttons[i-1][j+1].getPlayer()==NO_PLAYER||buttons[i-1][j+1].getText()=="*")){
                        while(i<7&&j>0&&buttons[i][j].getPlayer()==next){
                            i++;
                            j--;
                        }
                        if(i<=7&&j>=0&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m - 1, n + 1));
                        }
                    }
                    i=m;
                    j=n;
                    if(j+1<=7&&(buttons[i][j+1].getPlayer()==NO_PLAYER||buttons[i][j+1].getText()=="*")){
                        while (j>0&&buttons[i][j].getPlayer()==next){
                            j--;
                        }
                        if(j>=0&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m, n + 1));
                        }
                    }
                    j=n;
                    if(i+1<=7&&j+1<=7&&(buttons[i+1][j+1].getPlayer()==NO_PLAYER||buttons[i+1][j+1].getText()=="*")){
                        while (i>0&&j>0&&buttons[i][j].getPlayer()==next){
                            i--;
                            j--;
                        }
                        if (i>=0&&j>=0&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m+1,n+1));
                        }
                    }
                    i=m;
                    j=n;
                    if(i+1<=7&&(buttons[i+1][j].getPlayer()==NO_PLAYER||buttons[i+1][j].getText()=="*")) {
                        while (i>0&&buttons[i][j].getPlayer()==next) {
                            i--;
                        }
                        if (i>=0&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m+1,n));
                        }
                    }
                    i=m;
                    if(i+1<=7&&j-1>=0&&(buttons[i+1][j-1].getPlayer()==NO_PLAYER||buttons[i+1][j-1].getText()=="*")){
                        while(i>0&&j<7&&buttons[i][j].getPlayer()==next){
                            i--;
                            j++;
                        }
                        if(i>=0&&j<=7&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m+1,n-1));
                        }
                    }
                    i=m;
                    j=n;
                    if(j-1>=0&&(buttons[i][j-1].getPlayer()==NO_PLAYER||buttons[i][j-1].getText()=="*")){
                        while(j<7&&buttons[i][j].getPlayer()==next){
                            j++;
                        }
                        if (j<=7&&buttons[i][j].getPlayer()==current){
                            hint.add(new Coordinate(m,n-1));
                        }
                    }
                    j=n;
                }
            }
        }
        return hint;
    }

    public boolean showHints(int current,int next){
        ArrayList<Coordinate> hint=getHints(current,next);
        if(hint.size()==0){
            player1Turn=!player1Turn;
            Toast.makeText(this,"No Possible Moves!! Passed",Toast.LENGTH_SHORT).show();
            return false;
        }
        int red=Color.parseColor("#000000");
        for(Coordinate p:hint){

            buttons[p.getX()][p.getY()].setTextColor(red);
            buttons[p.getX()][p.getY()].setText("*");
        }
        return true;
    }
    public boolean validMove(int current,int next,MyButton b){
        ArrayList<Coordinate> hint=getHints(current,next);

        boolean out=false;

        for(Coordinate p:hint){

            if(p.getX()==b.getRow()&&p.getY()==b.getCol()){

                out=true;
            }
        }

        return out;
    }
    public void clearHints(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(buttons[i][j].getText()=="*"){
                    buttons[i][j].setText("");
                }
            }
        }
    }

    public void flip(int row,int col,int current,int next,int currentSym){
        int i=row;
        int j=col;

        if(i-1>=0&&j-1>=0&&buttons[i-1][j-1].getPlayer()==next){
            i--;
            j--;
            while(i>0&&j>0&&buttons[i][j].getPlayer()==next){
                i--;
                j--;
            }
            if(i>=0&&j>=0&&buttons[i][j].getPlayer()==current){
                i++;
                j++;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);;
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }

                    i++;
                    j++;
                }
            }
        }
        i=row;
        j=col;
        if(i-1>=0&&buttons[i-1][j].getPlayer()==next){
            i--;
            while(i>0&&buttons[i][j].getPlayer()==next){
                i--;
            }
            if(i>=0&&buttons[i][j].getPlayer()==current){
                i++;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);;
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    i++;
                }
            }
        }
        i=row;
        j=col;
        if(i-1>=0&&j+1<=7&&buttons[i-1][j+1].getPlayer()==next){
            i--;
            j++;
            while(i>0&&j<7&&buttons[i][j].getPlayer()==next){
                i--;
                j++;
            }
            if(i>=0&&j<=7&&buttons[i][j].getPlayer()==current){
                i++;
                j--;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    i++;
                    j--;
                }
            }
        }
        i=row;
        j=col;
        if(j+1<=7&&buttons[i][j+1].getPlayer()==next){
            j++;
            while(j<7&&buttons[i][j].getPlayer()==next){
                j++;
            }
            if(j<=7&&buttons[i][j].getPlayer()==current){
                j--;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    j--;
                }
            }
        }
        i=row;
        j=col;
        if(i+1<=7&&j+1<=7&&buttons[i+1][j+1].getPlayer()==next){
            i++;
            j++;
            while(i<7&&j<7&&buttons[i][j].getPlayer()==next){
                i++;
                j++;
            }
            if(i<=7&&j<=7&&buttons[i][j].getPlayer()==current){
                i--;
                j--;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    i--;
                    j--;
                }
            }
        }
        i=row;
        j=col;
        if(i+1<=7&&buttons[i+1][j].getPlayer()==next){
            i++;
            while(i<7&&buttons[i][j].getPlayer()==next){
                i++;
            }
            if(i<=7&&buttons[i][j].getPlayer()==current){
                i--;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    i--;
                }
            }
        }
        i=row;
        j=col;
        if(i+1<=7&&j-1>=0&&buttons[i+1][j-1].getPlayer()==next){
            i++;
            j--;
            while(i<7&&j>0&&buttons[i][j].getPlayer()==next){
                i++;
                j--;
            }
            if(i<=7&&j>=0&&buttons[i][j].getPlayer()==current){
                i--;
                j++;
                while(buttons[i][j].getPlayer()==next){

                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    i--;
                    j++;
                }
            }
        }
        i=row;
        j=col;
        if(j-1>=0&&buttons[i][j-1].getPlayer()==next){
            j--;
            while(j>0&&buttons[i][j].getPlayer()==next){
                j--;
            }
            if(j<=7&&buttons[i][j].getPlayer()==current){
                j++;
                while(buttons[i][j].getPlayer()==next){
                    buttons[i][j].setPlayer(current);
                    buttons[i][j].setBackgroundResource(currentSym);
                    if(current==PLAYER1){
                        blackScore++;
                        whiteScore--;
                    }
                    else{
                        whiteScore++;
                        blackScore--;
                    }
                    j++;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        MyButton b=(MyButton) v;
        if(gameOver){
            return;
        }
        if(player1Turn){
            if(!validMove(PLAYER1,PLAYER2,b)){
                Toast.makeText(this,"Invalid Move",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            if(!validMove(PLAYER2,PLAYER1,b)){
                Toast.makeText(this,"Invalid Move",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        /*if(b.getPlayer()!=NO_PLAYER){

            return;
        }*/

        if(player1Turn){
            b.setPlayer(PLAYER1);
            b.setBackgroundResource(R.drawable.black);
            blackScore++;
            flip(b.getRow(),b.getCol(),PLAYER1,PLAYER2,R.drawable.black);
        }
        else{
            b.setPlayer(PLAYER2);
            b.setBackgroundResource(R.drawable.white);
            whiteScore++;
            flip(b.getRow(),b.getCol(),PLAYER2,PLAYER1,R.drawable.white);
        }



        textViewWhite.setText("WHITE= "+whiteScore);
        textViewBlack.setText("BLACK= "+blackScore);

        clearHints();

        int status=gameStatus();
        if(status==PLAYER_1_WIN){
            Toast.makeText(this,"BLACK WINS",Toast.LENGTH_SHORT).show();
            gameOver=true;
            return;
        }
        else if(status==PLAYER_2_WIN){
            Toast.makeText(this,"WHITE WINS",Toast.LENGTH_SHORT).show();
            gameOver=true;
            return;
        }
        else if(status==DRAW){
            Toast.makeText(this,"GAME DRAW",Toast.LENGTH_SHORT).show();
            gameOver=true;
            return;
        }

        player1Turn=!player1Turn;

        if(player1Turn){
            if(!showHints(PLAYER1,PLAYER2)){
                if(!showHints(PLAYER2,PLAYER1)){
                    int blockStatus=gameStatus();
                    if(blockStatus==PLAYER_1_WIN){
                        Toast.makeText(this,"BLACK WINS",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                    else if(blockStatus==PLAYER_2_WIN){
                        Toast.makeText(this,"WHITE WINS",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                    else if(blockStatus==DRAW){
                        Toast.makeText(this,"GAME DRAW",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                }
            }
        }
        else{
            if(!showHints(PLAYER2,PLAYER1)){
                if(!showHints(PLAYER1,PLAYER2)){
                    int blockStatus=gameStatusBlock();
                    if(blockStatus==PLAYER_1_WIN){
                        Toast.makeText(this,"BLACK WINS",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                    else if(blockStatus==PLAYER_2_WIN){
                        Toast.makeText(this,"WHITE WINS",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                    else if(blockStatus==DRAW){
                        Toast.makeText(this,"GAME DRAW",Toast.LENGTH_SHORT).show();
                        gameOver=true;
                        return;
                    }
                }
            }
        }
    }
    public int gameStatus(){

        if(whiteScore==0){
            return PLAYER_1_WIN;
        }
        else if(blackScore==0){
            return PLAYER_2_WIN;
        }

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(buttons[i][j].getPlayer()==NO_PLAYER||buttons[i][j].getText()=="*"){
                    return INCOMPLETE;
                }
            }
        }
        if(whiteScore>blackScore){
            return PLAYER_2_WIN;
        }
        else if(blackScore>whiteScore){
            return PLAYER_1_WIN;
        }
        else {
            return DRAW;
        }
    }
    public int gameStatusBlock(){
        if(whiteScore>blackScore){
            return PLAYER_2_WIN;
        }
        else if(blackScore>whiteScore){
            return PLAYER_1_WIN;
        }
        else {
            return DRAW;
        }
    }
}
