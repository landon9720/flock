package kuhn.flock;

import kuhn.flock.behavior.Behavior;
import kuhn.flock.boid.BlueBoid;
import kuhn.flock.boid.Boid;
import kuhn.flock.boid.GreenBoid;
import kuhn.flock.boid.RedBoid;
import kuhn.flock.boid.YellowBoid;
import processing.core.PApplet;
import processing.core.PVector;

public class Flock extends PApplet {

	// settings
	private static final int		SIZE_WIDTH				= 1000;
	private static final int		SIZE_HEIGHT				= 800;
	private static final float		POPULATION_DENSITY		= 0.0003125f;
	private static final float		FOLLOW_BOID_IDLE_SPEED	= 1.0f;
	private static final int		FOLLOW_BOID_IDLE_MAX	= 50;
	private static final int		NUM						= (int)((SIZE_WIDTH * SIZE_HEIGHT) * POPULATION_DENSITY);
	public static final int			MAX_DEAD				= 500;
	public static final int			HIGH_ENERGY				= 500;
	private static final float[]	SPAWN_WEIGHTS			= new float[] {
			1.0f, 0.10f, 0.05f, 0.01f						};
	private static float			SPAWN_WEIGHTS_SUM		= 0.0f;
	private final Class[]			SPAWN_SPECIES			= new Class[] {
			BlueBoid.class, GreenBoid.class, RedBoid.class, YellowBoid.class };
	
	// construction
	public Flock() {
		Flock.instance = this;
		for (int i = 0; i < blood.length; ++i)
			blood[i] = new PVector(Float.MAX_VALUE, Float.MAX_VALUE, 100);
	}
	static {
		for (int i = 0; i < SPAWN_WEIGHTS.length; ++i)
			SPAWN_WEIGHTS_SUM += SPAWN_WEIGHTS[i];		
	}
	static public void main(String args[]) {
		PApplet.main(new String[] {
				"--present", "kuhn.flock.Flock" });
	}
	public static Flock		instance;

	// member data
	private Boid[]			boids				= new Boid[0];
	private PVector[]		blood				= new PVector[1000];
	private PVector			camera				= new PVector(SIZE_WIDTH / 2, SIZE_HEIGHT / 2); // where the camera is
	private Boid			follow				= null;											// the boid the camera is following
	private int				followIdle			= 0;											// number of frames the followed boid has been idle
	private float			zoom				= 1.0f;											// current camera zoom
	private float			rotation			= 0.0f;											// current camera rotation
	private int				iblood				= 0;

	// Processing framework methods
	public void setup() {
		size(SIZE_WIDTH, SIZE_HEIGHT);
		colorMode(HSB, 360, 100, 100, 100);
		smooth();
		noStroke();
		frameRate(30);
		noCursor();
	}

	public void draw() {
		// retire the boid being followed if dead or idle
		if (follow != null && (follow.getDead() > 25 || followIdle > FOLLOW_BOID_IDLE_MAX))
			follow = null;

		// find a boid to follow
		if (follow == null) {
			// find a suitable boid
			// work backwards, to favor newer boids
			Boid green = null;
			for (int i = boids.length - 1; i >= 0; --i) {
				Boid boid = boids[i];
				if (boid.isAlive() && boid.getEnergy() > HIGH_ENERGY / 2 && !(boid instanceof BlueBoid) && boid.getVelocity().mag() > 1.0f) {
					if (boid instanceof GreenBoid) {
						green = boid;
					}
					else {
						follow = boid;
						break;
					}
					followIdle = 0;
				}
			}
			// if didn't find a suitable boid, fall back on a green boid (if one exists)
			if (follow == null)
				follow = green;
		}
		
		// update the followed boid idle count
		if (follow != null && follow.getVelocity().mag() < FOLLOW_BOID_IDLE_SPEED)
			++followIdle;

		// update the camera pan and zoom
		if (follow != null) {
			PVector move = PVector.sub(follow.getPosition(), camera);
			float distance = move.mag();
			move.normalize();
			move.mult(distance / 15);
			camera.add(move);
			float targetZoom = max(4.0f - sq(distance) / 10000, 1.0f);
			if (targetZoom < zoom)
				zoom += (targetZoom - zoom) / 5;
			else
				zoom += (targetZoom - zoom) / 15;
		}

		// update the camera rotation
		float targetRotation = map(noise(camera.x / 300, camera.y / 300), 0, 1, 0, TWO_PI);
		if (targetRotation > rotation)
			rotation += (targetRotation - rotation) / 30;
		else
			rotation -= (rotation - targetRotation) / 30;

		// apply zoom and pan
		scale(zoom);
		translate(width / zoom / 2 - camera.x, height / zoom / 2 - camera.y);

		// apply rotation
		translate(camera.x, camera.y);
		rotate(rotation);
		translate(-camera.x, -camera.y);

		// draw the play field
		background(0);
		stroke(70);
		fill(50);
		rect(0, 0, width, height);
		float spacing = width / 5;
		for (float i = 0; i < width; i += spacing)
			line(i, 0, i, height);
		for (int i = 0; i < height; i += spacing)
			line(0, i, width, i);

		// draw the blood!!!
		strokeWeight(2.0f);
		for (PVector drop : blood) {
			stroke(0, 100, 60);
			point(drop.x, drop.y);
		}

		// move all the living boids
		strokeWeight(1.0f);
		for (Boid boid : boids) {
			if (boid.isAlive()) {
				flock(boid, boids);
				move(boid);
			}
			draw(boid);
		}

		int gc = 0;
		for (Boid boid1 : boids) {
			// age the dead
			if (boid1.isDead()) {
				boid1.setDead(boid1.getDead() + 1);
				if (boid1.getDead() > MAX_DEAD)
					++gc;
			}
			// collision detect
			for (Boid boid2 : boids) {
				if (boid1 == boid2)
					continue;
				if (PVector.sub(boid1.getPosition(), boid2.getPosition()).mag() < max(boid1.getSize(), boid2.getSize()) * 2) {
					boolean alive = boid2.isAlive();
					boid1.getCollisionBehavior().collide(boid1, boid2);
					if (alive && boid2.isDead())
						bleed(boid1.getPosition(), boid1.getVelocity());
				}
			}
			// spawn the well fed
			if (boids.length < NUM && boid1.getEnergy() > HIGH_ENERGY * 2)
				boids = (Boid[])append(boids, boid1.spawn());
		}

		// clean up any long dead boids
		if (gc > 0) {
			Boid[] newBoids = new Boid[0];
			for (Boid boid : boids)
				if (boid.getDead() < MAX_DEAD)
					newBoids = (Boid[])append(newBoids, boid);
			boids = newBoids;
		}

		// spawn new boids
		if (frameCount % 5 == 0 && boids.length < NUM) {
			spawn();
		}
	}

	// boid methods

	private void flock(Boid boid, Boid[] flock) {
		Behavior[] behaviors = boid.getBehaviors();
		PVector accelerationVector = new PVector(0, 0);
		for (int i = 0; i < behaviors.length; ++i) {
			PVector steer = behaviors[i].steer(boid, flock);
			steer.normalize();
			steer.mult(behaviors[i].getWeight());
			accelerationVector.add(steer);
		}
		accelerationVector.normalize();
		accelerationVector.mult(boid.getAccel());
		PVector velocity = boid.getVelocity();
		velocity.add(accelerationVector);
		float speed = boid.getSpeedBehavior().speed(boid, flock);
		velocity.limit(speed);
	}

	private void spawn() {
		float w = random(0.0f, SPAWN_WEIGHTS_SUM);
		int i;
		for (i = 0; w > 0; ++i) {
			w -= SPAWN_WEIGHTS[i];
		}
		try {
			boids = (Boid[])append(boids, SPAWN_SPECIES[i - 1].newInstance());
		} catch (Exception ex) {
			// a spoon full of sugar helps the exception, go down
		}
	}

	private void move(Boid boid) {
		boid.setEnergy(boid.getEnergy() - max(boid.getVelocity().mag(), 1.0f) * boid.getBurn());
		if (boid.getEnergy() < 0.0f) {
			boid.kill();
		} else {
			boid.getPosition().add(boid.getVelocity());
			boid.wrap(this);
		}
	}

	private void draw(Boid boid) {
		if (boid.isAlive()) {
			noStroke();
			fill(boid.getFill(), map(boid.getEnergy(), 0, HIGH_ENERGY, 0, 100), 100, 100);
		} else {
			int color = color(0, 0, 70, map(boid.getDead(), 0, MAX_DEAD, 100, 0));
			if (boid.getEnergy() < 0.0f) {
				noFill();
				stroke(color);
			} else {
				fill(color);
				noStroke();
			}
		}
		pushMatrix();
		boid.draw(this);
		popMatrix();
	}

	private void bleed(PVector p, PVector v) {
		v = PVector.mult(v, 4);
		for (int i = 0; i < 10; ++i) {
			blood[iblood++] = PVector.add(p, v);
			iblood %= blood.length;
			v = PVector.div(v, 1.1f);
			v = PVector.add(v, new PVector(random(-3f, 3f), random(-3f, 3f)));
		}
	}
}