package kmeanCluster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
//import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class KMeansMapper extends Mapper<Object, Text, DoubleWritable, DoubleWritable> {
	public void map(Object key, Text value, Context context) throws IOException,
			InterruptedException {
		
		FileSystem hdfs=FileSystem.get(new Configuration());
		
		BufferedReader br=null;
		Configuration conf = context.getConfiguration();
	    int thecounter = Integer.parseInt(conf.get("myvariable1"));
	
	    if(thecounter!=0)
		{
			Path newFolderPath=new Path("/outputforkmeans/outputfolder"+(thecounter-1));
			Path newFilePath=new Path(newFolderPath+"/part-r-00000");
			br = new BufferedReader(new InputStreamReader(hdfs.open(newFilePath)));

		}
		else
		{
			
			Path newFolderPath=new Path("/Centroids");
			Path newFilePath=new Path(newFolderPath+"/Centroid1.txt");
			br = new BufferedReader(new InputStreamReader(hdfs.open(newFilePath)));
			
		}
		String line;
		boolean status=false;
		ArrayList<Double> list = new ArrayList<Double>();
		while ((line = br.readLine()) != null) {
			list.add(Double.parseDouble(line));
		}
		br.close();
				//		hdfs.delete(newFilePath, true); 	
		if(list.size()<3)
		{status=true;
			list.add(list.get(0)+list.get(1)+200);
		}
		Double count = Double.parseDouble(value.toString());
		Double firstVal = Math.abs(count - list.get(0));
		Double secondVal = Math.abs(count - list.get(1));
		Double thirdVal = Math.abs(count - list.get(2));
		
		
		
		if(firstVal <= secondVal && firstVal <= thirdVal ){
			context.write(new DoubleWritable(list.get(0)), new DoubleWritable(count));
		}
		else if(secondVal <= thirdVal && secondVal <= firstVal ){
			context.write(new DoubleWritable(list.get(1)), new DoubleWritable(count));
		}
		else if(thirdVal <= firstVal && thirdVal <= secondVal ){
			if(status==true)
			{
				context.write(new DoubleWritable(list.get(2)), new DoubleWritable(list.get(2)));
			}
			context.write(new DoubleWritable(list.get(2)), new DoubleWritable(count));
		}

	}

	
}
