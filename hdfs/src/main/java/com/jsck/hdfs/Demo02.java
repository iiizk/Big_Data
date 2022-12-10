package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;

public class Demo02 {
    /*
     * 从HDFS中下载指定的文件，如果本地文件与要下载的文件名称相同，则自动对下载的文件重命名
     * shell语句 和JAVA API
     *
     * */
    /*if hadoop fs -test -e file:///usr/local­c/hadoop-2.7.3/test/1.txt;
       then hadoop fs -get /test01/1.txt ./2.txt;
       else hadoop fs -get /test01/1.txt ./1.txt;
       fi
    *
    * */
    public static void copyToLocal(Configuration conf,String remoteFilePath,
                                   String localFilePath) throws IOException {
        FileSystem fs =FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        File f =new File(localFilePath);
        //如果文件名存在，自动重命名（在文件名后面加上_0,_1,_2....）
        if (f.exists()){
            System.out.println(localFilePath+"已存在");
            //后面加的名字
            Integer i=1;
            while (true){
                f=new File(localFilePath+"_"+i.toString());
                if (!f.exists()){
                    localFilePath=localFilePath+"_"+i.toString();
                    break;
                }
            }
            System.out.println("将文件重命名为："+localFilePath);
        }
        //下载文件到本地
        Path localPath =new Path(localFilePath);
        fs.copyToLocalFile(remotePath,localPath);
        fs.close();

    }

    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.100.10:9000");
        //设置用户名为root
        System.setProperty("HADOOP_USER_NAME", "root");
        //本地路径window 上传文件
        String localFilePath = "D:\\hadoop\\1.txt";
        //HDFS路径
        String remotePath = "/test/1.txt";
        try {
            Demo02.copyToLocal(conf,remotePath,localFilePath);
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}