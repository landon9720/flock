package kuhn.flock.boid;

import kuhn.flock.behavior.Behavior;
import kuhn.flock.behavior.BreakingBehavior;
import kuhn.flock.behavior.HuntBehavior;
import kuhn.flock.behavior.SeparateBehavior;
import kuhn.flock.collision.CollisionBehavior;
import kuhn.flock.collision.EatTheLiving;
import kuhn.flock.speed.FixedSpeed;
import kuhn.flock.speed.SpeedBehavior;

public class RedBoid extends Boid {
	static Behavior[]			BEHAVIORS			= new Behavior[] {
			new BreakingBehavior(0.1f), 
			new HuntBehavior(1.0f, 50, BlueBoid.class), 
			new SeparateBehavior(1.0f, 15, RedBoid.class),
			new SeparateBehavior(2.0f, 90, YellowBoid.class) };
	static CollisionBehavior	COLLISION_BEHAVIOR	= new EatTheLiving(BlueBoid.class);
	static SpeedBehavior		SPEED_BEHAVIOR		= new FixedSpeed(3.0f);
	public RedBoid() {
		setSize(2.5f);
		setSpeedBehavior(SPEED_BEHAVIOR);
		setAccel(0.6f);
		setBehaviors(BEHAVIORS);
		setCollisionBehavior(COLLISION_BEHAVIOR);
		setFill(22);
		setBurn(1.5f);
	}
}