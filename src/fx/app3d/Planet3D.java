package fx.app3d;


import com.sun.javafx.geom.Vec3d;

import fx.physix3d.Body3D;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Planet3D extends Body3D {
	private final double MASS_KG;
	private final double RADIUS_KM;
	private final double DENSITY_G_CM=5.2;
	private final double VOLUME_KM;
	private Double renderScale;
	private double timeStep=3000;
	
	private final Vec3d FIRSTPOS;
	private Vec3d oldPos;
	private int counter=5;
	private Color color;
	private Sphere sphere;
	private boolean backtostart=false;
	public Planet3D(double x, double y, double z, int radius, double mass, Color color, Group world, Double rs){
		super(x, y, z, world, rs);
		renderScale=rs;
		oldPos = new Vec3d(this);
		FIRSTPOS=new Vec3d(this);
		RADIUS_KM=radius;
		VOLUME_KM=calcVol();
		MASS_KG=mass;
		this.color=color;
		makeSphere(radius*renderScale);
		System.out.println("Constructing planet at ("+x+","+y+","+z+"), in program:("+sphere.getTranslateX()+","+sphere.getTranslateY()+","+sphere.getTranslateZ()+"), radius:"+radius+"("+sphere.getRadius()+" in program), and mass:"+mass+"("+MASS_KG+" in program)");
		world.getChildren().add(sphere);
	}
	@Deprecated
	public Planet3D(double x, double y, double z, int radius, Color color, Group world){
		super(x, y, z, world, new Double(0));
		System.out.println("Constructing planet at ("+x+","+y+","+z+"), radius:"+radius);
		oldPos = new Vec3d(this);
		FIRSTPOS=new Vec3d(this);
		RADIUS_KM=calcRadiusKm(radius);
		VOLUME_KM=calcVol();
		MASS_KG=calcMass();
		this.color=color;
		makeSphere(radius);
		
	}
	
	//===============GETTERS==============
	public Sphere getSphere() {
		System.out.println("GetSphere.");
		return sphere;
	}
	public Color getColor() {
		return this.color;
	}
	public double getRadius(){
		return RADIUS_KM*renderScale;
	}
	public double getMass(){
		return MASS_KG;
	}
	
	//===============SETTERS==============
	public void setSize(double newSize){
		this.sphere.setRadius(newSize*renderScale);
	}
	public void increaseSize(double increaseAmount){
		this.sphere.setRadius(sphere.getRadius()+increaseAmount);
	}
	public Planet3D invisible() {
		sphere.setVisible(false);
		return this;
	}
	public void setSphereSize(double newRad) {
		sphere.setRadius(newRad);
	}
	private void setSphereTranslate(){
		sphere.setMaterial(new PhongMaterial(color));
		sphere.setTranslateX(x*renderScale);
		sphere.setTranslateY(y*renderScale);
		sphere.setTranslateZ(z*renderScale);
		
	}
	
	//===============CALCULATIONS==============
	private double calcVol(){
		return (4/(3*Math.PI*Math.pow(RADIUS_KM, 3)));
	}
	private double calcRadiusKm(double radius){
		return radius;
	}
	private double calcMass(){
		return ((DENSITY_G_CM*1000*1000)*VOLUME_KM);
	}
	private void passedFirst(){
		if (this.x==FIRSTPOS.x) {
			backtostart=true;
		}
	}
	public void updateP(Group world) {
		System.out.println("");
//		System.out.println("oldpos:("+x+","+y+","+z+")");
		if(counter==0&!backtostart){
			passedFirst();
			counter=60;
//			createConnection(oldPos, this, sphere);
			oldPos.set(this);
		}else if (backtostart)System.out.println("BACK AROUND");
		Vec3d tempVel=new Vec3d(getVelocity());
		tempVel.mul(timeStep);
		super.sub(tempVel);
		counter--;
//		System.out.println("newpos:("+x+","+y+","+z+")");
		setSphereTranslate();
		System.out.println("sphere moved to: ("+sphere.getTranslateX()+","+sphere.getTranslateY()+","+sphere.getTranslateY()+")");
//		super.updateLight();
		
//		System.out.println("");
		
	}
	
	//============OTHER========================
	public void makeSphere(double radius){
		System.out.println("makeSphere");
		sphere=new Sphere(radius);
		setSphereTranslate();
		System.out.print("Sphere with radius:["+sphere.getRadius()+"]");
		Reflection reflection=new Reflection();
		reflection.setFraction(1);
		sphere.setEffect(reflection);
		sphere.setVisible(true);
	}
	public void updateScale(Double scale){
		sphere.setRadius(getRadius());
		this.renderScale=scale;
	}
}
