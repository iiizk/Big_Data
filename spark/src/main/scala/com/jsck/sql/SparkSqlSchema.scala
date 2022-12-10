package com.jsck.sql

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}


/*
*编程的方式定义Schema
* 当case类不能提前定义的时候，就需要采用编程方式定义Schema信息排【
* 定义DaraFrame 主要包括3个步奏，具体如下：
* 1.创建一个ROW对象结构的RDD
* 2.基于Structtype类型创建Schema
* 3.通过SparkSession提供的createDataframe()方法拼接Schema
 */
object SparkSqlSchema {
  def main(args: Array[String]): Unit = {
    //1.构建SparkSession
    val spark:SparkSession=SparkSession.builder()
      .appName("SparkSqlSchema")//指定业务的名字，和类名一样
      .master("local[2]")//表示本地模式启动，并启动俩个线程
      .getOrCreate()//创建SparkSession对象
    // 2.获取SparkContext对象
    val sc:SparkContext=spark.sparkContext
    //设置日志打印级别。设置程警告级别
    sc.setLogLevel("WARN")
    //3.加载数据
    val dataRDD:RDD[String]=sc.textFile("D:\\hadoop\\win_hadoop\\person.txt")
    //4.切片每一行
    val dataArrayRDD:RDD[Array[String]]=dataRDD.map(_.split(" "))
    //5.加载数据到rowd对象中
    val personRDD:RDD[Row]=dataArrayRDD.map(x=>Row(x(0).toInt,x(1),x(2).toInt))
    //6.创建Schema
    val schema:StructType=StructType(Seq(
      StructField("id",IntegerType,false),
      StructField("name",StringType,false),
      StructField("age",IntegerType,false)
    ))
    //7.利用personRDD与schema创建DataFrame
    val personDF:DataFrame=spark.createDataFrame(personRDD,schema)
    //8.DSL操作
    println("----------DSL----------")
    personDF.show()
    //9.SQL风格
    println("----------SQL----------")
    //蒋DataFrame注册成临时表
    personDF.createOrReplaceTempView("t_person")
    spark.sql("select * from t_person").show()
    //10.关闭资源
    sc.stop()
    spark.stop()
  }
}
