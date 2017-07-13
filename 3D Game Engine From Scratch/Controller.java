import java.awt.event.KeyEvent;

public class Controller {

	public static double x, z, y, rotation=0, xa, za, rotationa;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean turnLeft, boolean turnRight,
			boolean jump, boolean crouch,boolean run ) {
		double rotationSpeed = 0.005;
		double walkSpeed = 0.2;
		double jumpHeight =6;
		double crouchHeight=3;
		double xMove = 0;
		double zMove = 0;

		if (forward) {
			zMove++;
		}
		if (back) {
			zMove--;
		}
		if (left) {
			xMove--;
		}
		if (right) {
			xMove++;
		}
		if (turnLeft) {
			rotationa -= rotationSpeed;
		}
		if (turnRight) {
			rotationa += rotationSpeed;
		}
		if (jump) {
			y += jumpHeight;
			run=false;
			walkSpeed = 0.3;
		}
		if (crouch) {
			y -= crouchHeight;
			run=false;
			walkSpeed = 0.3;
		}
		if(run ){
			walkSpeed*=3;
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		z += za;
		y *= 0.5;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.5;
	}
}
