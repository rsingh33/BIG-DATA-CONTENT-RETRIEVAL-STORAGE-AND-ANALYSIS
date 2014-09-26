package trends;
import java.io.IOException;
//import java.util.Iterator;
//import java.util.Map;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class PairsReducer extends Reducer<Pairs,DoubleWritable,Pairs,Text> {
    
    public class MyDataStructure {
    	   private String keystring;
    	   private int countvalue;

    	   public MyDataStructure(String keystring, int countvalue) {
    	       this.keystring = keystring;
    	       this.countvalue = countvalue;
    	   }
    	   
    	   public String getKey(){
    	        return keystring;
    	    }

   	    public int getCount() {
	        return countvalue;
	    }

    	}

    static List<MyDataStructure> map = new ArrayList<MyDataStructure>();

    @Override
    
    protected void reduce(Pairs key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
    
    	
    	int count = 0;
        for (DoubleWritable value : values) {
             count += value.get();
        }
        if(key.getNeighbor().toString().contains("*"))
        		{
        	map.add(new MyDataStructure(key.getWord().toString(),count));
        	
        		}
        else
        {
        Integer count2=0;
        Double count3;
        Text newText=new Text();
        for (MyDataStructure s : map)
            {
            if(s.getKey().equals(key.getWord().toString()))
            count2=count2+s.getCount();
            }
        		
        	count3=count*1.0/count2;
        	newText.set("Count= "+count+"  Relative Frequency= "+count3);
        	context.write(key,newText);
        count2=0;
        }
       }
    }
