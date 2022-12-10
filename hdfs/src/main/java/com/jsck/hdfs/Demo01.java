package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileInputStream;
import java.io.IOException;

public class Demo01 {
    /*
    * shell操作
    *if
hadoop fs -test -e /test/1.txt
then
hadoop fs -appendToFile 1.txt /test/1.txt
else
hadoop fs -put -f 1.txt /test/1.txt
fi
    *
    *
    *if
hadoop fs -test -e /test/1.txt
then
hadoop fs -appendToFile 1.txt /test/1.txt
else
hadoop fs -put 0f 1.txt /test/1.txt
fi
    *
    * */
    //静态方法，胖墩路径是否存在
    public static boolean test(Configuration conf, String path) throws IOException {
        FileSystem fileSystem = FileSystem.get(conf);
        return fileSystem.exists(new Path(path));
    }

    public  static void copyFromLocalFile(Configuration conf,String localFilePath,
                                          String remoteFilePath) throws IOException{
        FileSystem fileSystem = FileSystem.get(conf);
        Path localpath= new Path(localFilePath);
        Path remotePath= new Path(remoteFilePath);
        //是否删除隐藏文件
        fileSystem.copyFromLocalFile(false,true,localpath,remotePath);
        fileSystem.close();
    }
    //    追加尔方法
    public  static void appendToFile(Configuration conf,String localFilePath,
                                     String remotrFilePath) throws IOException {
        FileSystem fileSystem =FileSystem.get(conf);
        Path remotePath = new Path(remotrFilePath);
//        创建一个文件读入流
        FileInputStream in = new FileInputStream(localFilePath);
//      创建一个文件输出流，输出的内容追加到文件末尾
        FSDataOutputStream out = fileSystem.append(remotePath);
//        读写文件内容  声明了一个字节数组，一次性读1024个字节
        byte[] data =new byte[1024];
        int read = -1 ;
        while ((read=in.read(data)) >0){
            out.write(data,0,read);
        }
        out.close();
        in.close();
        fileSystem.close();
    }
    //    psvm
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://master:9000");
        System.setProperty("HADOOP_USER_NAME","root");
//       本地路径
        String localFilePath="D:\\hadoop\\1.txt";

//       linux路径
//         String localFilePath="/usr/local/src/hadoop-2.7.3/test/1.txt";

//        HDFS路径
        String remoteFilePath = "/test/1.txt";
//        追加
        String choice = "append";
//        覆盖
//        String choice = "overwrite";
//       判断文件是否存在
        Boolean fileExists =false;
        if (Demo01.test(conf,remoteFilePath)){
            fileExists=true;
            System.out.println(remoteFilePath +"已存在");//sout
        }else{
            System.out.println(remoteFilePath +"不存在");
        }
//        进行处理
        if(!fileExists){
//            如果文件不存在
            Demo01.copyFromLocalFile(conf,localFilePath,remoteFilePath);
            System.out.println(localFilePath+"已上传至："+remoteFilePath);
        }else if(choice.equals("overwrite")){//选择的是覆盖
            Demo01.copyFromLocalFile(conf,localFilePath,remoteFilePath);
            System.out.println(localFilePath+"已覆盖至："+remoteFilePath);
        }else if(choice.equals("append")) {
            Demo01.appendToFile(conf,localFilePath, remoteFilePath);
            System.out.println(localFilePath + "已追加至：" + remoteFilePath);


        }
    }


}
