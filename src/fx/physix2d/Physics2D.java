package fx.physix2d;

import java.util.ArrayList;
import java.util.Random;

import fx.app2d.Planet2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Physics2D {
	private ArrayList<Planet2D>planetList;
	private CollissionHandler2D colHand;
	private boolean clear=true;
	private static final double G=6.67408*(10^(-11));
	public Physics2D(Canvas c) {
		c.addEventFilter(MouseEvent.MOUSE_PRESSED, clickCreatePlanet);
		c.addEventFilter(MouseEvent.MOUSE_RELEASED, clickCreatePlanet);
		colHand=new CollissionHandler2D();
	}
	public void render(GraphicsContext gc,boolean drawPos) {
		if(planetList!=null){
			System.out.println(planetList.size());
			if (planetList.size()>0) {
				gravityCalc();
				
			}
			updatePos();
			System.out.println("render");
			for(Planet2D p:planetList){
//				colHand.detectEdge(p);
//				Planet coillide=colHand.detectCollision(p,planetList);
//				if (coillide!=null) {
//					p.increaseSize(coillide.getSize());
//					p.changeVel(coillide, G);
//					planetList.remove(coillide);
//					coillide=null;
//				}
				p.render(gc, drawPos);
				
			}
//			if (planetList.size()>1)
//				System.exit(0);

		}
		
	}
	public boolean clearPath() {
		return clear;
	}
	private void makeClear(){
		clear=!clear;
	}
	private void gravityCalc(){
		System.out.println("gravitycalc");
		for(Planet2D p:planetList){
			for(Planet2D other:planetList){
				if (!p.equals(other))p.gravAcc(other,G);
			}
		}
	}
	private void updatePos(){
		System.out.println("posupdate");
		for(Planet2D p:planetList){
			p.move();
		}
	}
	
	private void addPlanet(Planet2D p){
		if(planetList==null)planetList=new ArrayList<>();
		planetList.add(p);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	EventHandler<MouseEvent> clickCreatePlanet = new EventHandler<MouseEvent>() { 
		   @Override 
		   public void handle(MouseEvent e) {
			   System.out.println("createPlanet");
			   int size=10;
			   Planet2D temp;
			   System.out.println(e.getEventType()+" "+e.isPrimaryButtonDown());
			   if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)&&e.isPrimaryButtonDown()) {
				   System.out.println("press");
				   Random random=new Random();
				   double r=random.nextDouble();
				   Color color=new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1);
				   temp=new Planet2D((e.getSceneX()-(size/2)), (e.getSceneY()-(size/2)),size, color);
				   temp.startInflate();
				   addPlanet(temp);
			   }else if(e.getEventType().equals(MouseEvent.MOUSE_RELEASED)){
				   System.out.println("release");
				   temp=planetList.get(planetList.size()-1);
				   temp.stopInflate();
			   }else if(e.isSecondaryButtonDown())makeClear();
			   System.out.println("planetCreated");
		   } 
		}; 
	
}
