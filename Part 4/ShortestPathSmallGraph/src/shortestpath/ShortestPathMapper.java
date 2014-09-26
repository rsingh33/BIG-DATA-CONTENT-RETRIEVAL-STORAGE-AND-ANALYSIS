package shortestpath;

import java.io.IOException;



//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



public class ShortestPathMapper extends Mapper<Object, Text, Text, Text> {
	//private static final transient Logger LOG = LoggerFactory.getLogger(ShortestPath.class);

	private Node node = new Node();
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] result = value.toString().split("\\s+");
		if(result.length>2)
		{

			int distance=Integer.parseInt(result[1]);
			String[] path=result[2].split(":");
			node.setAdjacency(result[2]);
			node.setDistance(distance);
			context.write(new Text(result[0]),new Text(node.toString()));

			for(int i=0;i<path.length;i++)
			{
				context.write(new Text(path[i]), new Text((distance+1)+""));
			}
		}
	}
}