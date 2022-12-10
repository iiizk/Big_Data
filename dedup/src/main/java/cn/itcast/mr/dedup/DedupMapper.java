package cn.itcast.mr.dedup;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DedupMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    private static Text field=new Text();//v1
    //<0,"2018-3-1 a"><11,"2018-3-2 b">
    //<k1,v1>
    //k1-key;v1-value

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        super.map(key, value, context);
        field=value;//v1
        context.write(field,NullWritable.get());//k2="2018-3-1 a",v2=null;
    }
}
