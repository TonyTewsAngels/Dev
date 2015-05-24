package teacheasy.login;

import java.io.*;

public class fileReader {
 
    public static String main(String[] args) throws FileNotFoundException {
        File testFile = new File("DummyDoc.txt");
        System.out.println("Contents of File:n" + fileToString(testFile));
        
        //Test to return file content from the main
        return fileToString(testFile);
    }
 
    static public String fileToString(File textFile) throws FileNotFoundException {
 
    	File testFile = new File("DummyDoc.txt");
    	
        // Checks if file exists
        if (!textFile.exists()) {
            throw new FileNotFoundException ("File does not exist: " + textFile);
        }
 
        StringBuilder contents = new StringBuilder();
 
        try {
            BufferedReader input =  new BufferedReader(new FileReader(textFile));
            try {
                String line = null;
                while (( line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
            finally {
                input.close();
            }
	}
	catch (IOException ex){
            ex.printStackTrace();
	}
 
        return contents.toString();
        
}
}