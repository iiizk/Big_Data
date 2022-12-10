package cn.itcast.mr.invertedIndex;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class InvertedIndexCombiner extends Reducer<Text, Text, Text, Text> {
    private static Text info=new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        super.reduce(key, values, context);
        int sum=0;
        //key-k2=;value-v2="1"
        //<k2,v2>1<"MapReduce:file1.txt","1">
        //<k2,v2>2<"is:file1.txt","1">
        //<k2,v2>3<"simple:file1.txt","1">
        for(Text value:values){
            sum+=Integer.parseInt(value.toString());
        }//sum=1
        int splitIndex=key.toString().indexOf(":");//9
        info.set(key.toString().substring(splitIndex+1)+":"+sum);//v2 info="file1.txt:1"
        key.set(key.toString().substring(0,splitIndex));//key2="MapReduce"
        context.write(key,info);//k2',v2'
    }
}
