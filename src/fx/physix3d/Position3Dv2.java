package fx.physix3d;

import com.sun.javafx.geom.Vec3d;

import fx.app3d.Planet3D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public abstract class Position3Dv2 extends Vec3d{
	private Group world;
	private PointLight light;
	public Position3Dv2(double xPos,double yPos,double zPos, Group world){
		super(xPos,yPos,zPos);
		this.world=world;
		light=new PointLight();
		light.setTranslateX(x);
		light.setTranslateY(y);
		light.setTranslateZ(z);
		world.getChildren().add(light);
	}
	public void setX(double newX){
		super.x=newX;
	}
	public void setY(double newY){
		super.y=newY;
	}
	public void setZ(double newZ){
		super.z=newZ;
	}
	public void lightSwitch() {
		boolean a=light.isLightOn();
		light.setLightOn(!a);
	}
	private Vec3d midpoint(Vec3d other) {
		return new Vec3d(other.x+(super.x-other.x)/2.0,other.y+(super.y-other.y)/2.0,other.z+(super.z-other.z)/2.0);
	}
	
	public void updateLight(){
		light.setTranslateX(x+15);
		light.setTranslateY(y);
		light.setTranslateZ(z);
	}
	
	private void makeTail(Sphere sphere){

		
	}
	public void createConnection(Vec3d origin, Vec3d target,Sphere s) {
		
	    Point3D yAxis = new Point3D(0, 1, 0);
	    Vec3d diff = new Vec3d(target);
	    diff.sub(origin);
	    double height = diff.length();
	    Vec3d mid=midpoint(target);
	    
	    Translate moveToMidpoint = new Translate(mid.x, mid.y, mid.z);

	    mid.cross(diff, new Vec3d(0,1,0));
	    Point3D axisOfRotation=new Point3D(mid.x,mid.y,mid.z);
	    diff.normalize();
	    
	    double angle = Math.acos(diff.dot(mid));
	    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

	    Cylinder line = new Cylinder(100,height,10);

	    line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
	    line.setMaterial(s.getMaterial());
	    line.setVisible(true);
	    System.out.println("line, height: "+line.getHeight()+"radius:"+line.getRadius()+"pos:"+line.getTranslateX()+","+line.getTranslateY()+","+line.getTranslateZ());
	    world.getChildren().add(line);
	}
	public void updatePos(Vec3d velocity, Sphere sphere){
		
		
	}
	public void makePos(Vec3d direction,double speed){
//		Vec3d v=new Vec3d((direction.getX()*speed), (direction.getY()*speed), (direction.getY()*speed));
//		posit=posit.subtract(v);
	}
	@Override
	public String toString(){
		return "["+x+","+y+","+z+"]";
	}
}
