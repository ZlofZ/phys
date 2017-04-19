package fx.app3d;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import com.sun.javafx.perf.PerformanceTracker;

import fx.controls3d.CameraHandler;
import fx.controls3d.TopBarControls;
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
import javafx.scene.control.TextArea;
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
	private final Group root;
	final Group axisGroup;
	private Xform world;
	private Scene scene;
	private Label fps=new Label("fps");
	private CameraHandler ch;
	private TopBarControls tpb;
    private static long lastUpdate=0;
    private long last=0;
    private NumberFormat formatter = new DecimalFormat("#0.00");
    Physics3D phys;
    private static double width = 1280;
	private static double height = 720;
	public static Integer renderScale;
	private Timeline timeline=new Timeline();
    boolean timelinePlaying = false;
    
	private SubScene sbs;
	private VBox topBar;
	private Xform container;
	private Group sp;
	
	
	
	
	
	
	
	public void draw(){
		if(phys.isEmpty()){
			System.out.println("gravCalc");
			phys.gravityCalc();
			System.out.println("updatePos");
			phys.updatePos();
		}
		
	}
	
	public void fps(long now){
		if((now-lastUpdate)>100000000){
	    	fps.setText("FPS:"+formatter.format((1000000000.0/(now-last))));
	    	lastUpdate=now;
	    }
		last=now;
	}
	
	private void before(){
		System.out.println("before");
		makeEarth();
	}
	
	
	private void makeEarth() {
		System.out.println("PlanetCreation");
		Planet3D s=new Planet3D(0, 0, 0, 695700, 1.989*Math.pow(10, 30), Color.ORANGERED, container, renderScale);
		Planet3D e=new Planet3D(149600000, 0, 0, 6371,(5.972*Math.pow(10, 24)), Color.GREEN, container, renderScale);
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
		
		ch.handleKeyboard(scene, container);
        ch.handleMouse(scene, container);
    }
	private SubScene subScene(){
		System.out.println("subScene");
		SubScene scene3d = new SubScene(container, width, height);
	    scene3d.setFill(Color.rgb(10, 10, 40));
	    sbs=scene3d;
	    return scene3d;
	}
	private TextArea makeDistBox(){
		TextArea textArea= new TextArea();
		tpb.addDistControls(textArea);
		return textArea;
	}
	private Slider scaleSlider(){
		if(tpb==null)tpb=new TopBarControls();
		
		Slider s=new Slider(1,1000,500);
		renderScale=(int) s.getValue();
		tpb.sliderHandler(s, renderScale, ch);
		return s;
	}
	private VBox makeTopBar(){
		System.out.println("makeTopBar");
		VBox b=new VBox(fps,scaleSlider(),makeDistBox());
		return b;
	}
	private void setUp(Stage primaryStage, AnimationTimer at){
		System.out.println("setUp");
		container=new Xform();
		phys=new Physics3D(container);
		ch=new CameraHandler(container, axisGroup, timeline, timelinePlaying);
		topBar=new VBox(makeTopBar(),subScene());
		buildScene();
		buildCamera();
		buildAxes();
		makeStage(primaryStage);
		primaryStage.show();
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
