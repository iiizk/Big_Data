package cn.itcast.mr.invertedIndex;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.io.IOException;
public class InvertedIndexMapper extends Mapper<LongWritable,
        Text, Text, Text>
{
//    public InvertedIndexMapper() {
//        super();
//    }
    private static Text keyInfo=new Text();//存储单词和URL组合
    private static final Text valueInfo=new Text("1");//存储词频，初始化为1

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        super.map(key, value, context);
        //key--k1,vaue--v1;mapreduce is simple
        String line= value.toString();
        String[] fields= StringUtils.split(line," ");
        //fields=["mapreduce","is","simple"]
        FileSplit fileSplit=(FileSplit) context.getInputSplit();//得到这行数据所在的运行切片
        String fileName=fileSplit.getPath().getName();//根据文件切片得到文件名
        //fileName="file1.txt"
        for (String filed:fields){
            //key值由单词和文件名组成，如“MapReduce:file1.txt”,k2值"1"
            keyInfo.set(filed+":"+fileName);
            context.write(keyInfo,valueInfo);//k2--keyInfo,v2--valueInfo
        }
    }
}