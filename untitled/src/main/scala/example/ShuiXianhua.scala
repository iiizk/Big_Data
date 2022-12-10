package example

object ShuiXianhua {
  def main(args: Array[String]): Unit = {
    for ( i<-100 to 999){
      val a=i%10
      val b=i%100/10
      val c=i/100
      if (a*a*a + b*b*b + c*c*c ==i){
        System.out.println(i)
      }
    }
  }
}