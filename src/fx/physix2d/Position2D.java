package fx.physix2d;

import com.sun.javafx.geom.Vec2d;

import fx.app2d.Planet2D;
import javafx.geometry.Point2D;


public abstract class Position2D {
	private Vec2d pos;
	private Point2D posit;
	private Point2D drawPos;
	
	public Position2D(double xPos,double yPos){
		posit=new Point2D(xPos, yPos);
	}
	public double getDistance(Planet2D other){
		double dist = posit.distance(other.getPos());
		if(dist<10)
			return 10;
//		else if(dist>500)
//			return 500;
		else return dist;
		
	}
	public Vec2d getVector(){
		return pos;
	}
	public Point2D getPos(){
		return posit;
	}
	public double getX(){
		return posit.getX();
	}
	public double getY(){
		return posit.getY();
	}
	public void setX(double newX){
		posit=new Point2D(newX, getY());
	}
	public void setY(double newY){
		posit=new Point2D(getX(), newY);
	}
	public void updatePos(Point2D velocity){
		posit=posit.subtract(velocity);
		System.out.println("new pos: ("+getX()+","+getY()+")");
	}
	public void makePos(Point2D direction,double speed){
		Point2D v=new Point2D((direction.getX()*speed), (direction.getY()*speed));
		posit=posit.subtract(v);
	}
	public abstract void move();
}
