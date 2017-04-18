package fx.app3d;


import java.util.Random;

import com.sun.javafx.perf.PerformanceTracker;

import fx.controls3d.CameraHandler;
import fx.launch.Xform;
import fx.physix3d.Physics3D;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.geometry.Side;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Appen3D {
	private PerformanceTracker tracker;
	private final Group root;
	final Group axisGroup;
	private Xform world;
	private Scene scene;
	private Label fps=new Label("fps");
	private CameraHandler ch;
	private Xform cameraXform;
    private Xform cameraXform2;
    private Xform cameraXform3;
    private static long timeLastFrame=0;
    
    Physics3D phys;
	
    private static double width = 1280;
	private static double height = 720;
	
	private Timeline timeline=new Timeline();
    boolean timelinePlaying = false;
    
	
	
	
	
	
	
	
	
	private SubScene sbs;
	
	private VBox topBar;
	private Slider cameraZpos=new Slider(3000, 10000, 15000);
	
	private Xform container;
	private Group sp;
	private Group sp2;
	private Group sp3;
	
	private int counter=0;
	
	
	
	
	
	
	public void draw(){
		if(phys.isEmpty()){
			System.out.println("gravCalc");
			phys.gravityCalc();
			System.out.println("updatePos");
			phys.updatePos();
		}
		
	}
	
	public void fps(long now){
		String string="FPS:"+((now-timeLastFrame)*0.000000001);
		timeLastFrame=now;
		fps.setText(string);
		
	}
	
	private void before(){
		System.out.println("before");
		makeEarth();
	}
	
	
	private void makeEarth() {
		Planet3D s=new Planet3D(0, 0, 0, 695700, 1.989*Math.pow(10, 30), Color.ORANGERED, container);
		Planet3D e=new Planet3D(149600000, 0, 0, 6371,(5.972*Math.pow(10, 24)), Color.GREEN, container);
		e.addVelocity(0,0,29782/100);
//		s.lightSwitch();
//		e.setX(0);
		e.lightSwitch();
		e.setSphereSize(1000);
//		s.invisible();
//		s.createConnection(s, e, s.getSphere());
		phys.addPlanet(s);
		System.out.println("the earth");
		phys.addPlanet(e);
		phys.printPlanets();
		phys.gravityCalc();
		phys.updatePos();
		phys.printPlanets();
	}
	private void makePlanets() {
		Random random=new Random();
		for(int i=0;i<3;i++){
			Color color=new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
			phys.addPlanet(new Planet3D((random.nextDouble()*200)-100, (random.nextDouble()*200)-100, (random.nextDouble()*200)-100, random.nextInt(25)+5, color, sp));
		}
	}
	
	
	private void rotate(){
		if(counter<=720*2)
			counter++;
		else counter=1;
		
		container.setRotate(counter*.25);
		sp.setRotate(counter);
		sp2.setRotate(-counter*2);
		sp3.setRotate(counter);
	}
	
	
	private void updateCam(){
		ch.getCamera().setTranslateZ(-cameraZpos.getValue());
	}
	
	private void makeShapes(){
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.LIGHTBLUE);
		
		Box box = new Box(1000, 1000, 1000);
		box.setTranslateX(-1200);
		box.setTranslateY(0);
		box.setTranslateZ(1);
		box.setMaterial(material);
		box.setDepthTest(root.getDepthTest());
		
		Sphere sphere = new Sphere(500);
		sphere.setTranslateX(0);
		sphere.setTranslateY(-5);
		sphere.setTranslateZ(2);
		sphere.setMaterial(material);
		sphere.setDepthTest(root.getDepthTest());
		
		Cylinder cylinder = new Cylinder(400, 1200);
		cylinder.setTranslateX(+1200);
		cylinder.setTranslateY(-25);
		cylinder.setTranslateZ(3);
		cylinder.setMaterial(material);
		cylinder.setDepthTest(root.getDepthTest());
		
		PointLight light = new PointLight();
		light.setTranslateX(0);
		light.setTranslateY(100);
		light.setTranslateZ(-3100);
		sp2=new Group(box, sphere);
		sp3=new Group(cylinder);
		sp=new Group(sp2,sp3);
		container=new Xform(sp);
		setRotY();
		root.getChildren().addAll(topBar,container,light);
	}
	
	private void setRotY(){
		container.setRotationAxis(Rotate.X_AXIS);
		sp.setRotationAxis(Rotate.Y_AXIS);
		sp2.setRotationAxis(Rotate.Y_AXIS);
		sp3.setRotationAxis(Rotate.Y_AXIS);
	}
	
	
	
	
	
	private void buildAxes() {
        System.out.println("buildAxes");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
 
        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);
 
        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);
 
        final Box xAxis = new Box(24000.0, 2, 2);
        final Box yAxis = new Box(2, 24000.0, 2);
        final Box zAxis = new Box(2, 2, 24000.0);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
    }
	
	private void buildScene() {
        System.out.println("buildScene");
        root.getChildren().add(container);
//        root.getChildren().add(world);
        container.getChildren().add(new AmbientLight());
        scene=new Scene(topBar, width, height, true);
        scene.setFill(Color.BLACK);
        
    }
	private void makeStage(Stage primaryStage){
		System.out.println("makeStage");
		primaryStage.setTitle("3D JavaFX");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	private void buildCamera() {
		System.out.println("buildCamera");
		ch=new CameraHandler(container, axisGroup, timeline, timelinePlaying);
		ch.handleKeyboard(scene, container);
        ch.handleMouse(scene, container);
        cameraXform=ch.getCameraXform();
        cameraXform2=ch.getCameraXform2();
        cameraXform3=ch.getCameraXform3();
    }
	private SubScene subScene(){
		SubScene scene3d = new SubScene(container, width, height);
	    scene3d.setFill(Color.rgb(10, 10, 40));
//	    scene3d.setCamera(ch.getCamera());
	    sbs=scene3d;
	    return scene3d;
	}
	private VBox makeTopBar(){
		VBox b=new VBox(fps);
		return b;
	}
	private void setUp(Stage primaryStage, AnimationTimer at){
		System.out.println("setUp");
		container=new Xform();
		phys=new Physics3D(container);
		topBar=new VBox(makeTopBar(),subScene());
		buildScene();
		buildCamera();
		buildAxes();
		makeStage(primaryStage);
		primaryStage.show();
//		scene.setCamera
		sbs.setCamera(ch.getCamera());
		before();
		System.out.println("starting loop");
		at.start();
	}

	public Appen3D(Stage primaryStage, AnimationTimer at, Group root, Xform world){
		this.root=root;
		this.world=world;
		axisGroup = new Group();
		setUp(primaryStage,at);
	}
}
