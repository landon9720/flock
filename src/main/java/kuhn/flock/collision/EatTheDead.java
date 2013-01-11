package kuhn.flock.collision;

import kuhn.flock.boid.Boid;

public class EatTheDead implements CollisionBehavior {
	private int rate;
	public EatTheDead(int rate) {
		this.rate = rate;
	}
	public void collide(Boid self, Boid other) {
		if (other.isAlive())
			return;
		other.setDead(other.getDead() + rate);
		self.setEnergy(self.getEnergy() + rate);
	}
}
