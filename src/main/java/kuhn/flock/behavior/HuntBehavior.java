package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class HuntBehavior extends Behavior {
	float	range;
	Class	species;
	public HuntBehavior(float weight, float range, Class species) {
		super(weight);
		this.range = range;
		this.species = species;
	}
	public PVector steer(Boid boid, Boid[] flock) {
		PVector near = null;
		float nearDist = Float.MAX_VALUE;
		for (Boid other : flock) {
			if (boid == other)
				continue;
			if (other.isDead())
				continue;
			if (!(species.isAssignableFrom(other.getClass())))
				continue;
			PVector diff = PVector.sub(other.getPosition(), boid.getPosition());
			float d = diff.mag();
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