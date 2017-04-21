package fx.controls3d;


import fx.physix3d.Physics3D;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;



public class TopBarControls {
	private static Double scale;
	CameraHandler ch;
	Physics3D phys;
	public TopBarControls() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void addDistControls(TextArea t, Button b, Button b2){
		b.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				ch.setDist(Double.parseDouble(t.getText()));
			}
		});
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				phys.get(1).setSphereSize(10000);;
			}
		});
	}
	
	public void sliderHandler(Slider s, Double rs, CameraHandler c, Physics3D p){
		scale=rs;
		ch=c;
		phys=p;
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	System.out.println(scale);
            	scale=new_val.doubleValue();
            	phys.updateScale(scale);
//            	ch.setDist(new_val.doubleValue());
            }
        });
	}
	
}