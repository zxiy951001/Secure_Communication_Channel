import java.net.*; 
import java.util.*;
import java.io.*;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.util.Scanner; 
import static java.lang.System.*;


public class host { 
	public static void main(String[] args) throws IOException,NoSuchAlgorithmException, UnsupportedEncodingException
	{ 
		try { 
			int port = 8088; 

			// Host Key 
			int X = new Random().nextInt((10 - 2) + 1) + 2;
			// Client p, g, and key 
			int p, g, clientA, AtoB,pw;
			int shared_key;
			String strp,strg,strpw; 
			String Bstr;
			
			
			//to read computed hosts PW, p and g from text file
			FileReader fr = new FileReader("Host.txt");	
			BufferedReader br = new BufferedReader(fr);

			strpw = br.readLine().replaceAll("\\s",""); 
			strp = br.readLine().replaceAll("\\s",""); 
			strg = br.readLine().replaceAll("\\s","");
			br.close();
			
			pw = Integer.parseInt(strpw); // converting str p to int p
			p = Integer.parseInt(strp);
			g = Integer.parseInt(strg); 
			
			
			// Established Connection 
			ServerSocket serverSocket = new ServerSocket(port); 
			System.out.println("Waiting for client on port: " + serverSocket.getLocalPort()); 
			Socket server = serverSocket.accept(); 
			System.out.println("Client Connected to: " + server.getRemoteSocketAddress()); 

			// calculate A to B
			AtoB = (int)((Math.pow(g, X)) % p); 
			Bstr = String.valueOf(AtoB);
			
			//encrypt AtoB;
			String[] encryp_para = new String[] {Bstr,strpw}; 
			int encrypt_AB[] = encrypt.main(encryp_para);
			
			
			//convert encrypted value to String
			char enc[] = new char[encrypt_AB.length];
			for (int i = 0; i < encrypt_AB.length; i++)
				enc[i] = (char)encrypt_AB[i];
			String enc2 = new String(enc);

			//Sending AtoB value to Client
			OutputStream outToclient = server.getOutputStream(); 
			DataOutputStream out = new DataOutputStream(outToclient); 
			out.writeUTF(enc2);
			
			
			//Accepts data from client 
			DataInputStream in = new DataInputStream(server.getInputStream()); 
			String receiveBtoA = in.readUTF(); // to accept BtoA 
			
			String[] decrypBtoA = new String[] {receiveBtoA,strpw};
			int decrypt_BA[] = decrypt.main(decrypBtoA);
			
			char dec_BA[] = new char[decrypt_BA.length];
			for (int i = 0; i < decrypt_BA.length; i++)
				dec_BA[i] = (char)decrypt_BA[i];
			String dec_BA2 = new String(dec_BA);
			int dec_BA3 = Integer.parseInt(dec_BA2); 
			
			//Shared key calculation
			shared_key = (int)Math.pow(dec_BA3, X) % p;
			String skey = String.valueOf(shared_key);
			String HashKey =  Hashing.SHA1(skey);
			
			System.out.println("\nSecure connection established!\n");
			
			Scanner sc = new Scanner(System.in);
		
        		String msg="";
        		String H;

        		do{
        			//Receive message
				msg = in.readUTF();
				
				
        				String[] decrypC = new String[] {msg,strpw}; //encrypt for AtoB;
					int decryt_C[] = decrypt.main(decrypC);
			
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
						System.out.println("\nMessage from Client: " + MsplitH[0]); 
					}
					else{
						System.out.println("\nError");
					}

        		
        			System.out.println("\nEnter your message (type 'exit' to quit):");
        			msg = sc.nextLine();
        			
        			if(msg.equals("exit")){
						System.out.println("\nExit Program");
						out.writeUTF("exit");
						break; 
				}
        			
        			H = Hashing.SHA1(HashKey+"#"+msg);
        			
        			String[] encryptC = new String[] {(msg+"#"+H),strpw}; 
				int encrypt_C[] = encrypt.main(encryptC);

				char encC[] = new char[encrypt_C.length];
				for (int i = 0; i < encrypt_C.length; i++)
					encC[i] = (char)encrypt_C[i];
				String encC2 = new String(encC);

				out.writeUTF(encC2); 
					
        		
        		}while(!(msg.equals("exit")));

		} 
		catch (SocketTimeoutException s) { 
			System.out.println("Socket timed out!"); 
		} 
		catch (IOException e) { 
		} 
	}
	

	
}


//Encrytion 
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
		//decrypt[l]=z^cipher[l];
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

//Decryption 
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

//SHA-1 Hashing 
class Hashing { 
 
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
 
    public static String SHA1(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
    MessageDigest md;
    md = MessageDigest.getInstance("SHA-1");
    byte[] sha1hash = new byte[40];
    md.update(text.getBytes("iso-8859-1"), 0, text.length());
    sha1hash = md.digest();
    return convertToHex(sha1hash);
    } 
}   

