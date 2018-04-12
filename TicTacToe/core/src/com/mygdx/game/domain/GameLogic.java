package com.mygdx.game.domain;

/**
 * Created by KasperKBerg on 12.04.2018.
 */

public class GameLogic {


    private char[][] brett;
    private int amountToWin;
    private boolean hasWinner;
    private char winner = '-';

    public GameLogic(char[][] brett, int amountToWin){
        this.brett=brett;
        this.amountToWin=amountToWin;
    }

    public void update(float dt){

    }

    public char getWinner() {
        return winner;
    }

    public void setAmountToWin(int amountToWin) {
        this.amountToWin = amountToWin;
    }

    public void setBrett(char[][] brett) {
        this.brett = brett;
    }

    public char[][] getBrett() {
        return brett;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public void clearBoard(char[][] board){
        for(int i = 0; i<brett.length; i++){
            for(int j = 0; j<brett[i].length; j++){
                brett[i][j]='-';
            }
        }
    }

    public void setTile(int row, int col,char player){
        brett[brett.length-row-1][col] = player;
    }

    public String printBoard(char[][] matrix){
        String midBoard = "\n";
        for (int row = 0; row < matrix.length; row++){
            for (int column = 0; column < matrix.length; column++){
                if(column ==0){
                    midBoard+=" ";
                }
                midBoard += brett[row][column];
                if(column !=(matrix.length-1)){
                    midBoard+= " | ";
                }
            }
            midBoard += " \n";
            if( row != (matrix.length-1)){
                for (int k = 0; k<matrix.length;k++){
                    midBoard += "------";
                }
            }
            midBoard += "\n";
        }
        return midBoard;
    }

    private char isWinner(char[][] board,int amountToWin){
        char[][] brett1 = board;
        char winner = '-';

        //Checking rows
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j<board[0].length-2;j++){
                if(board[i][0+j]==board[i][1+j] && board[i][0+j]==board[i][2+j] && board[i][0+j]!='-'){
                    return board[i][0+j];
                }
            }
        }
        //Checking columns
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j<board.length-2;j++){
                if(board[0+j][i]==board[1+j][i] && board[0+j][i]==board[2+j][i] && board[0+j][i]!='-'){
                    return board[0+j][i];
                }
            }
        }

        //Checking diagonal \
        for (int i = 0; i<board.length-2;i++){
            int teller = 0;
            if(board[0+i][0+i] == board[1+i][1+i] && board[0+i][0+i] == board[2+i][2+i] && board[0+i][0+i] != '-'){
                return board[0+i][0+i];

                //teller ++;
            }
        }

        //Checking diagonal /
        for (int i = 0; i<board.length-2;i++){
            if(board[0+i][2+i] == board[1+i][1+i] && board[0+i][2+i] == board[2+i][0+i] && board[0+i][2+i] != '-'){
                return board[0+i][2+i];
            }
        }
        return '-';
    }

    public static boolean checkForWinner( char [][] matrix , int amountToWin)
    {

    /* We traverse each element in the matrix */



        for( int row = 0; row < matrix.length; row++ )
        {
            for( int col = 0; col < matrix[row].length; col++ )
            {

                // This is the current element in our matrix
                int element = matrix[row][col];


                // 3 in a row to win

                if (amountToWin==3){
                    //Checking rows
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j<matrix[0].length-2;j++){
                            if(matrix[i][0+j]==matrix[i][1+j] && matrix[i][0+j]==matrix[i][2+j] && matrix[i][0+j]!='-'){
                                return true;
                            }
                        }
                    }
                    //Checking columns
                    for (int i = 0; i < matrix[0].length; i++) {
                        for (int j = 0; j<matrix.length-2;j++){
                            if(matrix[0+j][i]==matrix[1+j][i] && matrix[0+j][i]==matrix[2+j][i] && matrix[0+j][i]!='-'){
                                return true;
                            }
                        }
                    }
                }


                // 4 in a row to win


                else if (amountToWin==4){
                    /* If there are 3 elements remaining to the right of the current element's
           position and the current element equals each of them, then return true */
                    if( col <= matrix[row].length-amountToWin && element == matrix[row][col+1] && element == matrix[row][col+2] && element == matrix[row][col+3] && element != '-')
                        return true;

        /* If there are 3 elements remaining below the current element's position
           and the current element equals each of them, then return true */
                    if( row <= matrix.length - amountToWin && element == matrix[row+1][col] && element == matrix[row+2][col] && element == matrix[row+3][col] && element != '-')
                    {
                        return true;
                    }


        /* If we are in a position in the matrix such that there are diagonals
           remaining to the bottom right of the current element, then we check */
                    if( row <= matrix.length-amountToWin && col <= matrix[row].length-amountToWin )
                    {
                        // If the current element equals each element diagonally to the bottom right
                        if( element == matrix[row+1][col+1] && element == matrix[row+2][col+2] && element == matrix[row+3][col+3] && element != '-')
                            return true;
                    }


        /* If we are in a position in the matrix such that there are diagonals
           remaining to the bottom left of the current element, then we check */
                    if( row <= matrix.length-amountToWin && col >= matrix[row].length-amountToWin )
                    {
                        // If the current element equals each element diagonally to the bottom left
                        if( element == matrix[row+1][col-1] && element == matrix[row+2][col-2] && element == matrix[row+3][col-3] && element != '-')
                            return true;
                    }
                }
            }
        }

    /* If all the previous return statements failed, then we found no such
       patterns of four identical elements in this matrix, so we return false */
        return false;
    }


    //enum char{'-', 'X', 'O'};

    int n = amountToWin;
    int moveCount;

    public void Move(int x, int y, char c){
        if(brett[x][y] == '-'){
            brett[x][y] = c;
        }
        moveCount++;

        //check end conditions

        //check col
        for(int i = 0; i < n; i++){
            if(brett[x][i] != c)
                break;
            if(i == n-1){
                hasWinner = true;
                winner = c;
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(brett[i][y] != c)
                break;
            if(i == n-1){
                hasWinner = true;
                winner = c;
            }
        }

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(brett[i][i] != c)
                    break;
                if(i == n-1){
                    hasWinner = true;
                    winner = c;
                }
            }
        }

        //check anti diag (thanks rampion)
        if(x + y == n - 1){
            for(int i = 0; i < n; i++){
                if(brett[i][(n-1)-i] != c)
                    break;
                if(i == n-1){
                    hasWinner = true;
                    winner = c;
                }
            }
        }

        //check draw
        if(moveCount == (Math.pow(n, 2) - 1)){
            hasWinner = false;
            winner = 'D';
            System.out.println("DRAWWWWWWWW");
        }
    }


}
