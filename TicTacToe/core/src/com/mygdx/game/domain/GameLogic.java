package com.mygdx.game.domain;

/**
 * Created by KasperKBerg on 12.04.2018.
 */

public class GameLogic {


    private char[][] board;
    private int n;
    private boolean hasWinner;
    private char winner = '-';


    public GameLogic(int n){
        this.board=new char[n][n];
        this.n=n;
        clearBoard();
    }

    public void expandBoard(){
        this.n++;
        char[][] newBoard = new char[n][n];
        for(int i = 0; i<newBoard.length; i++){
            for(int j = 0; j<newBoard[i].length; j++){
                newBoard[i][j]='-';
            }
        }
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                newBoard[i+1][j] = board[i][j];
            }
        }
        this.board = newBoard;
        System.out.println(printBoard());
    }


    public void clearBoard(){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                board[i][j]='-';
            }
        }
    }

    public void update(float dt){

    }

    public int getN(){
        return n;
    }

    public char getWinner() {
        return winner;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public String printBoard(){
        char[][] matrix = board;
        String midBoard = "\n";
        for (int row = 0; row < matrix.length; row++){
            for (int column = 0; column < matrix.length; column++){
                if(column ==0){
                    midBoard+=" ";
                }
                midBoard += board[row][column];
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

    private int moveCount;

    public void setZeroMoveCount(){
        moveCount=0;
    }


    //Place move and check for end conditions

    public void Move(int x, int y, char c){
        if(board[board.length-1-x][y] == '-'){
            board[board.length-1-x][y] = c;
            moveCount++;
        }
        //check end conditions
        //check col
        for (int i = 0; i < n; i++){
            if(board[i][y] != c)
                break;
            if(i == n-1){
                hasWinner = true;
                winner = c;
                System.out.println("Winning by col");
            }
        }
        //check row
        for(int i = 0; i < n; i++){
            if(board[n-1-x][i] != c)
                break;
            if(i == n-1){
                hasWinner = true;
                winner = c;
                System.out.println("Winning by row");
            }
        }

        //Checking diagonals

        //anti diag

        if(x + y == n - 1){
            for(int i = 0; i < n; i++){
                if(board[i][i] != c)
                    break;
                if(i == n-1){
                    hasWinner = true;
                    winner = c;
                    System.out.println("Winning by anti diag");
                }
            }
        }


        //diag
        if(x == y){
            for(int i = 0; i < n; i++){
                if(board[i][(n-1)-i] != c)
                    break;
                if(i == n-1){
                    hasWinner = true;
                    winner = c;
                    System.out.println("Winning by diag");
                }
            }
        }


        //check draw
        else if(moveCount == (Math.pow(n, 2))){
            hasWinner = false;
            winner = 'D';
        }
    }

    public int getMoveCount() {
        return moveCount;
    }
}
