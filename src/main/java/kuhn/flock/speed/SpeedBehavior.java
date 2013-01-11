package kuhn.flock.speed;

import kuhn.flock.boid.Boid;

public interface SpeedBehavior {
	float speed(Boid boid, Boid[] flock);
}
