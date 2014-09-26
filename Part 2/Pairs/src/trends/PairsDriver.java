package trends;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.MapWritable;
//import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class PairsDriver {
	//private static final transient Logger LOG = LoggerFactory.getLogger(CoOccurrenceDriver.class);
	public static void main(String[] args) throws Exception {
		String inputPath = "/newinput";
		String outputPath = "/newoutput";
		deleteFolder(new Configuration(), outputPath);

		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(PairsDriver.class);

		job.setMapperClass(PairsMapper.class);
		//job.setCombinerClass(TrendingReducer.class);
		job.setNumReduceTasks(3);
		job.setPartitionerClass(PairsPartitioner.class);
		job.setReducerClass(PairsReducer.class);
		job.setMapOutputKeyClass(Pairs.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		job.setOutputKeyClass(Pairs.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

	private static void deleteFolder(Configuration conf, String folderPath)
			throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if (fs.exists(path)) {
			fs.delete(path, true);
		}
	}

}
