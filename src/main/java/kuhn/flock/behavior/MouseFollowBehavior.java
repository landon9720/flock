package kuhn.flock.behavior;

import kuhn.flock.Flock;
import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class MouseFollowBehavior extends Behavior {
	public MouseFollowBehavior(float weight) {
		super(weight);
	}
	public PVector steer(Boid boid, Boid[] flock) {
		Flock f = Flock.instance;
		if (f.mouseX == f.pmouseX && f.mouseY == f.pmouseY)
			return new PVector(0, 0);
		return PVector.sub(new PVector(f.mouseX, f.mouseY), boid.getPosition());
	}
}