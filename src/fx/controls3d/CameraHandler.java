package fx.controls3d;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import static javafx.scene.input.KeyCode.*;

import fx.launch.Xform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;

public class CameraHandler {
	
	
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private static Xform cameraXform = new Xform();
    private static Xform cameraXform2 = new Xform();
    private static Xform cameraXform3 = new Xform(); 
    double cameraDistance = 1000000;
    
    private static Timeline timeline;
    private static boolean timelinePlaying;
    
    private Group axisGroup;
	
	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;
	
	double ONE_FRAME = 1.0/24.0;
	double DELTA_MULTIPLIER = 200.0;
	double CONTROL_MULTIPLIER = 0.1;
	double SHIFT_MULTIPLIER = 0.1;
	double ALT_MULTIPLIER = 0.5;
	
	public void handleMouse(Scene scene, final Node root) {
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				// System.out.println("mousePressed");
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}
			
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				// System.out.print("mouseDragged");
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX);
				mouseDeltaY = (mousePosY - mouseOldY);

				double modifier = 1.0;
				double modifierFactor = 0.1;

				if (me.isControlDown()) {
					// System.out.print(", CTRLdown");
					modifier = 0.1;
				}
				if (me.isShiftDown()) {
					// System.out.print(", SHIFTdown");
					modifier = 10.0;
				}
				if (me.isPrimaryButtonDown()) {
					// System.out.print(", Mouse1");
					// System.out.println(", ("+(int)(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0)+","+(int)(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0)+") vs ("+(int)cameraXform.ry.getAngle()+","+(int)cameraXform.ry.getAngle()+")");
					cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0); // +
					cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0); // -
				} else if (me.isSecondaryButtonDown()) {
					// System.out.println(", Mouse2");
					double z = camera.getTranslateZ();
					double newZ = z + mouseDeltaX * modifierFactor * modifier;
					camera.setTranslateZ(newZ);
				} else if (me.isMiddleButtonDown()) {
					// System.out.println(", MouseMiddle");
					cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3); // -
					cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3); // -
				}
			}
		});
	}

	public void handleKeyboard(Scene scene, final Node root) {
		final boolean moveCamera = true;
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				Duration currentTime;
				switch (event.getCode()) {
				case Z:
					// System.out.print("Zdown");
					if (event.isShiftDown()) {
						// System.out.print(", SHIFTdown");
						cameraXform.ry.setAngle(0.0);
						cameraXform.rx.setAngle(0.0);
						camera.setTranslateZ(-300000.0);
					}
					cameraXform2.t.setX(0.0);
					cameraXform2.t.setY(0.0);
					// System.out.println();
					break;
				case X:
					// System.out.print("Xdown");
					if (event.isControlDown()) {
						// System.out.print(", CTRLdown");
						if (axisGroup.isVisible()) {
							// System.out.println(", setVisible(false)");
							axisGroup.setVisible(false);
						} else {
							// System.out.println(", setVisible(true)");
							axisGroup.setVisible(true);
						}
					}
					break;
				case SPACE:
					// System.out.println("SPACEdown");
					if (timelinePlaying) {
						timeline.pause();
						timelinePlaying = false;
					} else {
						timeline.play();
						timelinePlaying = true;
					}
					break;
				case UP:
					// System.out.print("UPdown");
					if (event.isControlDown() && event.isShiftDown()) {
						// System.out.print(", CTRLdown, SHIFTdown");
						cameraXform2.t.setY(cameraXform2.t.getY() - 10.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown() && event.isShiftDown()) {
						// System.out.print(", ALTdown, SHIFTdown");
						cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0 * ALT_MULTIPLIER);
					} else if (event.isControlDown()) {
						// System.out.print(", CTRLdown");
						cameraXform2.t.setY(cameraXform2.t.getY() - 1.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown()) {
						// System.out.print(", ALTdown");
						cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0 * ALT_MULTIPLIER);
					} else if (event.isShiftDown()) {
						// System.out.print(", SHIFTdown");
						double z = camera.getTranslateZ();
						double newZ = z + 5.0 * SHIFT_MULTIPLIER;
						camera.setTranslateZ(newZ);
					}
					// System.out.println();
					break;
				case DOWN:
					// System.out.print("DOWNdown");
					if (event.isControlDown() && event.isShiftDown()) {
						// System.out.print(", CTRLdown, SHIFTdown");
						cameraXform2.t.setY(cameraXform2.t.getY() + 10.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown() && event.isShiftDown()) {
						// System.out.print(", ALTdown, SHIFTdown");
						cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0 * ALT_MULTIPLIER);
					} else if (event.isControlDown()) {
						// System.out.print(", CTRLdown");
						cameraXform2.t.setY(cameraXform2.t.getY() + 1.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown()) {
						// System.out.print(", ALTdown");
						cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0 * ALT_MULTIPLIER);
					} else if (event.isShiftDown()) {
						// System.out.print(", SHIFTdown");
						double z = camera.getTranslateZ();
						double newZ = z - 5.0 * SHIFT_MULTIPLIER;
						camera.setTranslateZ(newZ);
					}
					// System.out.println();
					break;
				case RIGHT:
					// System.out.print("RIGHTdown");
					if (event.isControlDown() && event.isShiftDown()) {
						// System.out.print(", CTRLdown, SHIFTdown");
						cameraXform2.t.setX(cameraXform2.t.getX() + 10.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown() && event.isShiftDown()) {
						// System.out.print(", ALTdown, SHIFTdown");
						cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0 * ALT_MULTIPLIER);
					} else if (event.isControlDown()) {
						// System.out.print(", CTRLdown");
						cameraXform2.t.setX(cameraXform2.t.getX() + 1.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown()) {
						// System.out.print(", ALTdown");
						cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0 * ALT_MULTIPLIER);
					}
					// System.out.println();
					break;
				case LEFT:
					// System.out.print("LEFTdown");
					if (event.isControlDown() && event.isShiftDown()) {
						// System.out.print(", CTRLdown, SHIFTdown");
						cameraXform2.t.setX(cameraXform2.t.getX() - 10.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown() && event.isShiftDown()) {
						// System.out.print(", ALTdown, SHIFTdown");
						cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0 * ALT_MULTIPLIER); // -
					} else if (event.isControlDown()) {
						// System.out.print(", CTRLdown");
						cameraXform2.t.setX(cameraXform2.t.getX() - 1.0 * CONTROL_MULTIPLIER);
					} else if (event.isAltDown()) {
						// System.out.print(", ALTdown");
						cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0 * ALT_MULTIPLIER); // -
					}
					// System.out.println();
					break;
				default:
					break;
				}
			}
		});
	}
	
	public double getMousePosX() {
		return mousePosX;
	}

	public void setMousePosX(double mousePosX) {
		this.mousePosX = mousePosX;
	}

	public double getMousePosY() {
		return mousePosY;
	}

	public void setMousePosY(double mousePosY) {
		this.mousePosY = mousePosY;
	}

	public double getMouseOldX() {
		return mouseOldX;
	}

	public void setMouseOldX(double mouseOldX) {
		this.mouseOldX = mouseOldX;
	}

	public double getMouseOldY() {
		return mouseOldY;
	}

	public void setMouseOldY(double mouseOldY) {
		this.mouseOldY = mouseOldY;
	}

	public double getMouseDeltaX() {
		return mouseDeltaX;
	}

	public void setMouseDeltaX(double mouseDeltaX) {
		this.mouseDeltaX = mouseDeltaX;
	}

	public double getMouseDeltaY() {
		return mouseDeltaY;
	}

	public void setMouseDeltaY(double mouseDeltaY) {
		this.mouseDeltaY = mouseDeltaY;
	}

	public PerspectiveCamera getCamera() {
		return camera;
	}

	public Xform getCameraXform() {
		return cameraXform;
	}

	public Xform getCameraXform2() {
		return cameraXform2;
	}

	public Xform getCameraXform3() {
		return cameraXform3;
	}

	public double getCameraDistance() {
		return cameraDistance;
	}
	private void updateCamDistance(){
		camera.setTranslateZ(-cameraDistance);
	}
	public void setDist(double dist){
		cameraDistance=dist;
		updateCamDistance();
	}
	private void buildCamera(){
		cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);
        
		camera.setNearClip(0.1);
        camera.setFarClip(10000000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
	}
	private void setUp(Group root){
		buildCamera();
		root.getChildren().add(cameraXform);
        
	}
	public CameraHandler(Group root, Group axisGroup, Timeline timeline, boolean timelineplaying){
		this.timeline=timeline;
		this.timelinePlaying=timelineplaying;
		this.axisGroup=axisGroup;
		setUp(root);
	}
}
