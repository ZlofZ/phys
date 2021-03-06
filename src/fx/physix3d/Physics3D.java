package fx.physix3d;

import java.util.ArrayList;
import java.util.Random;

import com.sun.javafx.geom.Vec3d;

import fx.app3d.Planet3D;
import fx.launch.Xform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;

public class Physics3D {
	private ArrayList<Planet3D>planetList;
	private Group world;
	private boolean clear=true;
	public Physics3D(Group root) {
		world=root;	
	}
	
	
	
	//======================GETTERS==============================
	public boolean isEmpty(){
		return (planetList!=null);
	}
	//======================SETTERS==============================
	public boolean clearPath() {
		return clear;
	}
	private void makeClear(){
		clear=!clear;
	}
	public void updatePos(){
//		System.out.println("posupdate");
		for(Planet3D p:planetList){
			if(planetList.indexOf(p)==0)System.out.println("For SUN");
			else System.out.println("For EARTH");
			p.updateP(world);
		}
	}
	//=====================CALCULATIONS==========================
	public void gravityCalc(){
//		System.out.println("gravitycalc");
		for(Planet3D p:planetList){
			if(planetList.indexOf(p)==0)System.out.println("For SUN");
			else System.out.println("For EARTH");
			for(Planet3D other:planetList){
				if (!p.equals(other))p.calculateVelocity(other);
			}
		}
	}
	//=====================OTHERS==========================
	public void addPlanet(Planet3D p){
		if(planetList==null)planetList=new ArrayList<>();
		System.out.print("planet in: ");
		planetList.add(p);
		System.out.print("list");
		System.out.println(" and world");
	}
	public void printPlanets(){
		System.out.println("======================================================================");
		for(Planet3D p:planetList){
			System.out.println("Planet"+planetList.indexOf(p)+":"+p);
		}
	}
}
