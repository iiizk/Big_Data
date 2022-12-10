package com.jsck.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class HdfsClient{
    /*写了一个静态方法，通过类名·出来
     *如果不是，要实例化才能使用，
     * 什么是实例化？就是创建一个对象Configuration conf = new Configuration();
     *  new后面写什么该类的构造器
     *方法名为类名，而且方法没有返回值。并不是返回值为空
     *这个类中有几个构造器，那么就有几种方式去创建该类的对象
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        //该类的对象封装了客户端或者服务器的配置
        Configuration conf = new Configuration();

        /*第一种方法
        设置Configuration对象，
        设置的目的是来指定我们要操作的文件系统
         */
        conf.set("fs.defaultFS","hdfs://master:9000");
        /*该类的对象是一个文件系统对象，可以用该对象的一些方法来对
        文件进行操作，通过FileSystem的静态方法get获得该对象
         *get方法从conf中的一个参数fs.defaultFS的配置判断具体是什么类型的文件系统
         */
        /*
        获取我们指定的文件系统，获取FileSystem就相当于获取了主节点中所有的
        元数据信息
         */

        FileSystem fs = FileSystem.get(URI.create("hdfs://master:9000"),conf, "root");
        System.out.println(fs.toString());
        Path src=new Path("D:\\win_hadoop\\hadoop\\zk.txt");
        Path dst=new Path("/");
        fs.copyFromLocalFile(src,dst);

//        fs.copyFromLocalFile(new Path("D:\\win_hadoop\\hadoop\\zk.txt"),new Path("/"));

        //        删除
//        fs.delete(new Path("/zk.txt"),true);
        fs.close();



        /*第二种方法
        URI统一资源标识符
        9000 hdfs操作端口
        50070 web页面的端口
        FileSystem fs = FileSystem.get(URI.create("hdfs://master:9000"),
        conf, "root");
         */

        // fs.copyToLocalFile(new Path("/test"), new Path("/home/master/apps/a"));
        // fs.copyToLocalFile(new Path("/test"), new Path("d:\\JAVA"));
        //关闭，因为HDFS不支持并发写入，不关闭，其他人写不了
        //fs.close();


    }
}

