package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Presenter;

public class Maze3dModel extends abstractModel {
    // Maze3d myMaze;
    // HashMap <Maze3d, Solution<Position>> hMazeSol;


    public Maze3dModel() {
	// TODO Auto-generated constructor stub
    }

    @Override
    public void dirToPath(String[] arr) {

	File f = null;
	String s = arr[arr.length - 1];
	try {
	    f = new File(s);
	    notifyObservers();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    // e.toString();
	    String[] error = "Folder does not exist".split(" ");
	    c.displayString(error);
	}
    }

    /**
     * This method create a new Maze3d in a thread. All the mazes saved in an
     * HashMap.
     */
    @Override
    public void generateMaze(String[] arr) {
	threadpool.execute(new Runnable() {

	    @Override
	    public void run() {
		sb = new StringBuilder();
		int i = 1;
		if (arr[i].equals("3d") || arr[i].equals("3D")) {
		    i++;
		    ;
		    if (arr[i].equals("maze")) {
			i++;
		    }
		}
		while (i < arr.length) {
		    sb.append(arr[i]);
		    i++;
		}
		String name = sb.substring(0, sb.length() - 3);
		int x = (int) sb.charAt(sb.length() - 3) - 48;
		int y = (int) sb.charAt(sb.length() - 2) - 48;
		int z = (int) sb.charAt(sb.length() - 1) - 48;
		MyMaze3dGenerator mg = new MyMaze3dGenerator(x, y, z);
		Maze3d m = mg.generate(x, y, z);
		hMaze.put(name, m);
		String[] messege = ("maze " + name + " is ready").split(" ");
		c.displayString(messege);
	    }
	});
    }

    /**
     * This method gets a Maze name and sends the Controller this maze.
     */
    @Override
    public void sendGame(String[] arr) {
	sb = new StringBuilder();
	for (int i = 1; i < arr.length; i++) {
	    sb.append(arr[i]);
	}
	String name = sb.toString();
	byte[] byteArr;
	try {
	    Maze3d temp = hMaze.get(name);
	    byteArr = temp.toByteArray();
	    c.displayByte(byteArr);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    c.displayString(("Error uccord, please try again").split(" "));
	}
    }

    /**
     * This method gets a name of a maze and sends the Controller a CrossSection
     * of this maze
     */
    @Override
    public void crossSection(String[] arr) {
	sb = new StringBuilder();
	int i = arr.length - 2;
	if (arr[i].equals("for")) {
	    for (int j = i + 1; j < arr.length; j++) {
		sb.append(arr[j]);
	    }
	}
	String name = sb.toString();
	int index = (arr[--i].charAt(0)) - 48;
	char crossBy = arr[--i].charAt(0);
	Maze3d maze = hMaze.get(name);
	int[][] myMaze = null;
	switch (crossBy) {
	case 'x':
	    myMaze = maze.getCrossSectionByX(index);

	    break;
	case 'X':
	    myMaze = maze.getCrossSectionByX(index);

	    break;
	case 'y':
	    myMaze = maze.getCrossSectionByY(index);
	    break;
	case 'Y':
	    myMaze = maze.getCrossSectionByY(index);
	    break;
	case 'z':
	    myMaze = maze.getCrossSectionByZ(index);
	    break;
	case 'Z':
	    myMaze = maze.getCrossSectionByZ(index);
	    break;

	default:
	    System.out.println("undefined letter ");
	    break;
	}
	c.displayInt(myMaze);

    }

    /**
     * This methods gets a name of a file and a maze and save this maze in the
     * file.
     */
    @Override
    public void save(String[] arr) {
	String name = arr[arr.length - 2];
	String fileName = arr[arr.length - 1];
	Maze3d m = hMaze.get(name);
	try {
	    OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
	    out.write(m.toByteArray());
	    out.close();
	    c.displayString("The maze is saved".split(" "));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * This methods gets a name of a file and a maze and load to the new maze
     * the objects from the file.
     */
    @Override
    public void load(String[] arr) {
	String name = arr[arr.length - 1];
	String fileName = arr[arr.length - 2];
	try {
	    byte[] temp = new byte[4096];
	    InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
	    int numOfBytes = in.read(temp);
	    in.close();
	    byte[] b = new byte[numOfBytes];
	    for (int i = 0; i < b.length; i++) {
		b[i] = temp[i];
	    }
	    Maze3d maze = new Maze3d(b);
	    hMaze.put(name, maze);
	    c.displayString("The maze is loaded".split(" "));
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * This method gets a name of a maze and solving algorithm and solves it in
     * a Thread. All the solutions saved in an HashMap.
     */
    @Override
    public void solve(String[] arr) {
	String nameAlg = arr[arr.length - 1];
	sb = new StringBuilder();
	for (int i = 1; i < arr.length - 1; i++) {
	    sb.append(arr[i]);
	}
	String name = sb.toString();

	if ((hSol.get(name)) != null) {
	    c.displayString(("solution for " + name + " is ready").split(" "));
	}
	threadpool.execute(new Runnable() {
	    @Override
	    public void run() {
		Maze3d m = hMaze.get(name);
		SearchableMaze sMaze = new SearchableMaze(m);
		CommonSearcher<Position> cs;
		switch (nameAlg) {
		case "Astar":
		    cs = new AStar<Position>(new MazeManhattenDistance());
		    hSol.put(name, cs.search(sMaze));
		    break;
		case "A*":
		    cs = new AStar<Position>(new MazeManhattenDistance());
		    hSol.put(name, cs.search(sMaze));
		    break;
		case "BFS":
		    cs = new BFS<Position>();
		    hSol.put(name, cs.search(sMaze));
		    break;
		default:
		    c.displayString(("The algorithm was not found").split(" "));
		    break;
		}

		c.displayString(("solution for " + name + " is ready").split(" "));
	    }
	});
    }

    /**
     * This method gets a name of a maze and sends the Controller its solution
     */
    @Override
    public void bringSolution(String[] arr) {
	Solution<Position> s = hSol.get(arr[arr.length - 1]);
	if (s != null) {
	    Stack<Position> sol = s.getSolution();
	    sb = new StringBuilder();
	    while (!sol.isEmpty()) {
		sb.append(sol.pop());
	    }
	    c.displayString(sb.toString().split(" "));
	    return;
	}
	c.displayString(("Solution do not exist for " + arr[arr.length - 1] + " maze.").split(" "));
    }

    /**
     * This method gets a name of a maze and sends the Controller its size.
     */
    @Override
    public void gameSize(String[] arr) {
	Maze3d temp = hMaze.get(arr[arr.length - 1]);
	try {
	    int size = (temp.toByteArray()).length;
	    c.displayString(("Maze " + arr[arr.length - 1] + " size is:" + size).split(" "));
	} catch (IOException e) {
	    c.displayString(("Error, please try again...").split(" "));
	}
    }

    /**
     * This method gets a name of a file and sends the Controller its size.
     */
    @Override
    public void fileSize(String[] arr) {
	File f = new File(arr[arr.length - 1]);
	if (f.exists()) {
	    long size = f.length();
	    c.displayString(("File " + arr[arr.length - 1] + " size is:" + size).split(" "));
	} else {
	    c.displayString(("Error, please try again...").split(" "));
	}
    }

    /**
     * This method closes all the open threads.
     */
    @Override
    public void close() {
	System.out.println("shutting down");
	threadpool.shutdown();
	// wait 10 seconds over and over again until all running jobs have
	// finished
	try {
	    boolean allTasksCompleted = false;
	    while (!(allTasksCompleted = threadpool.awaitTermination(10, TimeUnit.SECONDS)))
		;
	    c.exit();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }

}
