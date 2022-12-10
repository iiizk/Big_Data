package com.jsck.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileInputStream;
import java.io.IOException;

public class Demo08 {
    /*
    * 向HDFS中指定的文件追加内容，由用户指定内容原有文件 开头或结尾
    * shell语句
    *   hadoop fs -appendToFile 1.txt /test02/1.txt
    * */
    //1判断路径是否存在
    public static boolean test(Configuration conf, String path) throws IOException {
        FileSystem fileSystem = FileSystem.get(conf);
        return fileSystem.exists(new Path(path));
    }
    //2.追加文本内容
    public static void appendContentToFile(Configuration conf,String content,String remoteFilePath) throws IOException {

        FileSystem fs = FileSystem.get(conf);
        Path remotePath =new Path(remoteFilePath);
        //创建一个文件输出流，输出的内容将追加到文件末尾
        FSDataOutputStream out = fs.append(remotePath);
        out.write(content.getBytes());
        out.close();
        fs.close();

    }
    //   3. 追加尔方法
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
        fileSystem.close();
    }
    //4.剪切，移动文件到本地，移动后删除源文件
    public static void moveToLocalFile(Configuration conf, String remoteFilePath, String remoteFilepath) throws IOException{
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoteFilepath);
        FSDataOutputStream out = fs.create(remotePath);
        out.close();
        fs.close();

    }

    //5.创建源文件
    public static void touchz(Configuration conf,String remoteFilePath) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path remotePath = new Path(remoteFilePath);
        FSDataOutputStream out = fs.create(remotePath);
        out.close();
        fs.close();

    }

    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://master:9000");
        System.setProperty("HADOOP_USER_NAME","root");
        //HDFS路径的文件目录的路径
        String remoteFilePath="/test02/1.txt";

        //要追加的内容
        String content = "hi126  \n" ;
//        String choice = "after";//表示追加到文件末尾
        String choice = "before";//表示追加到文件开头
        if (!Demo08.test(conf,remoteFilePath)){
            //表示文件不存在
            System.out.println("文件不存在："+ remoteFilePath);
        }else {
            if (choice.equals("after")){
                Demo08.appendContentToFile(conf,content,remoteFilePath);
                System.out.println("追加到文件末尾："+ remoteFilePath);
            }else if (choice.equals("before")){//表示追加到文件开头
                //创建一个临时的文件
                String localTmpPath = "/test02/tmp.txt";
                //2.把原来的文件剪切到这个临时文件
                Demo08.moveToLocalFile(conf,remoteFilePath,localTmpPath);
                //3.创建一个新的文件
                Demo08.touchz(conf,remoteFilePath);
                //4.先写入要追加的内容
                Demo08.appendContentToFile(conf,content,remoteFilePath);
                //5.再写入原来的内容
                Demo08.appendToFile(conf,localTmpPath,remoteFilePath);
                System.out.println("已追加到开头："+ remoteFilePath);
            }
        }
///////hadoop fs -cat /test02/1.txt   查看

    }

}