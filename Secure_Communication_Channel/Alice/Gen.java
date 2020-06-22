import java.net.*; // providing java.net packages
import java.util.*; // providing java.util packages
import java.lang.Math; // to do maths computation (which includes import.java.math.BigInteger;)
import java.io.*;
import static java.lang.System.*;

public class Gen
{
	public static void main(String[] args)throws IOException
	{
		try
		{
			FileReader fr = new FileReader("Host.txt");	
			BufferedReader br = new BufferedReader(fr);
		
			String str1; // PW
			String str2; // safe prime p

			str1 = br.readLine(); // compute line 1 and put PW into str1
			str2 = br.readLine(); // compute line 2 and put p into str2

			br.close();			
			int newstr2 = Integer.parseInt(str2); // converting str p to int p 
			
			int min = 2;
			int max = (newstr2)-2;

			Random random = new Random();
			int primitivecheck = 1;


			while (primitivecheck ==1)
			{		
				int g = (random.nextInt((max-min)+1)+min); // 0-1000000005
				double primitivecheck2 = (Math.pow(g,((newstr2-1)/2))) % newstr2; 
				primitivecheck = (int)primitivecheck2;

				if (primitivecheck !=1)
				{
					String strg = Integer.toString(g);
				
					/* Appends "g" number into Host.txt */
					BufferedWriter bw = null;		
					FileWriter fw = null;	
		
					String filename = "Host.txt";
					File file = new File(filename);
					fw = new FileWriter("Host.txt",true);
					bw = new BufferedWriter(fw);
					
					
					bw.write(strg);
					bw.close();
					
					copyFile();	//copys pw,p,g value to client folder
	
				}
			
			}
		
		}

		catch (IOException e)
		{

			System.out.println("Error");

		}
	}
	
	public static void copyFile(){
		try
		{
		
			File sourceFile = new File("Host.txt");
			File destinationFile = new File("/home/vmw_ubuntu/Music/Assn1/Bob/Client.txt");

			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			FileOutputStream fileOutputStream = new FileOutputStream(
					destinationFile);

			int bufferSize;
			byte[] bufffer = new byte[512];
			while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
			    fileOutputStream.write(bufffer, 0, bufferSize);
			}
			fileInputStream.close();
			fileOutputStream.close();
		}

		catch (IOException e)
		{

			System.out.println("Error");

		}
	}

}




















