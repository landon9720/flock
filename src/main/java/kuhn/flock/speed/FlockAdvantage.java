package kuhn.flock.speed;

import kuhn.flock.Flock;
import kuhn.flock.boid.Boid;
import processing.core.PVector;

public class FlockAdvantage implements SpeedBehavior {
	private float	min, max;
	private int		flockSize;
	private float	range;
	public FlockAdvantage(float min, float max, int flockSize, float range) {
		this.min = min;
		this.max = max;
		this.flockSize = flockSize;
		this.range = range;
	}
	@Override
	public float speed(Boid boid, Boid[] flock) {
		int count = 0;
		for (int i = 0; i < flock.length; ++i) {
			Boid other = flock[i];
			if (boid == other)
				continue;
			if (other.isDead())
				continue;
			if (!(boid.getClass().isAssignableFrom(other.getClass())))
				continue;
			if (PVector.sub(boid.getPosition(), other.getPosition()).mag() < range)
				++count;
			if (count == flockSize)
				break;
		}
		return Flock.map(count, 0, flockSize, min, max);
	}
}
