![image](https://raw.github.com/landon9720/flock/master/screens/screenshot.png)

## a boot

This is a Scala/SBT wrapper around my original Java [project](http://code.google.com/p/kuhn/). One day I may port it to pure Scala (a way better language for these kinds of problems) (or better yet, ProcessingJS!).

Clone this git repo.

``` bash
sbt run
```

## tour

blue1.png

Blue boids are the  grazers in this world. They travel together, moving slow. They feed on … well, I am not sure. The hungrier they get, the slower they get.

blue2.png

This poor group is looking very hungry, indeed.

orange1.png

Here we meet the orange boids. As you can see, they are terrifying hunters.

orange2.png

Here we see another grizzly scene. Notice the rotting bodies? Those are food.

green.png

The green boids. They are the scavengers. The flies. They buzz and swarm. And keep me up at night.

yellow.png

Now meet the loan hunter: the yellow boid. He (or she) is the top predator of this world.

## credits

kuhn.flock.Flock is based on the Processing example at
http://www.processing.org/learning/topics/flocking.html.

The Processing example is an implementation of the original
algorithm by Craig Reynold.
