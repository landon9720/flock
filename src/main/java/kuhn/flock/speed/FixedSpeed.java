package kuhn.flock.speed;

import kuhn.flock.boid.Boid;

public class FixedSpeed implements SpeedBehavior {
	float	speed;
	public FixedSpeed(float speed) {
		this.speed = speed;
	}
	@Override
	public float speed(Boid boid, Boid[] flock) {
		return speed;
	}
}
