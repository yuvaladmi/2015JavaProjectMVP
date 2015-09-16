package view;

import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;

public class Maze3dView extends Observable implements View {

	@Override
	public void displayData(Maze3d maze) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getUserCommand() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void addObserver(Object o){
		notifyObservers(o);
	}

}
