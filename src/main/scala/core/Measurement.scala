package core


/**
  * Created by nekomiko on 04.11.16.
  */
class Measurement(val id: Int, var name: String, var value: Int) {
  override def toString = id + ": " + name + " (" + value +")"

}
