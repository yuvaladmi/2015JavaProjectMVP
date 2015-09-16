package model;

import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

public class Maze3dModel extends Observable implements Model {
	Maze3d myMaze;
	HashMap <Maze3d, Solution<Position>> hMazeSol;
	

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveBackward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveForward() {
		// TODO Auto-generated method stub

	}

	@Override
	public Maze3d getData() {
		// TODO Auto-generated method stub
		return null;
	}
	public void addObserver(Object o){
		notifyObservers(o);
	}

}
