package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

public class Demo07 {
    //1判断路径是否存在
    public static boolean test(Configuration conf, String path) throws IOException {
        FileSystem fileSystem = FileSystem.get(conf);
        return fileSystem.exists(new Path(path));
    }
    //2判断目录是否为空
    public static boolean isDirEmpty(Configuration conf,String remoteDir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dirPath = new Path(remoteDir);
        //true 空；false 非空
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(dirPath, true);
        return !remoteIterator.hasNext();

    }
    //3创建目录
    public static boolean mkdir(Configuration conf,String remoteDir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dirPath = new Path(remoteDir);
        boolean b=fs.mkdirs(dirPath);
        fs.close();
        return b;
    }
    //4删除目录
    public static boolean rmDir(Configuration conf,String remoteDir) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path dirPath = new Path(remoteDir);
        //第二个参数，表示是否递归删除
        boolean delete = fs.delete(dirPath, true);
        fs.close();
        return delete;
    }

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.100.10:9000");
        System.setProperty("HADOOP_USER_NAME","root");
        //HDFS路径的文件目录的路径
        String remoteFilePath="/test02/demo06";
        //是否强制删除
        Boolean forceDelete=false;
        if (!Demo07.test(conf,remoteFilePath)){
            Demo07.mkdir(conf,remoteFilePath);
            System.out.println("目录创建完毕："+remoteFilePath);
        }else {
            //目录为空的时候或者强制删除
            if (Demo07.isDirEmpty(conf,remoteFilePath)||forceDelete){
                Demo07.rmDir(conf,remoteFilePath);
                System.out.println("目录已删除完毕");
            }else{
                System.out.println("目录不为空，不删除："+remoteFilePath);
            }
        }
    }
}

