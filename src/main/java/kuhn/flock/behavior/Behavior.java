package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public abstract class Behavior {
	float	weight;
	public Behavior(float weight) {
		this.weight = weight;
	}
	public float getWeight() {
		return weight;
	}
	public abstract PVector steer(Boid boid, Boid[] flock);
}