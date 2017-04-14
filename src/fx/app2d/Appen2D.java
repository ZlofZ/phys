package fx.app2d;


import fx.physix2d.Physics2D;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Appen2D {
	private final int CENTER_HEIGHT=(720/2);
	private final int CENTER_WIDTH=(1280/2);
	private static GraphicsContext gc;
	private Canvas canvas;
	Physics2D phys;
	private Planet2D centerPlanet;
	public Appen2D(GraphicsContext gc,AnimationTimer at,Canvas c){
		Appen2D.gc=gc;
		this.canvas=c;
		setUp(at);
	}
	
	
	
	
	
	
	public void draw(){
		if(phys.clearPath())
			gc.clearRect(0, 0, 1280, 720);
		drawPlanets();
	}
	
	private void setUp(AnimationTimer at){
		at.start();
		phys=new Physics2D(canvas);
//		makeCenterPlanet();
		
		
	}
	private void makeCenterPlanet(){
		centerPlanet=new Planet2D(CENTER_WIDTH, CENTER_HEIGHT,50, Color.BLACK);
	}
	private void drawPlanets(){;
		phys.render(gc, phys.clearPath());
	}
	
	
	
	
	
	  
	
	
	
	
	
	
	
}
