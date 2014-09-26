package shortestpath;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class Node implements Writable,WritableComparable<Node>{
	   private Text adjacencylist;
	   private IntWritable distance;
	   
	   public enum MyCounters {
		   Counter;
	   }
	   
	   public Node(Text adjacencylist, IntWritable distance) {
	       this.adjacencylist = adjacencylist;
	       this.distance = distance;
	   }
	   
	   
	   

	   public Node() {
	        this.adjacencylist = new Text();
	        this.distance = new IntWritable();
	    }

	    
	    public void setAdjacency(String adjacencylist){
	        this.adjacencylist.set(adjacencylist);
	    }
	    public void setDistance(int distance){
	        this.distance.set(distance);
	    }

	    public Text getAdjacency() {
	        return adjacencylist;
	    }

	    public IntWritable getDistance() {
	        return distance;
	    }

	    public String toString()
	    {
	    	return distance+" "+ adjacencylist;
	    }
		@Override
		public int compareTo(Node arg0) {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public void readFields(DataInput arg0) throws IOException {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void write(DataOutput arg0) throws IOException {
			// TODO Auto-generated method stub
			
		}
	}
