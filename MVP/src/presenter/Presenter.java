package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer{

	View v;
	Model m;
	public Presenter(Model m2, View v2) {
		v=v2;
		m=m2;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
