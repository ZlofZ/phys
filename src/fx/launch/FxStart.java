package fx.launch;

import fx.app2d.Appen2D;
import fx.app3d.Appen3D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Camera;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class FxStart extends Application {
	
	
	private final Group root = new Group();
	private final Xform world = new Xform();
    
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer at = new MyTimer();
    private Appen2D app2d;
    private Appen3D app3d;
    private boolean run3D=true;
	
    
    
    
    
    @Override
	public void start(Stage primaryStage) throws Exception {
    	System.out.println("start");
		if(run3D)
			run3D(primaryStage);
		else
			run2D(primaryStage);
	}
	
	
	
	
	
	
	
	
	
	private void run3D(Stage primaryStage){
		check3DSupport();
		app3d=new Appen3D(primaryStage,at,root,world);
	}
	
	private void run2D(Stage primaryStage){
		root.getChildren().add(canvas);
		gc=canvas.getGraphicsContext2D();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
        app2d=new Appen2D(gc,at,canvas);
	}
	
	private void check3DSupport(){
		System.out.println("3D supported? " + Platform.isSupported(ConditionalFeature.SCENE3D));
		root.setDepthTest(DepthTest.ENABLE);
	}
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	
	private class MyTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
        	if(run3D){
        		app3d.draw();
        		app3d.fps(now);
        	}else 
        		app2d.draw();
        }
    }
}