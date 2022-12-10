package com.jsck.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //单词记录频次的功能
    //1.创建一个sparkconf对象
    val conf=new SparkConf().setAppName("WordCount")
    //2.创建spark和context对象,它是所有任务计算的源头
    val context =new SparkContext(conf)
    //3.业务逻辑:存放元数据的
    val value =context.textFile(args(0))
    //4.接风每一行获取所有的单词 首先拿到所有的value,然后以空格进行拆分,放的是每一个单词
    val words =value.flatMap(_.split(" "))
    //5.每个单词记为1，把集合中的每个单词转为(单词,1)
    val wordAndOne=words.map(x=>(x,1))
    //6.将相同的单词汇总
    val result =wordAndOne.reduceByKey((x,y)=>x+y)
    //7.在hdfs输出结果数据             //val finalResult=result.collect()    //println(finalResult.toBuffer)

    result.saveAsTextFile(args(1))
    //8.关闭context对象
    context.stop()
  }
}
