package kuhn.flock.behavior;

import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class BreakingBehavior extends Behavior {
	public BreakingBehavior(float weight) {
		super(weight);
	}
	public PVector steer(Boid boid, Boid[] flock) {
		if (boid.getVelocity().mag() < boid.getAccel())
			return new PVector(0, 0);
		else
			return PVector.sub(new PVector(0, 0), boid.getVelocity());
	}
}