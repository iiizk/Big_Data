package example

object ListTest {
  def main(args: Array[String]): Unit = {
    val list=List(1,2,3,4,5,6,7,8,89,11)
    System.out.println(list.take(5))
    System.out.println(list.companion(0))
  }
}