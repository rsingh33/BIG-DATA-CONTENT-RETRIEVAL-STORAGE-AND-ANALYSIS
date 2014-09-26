package trends;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class PairsPartitioner extends Partitioner<Pairs,DoubleWritable> {

	    @Override
	    public int getPartition(Pairs wordPair, DoubleWritable intWritable, int numPartitions) {
	        return Math.abs(wordPair.getWord().hashCode() % numPartitions);
	    }
	}
