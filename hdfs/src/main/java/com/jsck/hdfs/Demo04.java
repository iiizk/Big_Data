package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Demo04 {
    //显示HDFS中指定的文件的读写权限  大小 创建时间 路径等信息。
    //显示指定文件信息
    public static void ls(Configuration conf,String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        //查看目录
        FileStatus[] fileStatuses = fs.listStatus(remotePath);
        //遍历,写循环  foreacf  快捷键 iter
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println("路径:"+fileStatus.getPath().toString());
            System.out.println("权限："+fileStatus.getPermission().toString());
            System.out.println("大小："+fileStatus.getLen());
            long time=fileStatus.getModificationTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date =format.format(time);
            System.out.println("创建时间为:"+date);

        }
        fs.close();

    }
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.100.10:9000");
        System.setProperty("HADOOP_USER_NAME","root");
        //HDFS路径
        String remoteFilePath="/test02/1.txt";
        System.out.println("开始读取文件信息："+remoteFilePath);
        Demo04.ls(conf,remoteFilePath);
        System.out.println("读取完成");
    }
}
