package shortestpath;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import shortestpath.Node.MyCounters;


public class ShortestPathReducer 
extends Reducer<IntWritable,Text,IntWritable,Text> {

	public void reduce(IntWritable key, Iterable<Text>distances, Context context) throws IOException, InterruptedException {

		Node M=new Node();
		int dmin=125;

		for(Text s:distances)
		{
			if(s.toString().contains(" "))
			{
				String []parts=s.toString().split(" ");

				M.setAdjacency(parts[1].toString());
				if(Integer.parseInt(parts[0].toString())<dmin)
					dmin=Integer.parseInt(parts[0].toString());
			}
			else
			{
				if(Integer.parseInt(s.toString())<dmin)
				{
					dmin=Integer.parseInt(s.toString());

				}
			}
			//		context.write(key,new Text(M.toString()));
		}
		M.setDistance(dmin);
		if(M.getDistance().get()==125)
		{
			context.getCounter(MyCounters.Counter).increment(1);
		}

		context.write(key,new Text(M.toString()));
	}
}
