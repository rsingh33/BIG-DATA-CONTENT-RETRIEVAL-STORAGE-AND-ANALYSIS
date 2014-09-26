package stripes;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;


public class StripesReducer extends Reducer<Text, MapWritable, Text, Text> {
	private MapWritable OutPutMap = new MapWritable();

	@Override
	protected void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
		OutPutMap.clear();
		String output="";    
		double counter=0;
		for (MapWritable value : values) {

				Set<Writable> keys = value.keySet();
			for (Writable key1 : keys) {
				DoubleWritable fromCount = (DoubleWritable) value.get(key1);
				if (OutPutMap.containsKey(key1)) {
					DoubleWritable count = (DoubleWritable) OutPutMap.get(key1);
					count.set(count.get() + fromCount.get());
				} else {
					OutPutMap.put(key1, fromCount);
				}



			}
		}
		

		Set<Writable> keyset = OutPutMap.keySet();
		for (Writable key2 : keyset) {
			counter+=((DoubleWritable)OutPutMap.get(key2)).get();
		}
		
		Iterator<Map.Entry<Writable,Writable>> entries4;
		entries4 = OutPutMap.entrySet().iterator();
		while(entries4.hasNext()){
			Map.Entry<Writable,Writable> pairs = (Map.Entry<Writable,Writable>) entries4.next();
		output=" "+pairs.getKey()+" : "+" Count= "+pairs.getValue()+" : Relative Frequency= "+(((DoubleWritable)pairs.getValue()).get()/counter*1.0);
			
			
			if(output.contains("#"))
			{
				context.write(key, new Text(output));

			}
			

		}
		context.write(null, new Text("\n"));
	}
}
