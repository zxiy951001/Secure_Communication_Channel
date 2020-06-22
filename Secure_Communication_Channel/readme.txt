READ ME:
=========================================================================================================
REFERENCE/SOURCE:

ENCRYPTION/DECRYPTION SOURCE CODE: https://stackoverflow.com/questions/12289717/rc4-encryption-java

SHA-1 HASH SOURCE CODE:
http://www.anyexample.com/programming/java/java_simple_class_to_compute_md5_hash.xml


=========================================================================================================
STEP 1: (IMPORTANT)
CHANGE WRITE DIRECTORY OF CLIENT TEXT FILE:
1: OPEN UP GEN.JAVA
2: CHANGE FROM :File destinationFile = new File("/home/vmw_ubuntu/Desktop/Assn1/Bob/Client.txt");
   TO	       :File destinationFile = new File("/YOURPATH/Assn1/Bob/Client.txt");



STEP 2:
RUN GEN FILE.
----> $ cd /yourpathfile/Alice
----> $ java Gen
1: CHECK THAT THERE'S A CLIENT.TXT FILE IN BOB'S DIRECTORY (IF THERE ISN'T, GO BACK TO STEP 1 AND RECHECK YOUR DIRECTORY.)
2: RE-CHECK YOUR PATHFILE DIRECTORY IN STEP 1
3: NAVIGATE TO ALICE'S DIRECTORY AND OPEN UP HOST.TXT AND REMOVE THE LAST LINE(SHARED KEY).
4: REPEAT STEP 2.

STEP 3:
RUN HOST FILE.
----> $ cd /yourpathfile/Alice
----> $ java host

STEP 4:
RUN CLIENT FILE.
----> $ cd /yourpathfile/Bob
----> $ java client
