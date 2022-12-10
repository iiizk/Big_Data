package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Demo03 {



    //读取文件内容的方法
    public static void cat(Configuration conf, String remoteFilePath) throws IOException {
        FileSystem fs =FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        FSDataInputStream in = fs.open(remotePath);
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        String line =null;
        while ((line= d.readLine()) !=null){
            System.out.println(line);
        }
        d.close();
        in.close();
        fs.close();

    }
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://master:9000");
        //设置用户名为root
        System.setProperty("HADOOP_USER_NAME", "root");
        //HDFS路径
        String remoteFilePath = "/test/1.txt";

        System.out.println("开始读取文件:"+remoteFilePath);
        Demo03.cat(conf,remoteFilePath);
        System.out.println("读取完成");
    }
}
