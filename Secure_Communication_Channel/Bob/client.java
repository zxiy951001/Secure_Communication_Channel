import java.net.*; 
import java.util.*;
import java.io.*;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import static java.lang.System.*;


public class client { 
	public static void main(String[] args) throws IOException,NoSuchAlgorithmException, UnsupportedEncodingException
	{ 
		try { 
			String Astr; 
			String serverName = "127.0.0.1"; 
			int port = 8088; 
			
			String receiveAtoB;
			
			// Client Key 
			int Y = new Random().nextInt((10 - 2) + 1) + 2;
 
			int p, g, clientA, BtoA, pw;
			int shared_key;
			String strp,strg,strpw; 
			
			FileReader fr = new FileReader("Client.txt");	
			BufferedReader br = new BufferedReader(fr);

			strpw = br.readLine().replaceAll("\\s",""); 
			strp = br.readLine().replaceAll("\\s",""); 
			strg = br.readLine().replaceAll("\\s","");
			br.close();
			
			pw = Integer.parseInt(strpw); 
			p = Integer.parseInt(strp);
			g = Integer.parseInt(strg);
			 
			
			
			
			
			// Establish connection 
			System.out.println("Connecting to " + serverName 
							+ " on port " + port); 
			Socket client = new Socket(serverName, port); 
			System.out.println("Host Connected to "
							+ client.getRemoteSocketAddress()); 

			// Accept data 
			DataInputStream in = new DataInputStream(client.getInputStream()); 

			receiveAtoB = in.readUTF();
			
			//decrypt AtoB
			String[] decrypAtoB = new String[] {receiveAtoB,strpw};
			int decrypt_AB[] = decrypt.main(decrypAtoB); 

			BtoA = (int)((Math.pow(g, Y)) % p); // calculation of B 
			Astr = String.valueOf(BtoA);
			
		
			String[] encryp_para = new String[] {Astr,strpw}; //encrypt AtoB;
			int encrypt_BA[] = encrypt.main(encryp_para);
			
		
			char enc[] = new char[encrypt_BA.length];
			for (int i = 0; i < encrypt_BA.length; i++)
				enc[i] = (char)encrypt_BA[i];
			String enc2 = new String(enc);
			
			// Sends BtoA to Host 
			OutputStream outToServer = client.getOutputStream(); 
			DataOutputStream out = new DataOutputStream(outToServer); 

			 
			out.writeUTF(enc2);
			
			
			char dec_AB[] = new char[decrypt_AB.length];
			for (int i = 0; i < decrypt_AB.length; i++)
				dec_AB[i] = (char)decrypt_AB[i];
			String dec_AB2 = new String(dec_AB);
			int dec_AB3 = Integer.parseInt(dec_AB2); //convert string to in

			shared_key = (int)Math.pow(dec_AB3, Y) % p;

			String skey = String.valueOf(shared_key);
			String HashKey =  Hashing.SHA1(skey);
			
			System.out.println("\nSecure connection established!\n");
			
			Scanner sc = new Scanner(System.in);
		
        		String msg="";
        		String H;
        		
			do{
				
				//send message to host
				System.out.println("\nEnter your message    ('exit' to quit):");

        			msg = sc.nextLine();

        			H = Hashing.SHA1(HashKey+"#"+msg);

        			String[] encryptC = new String[] {(msg+"#"+H),strpw}; 
				int encrypt_C[] = encrypt.main(encryptC);
				
				//code for decrypt
				char encC[] = new char[encrypt_C.length];
				for (int i = 0; i < encrypt_C.length; i++)
					encC[i] = (char)encrypt_C[i];
				String encC2 = new String(encC);
			
				
				out.writeUTF(encC2); // Sending AtoB

				//Receive message
				msg = in.readUTF();
				
				if(msg.equals("exit")){
					System.out.println("\nExit Program");
					break; 
				}
				
				String[] decrypC = new String[] {msg,strpw}; //encrypt for AtoB;
				int decryt_C[] = decrypt.main(decrypC);
			
				//code for decrypt
				char dec[] = new char[decryt_C.length];
				for (int i = 0; i < decryt_C.length; i++)
					dec[i] = (char)decryt_C[i];
				String dec2 = new String(dec);

				String[] MsplitH=dec2.split("#");
				String checkH = Hashing.SHA1(HashKey+"#"+MsplitH[0]);

				if(checkH.equals(MsplitH[1])){
					if(MsplitH[0].equals("exit")){
						System.out.println("\nExit Program");
						out.writeUTF("exit");
						break; 
					}
        					
					System.out.println("\nMessage from Host: " + MsplitH[0]); 
				}
				else{
					System.out.println("\nError");
				}
				
				
				
			}while(!(msg.equals("exit")));
			 
			client.close(); 
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	static void sendMessage(String HashKey){
	
	
	}
}

class decrypt
{
	public static int[] main(String args[])throws IOException
	{
	int temp=0;
	String ptext = args[0];
	String key = args[1];
	int s[]=new int[256];
	int k[]=new int[256];


	char ptextc[]=ptext.toCharArray();
	char keyc[]=key.toCharArray();
	int cipher[]=new int[ptext.length()];
	int decrypt[]=new int[ptext.length()];

	int ptexti[]=new int[ptext.length()];
	int keyi[]=new int[key.length()];
		for(int i=0;i<ptext.length();i++)
		{
			ptexti[i]=(int)ptextc[i];
		}
		for(int i=0;i<key.length();i++)
		{
			keyi[i]=(int)keyc[i];
		}
		for(int i=0;i<255;i++)
		{
			s[i]=i;
			k[i]=keyi[i%key.length()];
		}
		int j=0;
		for(int i=0;i<255;i++)
		{
			j=(j+s[i]+k[i])%256;
			temp=s[i];
			s[i]=s[j];
			s[j]=temp;
		}
		int i=0;
		j=0;
		int z=0;
		for(int l=0;l<ptext.length();l++)
		{
			i=(l+1)%256;
			j=(j+s[i])%256;
			temp=s[i];
			s[i]=s[j];
			s[j]=temp;
			z=s[(s[i]+s[j])%256];
			cipher[l]=z^ptexti[l];
		}

	return cipher;
}

	static void display(int disp[])
	{
		char convert[]=new char[disp.length];
		for(int l=0;l<disp.length;l++)
		{
			convert[l]=(char)disp[l];
			System.out.print(convert[l]);
		}
	}

}  

class encrypt
{
	public static int[] main(String args[])throws IOException
	{
	int temp=0;
	String ptext = args[0];
	String key = args[1];
	int s[]=new int[256];
	int k[]=new int[256];

	char ptextc[]=ptext.toCharArray();
	char keyc[]=key.toCharArray();
	int cipher[]=new int[ptext.length()];
	int decrypt[]=new int[ptext.length()];

	int ptexti[]=new int[ptext.length()];
	int keyi[]=new int[key.length()];
		for(int i=0;i<ptext.length();i++)
		{
			ptexti[i]=(int)ptextc[i];
		}
		for(int i=0;i<key.length();i++)
		{
			keyi[i]=(int)keyc[i];
		}
		for(int i=0;i<255;i++)
		{
			s[i]=i;
			k[i]=keyi[i%key.length()];
		}
		int j=0;
		for(int i=0;i<255;i++)
		{
			j=(j+s[i]+k[i])%256;
			temp=s[i];
			s[i]=s[j];
			s[j]=temp;
		}
	int i=0;
	j=0;
	int z=0;
		for(int l=0;l<ptext.length();l++)
		{
			i=(l+1)%256;
			j=(j+s[i])%256;
			temp=s[i];
			s[i]=s[j];
			s[j]=temp;
			z=s[(s[i]+s[j])%256];
			cipher[l]=z^ptexti[l];
			
		}
	return cipher;
}

static void display(int disp[])
{
	char convert[]=new char[disp.length];
		for(int l=0;l<disp.length;l++)
		{
			convert[l]=(char)disp[l];
			System.out.print(convert[l]);
		}
	}

}
 

