package trends;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PairsMapper extends Mapper<Object, Text, Pairs, DoubleWritable> {

	private Pairs group = new Pairs();
	private DoubleWritable ONE = new DoubleWritable(1);
	private DoubleWritable MANY = new DoubleWritable();
	int count9=0;
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {

		String[] result = value.toString().split("\\s");
		String temp=""; 
		if (result.length > 1) {
			for (int i = 0; i < result.length; i++) {
				for(int j=0;j<result.length-1;j++)
				{
					if (result[j].compareTo(result[j+1])>0)
					{
						temp       = result[j];
						result[j]   = result[j+1];
						result[j+1] = temp;
					}


				}
			}
		}

		if (result.length > 1) {
			for (int i = 0; i < result.length; i++) {
				if(result[i].length()>0)
				{
					if(result[i].substring(0,1).equals("#"))
					{
						count9=0;
						group.setWord(result[i]);
						for (int j = i+1; j <result.length; j++) {
							if(result[j].length()>0)
							{
								if(result[j].substring(0,1).equals("#"))
								{

									if(result[i].equals(result[j]))
									{
										continue;
									}
									else
									{
										count9++;


										group.setNeighbor(result[j]);
										context.write(group, ONE);
									}
								}
							}	}
						if(count9==0)
						{continue;}
						else
						{
							group.setNeighbor("*");
							MANY.set(count9*1.0);
							context.write(group,MANY );
						}
					}
				}
			}}
	}
}
