package kuhn

import processing.core._
import PConstants._

class Main extends PApplet {

	import PApplet._
	import PVector._

	val circle = Circle(new PVector(400, 400), new PVector(-1, 1))
	val follow = Follow(new PVector(500, 400), new PVector(-1, 1), circle)
	var population:Seq[Bug] = Seq(circle, follow)

	override def setup {
		size(800, 800, P2D)
		colorMode(HSB, 100)
		frameRate(30)
		smooth
	}

	override def draw {
		background(50, 50, 50)
//		translate(width / 2, height / 2)
//		scale(2)
//		translate(-width / 2, -height / 2)
		population = population.map(_.behave)
		population.foreach(_.draw)
	}

	trait Bug {
		val position:PVector
		val direction:PVector
		def draw {
			pushMatrix
			val (x, y) = (position.x, position.y)
			translate(x, y)
			rotate(direction.heading2D)

			fill(50)
			stroke(10)
			strokeWeight(6)

			line(0, 0, 40, 40)
			line(0, 0, 40, -40)
			ellipse(0, 0, 30, 30)

			popMatrix
		}
		def behave:Bug
	}

	case class Circle(position:PVector, direction:PVector) extends Bug {
		def behave = {
			copy(
				position = {
					val p = position.get
					p.add(direction)
					p
				},
				direction = {
					val d = direction.get
					d.rotate(PI / 120)
					d
				}
			)
		}
	}

	case class Follow(position:PVector, direction:PVector, target:Bug) extends Bug {
		def behave = {

			copy(
				position = {
					val p = position.get
					p.add(direction)
					p
				},
				direction = {
					val d = PVector.sub(population.head.position, position)
					d.normalize
					d
				}
			)
		}
	}

}

object Main {
	def main(args:Array[String]) {
		PApplet.main(Array("kuhn.Main"))
	}
}