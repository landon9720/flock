package kuhn.flock.boid;

import kuhn.flock.behavior.Behavior;
import kuhn.flock.behavior.HuntBehavior;
import kuhn.flock.behavior.SeparateBehavior;
import kuhn.flock.collision.CollisionBehavior;
import kuhn.flock.collision.EatTheLiving;
import kuhn.flock.speed.FixedSpeed;
import kuhn.flock.speed.SpeedBehavior;

public class YellowBoid extends Boid {
	static Behavior[]			BEHAVIORS			= new Behavior[] {
			new HuntBehavior(1.0f, 300, RedBoid.class),
			new SeparateBehavior(1.0f, 20, YellowBoid.class)};
	static CollisionBehavior	COLLISION_BEHAVIOR	= new EatTheLiving(RedBoid.class);
	static SpeedBehavior		SPEED_BEHAVIOR		= new FixedSpeed(8.0f);
	public YellowBoid() {
		setSize(4.0f);
		setSpeedBehavior(SPEED_BEHAVIOR);
		setAccel(2.0f);
		setBehaviors(BEHAVIORS);
		setCollisionBehavior(COLLISION_BEHAVIOR);
		setFill(60);
		setBurn(0.8f);
	}
}