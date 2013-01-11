package kuhn.flock.collision;

import kuhn.flock.boid.Boid;

public class NullCollisionBehavior implements CollisionBehavior {
	public static CollisionBehavior	NULL_COLLISION_BEHAVIOR	= new NullCollisionBehavior();
	@Override
	public void collide(Boid self, Boid other) {
	}
}
