
package cn.itcast.mr.dedup;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

public class DedupDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);//Job和configuration绑定

        job.setJarByClass(DedupDriver.class);
        job.setMapperClass(DedupMapper.class);
        job.setReducerClass(DedupReduce.class);
        //设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        //设置输入和输出目录
        FileInputFormat.setInputPaths(job, new Path("D:\\hadoop\\win_hadoop\\dedup_file\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\win_hadoop\\dedup_file\\output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}