package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class Demo06 {
/**
 *提供一个HDFS内文件的路径，对该文件进行创建和删除操作。如果文件所在目录不存在。
 * 则自动创建目录
 *在test02中创建一个demo06的路径6.txt
 *test02/demo06 /6.txt
 * shell语句
 * if
 * hadoop fs -test -d /test02/demo6;
 * then hadoop fs  -touchz /test02/demo6/6.txt;
 * else hadoop fs -mkdir -p /test02/demo6 &&hadoop fs -touchz /test02/demo6/6.txt;
 * fi
 **/
//1判断路径是否存在
    public static boolean test(Configuration conf,String path) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        return fs.exists(new Path(path));

    }
    //2创建目录方法
    public static boolean mkdir(Configuration conf,String remoteDir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dirPath = new Path(remoteDir);
        boolean result=fs.mkdirs(dirPath);
        fs.close();
        return result;
    }
    //3删除文件的方法
    public static boolean rm(Configuration conf,String remoFIlePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoFIlePath);
        boolean delete = fs.delete(remotePath, false);
        fs.close();
        return delete;
    }
    //4创建文件的方法
    public static void touchz(Configuration conf,String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        FSDataOutputStream out = fs.create(remotePath);
        out.close();
        fs.close();

    }
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.100.10:9000");
        System.setProperty("HADOOP_USER_NAME","root");
        //HDFS路径的文件目录的路径
        String remoteFilePath="/test2/demo06/7.txt";
        //HDFS的目录
        String remoteDir="/test2/demo06";
        //判断路径或者文件是否存在，存在则删除，不存在则进行创建
        if (Demo06.test(conf,remoteFilePath)){
            Demo06.rm(conf,remoteFilePath);
            System.out.println("路径存在，进行删除："+remoteFilePath);
        }else {
            if (!Demo06.test(conf,remoteDir)){
                Demo06.mkdir(conf,remoteDir);
                System.out.println("文件已创建"+remoteDir);
            }
            Demo06.touchz(conf,remoteFilePath);
            System.out.println("文件已经创建完成"+remoteFilePath);
        }
    }
}
