package shortestpath;

/*import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
*/
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.Writable;
//import org.apache.hadoop.io.WritableComparable;

public class Node{
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
	}
