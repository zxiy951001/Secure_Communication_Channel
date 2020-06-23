# Secure_Communication_Channel
Program allowing two parties to establish a secure communication channel

# Summary
UDP program written in java allowing two parties to establish a secure communication channel. Programs “Host” and “Client”, which are executed by Alice and Bob, respectively.
Alice and Bob share a common password PW, which contains at least 6 alphanumeric characters. 
Alice/Host stores the password in the hashed form (i.e., H(PW) where H denotes the SHA-1 hash function) and Bob/Client memorizes the password. They want to establish a secure communication channel that can provide data confidentiality and integrity. They aim to achieve this goal via the following steps: (1) use the shared password to establish a shared session key; (2) use the shared session key to secure the communication.

# Key Exchange Protocol 

1: B -> A: “Bob”

2: A -> B:p,g,ga modp

3: B -> A: E(H(PW), gb mod p)

4: A -> B:E(K,NA)

5: B -> A: E(K, NA+1)

6: A -> B: “Successful” (or “Unsuccessful”)



# READ ME:

**readme file can be found under "Secure_Communication_Channel" Folder


REFERENCE/SOURCE:


ENCRYPTION/DECRYPTION SOURCE CODE: 

https://stackoverflow.com/questions/12289717/rc4-encryption-java

SHA-1 HASH SOURCE CODE:

http://www.anyexample.com/programming/java/java_simple_class_to_compute_md5_hash.xml

