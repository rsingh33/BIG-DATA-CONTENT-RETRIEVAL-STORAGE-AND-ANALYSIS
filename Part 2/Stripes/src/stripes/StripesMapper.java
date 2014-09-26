package stripes;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class StripesMapper extends Mapper<LongWritable,Text,Text,MapWritable> {
	private MapWritable occurrenceMap = new MapWritable();
	private Text word = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		// int neighbors = context.getConfiguration().getInt("neighbors", 2);
		String[] tokens = value.toString().split("\\s+");
		String temp=""; 
		if (tokens.length > 1) {
			for (int i = 0; i < tokens.length; i++) {
				for(int j=0;j<tokens.length-1;j++)
				{
					if (tokens[j].compareTo(tokens[j+1])>0) /* For descending order use < */
					{
						temp       = tokens[j];
						tokens[j]   = tokens[j+1];
						tokens[j+1] = temp;
					}


				}
			}
		}


		if (tokens.length > 1) {
			for (int i = 0; i < tokens.length; i++) {
				if(tokens[i].length()>0)
				{
				if(tokens[i].substring(0,1).equals("#"))
				{
					word.set(tokens[i]);
					occurrenceMap.clear();

					for (int j = i+1; j < tokens.length; j++) {
						if(tokens[j].length()>0)
						{						if(tokens[j].substring(0,1).equals("#"))
						{
							if(tokens[j].equals(tokens[i]))
								continue;
							
							Text neighbor = new Text(tokens[j]);


							if(occurrenceMap.containsKey(neighbor)){

								DoubleWritable count = (DoubleWritable)occurrenceMap.get(neighbor);

								count.set(count.get()+1);
								occurrenceMap.remove(neighbor);
								occurrenceMap.put(neighbor, count);

							}
							else
							{

								occurrenceMap.put(neighbor,new DoubleWritable(1));
							}


						}

					}
				}
					context.write(word,occurrenceMap);
				}
			}
		}
	}
}

}
