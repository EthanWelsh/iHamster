import java.awt.AWTException;
import java.awt.Robot;

public class iHamster {

	static char[][] myMaze = {
		{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
		{ '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#' },
		{ 'S', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#' },
		{ '#', '#', '#', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
		{ '#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '.', '#' },
		{ '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
		{ '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', 'F' },
		{ '#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#' },
		{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
	};

	/*
	static char[][] myMaze = {

		{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
		{ '#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#'},
		{ 'S', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#'},
		{ '#', '#', '#', '.', '#', '.', '.', '.', '.', '#', '.', '#'},
		{ '#', '.', '.', '.', '.', '.', '#', '#', '.', '#', '.', 'F'},
		{ '#', '#', '#', '#', '#', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#' },
		{ '#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#' },
		{ '#', '#', '#', '#', '#', '.', '#', '#', '#', '#', '.', '#' },
		{ '#', '.', '.', '.', '.', '.', '.', '.', '#', '.', '.', '#' },
		{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
	};
	 */

	static int moves = 0; // Counts numbers of moves

	// Set constants for direction
	static final int east = 0;
	static final int south = 1;
	static final int west = 2;
	static final int north = 3;

	// Track my position on the maze
	static int x = 0;
	static int y = 0;

	// Track my direction
	static int direction = east;

	// Determine the format of the maze
	static final char start = 'S';
	static final char finish = 'F';
	static final char wall = '#';
	static final char path = '.';



	public static void main(String[] args) 
	{
		for (int startX=0; startX<12; startX++) 
		{
			for (int startY=0; startY<12; startY++) 
			{
				if (myMaze[startX][startY] == start) 
				{
					x = startX;
					y = startY;
				}
			}
		}

		System.out.println("I'm solving the maze...");
		solveMaze();
		System.out.println("SOLVED in " + moves + " moves");
	}

	public static void solveMaze () 
	{ // Solves the maze by sticking to the right wall.

		if (myMaze[y][x] == finish) return; // BASE CASE

		if(wallOnRight()) 
		{
			moves++;
			if (moveForward()!=true) //Move forward
			{ // Otherwise, turn left
				if ((direction - 1)<0) 
				{
					direction = 3;
				} 
				else 
				{
					direction = direction - 1;
				}
			}
		} 
		else 
		{ // If there is no wall on your right turn right
			direction = (direction + 1) % 4;
			moves++;
			moveForward();
		}
		printMaze();
		solveMaze();
	}


	public static boolean moveNorth () 
	{ // Moves in the northward direction
		if (myMaze[y-1][x] == path || myMaze[y-1][x] == finish) 
		{ //up
			y = y-1;
			direction = north;
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static boolean moveSouth () 
	{ // Moves in the southward direction
		if (myMaze[y+1][x] == path || myMaze[y+1][x] == finish) 
		{
			y = y+1;
			direction = south;
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static boolean moveEast () 
	{ // Moves in the eastward direction
		if (myMaze[y][x+1] == path || myMaze[y][x+1] == finish) 
		{
			x = x+1;
			direction = east;
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static boolean moveWest () 
	{ // Moves in the westward direction
		if (myMaze[y][x-1] == path || myMaze[y-1][x] == finish) 
		{
			x = x-1;
			direction = west;
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static void printMaze () 
	{ // Print the maze & my current location
		for (int i = 0; i < 12; i++) 
		{
			for (int q=0; q<12; q++) 
			{
				if (i == y && q == x) 
				{ // Show my direction in the maze
					if (direction == north) System.out.print("^" + " "); // If this is my current location, print a * in its place
					if (direction == south) System.out.print("v" + " "); // If this is my current location, print a * in its place
					if (direction == east) System.out.print(">" + " "); // If this is my current location, print a * in its place
					if (direction == west) System.out.print("<" + " "); // If this is my current location, print a * in its place
				}
				else System.out.print(myMaze[i][q] + " "); // Otherwise, just do your stuff
			}
			System.out.println();	
		}
		for (int i = 0; i<15; i++) System.out.println();
		robotWait(250); // Wait .25 of a second before printing next maze, for animation effect
	}

	public static boolean moveForward () 
	{ // Moves forward depending on what direction is being faced
		if (direction == north) 
		{
			return moveNorth();
		}
		if (direction == east) 
		{ 
			return moveEast();
		}
		if (direction == south) 
		{ 
			return moveSouth();
		}
		if (direction == west) 
		{ 
			return moveWest();
		}
		return false;
	}

	public static boolean wallOnRight () 
	{ // Checks to see weather a wall is on the right, depending upon my direction
		if (direction == east) 
		{
			if (myMaze[y+1][x] == wall) return true;
			else return false;
		}
		if (direction == west) 
		{
			if (myMaze[y-1][x] == wall) return true;
			else return false;
		}
		if (direction == south) 
		{
			if (myMaze[y][x-1] == wall) return true;
			else return false;
		}
		if (direction == north) 
		{
			if (myMaze[y][x+1] == wall) return true;
			else return false;
		}
		return false;
	}

	private static void robotWait(int i) 
	{
		try 
		{ 
			Robot robot = new Robot(); 
			robot.delay(i);
		} 
		catch (AWTException e) 
		{ 
			e.printStackTrace(); 
		} 
	}
}
