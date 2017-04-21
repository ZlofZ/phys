package fx.physix3d;

import java.util.ArrayList;


import fx.app3d.Planet3D;
import javafx.scene.Group;

public class Physics3D {
	private ArrayList<Planet3D>planetList;
	private Group world;
	private boolean clear=true;
	public Physics3D(Group root) {
		System.out.println("Physics init");
		world=root;	
	}
	
	
	
	//======================GETTERS==============================
	public boolean isEmpty(){
		return (planetList!=null);
	}
	public Planet3D get(int index){
		return planetList.get(index);
	}
	//======================SETTERS==============================
	public boolean clearPath() {
		return clear;
	}
	@SuppressWarnings("unused")
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
	public void updateScale(Double scale){
		for(Planet3D p:planetList){
			p.updateScale(scale);
		}
	}
}
