//class:SE1B  
//name:Cheung Chun Fung
//student number:180468483
//assignment:Othello
import java.util.*;
public class Othello{
	public static Scanner input = new Scanner(System.in); //Declaration for Scanner
	public static int size =6;
	public static int[][] playBoard= new int[size][size]; //create board size
	public static int directions[][] = { {0,1} , {1, 1} , {1,0}, {1, -1}, {0, -1}, {-1,-1}, {-1,0},{-1,1}}; //8 directions for checking if it is the valid moves
	public static int player1Count = 2, player2Count =2; //for counting number of chess on the board for both player
	public static void main(String [] argv){
		int center;
		center = size / 2; //to set up 2 default moves for each players 
		playBoard[center-1][center-1] = 1;
		playBoard[center-1][center] = 2;
		playBoard[center][center] = 1;
		playBoard[center][center-1] = 2;
		printBoard(playBoard);
		playerTurn();
	}
	public static void playerTurn(){ //input and rules
		int inputI, inputJ;
		int round = 1;
		while(true){
			System.out.printf("\nPlease enter the position of '%d': ", currentPlayer(round));
			inputI = input.nextInt();
			inputJ = input.nextInt();
			if (!checkMoves(inputI,inputJ,round))
				continue;
			flip(inputI, inputJ, round);
			if(counter())
				break;
			printBoard(playBoard);
			round++;
		}
	}
	public static boolean checkMoves(int inputI,int inputJ,int round){ //checking the moves if it is valid 
		if(checkInBounds(inputI, inputJ)){
			System.out.print("Error - input numbers should be 0 to 5");
			return false;
		}else if(checkCellEmpty(inputI, inputJ, round)){
			System.out.println("Error - input cell is not empty.");
			return false;
		}else if(!checkDirction(inputI, inputJ, round)) {
			System.out.println("Error - invalid move");
			return false;
		}else 
			return true;
	}
	public static boolean checkCellEmpty(int inputI,int inputJ, int round){ //checking and making sure that no one has played on it
		if(playBoard[inputI][inputJ] == currentPlayer(round)){
			return true;
		}else 
			return false;
	}
	public static boolean checkInBounds(int inputI, int inputJ){ //checking and making sure that its not out of bounds
		if(inputI > 5 || inputI < 0 || inputJ > 5 || inputJ < 0){
			return true;
		}else 
			return false;
	}
	public static boolean checkDirction(int inputI,int inputJ, int round){ //checking 8 directions around the moves you placed
		int y, x;
		for (int i = 0; i < directions.length; i++){
			y = inputI + directions[i][0];
			x = inputJ + directions[i][1];
			
			if (checkInBounds(y, x)||aroundMoves(y, x, round)){
				continue;
			}
			while (!aroundMoves(y, x, round)){
				y += directions[i][0];
				x += directions[i][1];
				if (checkInBounds(y, x)||playBoard[y][x] == 0){
					break;
				}else if (playBoard[y][x] == currentPlayer(round)){
					return true;
				}
			}
		}return false;
	}
	public static boolean aroundMoves(int inputI,int inputJ,int round){ //checking if there is a chess of currecnt player or empty 
		if(playBoard[inputI][inputJ] == currentPlayer(round)|| (playBoard[inputI][inputJ] == 0)){
			return true;
		}else 
			return false;
	}
	public static void flip(int inputI, int inputJ,int round){ //flip all the pieces between them
		int y, x;
		for (int count=0,i = 0; i <directions.length; i++){
			count = 0;
			y = inputI + directions[i][0];
			x = inputJ + directions[i][1];
			count++;
			
			if (checkInBounds(y, x)||aroundMoves(y, x, round)){
				continue;
			}while (!aroundMoves(y, x, round)){
				y += directions[i][0];
				x += directions[i][1];
				count++;
				if (checkInBounds(y, x)||playBoard[y][x] == 0){
					break;
				}else if(playBoard[y][x] == currentPlayer(round)){
					for(int j = 0; j <= count; j++){
						playBoard[inputI][inputJ] = currentPlayer(round);
						playBoard[inputI + j*directions[i][0]][inputJ + j*directions[i][1]] = currentPlayer(round);
					}break;
				}
			}
			
		}
	}
	public static int currentPlayer(int round) { //switching players
		if(round % 2 == 1){
			return 1;
		}else
			return 2;
	}
	public static void printBoard(int [][] array){ //printing the board 
		System.out.println("   0 1 2 3 4 5");
		System.out.println("   -----------");
		for(int i=0;i<array.length;i++){
			System.out.print(i + "| ");
			for(int j=0;j<array[i].length;j++){
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public static boolean counter(){ //counting the chess
		player1Count=0;player2Count=0;
		for(int i = 0; i< playBoard.length; i++){
			for(int j = 0; j< playBoard[i].length; j++){
				if(playBoard[i][j]==1){
					player1Count++;
				}else if(playBoard[i][j]==2){
					player2Count++;
				}
			}
		}if(player1Count+player2Count==36){
			gameOver();
			return true;
		}return false;
	}
	public static void gameOver(){ //showing if someone wins or its a draw.
		printBoard(playBoard);
		System.out.println("\nGame Finishes.");
		System.out.println("\t'1' - " + player1Count);
		System.out.println("\t'2' - " + player2Count);
		if(player1Count > player2Count){
			System.out.print("Black wins.");
		}else if(player1Count < player2Count) {
			System.out.print("White wins.");
		}else if(player1Count == player2Count) {
			System.out.print("Draw.");
		}
	}
}