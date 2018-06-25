package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		// ADD YOUR CODE HERE
        c.print();
        int input = 0;
        while (true){
            System.out.print("Please enter your next move: ");
            try{
                input = Integer.parseInt(keyboard.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Illegal column");
            }
            try{
                if (c.available[input] <= 5){
                    return input;
                } else{
                    System.out.println("Column is full, you can try again!");
                }
            } catch (IndexOutOfBoundsException e){
                System.out.println("Illegal column");
            }
        }
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
        int player1 = 1;
        int column = 0;
        int count = 0;
        if (c.canWinNextRound(player1) != -1){
            column = c.canWinNextRound(player1);
            return column;
        } else if (c.canWinTwoTurns(player1) != -1){
            column = c.canWinTwoTurns(player1);
            return column;
        }
        else{
            while (count <= 6){
                if (columnPlayed2 - count > -1 && c.available[columnPlayed2 - count] <= 5){
                    return (columnPlayed2 - count);
                }
                if (columnPlayed2 + count <= 6 && c.available[columnPlayed2 + count] <= 5){
                    return (columnPlayed2 + count);
                }
                count++;
            }
        }
        return -1;
	}
	
}
