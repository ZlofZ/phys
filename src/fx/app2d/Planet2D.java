package fx.app2d;

import fx.physix2d.Body2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Planet2D extends Body2D {
	
	private Paint color;
	private double pixelSize;
	private boolean inflate=false;
	public Planet2D(double x, double y, Paint color){
		super(x, y, 10);
		this.color=color;
		this.pixelSize=10;
	}
	public Planet2D(double x, double y, int size, Paint color){
		super(x, y, size);
		this.color=color;
		this.pixelSize=size;
		
	}
	public Paint getColor() {
		return this.color;
	}
	public double getSize(){
		return pixelSize;
	}
	public void setSize(double newSize){
		this.pixelSize=newSize;
	}
	public void increaseSize(double increaseAmount){
		this.pixelSize+=increaseAmount;
	}
	public void inflate(){
		pixelSize+=0.2;
//		shiftPos();
	}
	public void startInflate(){
		System.out.println("Startinflate");
		inflate=true;
	}
	public void stopInflate(){
		System.out.println("stopinflate");
		inflate=false;
	}
	private boolean doInflate(){
		return inflate;
	}
	public void render(GraphicsContext gc,boolean drawPos){
		
		if(doInflate())inflate();
		gc.setFill(color);
		gc.fillOval(getX()-(pixelSize/2), getY()-(pixelSize/2), pixelSize, pixelSize);
		if(drawPos){
			gc.setFill(Color.BLACK);
			gc.fillText("("+(int)getX()+","+(int)getY()+")", getX(), getY());
		}
	}
	@Override
	public void gravAcc(Planet2D other,double gc) {
		calcDrag(other);
	}
}
