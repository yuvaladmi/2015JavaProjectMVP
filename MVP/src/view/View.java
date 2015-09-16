package view;

import algorithms.mazeGenerators.Maze3d;

public interface View {

	public void displayData(Maze3d maze);
	public int getUserCommand();
	public void addObserver(Object o);
	
	
}
