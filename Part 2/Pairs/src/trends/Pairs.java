package trends;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Pairs implements Writable,WritableComparable<Pairs> {

    private Text word;
    private Text neighbor;

    public Pairs(Text word, Text neighbor) {
        this.word = word;
        this.neighbor = neighbor;
    }

    public Pairs(String word, String neighbor) {
        this(new Text(word),new Text(neighbor));
    }
    
    public void setWord(String word){
        this.word=new Text(word);
    }
    public void setNeighbor(String neighbor){
        this.neighbor=new Text(neighbor);
    }

    public Text getWord() {
        return word;
    }

    public Text getNeighbor() {
        return neighbor;
    }

    
    @Override
    public String toString() {
        return "word="+word+" neighbor="+neighbor;
    }

    public Pairs() {
        this.word = new Text();
        this.neighbor = new Text();
    }

    @Override
    public int compareTo(Pairs other) {
        int returnVal = this.word.compareTo(other.getWord());
        if(returnVal != 0){
            return returnVal;
        }
       if(this.neighbor.toString().equals("*")){
            return -1;
        }else if(other.getNeighbor().toString().equals("*")){
            return 1;
        }
        
        return this.neighbor.compareTo(other.getNeighbor());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        word.write(out);
        neighbor.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        word.readFields(in);
        neighbor.readFields(in);
    }
    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 163 * result + (neighbor != null ? neighbor.hashCode() : 0);
        return result;
    }

    }