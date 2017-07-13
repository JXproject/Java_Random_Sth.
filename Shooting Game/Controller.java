import java.awt.event.KeyEvent;

public class Controller {
	
	public void tick(boolean forward, boolean back, boolean left, boolean right,boolean shoot) {
		int xMove = 0;
		int yMove = 0;

		double xlSpeed=Game.player1.getSpeed();
		double xrSpeed=Game.player1.getSpeed();
		double yfSpeed=Game.player1.getSpeed();
		double ybSpeed=Game.player1.getSpeed();
		
		if(Game.yfdelayc!=-1){
			yfSpeed=Game.player1.getSpeed()/(Math.pow(((Game.tick-Game.yfdelayc)/10),2)+1);
		}
		if(Game.ybdelayc!=-1){
			ybSpeed=Game.player1.getSpeed()/(Math.pow(((Game.tick-Game.ybdelayc)/10),2)+1);
		}
		if(Game.xldelayc!=-1){
			xlSpeed=Game.player1.getSpeed()/(Math.pow(((Game.tick-Game.xldelayc)/10),2)+1);
		}
		if(Game.xrdelayc!=-1){
			xrSpeed=Game.player1.getSpeed()/(Math.pow(((Game.tick-Game.xrdelayc)/10),2)+1);
		}

		if (forward) {
			yMove-=(int)yfSpeed;
		}
		if (back) {
			yMove+=(int)ybSpeed;
		}
		if (left) {
			xMove-=(int)xlSpeed;
		}
		if (right) {
			xMove+=(int)xrSpeed;
		}

		Game.player1.move(xMove, yMove);
		
		if(shoot){
			Bullet bullet=new Bullet(Game.player1.getX(),Game.player1.getY(),Game.input.MouseX,Game.input.MouseY,400,Game.tick,100,10,10);
			Game.Ammo.add(bullet);
		}

	}
}
