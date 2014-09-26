
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortDocument
{

	public static void main(String[] args) throws FileNotFoundException,
	IOException {

		class MyDataStructure {
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
			 
			@Override
		        public String toString() {
		            return keystring+ "    "+countvalue;
		        }

		}

		
		File folder = new File("/home/aditya/Desktop/Sorting/Input/");
		File[] filesList = folder.listFiles();

		for (int i = 0; i < filesList.length; i++) {
			
			PrintWriter pw=new PrintWriter("/home/aditya/Desktop/Sorting/Output/output"+i+".txt");
			FileReader file = new FileReader("/home/aditya/Desktop/Sorting/Input/" + filesList[i].getName().toString());
			BufferedReader reader = new BufferedReader(file);
			List<MyDataStructure>map =new ArrayList<MyDataStructure>();
			

			String fromFile = reader.readLine();
			while (fromFile != null) {
				fromFile=fromFile.replace(":", "");
				String[] array = fromFile.split("\\s+");
				map.add(new MyDataStructure(array[0], Integer.parseInt(array[1])));
				fromFile=reader.readLine();
			}
			
			Collections.sort(map, new Comparator<MyDataStructure>() {
		        @Override
		        public int compare(MyDataStructure  entry1, MyDataStructure  entry2)
		        {
		        	if(entry1.countvalue>entry2.countvalue)
		            return  1;
		        	else if(entry1.countvalue==entry2.countvalue)
		        		return 0;
		        	else return -1;
		        }
		    });
			
			for(int xyz=map.size()-1;xyz>=0;xyz--)
				pw.println(map.get(xyz));
				
			pw.close();
			reader.close();
		}
		

	}
	
}