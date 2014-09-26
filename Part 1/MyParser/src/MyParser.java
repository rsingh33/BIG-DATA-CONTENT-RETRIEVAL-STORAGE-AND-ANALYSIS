
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MyParser
{

	public static void main(String[] args) throws FileNotFoundException,
	IOException {

		File folder = new File("/home/aditya/Desktop/Parsing/input/");
		File[] filesList = folder.listFiles();
		PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("/home/aditya/Desktop/Parsing/output/HashTags.txt", true)));
		PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("/home/aditya/Desktop/Parsing/output/Users.txt", true)));
		PrintWriter out3 = new PrintWriter(new BufferedWriter(new FileWriter("/home/aditya/Desktop/Parsing/output/MostOccuringWords.txt", true)));
		PrintWriter out4 = new PrintWriter(new BufferedWriter(new FileWriter("/home/aditya/Desktop/Parsing/output/trends.txt", true)));


		for (int i = 0; i < filesList.length; i++) {

			FileReader file = new FileReader("/home/aditya/Desktop/Parsing/input/" + filesList[i].getName().toString());
			BufferedReader reader = new BufferedReader(file);


			String fromFile = reader.readLine();
			while (fromFile != null) {
				String[] array = fromFile.split("\\s");

				if(array[0].startsWith("@"))
					out2.println(array[0]+" : "+array[1]);		
				else
				{
					out4.println(array[0]+ " : " + array[1]);

					if(array[0].contains("#"))
						out1.println(array[0]+" : "+array[1]);

					else
						out3.println(array[0]+" : "+array[1]);
				}
				fromFile = reader.readLine();	  
			}
			reader.close();
		}
		out1.close();
		out2.close();
		out3.close();
		out4.close();
	}
}
