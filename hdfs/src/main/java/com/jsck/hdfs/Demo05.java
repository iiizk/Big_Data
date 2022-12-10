package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Demo05 {
    /*给定HDFS某一个目录，输出该目录所有文件的读写权限、大小、创建时间
     * 、路径等信息，如果文件是目录，则递归输出该目录信息
     * hadoop fs -ls -R -h /test02*/
    //显示指定文件夹下所用文件信息
    public static void lsDir(Configuration conf,String remoteDie) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dirPath = new Path(remoteDie);
        //递归获取目录下的所有文件
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
        //输出每个文件的信息
        while (remoteIterator.hasNext()){
            FileStatus s = remoteIterator.next();
            System.out.println("路径：" + s.getPath().toString());
            System.out.println("权限：" + s.getPermission().toString());
            System.out.println("大小：" + s.getLen());
            long time = s.getModificationTime();
            SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            String date = format.format(time);
            System.out.println("创建时间为:" + date);
        }
        fs.close();
    }

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.100.10:9000");
        System.setProperty("HADOOP_USER_NAME","root");
        //HDFS路径
        String remoteFilePath="/test02";
        System.out.println("开始读取文件信息："+remoteFilePath);
        Demo05.lsDir(conf,remoteFilePath);
        System.out.println("读取完成");
    }
}
