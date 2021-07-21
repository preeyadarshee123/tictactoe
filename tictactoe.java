import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
class tictactoe{
    public static void main(String []args){
        ReentrantLock rel = new ReentrantLock();
        for(int i = 0;i < 2;i++){
            game obj = new game(rel);
            obj.start();
        }
        
    }
}

class game extends Thread{
    public char [][] board = new char [3][3];
    public static HashMap<String, Integer> ranklist;
    public String player1,player2;
    public int toss_winner;
    public ReentrantLock r1;
    public game(ReentrantLock rel){
        System.out.println();
        System.out.println();
        r1 = rel;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                board[i][j] = '_';
            }
        }
    }
    public  void  registerPlayers(){
        r1.lock();
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Player1's Name :");
        player1 = input.nextLine();
        System.out.println("Enter Player2's Name :");
        player2 = input.nextLine();
        r1.unlock();
    }
    public void toss(){
        r1.lock();
        System.out.println();
        Scanner input = new Scanner(System.in);
        System.out.println("Press Enter to randomly find who will move first");
        input.nextLine();
        Random rand = new Random();
        toss_winner = rand.nextInt(1000)%2;
        if(toss_winner == 0){
            System.out.println(player1 + " won the toss. " + player1  + " will move first");
        }
        else{
            System.out.println(player2 + " won the toss. " + player2  + " will move first");
        }
        r1.unlock();
    }
    public void showRules(){
        r1.lock();
        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Rules of the game : - ");
        System.out.println("1. Players can only move one per chance and then they will alternate moves.");
        System.out.println("2. Enter number of a cell to mark your move in that cell.");
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 6;j++){
                System.out.print(" ");
            }
            for(int j = 1;j <= 3;j++){
                System.out.print(i*3 + j + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println();
        r1.unlock();
    }
    public void showBoardState(){
        System.out.println();
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public void showRanklist(){
        System.out.println("Name   :   Score");
        for(String s : ranklist.keySet()){
            System.out.println(s + "   :   " + ranklist.get(s));
        }
    }
    public void gameplay(){
        Scanner input = new Scanner(System.in);
        r1.lock();
        System.out.println("Press Enter to start the game");
        input.nextLine();
        int game_state = 0;
        for(int i = 0;i < 9;i++){
            if(i != 0){
                r1.lock();
            }
            showBoardState();
            if(toss_winner == 0){
                if(i%2 == 0){
                    int flag = 1;
                    while(flag == 1){
                        System.out.println(player1 + " , Enter cell number to mark : ");
                        int cell = input.nextInt();
                        cell--;
                        int row = cell/3;
                        int col = cell%3;
                        if(board[row][col] == '_'){
                            board[row][col] = 'x';
                            flag = 0;
                        }
                        else{
                            System.out.println("Invalid Move. Cell was already marked. ");
                            System.out.println("Try Again.");
                        }
                    }
                    if(check() == true){
                        System.out.println(player1 + "won :D");
                        game_state = 1;
                        break;
                    }
                }
                else{
                    int flag = 1;
                    while(flag == 1){
                        System.out.println(player2 + " , Enter cell number to mark : ");
                        int cell = input.nextInt();
                        cell--;
                        int row = cell/3;
                        int col = cell%3;
                        if(board[row][col] == '_'){
                            board[row][col] = 'o';
                            flag = 0;
                        }
                        else{
                            System.out.println("Invalid Move. Cell was already marked.");
                            System.out.println("Try Again.");
                        }
                    }
                    if(check() == true){
                        System.out.println(player2 + "won :D");
                        game_state = 1;
                        break;
                    }
                }
            }
            else{
                if(i%2 == 0){
                    int flag = 1;
                    while(flag == 1){
                        System.out.println(player2 + " , Enter cell number to mark : ");
                        int cell = input.nextInt();
                        cell--;
                        int row = cell/3;
                        int col = cell%3;
                        if(board[row][col] == '_'){
                            board[row][col] = 'x';
                            flag = 0;
                        }
                        else{
                            System.out.println("Invalid Move. Cell was already marked. ");
                            System.out.println("Try Again.");
                        }
                    }
                    if(check() == true){
                        System.out.println(player2 + "won :D");
                        game_state = 1;
                        break;
                    }
                }
                else{
                    int flag = 1;
                    while(flag == 1){
                        System.out.println(player1 + " , Enter cell number to mark : ");
                        int cell = input.nextInt();
                        cell--;
                        int row = cell/3;
                        int col = cell%3;
                        if(board[row][col] == '_'){
                            board[row][col] = 'o';
                            flag = 0;
                        }
                        else{
                            System.out.println("Invalid Move. Cell was already marked. ");
                            System.out.println("Try Again.");
                        }
                    }
                    if(check() == true){
                        System.out.println(player1 + " won :D");
                        game_state = 1;
                        break;
                    }
                }
            }
            r1.unlock();
        }
        if(game_state == 0){
            System.out.println("Its a tie");
        }
    }
    public boolean check(){
        for(int i = 0;i < 3;i++){
            char ch1 = board[i][0],ch2 = board[0][i];
            boolean flag1 = true,flag2 = true;
            if(ch1 == '_'){
                flag1 = false;
            }
            if(ch2 == '_'){
                flag2 = false;
            }
            for(int j = 0;j < 3;j++){
                if(board[i][j] != ch1){
                    flag1 = false;
                }
                if(board[j][i] != ch2){
                    flag2 = false;
                }
            }
            if(flag1 == true || flag2 == true){
                return true;
            }
        }
        boolean flag3 = true,flag4 = true;
        char ch3 = board[0][0],ch4 = board[0][2];
        if(ch3 == '_'){
            flag3 = false;
        }
        if(ch4 == '_'){
            flag4 = false;
        }
        for(int i = 0;i < 3;i++){
            if(board[i][i] != ch3){
                flag3 = false;
            }
            if(board[i][2-i] != ch4){
                flag4 = false;
            }
        }
        if(flag3 == true || flag4 == true){
            return true;
        }
        return false;
    }
    public void run(){
        registerPlayers();
        toss();
        showRules();
        gameplay();
    }
}

