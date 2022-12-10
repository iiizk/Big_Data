package cn.itcast.mr.invertedIndex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapred.FileInputFormat;
//import org.apache.hadoop.mapred.FileOutputFormat;
//JobConf是旧API使用的，而我们需要的是新API
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class InvertedIndexRunner {
    public static void main(String[] args) throws IOException,
            ClassNotFoundException,InterruptedException{
        Configuration conf=new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(InvertedIndexRunner.class);

        job.setMapperClass(InvertedIndexMapper.class);
        job.setCombinerClass(InvertedIndexCombiner.class);
        job.setReducerClass(InvertedIndexReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


//        FileInputFormat.setInputPaths(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\input"));
//        //指定处理完成之后的结果所保存的位置
//        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\output"));

        FileInputFormat.addInputPath(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\output"));
        //向yarn集群提交这个job
//        boolean res =job.waitForCompletion(true);
//        System.exit(res ? 0 : 1);
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
