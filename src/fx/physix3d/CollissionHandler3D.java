package fx.physix3d;

import java.util.ArrayList;
import java.util.Set;

import fx.app2d.Planet2D;

public class CollissionHandler3D {
	
	
	public CollissionHandler3D(){
		
	}
	public int detectEdge(Planet2D p){
		double x=p.getX();
		double y=p.getY();
		double partSize=p.getSize()*0.45;
		if(y>(720+partSize))p.setY(0-partSize);
		else if(y<(0-partSize))p.setY(720+partSize);
		else if(x>(1280+partSize))p.setX(0-partSize);
		else if(x<(0-partSize))p.setX(1280+partSize);
		return 0;
	}
	public Planet2D detectCollision(Planet2D p, ArrayList<Planet2D> pl){
		for(Planet2D other:pl){
			if(!p.equals(other)){
				double dist=p.getDistance(other);
				if(((p.getSize()+other.getSize())/2>dist)&&p.getSize()>=other.getSize())return other;
			}
				
		}
		return null;
	
	}
}
