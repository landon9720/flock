package kuhn.flock.collision;

import kuhn.flock.boid.Boid;

public class EatTheLiving implements CollisionBehavior {
	Class	species;
	public EatTheLiving(Class species) {
		this.species = species;
	}
	public void collide(Boid self, Boid other) {
		if (other.isDead())
			return;
		if (!(species.isAssignableFrom(other.getClass())))
			return;
		other.kill();
		self.setEnergy(self.getEnergy() + other.getEnergy());
		other.setEnergy(0.0f);
	}
}
