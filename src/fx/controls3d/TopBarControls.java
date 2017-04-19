package fx.controls3d;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;



public class TopBarControls {
	Integer scale;
	CameraHandler ch;
	public TopBarControls() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void addDistControls(TextArea t){
		t.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCharacter().equals("ENTER"))
				ch.setDist(Integer.parseInt(event.getText()));
			}
		});
	}
	
	public void sliderHandler(Slider s, Integer rs, CameraHandler c){
		scale=rs;
		ch=c;
		s.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	System.out.println(ch.cameraDistance);
            	scale=new_val.intValue();
            	ch.setDist(new_val.doubleValue());
            }
        });
	}
	
}