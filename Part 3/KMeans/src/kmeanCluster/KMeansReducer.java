package kmeanCluster;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KMeansReducer extends Reducer<DoubleWritable, DoubleWritable, DoubleWritable, Text> {
	private static final transient Logger LOG = LoggerFactory.getLogger(KmeansDriver.class);
	public void reduce(DoubleWritable key, Iterable<DoubleWritable> values, Context context)
			throws IOException, InterruptedException {
		
		LOG.info("--------------IN REDUCER--------------");
		double count = 0;
		double sum = 0;
		double average = 0;
		//String toBeSend="";
		for (DoubleWritable val : values) {
			count++;
			double num = val.get();
           // toBeSend = toBeSend + ":" + val.toString();
			sum = sum + num;
			average = sum*0.1 / count;
		}
		
		if(key.get()!=average)
			context.getCounter(MyCounters.Counter).increment(1);
			
	context.write(new DoubleWritable(average),new Text(""));
		
	}

	
}
