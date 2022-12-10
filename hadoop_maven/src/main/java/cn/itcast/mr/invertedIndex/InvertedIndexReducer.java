package cn.itcast.mr.invertedIndex;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {
    private static Text result=new Text();
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
         super.reduce(key, values, context);
         //values
        // key-key2'="mapreduce" values-v2'={"file1.txt:1","file2.txt:1","file3.txt:2"}
         // 输入：<MapReduce file3:2>
        // 输出：<MapReduce file1:1;file2:1;file3:>;
        String fileList=new String();//用来存储v3的值
        for (Text value:values){
            fileList+=value.toString();
        }//fileList="file1.txt:1","file2.txt:1","file3.txt:2"
        result.set(fileList);
        context.write(key,result);

    }
}
