package fx.physix3d;


import com.sun.javafx.geom.Vec3d;

import fx.app3d.Planet3D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Path;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import sun.security.acl.WorldGroupImpl;


public abstract class Position3D {
	private Vec3d pos;
	private Point3D posit;
	private Point3D lastpos;
	private Point3D drawPos;
	private double oldx;
	private double oldy;
	private double oldz;
	private Point3D oldPos;
	private int counter=0;
	private Group world;
	private double size=10;
	public Position3D(double xPos,double yPos,double zPos, Group world){
		this.world=world;
		posit=new Point3D(xPos, yPos, zPos);
		oldPos=posit;
		oldx=getX();
		oldy=getY();
		oldz=getZ();
		
	}
	public Vec3d getVector(){
		return pos;
	}
	public Point3D getPos(){
		return posit;
	}
	public double getX(){
		return posit.getX();
	}
	public double getY(){
		return posit.getY();
	}
	public double getZ(){
		return posit.getZ();
	}
	public void setX(double newX){
		posit=new Point3D(newX, getY(), getZ());
	}
	public void setY(double newY){
		posit=new Point3D(getX(), newY, getZ());
	}
	public void setZ(double newZ){
		posit=new Point3D(getX(), getY(), newZ);
	}
	private void makeTail(Sphere sphere){
		counter=0;
		Cylinder c=createConnection(oldPos, getPos(),sphere);
		size=c.getHeight();
		world.getChildren().add(c);
		oldPos=getPos();
		oldx=getX();
		oldy=getY();
		oldz=getZ();
	}
	public Cylinder createConnection(Point3D origin, Point3D target,Sphere s) {
	    Point3D yAxis = new Point3D(0, 1, 0);
	    Point3D diff = target.subtract(origin);
	    double height = diff.magnitude();

	    Point3D mid = target.midpoint(origin);
	    Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

	    Point3D axisOfRotation = diff.crossProduct(yAxis);
	    double angle = Math.acos(diff.normalize().dotProduct(yAxis));
	    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

	    Cylinder line = new Cylinder(1, height,5);

	    line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
	    line.setMaterial(s.getMaterial());
	    return line;
	}
	public void updatePos(Point3D velocity, Sphere sphere){
		posit=posit.subtract(velocity);
		
		sphere.setTranslateX(getX());
		sphere.setTranslateY(getY());
		sphere.setTranslateZ(getZ());
		if(counter<180)counter++;
		else makeTail(sphere);
		
//		lastpos=getPos();
//		if(lastpos.distance(getPos())*100<1)sphere.setRadius(3);
//		else sphere.setRadius(lastpos.distance(getPos())*10);
		System.out.println("new size:"+sphere.getRadius()+", pos: ("+getX()+","+getY()+","+getZ()+")");
	}
	public void makePos(Point3D direction,double speed){
		Point3D v=new Point3D((direction.getX()*speed), (direction.getY()*speed), (direction.getY()*speed));
		posit=posit.subtract(v);
	}
	public abstract void move();
}
