package fx.physix2d;

import com.sun.javafx.geom.Vec2d;

import fx.app2d.Planet2D;
import javafx.geometry.Point2D;

public abstract class Body2D extends Position2D {
	private static final double G=6.67408*(10^(-11));
	private final double MASS_KG;
	private final double DIAMETER_KM;
	private final double DENSITY_G_CM=5.2;
	private final double VOLUME_KM;
	private Vec2d direction=new Vec2d();
	private Point2D velocity=new Point2D(0, 0);
	private Point2D dir=new Point2D(0,0);
	private double speed=0;
	public Body2D(double xPos, double yPos, double pxdiam) {
		super(xPos, yPos);
		DIAMETER_KM=calcDiam(pxdiam);
		VOLUME_KM=calcVol();
		MASS_KG=calcMass(pxdiam);
		
	}
	public Vec2d getDirection() {
		return direction;
	}
	public double getSpeed() {
		return speed;
	}
	public void changeVel(Planet2D other,double gc) {
		speed=(speed-other.getSpeed())/2;
	}
	public Point2D getVelocity(){
		return velocity;
	}
	public Point2D subVelocity(Point2D subVel) {
		return velocity.subtract(subVel);
	}
	public double getMass(){
		return MASS_KG;
	}
	public abstract void gravAcc(Planet2D other, double gc);
	
	private double calcVol(){
		double radM=((DIAMETER_KM*1000)/2);
		double volm=(4/(3*Math.PI*Math.pow(radM, 3)));
		return volm;
	}
	private double calcDiam(double diamPx){
		return diamPx;
	}
	
	private double calcMass(double diamPx){
		return ((DENSITY_G_CM*1000)*VOLUME_KM);
	}
	
	
	public void calcDrag(Planet2D other){
		Point2D dirOfOther=getPos().subtract(other.getPos()).normalize();
		double dragForce=gravitationalAcceleration(other)*3600*24*365;
		Point2D velocityOther=dirOfOther.multiply(dragForce);
		this.velocity=subVelocity(velocityOther);
		dir=velocity.normalize();
		speed=velocity.magnitude();
		
	}
	public void addForce(Planet2D other, double gc){
		Point2D dragHeading=getPos().subtract(other.getPos());  
		dragHeading=dragHeading.normalize();						//[den här planeten]-[den andra planeten]=riktningen av dragkraften mot [den andra planeten]
		double acc=gravitationalAcceleration(other);			//styrkan av dragkraften mot [den andra planeten]
		Point2D thisVel=new Point2D((dir.getX()*speed), (dir.getY()*speed));	//riktningen [den här planeten] har + dess hastighet
		Point2D otherVel=new Point2D((dragHeading.getX()*acc),(dragHeading.getY()*acc));	//riktningen mot [den andra planeten]+ dragKraften
		dir=thisVel.subtract(otherVel);			//[den här planeten]'s riktning+hastighet - dragkraften mot [den andra planeten] = riktningen och hastigheten som [den här planeten] kommer dras mot
		speed=dir.magnitude();					//nya hastigheten på [den här planeten]
		dir=dir.normalize();					//nya riktningen av [den här planeten]
	}
	private double gravitationalAcceleration(Planet2D other){
		double distance=getDistance(other); //avståndet mellan [den här planeten]och[den andra planeten]
		double gAcc=(G*other.getMass())/Math.pow(distance, 2);	//([gravitationalConstant]*[andraplanetens massa]/[avståndet mellan planeterna]^2   = dragkraften från [den här planeten]mot[den andra planeten]
		return gAcc;
	}
	@Override
	public void move(){
		updatePos(velocity);
	}
}
