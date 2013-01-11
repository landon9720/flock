![image](https://raw.github.com/landon9720/flock/master/screens/screenshot.png)

## a boot

This is a Scala/SBT wrapper around my original Java [project](http://code.google.com/p/kuhn/). One day I may port it to pure Scala (a way better language for these kinds of problems) (or better yet, ProcessingJS!).

Clone this git repo.

```
sbt run
```

## a tour

![image](https://raw.github.com/landon9720/flock/master/screens/blue1.png)

Blue boids are the  grazers in this world. They travel together, moving slow. They feed on nothing, which is why they eventually die.

![image](https://raw.github.com/landon9720/flock/master/screens/blue2.png)

This poor group is looking very hungry, indeed. But starvation is not the only way these poor fellows croak.

![image](https://raw.github.com/landon9720/flock/master/screens/orange1.png)

Here we meet the orange boids. As you can see, they are terrifying hunters.

![image](https://raw.github.com/landon9720/flock/master/screens/orange2.png)

Here we see another grizzly scene. Notice the rotting bodies? Those are food.

![image](https://raw.github.com/landon9720/flock/master/screens/green.png)

The green boids. They are the scavengers. The flies. They buzz and swarm. And keep me up at night.

![image](https://raw.github.com/landon9720/flock/master/screens/yellow.png)

Now meet the loan hunter: the yellow boid. He (or she) is the top predator of this world.

## credits

kuhn.flock.Flock is based on the Processing example at
http://www.processing.org/learning/topics/flocking.html.

The Processing example is an implementation of the original
algorithm by Craig Reynold.
