package kmeanCluster;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KmeansDriver {
	public static int globalcounter;
	
	public static void main(String[] args) throws Exception {

		String outputPath = "/outputforkmeans";
		deleteFolder(new Configuration(), outputPath);
		String inputPath = "/inputforkmeans";
		long counter;
		do
		{
			Configuration conf = new Configuration();

			conf.set("myvariable1", globalcounter+"");
			outputPath="/outputforkmeans/outputfolder"+KmeansDriver.globalcounter;
			Job job = Job.getInstance(conf);
			job.setJarByClass(KmeansDriver.class);
			job.setMapperClass(KMeansMapper.class);
			job.setReducerClass(KMeansReducer.class);
			job.setMapOutputKeyClass(DoubleWritable.class);
			job.setMapOutputValueClass(DoubleWritable.class);
			job.setOutputKeyClass(DoubleWritable.class);
			job.setOutputValueClass(Text.class);
			FileInputFormat.addInputPath(job, new Path(inputPath));
			FileOutputFormat.setOutputPath(job, new Path(outputPath));
			job.waitForCompletion(true);
			counter=job.getCounters().findCounter(MyCounters.Counter).getValue();
			job.killJob();
			KmeansDriver.globalcounter++;
		}while(counter>0);

		System.exit(1);

	}
 
	private static void deleteFolder(Configuration conf, String folderPath ) throws IOException {
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path(folderPath);
		if(fs.exists(path)) {
			fs.delete(path,true);
		}
	}
	
	

}
