package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class ScavangeBehavior extends Behavior {
	private float	range;
	public ScavangeBehavior(float weight, float range) {
		super(weight);
		this.range = range;
	}
	public PVector steer(Boid boid, Boid[] flock) {
		PVector near = null;
		float nearDist = Float.MAX_VALUE;
		for (Boid other : flock) {
			if (boid == other)
				continue;
			if (other.isAlive())
				continue;
			PVector diff = PVector.sub(other.getPosition(), boid.getPosition());
			float d = diff.mag();
			if (d < other.getSize() * 2) {
				return new PVector(0, 0);
			}
			if (d < range && d < nearDist) {
				near = diff;
				nearDist = d;
			}
		}
		if (near != null)
			return near;
		else
			return new PVector(0, 0);
	}
}