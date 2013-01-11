package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class SeparateBehavior extends Behavior {
	private float	distance;
	private Class	species;
	public SeparateBehavior(float weight, float distance, Class species) {
		super(weight);
		this.distance = distance;
		this.species = species;
	}
	public PVector steer(Boid boid, Boid[] flock) {
		PVector steer = new PVector(0, 0);
		for (Boid other : flock) {
			if (boid == other)
				continue;
			if (other.isDead())
				continue;
			if (!(species.isAssignableFrom(other.getClass())))
				continue;
			PVector diff = PVector.sub(boid.getPosition(), other.getPosition());
			float d = diff.mag();
			if (d < distance) {
				steer.add(diff);
			}
		}
		return steer;
	}
}