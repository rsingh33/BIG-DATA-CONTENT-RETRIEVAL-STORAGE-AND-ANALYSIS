package shortestpath;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shortestpath.Node.MyCounters;

public class ShortestPath {

	private static final transient Logger LOG = LoggerFactory.getLogger(ShortestPath.class);

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();		

		LOG.info("HDFS Root Path: {}", conf.get("fs.defaultFS"));
		LOG.info("MR Framework: {}", conf.get("mapreduce.framework.name"));
		/* Set the Input/Output Paths on HDFS */
		String inputPath = "/inputforSPSmallGraph/";
		String outputPath = "/outputforSPSmallGraph/";
		
		deleteFolder(new Configuration(), outputPath);
		/* FileOutputFormat wants to create the output directory itself.
		 * If it exists, delete it:
		 */
		deleteFolder(conf,outputPath);
		long counter = 1;
		long i=0;
		while(counter>0)
		{
		
			outputPath="/outputforSPSmallGraph/sample"+i;
		Job job = Job.getInstance(conf);
		job.setJarByClass(ShortestPath.class);
		job.setMapperClass(ShortestPathMapper.class);
		job.setReducerClass(ShortestPathReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		job.waitForCompletion(true);
		inputPath=outputPath;
		counter=job.getCounters().findCounter(MyCounters.Counter)
	    .getValue();
		i++;
		}
		System.exit(1);

		}
	
	
	/**
	 * Delete a folder on the HDFS. This is an example of how to interact
	 * with the HDFS using the Java API. You can also interact with it
	 * on the command line, using: hdfs dfs -rm -r /path/to/delete
	 * 
	 * @param conf a Hadoop Configuration object
	 * @param folderPath folder to delete
	 * @throws IOException
	 */
	private static void deleteFolder(Configuration conf, String folderPath ) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if(fs.exists(path)) {
			fs.delete(path,true);
		}
	}
}