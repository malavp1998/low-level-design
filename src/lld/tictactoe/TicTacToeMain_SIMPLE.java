package lld.tictactoe;

import java.util.Currency;
import java.util.List;
import java.util.Scanner;

/**
 * ====== START HERE — run this file ======
 *
 *   ./run.sh tictactoe
 *
 * The driver below is already written. Your job is to fill the TODOs in
 * Board.java, RowColumnDiagonalStrategy.java and Game.java until this prints
 * a real game where X wins on the top row.
 */
public class TicTacToeMain_SIMPLE {


    public static void printBoard(char board[][])
    {

        for(int i =0; i<board.length; i++)
        {
            for(int j =0; j<board[i].length; j++)
            {
                if((board[i][j]) == 0)
                {
                    System.out.print(" "+ "."+" "); 
                }
                else{
                System.out.print(" "+board[i][j]+" ");
                }
            }
            System.out.println();
        }

    }


    public static boolean checkIfUserInputisValid(int rowIndex, int colIndex, int n , int m )
    {
        if(rowIndex>=0 && rowIndex<n && colIndex>=0 && colIndex < m)
        {
            return true;
        }
        return false;
    }

    public static boolean checkifUserWins(int rowIndex,int colIndex, char board[][], String currUser)
    {
       
            // check current row
            int i = 1;
            for(i =1 ; i<board.length; i++)
            {
                if(board[rowIndex][i-1] != board[rowIndex][i])
                {
                     break;
                }
            }
            if(i == board.length)
            {
                System.out.println("ROW");
                return true;
            }


            //check current col

            for(i =1 ; i<board.length; i++)
             {
                    if(board[i-1][colIndex] != board[i][colIndex])
                    {
                         break;
                    }
            }
            if(i == board.length)
            {
                System.out.println("COL");

                    return true;
            }


            // check diagonals top to bottom
            if((rowIndex == 0 && colIndex == 0) || (rowIndex == 1 && colIndex == 1) || (rowIndex == 2 && colIndex == 2))
                {
                for(i =1 ; i<board.length; i++)
                    {
                       if(board[i-1][i-1] != board[i][i])
                           {
                                break;
                       }
                   }
                   if(i == board.length)
                   {
                    System.out.println("TB");

                           return true;
                   }

            }


            if((rowIndex == 2 && colIndex == 0) || (rowIndex == 1 && colIndex == 1) || (rowIndex == 0 && colIndex == 2))
                {
                    int j =0;
                    for(i =2 ; i>0; i--)
                    {
                        if(board[i][j] != board[i-1][++j])
                        {
                            break;
                        }
                    }
                    if(i == 0 )
                    {
                        System.out.println("BT");

                        return true;
                    }
                    
             }

             return false;

    }

  
    public static void main(String[] args) {

      char board[][] = new char[3][3];
      Scanner sc = new Scanner(System.in);
      

      String userOne = "P";
      String userTwo = "K";


      boolean isGameDraw = false;
      boolean foundWinner = false;

      String currUser = userOne;

      printBoard(board);



      while(!isGameDraw && !foundWinner)
      {
        System.out.println(currUser +"  "+ "Play and enter the valid x and y where x and y value shold by in ranger[0,2]");
        int rowIndex = sc.nextInt();
        int colIndex = sc.nextInt();

        while(!checkIfUserInputisValid(rowIndex,colIndex,board.length, board[0].length))
        {
            System.out.println("WARNING !!  Plese enter valid input ");
            System.out.println(currUser +"  "+ "Play and enter the valid x and y where x and y value should by in ranger[0,2]");
            rowIndex = sc.nextInt();
            colIndex = sc.nextInt();
        }

        if(currUser == userOne)
        {
            board[rowIndex][colIndex] = 'X';
        }
        else{
            board[rowIndex][colIndex] = 'O';
        }
        printBoard(board);
        foundWinner =  checkifUserWins(rowIndex,colIndex, board,currUser);
        if(foundWinner)
          System.out.println(currUser +" wins the game");

        if(currUser == userOne)
        {
            currUser = userTwo;
        }
        else{
            currUser = userOne;
        }

      }



      




    }
}
