package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class AlignmentBehavior extends Behavior {
	float	range;
	Class	species;
	public AlignmentBehavior(float weight, float range, Class species) {
		super(weight);
		this.range = range;
		this.species = species;
	}
	public PVector steer(Boid boid, Boid[] flock) {
		PVector steer = new PVector(0, 0);
		for (Boid other : flock) {
			if (boid == other)
				continue;
			if (other.isDead()) continue;
			if (!(species.isAssignableFrom(other.getClass())))
				continue;
			PVector diff = PVector.sub(boid.getPosition(), other.getPosition());
			float d = diff.mag();
			if (d < range) {
				steer.add(other.getVelocity());
			}
		}
		return steer;
	}
}