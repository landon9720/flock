package kuhn.flock.boid;

import kuhn.flock.behavior.AlignmentBehavior;
import kuhn.flock.behavior.Behavior;
import kuhn.flock.behavior.BreakingBehavior;
import kuhn.flock.behavior.CohesionBehavior;
import kuhn.flock.behavior.MouseFollowBehavior;
import kuhn.flock.behavior.SeparateBehavior;
import kuhn.flock.speed.FlockAdvantage;
import kuhn.flock.speed.SpeedBehavior;

public class BlueBoid extends Boid {
	static Behavior[]		BEHAVIORS		= new Behavior[] {
			new BreakingBehavior(0.1f), 
//			new MouseFollowBehavior(10.0f), 
			new SeparateBehavior(1.0f, 15.0f, BlueBoid.class),
			new SeparateBehavior(2.0f, 50, RedBoid.class),
			new AlignmentBehavior(1.0f, 50.0f, BlueBoid.class),
			new CohesionBehavior(1.0f, 50.0f, BlueBoid.class) };
	static SpeedBehavior	SPEED_BEHAVIOR	= new FlockAdvantage(2.0f, 4.0f, 10, 50.0f);
	public BlueBoid() {
		setSize(3.0f);
		setSpeedBehavior(SPEED_BEHAVIOR);
		setAccel(0.3f);
		setBehaviors(BEHAVIORS);
		setFill(214);
		setBurn(0.1f);
	}
}