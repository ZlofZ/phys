package fx.physix3d;

import com.sun.javafx.geom.Vec3d;

import fx.app3d.Planet3D;
import javafx.scene.Group;

public abstract class Body3D extends Position3Dv2 {
	private static final double G=6.67408*Math.pow(10, -11);
	@SuppressWarnings("unused")
	private Double renderScale;
	private Vec3d velocity=new Vec3d();
	
	public Body3D(double xPos, double yPos, double zPos, Group world, Double rs) {
		super(xPos, yPos, zPos, world, rs);
		this.renderScale=rs;
		System.out.println(xPos);
	}
	public Vec3d getVelocity() {
		return velocity;
	}	
	
	
	
	
	public void addVelocity(double x, double y, double z) {
		Vec3d temp=new Vec3d(x, y, z);
//		System.out.println("setting velocity: ("+temp.x+","+temp.y+","+temp.z+")");
		velocity.set(temp);
	}
	
	public void calculateVelocity(Planet3D other) {
//		System.out.println("this Planet is: ("+this.x+","+this.y+","+this.z+"), other Planet is: ("+other.x+","+other.y+","+other.z+")");
		Vec3d temp = new Vec3d(other);
		temp.sub(this);
//		System.out.println("\n[other]-[this]=[new dir] == ["+other.x+","+other.y+","+other.z+"]-["+this.x+","+this.y+","+this.z+"]=["+temp.x+","+temp.y+","+temp.z+"]");
		double force=forceCalc(other.getMass(), temp.length());
		temp.normalize();
//		System.out.println(", and direction between is this:("+temp.x+","+temp.y+","+temp.z+"),\nthe other planet has a mass of "+other.getMass());
//		System.out.println("the force added to the vector is:"+(Math.abs(force)));
		temp.mul(force);	
		velocity.sub(temp);
		
	}
	
	
	private double forceCalc(double massOther, double distance){
//		System.out.println("calculate dragforce: [force]=[G]x[otherMass]/[distance]^2");
		double temp1=G*massOther;
		double temp2=Math.pow((distance*1000), 2);
		double force=Math.abs(temp1/temp2);
//		System.out.println("Distance:["+distance+"Km]\nmassOther:["+massOther+"]Kg");
		System.out.println("Dragforce=["+force+"]");
		return force;
	}
	
//	public void calcDrag(Planet3D other){
//		Point3D dirOfOther=getPos().subtract(other.getPos()).normalize();
//		double dragForce=gravitationalAcceleration(other)*3600*24*365;
//		Point3D velocityOther=dirOfOther.multiply(dragForce);
//		this.velocity=subVelocity(velocityOther);
//		dir=velocity.normalize();
//		speed=velocity.magnitude();
//		
//	}
//	public void addForce(Planet3D other, double gc){
//		Point3D dragHeading=getPos().subtract(other.getPos());  
//		dragHeading=dragHeading.normalize();						//[den här planeten]-[den andra planeten]=riktningen av dragkraften mot [den andra planeten]
//		double acc=gravitationalAcceleration(other);			//styrkan av dragkraften mot [den andra planeten]
//		Point3D thisVel=new Point3D((dir.getX()*speed), (dir.getY()*speed), (dir.getZ()*speed));	//riktningen [den här planeten] har + dess hastighet
//		Point3D otherVel=new Point3D((dragHeading.getX()*acc),(dragHeading.getY()*acc),(dragHeading.getZ()*acc));	//riktningen mot [den andra planeten]+ dragKraften
//		dir=thisVel.subtract(otherVel);			//[den här planeten]'s riktning+hastighet - dragkraften mot [den andra planeten] = riktningen och hastigheten som [den här planeten] kommer dras mot
//		speed=dir.magnitude();					//nya hastigheten på [den här planeten]
//		dir=dir.normalize();					//nya riktningen av [den här planeten]
//	}
//	private double gravitationalAcceleration(Planet3D other){
//		double distance=getDistance(other); //avståndet mellan [den här planeten]och[den andra planeten]
//		double gAcc=(G*other.getMass())/Math.pow(distance*300, 2);	//([gravitationalConstant]*[andraplanetens massa]/[avståndet mellan planeterna]^2   = dragkraften från [den här planeten]mot[den andra planeten]
//		return gAcc;
//	}
}
