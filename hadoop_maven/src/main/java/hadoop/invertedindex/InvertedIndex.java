package hadoop.invertedindex;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class InvertedIndex {

    public static class Map extends Mapper<LongWritable, Text, Text, Text>{
        private static Text word = new Text();
        private static Text one = new Text();

        protected void map(LongWritable key, Text value, Context context)
                throws java.io.IOException ,InterruptedException {
            String fileName = ((FileSplit)context.getInputSplit()).getPath().getName();
            StringTokenizer st = new StringTokenizer(value.toString());
            while(st.hasMoreTokens()){
                word.set(st.nextToken()+"\t"+fileName);
                context.write(word, one);
            }
        };
    }
    public static class Combine extends Reducer<Text, Text, Text, Text>{
        private static Text word = new Text();
        private static Text index = new Text();

        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws java.io.IOException ,InterruptedException {
            String[] splits = key.toString().split("\t");
            if(splits.length != 2){
                return ;
            }
            long count = 0;
            for (Text v : values) {
                count++;
            }
            word.set(splits[0]);
            index.set(splits[1]+":"+count);
            context.write(word, index);
        };
    }
    public static class Reduce extends Reducer<Text, Text, Text, Text>{
        private static StringBuilder sub = new StringBuilder(256);
        private static Text index = new Text();

        protected void reduce(Text word, Iterable<Text> values, Context context)
                throws java.io.IOException ,InterruptedException {
            for (Text v : values) {
                sub.append(v.toString()).append(";");
            }
            index.set(sub.toString());
            context.write(word, index);
            sub.delete(0,sub.length());
        };
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
        if(otherArgs.length != 2){
            System.err.println("Usage:InvertedIndex");
            System.exit(2);
        }
        Job job = new Job(conf, "InvertedIndex");
        job.setJarByClass(InvertedIndex.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Combine.class);
        job.setReducerClass(Reduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\input"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\hadoop\\win_hadoop\\InvertedIndex\\output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}