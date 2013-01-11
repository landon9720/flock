package kuhn.flock.boid;

import kuhn.flock.Flock;
import kuhn.flock.behavior.Behavior;
import kuhn.flock.behavior.ScavangeBehavior;
import kuhn.flock.behavior.SeparateBehavior;
import kuhn.flock.collision.CollisionBehavior;
import kuhn.flock.collision.EatTheDead;
import kuhn.flock.speed.FixedSpeed;
import kuhn.flock.speed.SpeedBehavior;

public class GreenBoid extends Boid {
	static Behavior[]			BEHAVIORS			= new Behavior[] {
			new ScavangeBehavior(1.0f, 150), 
			new SeparateBehavior(0.5f, 1, GreenBoid.class) };
	static CollisionBehavior	COLLISION_BEHAVIOR	= new EatTheDead(200);
	static SpeedBehavior		SPEED_BEHAVIOR		= new FixedSpeed(5.0f);
	public GreenBoid() {
		setSize(1.0f);
		setSpeedBehavior(SPEED_BEHAVIOR);
		setAccel(2.0f);
		setBehaviors(BEHAVIORS);
		setCollisionBehavior(COLLISION_BEHAVIOR);
		setFill(112);
		setBurn(2.0f);
	}
	 @Override
	public void kill() {
		setDead(Flock.MAX_DEAD - 1);
	}
}