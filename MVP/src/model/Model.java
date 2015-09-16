package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	public void moveBackward();
	public void moveForward();
	public Maze3d getData();
	public void addObserver(Object o);

}
