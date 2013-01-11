package kuhn.flock.boid;

import kuhn.flock.Flock;
import kuhn.flock.behavior.Behavior;
import kuhn.flock.collision.CollisionBehavior;
import kuhn.flock.collision.NullCollisionBehavior;
import kuhn.flock.speed.SpeedBehavior;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Boid {
	private PVector				position;
	private PVector				velocity;
	private float				size;
	public SpeedBehavior		speedBehavior;
	private float				accel;
	private Behavior[]			behaviors;
	public CollisionBehavior	collisionBehavior	= NullCollisionBehavior.NULL_COLLISION_BEHAVIOR;
	private int					fill;
	private int					dead				= 0;
	public float				energy				= Flock.HIGH_ENERGY * 0.99f;
	public float				burn;
	public PVector getPosition() {
		return position;
	}
	public void setPosition(PVector position) {
		this.position = position;
	}
	public PVector getVelocity() {
		return velocity;
	}
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public SpeedBehavior getSpeedBehavior() {
		return speedBehavior;
	}
	public void setSpeedBehavior(SpeedBehavior speedBehavior) {
		this.speedBehavior = speedBehavior;
	}
	public float getAccel() {
		return accel;
	}
	public void setAccel(float accel) {
		this.accel = accel;
	}
	public Behavior[] getBehaviors() {
		return behaviors;
	}
	public void setBehaviors(Behavior[] behaviors) {
		this.behaviors = behaviors;
	}
	public CollisionBehavior getCollisionBehavior() {
		return collisionBehavior;
	}
	public void setCollisionBehavior(CollisionBehavior collisionBehavior) {
		this.collisionBehavior = collisionBehavior;
	}
	public int getFill() {
		return fill;
	}
	public void setFill(int fill) {
		this.fill = fill;
	}
	public int getDead() {
		return dead;
	}
	public void setDead(int dead) {
		this.dead = dead;
	}
	public boolean isDead() {
		return this.dead != 0;
	}
	public boolean isAlive() {
		return !isDead();
	}
	public void kill() {
		if (!isDead())
			dead = 1;
	}
	public float getEnergy() {
		return energy;
	}
	public void setEnergy(float energy) {
		this.energy = energy;
	}
	public float getBurn() {
		return burn;
	}
	public void setBurn(float burn) {
		this.burn = burn;
	}
	public Boid() {
		Flock f = Flock.instance;
		position = new PVector(f.random(0, f.width), f.random(0, f.height));
		velocity = new PVector(f.random(-1, 1), f.random(-1, 1));
	}
	public Boid spawn() {
		energy /= 2;
		try {
			Boid boid = getClass().newInstance();
			boid.velocity = PVector.div(velocity, 9);
			boid.position = position.get();
			return boid;
		} catch (Exception ex) {
			return null;
			// caller is expecting a value
			// oh well.
		}
	}
	public void draw(PApplet p) {
		p.translate(position.x, position.y);
		p.rotate(velocity.heading2D() + PApplet.HALF_PI);
		p.beginShape(PApplet.TRIANGLES);
		p.vertex(0, -size * 2);
		p.vertex(-size, size * 2);
		p.vertex(size, size * 2);
		p.endShape();
	}
	public void wrap(PApplet p) {
		if (getPosition().x < -getSize())
			getPosition().x += p.width;
		else if (getPosition().x > p.width + getSize())
			getPosition().x -= p.width;
		if (getPosition().y < -getSize())
			getPosition().y += p.height;
		else if (getPosition().y > p.height + getSize())
			getPosition().y -= p.height;
	}
}