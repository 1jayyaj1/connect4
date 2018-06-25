package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		// ADD YOUR CODE HERE
        int column = 0;
        if (spaceLeft == true){
            if (available[index] <= 5){
                int row = available[index];
                board[index][row] = player;
                available[index] += 1;
                for (int i = 0; i <= 6; i++){
                    if (available[i] == 6){
                        column += 1;
                    }
                    if (column == 7){
                        spaceLeft = false;
                    }
                }
            }
        }
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		// ADD YOUR CODE HERE
        int count = 0;
        //Vertical check
        for (int i = available[lastColumnPlayed] - 1; i >= 0; i--){
            if (board[lastColumnPlayed][i] == player && board[lastColumnPlayed][i] != 0){
                count += 1;
            } else {
                count = 0;
            }
            if (count >= 4){
                return true;
            }
        }
        count = 0;
        //Horizontal check
        for (int col = 0; col <= 6; col++){
            int row = available[lastColumnPlayed] - 1;
            if (board[col][row] == player && board[col][row] != 0){
                count += 1;
            } else{
                count = 0;
            }
            if (count >= 4){
                return true;
            }
        }
        //Ascending diagonal check
        for (int col = 3; col <= 6; col++){
            for (int row = 0; row < 3; row++){
                if (this.board[col][row] == player && this.board[col - 1][row + 1] == player
                    && this.board[col - 2][row + 2] == player && this.board[col - 3][row + 3] == player)
                    return true;
            }
        }
        //Descending diagonal check
        for (int col = 3; col <= 6; col++) {
            for (int row = 3; row <= 5; row++) {
                if (this.board[col][row] == player && this.board[col - 1][row - 1] == player
                    && this.board[col - 2][row - 2] == player && this.board[col - 3][row - 3] == player)
                    return true;
            }
        }
        return false;
	}
	
	public int canWinNextRound (int player){
		// ADD YOUR CODE HERE
        int temp = -1; //If there is no column that makes a player win next round
        for (int i = 0; i < 7; i++){    //For every column of the game configuration
            int row = this.available[i];
            if (row < 6){    //If their is at least one spot left in a specific column
                this.board[i][row] = player;
                this.available[i] += 1;    //Try setting the player to the next available spot in the current column
                if (this.isWinning(i, player)){    //If the test we just did makes the desired player win the game
                    if (temp == -1){
                        temp = i;    //Save this winning next move
                    } else{
                        if (i < temp){    //If their is multiple winning columns for the next move, save the winning column with the smallest index
                            temp = i;
                        }
                    }
                }
                this.board[i][row] = 0;
                this.available[i] -= 1;   //In all cases, revert the test cases to their original value
            }
        }
        return temp; //Return the column that makes the player win on the next move
	}
	
    public void removeDisk(int index, int player){
        int row = this.available[index] - 1;
        this.board[index][row] = 0;
        (this.available[index])--;
        for (int i = 0; i <= 6; i++){
            if (this.available[i] <= 5){
                this.spaceLeft = true;
            } else
                this.spaceLeft = false;
        }
    }
    
	public int canWinTwoTurns (int player){
		// ADD YOUR CODE HERE
        int count;
        int otherPlayer;
        if (player == 1){
            otherPlayer = 2;
        } else {
            otherPlayer = 1;
        }
        for (int i = 0; i <= 6; i++){
            if (this.available[i] <= 5){
                this.addDisk(i, player);
                if (!this.spaceLeft) {
                    this.removeDisk(i, player);
                    return -1;
                }
                if (this.canWinNextRound(otherPlayer) != -1){
                    return -1;
                }
                for (count = 0; count <= 6; count++){
                    if (this.available[count] <= 5){
                        this.addDisk(count, otherPlayer);
                        if (!this.spaceLeft){
                            this.removeDisk(count, otherPlayer);
                            this.removeDisk(i, player);
                            return -1;
                        }
                        if (this.canWinNextRound(player) == -1){
                            this.removeDisk(count, otherPlayer);
                            break;
                        }
                        this.removeDisk(count, otherPlayer);
                    }
                }
                if (count == 7){
                    this.removeDisk(i, player);
                    return i;
                }
                this.removeDisk(i, player);
            }
        }
        return -1;
	}
	
}
