package kuhn.flock.collision;

import kuhn.flock.boid.Boid;

public interface CollisionBehavior {
	void collide(Boid self, Boid other);
}
