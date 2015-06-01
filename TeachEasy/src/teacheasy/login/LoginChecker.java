/*
 * Lewis Thresh & Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 */
package teacheasy.login;

import java.io.*;

/**
 * Contains static methods for checking student and teacher
 * login credentials.
 * 
 * @author  Lewis Thresh & Alistair Jewers
 * @version 1.0 25 May 2015
 */
public class LoginChecker {
    
    /**
     * Checks a students login credentials.
     * 
     * @param username The username to check.
     * @param password The password to check.
     * @param student True if student credentials, False if teacher.
     * @return True if credentials are valid.
     */
    static public boolean checkLogin(String username, String password, boolean student) {
        
        /* Declare the credentials file */
        File credentialsFile;
        
        /* Load the appropriate file */
        if(student) {
            credentialsFile = new File("StudentLoginDetails.txt");
        } else {
            credentialsFile = new File("TeacherLoginDetails.txt");
        }
        
        /* Declare an input stream */
        BufferedReader input; 
        
        try {
            /* Initialise the input stream using the file */
            input = new BufferedReader(new FileReader(credentialsFile));
            
            /* Declare a string to hold the current line in the file */
            String line = null;
            
            /* While there are lines remaining to be read */
            while((line = input.readLine()) != null) {
                /* Separate using the tilde reg-ex */
                String[] component = line.split("~");
                
                /* Check the components */
                if((username.equals(component[0]) || username.equals(component[2])) && 
                    password.equals(component[1])) {
                    
                    /* Close the stream */
                    input.close();
                    
                    /* Credentials valid, return true */
                    return true;
                }
            }     
        } catch (IOException e) {
            /* Print any exceptions to the console */
            e.printStackTrace();
        }
        
        /* Credentials did not match any in records, return false */
        return false;
    }
}