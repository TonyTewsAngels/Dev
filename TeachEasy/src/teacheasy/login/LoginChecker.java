package teacheasy.login;

import java.io.*;

public class LoginChecker {   
    static public boolean checkLELogin(String username, String password) {
        File studentFile = new File("StudentLoginDetails.txt");
        BufferedReader input; 
        
        try {
            input = new BufferedReader(new FileReader(studentFile));
            String line = null;
            
            while((line = input.readLine()) != null) {
                String[] component = line.split("~");
                
                if((username.equals(component[0]) || username.equals(component[2])) && password.equals(component[1])) {
                    input.close();
                    return true;
                }
            }     
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    static public boolean checkTELogin(String username, String password) {
        File teacherFile = new File("TeacherLoginDetails.txt");
        BufferedReader input; 
        
        try {
            input = new BufferedReader(new FileReader(teacherFile));
            String line = null;
            
            while((line = input.readLine()) != null) {
                String[] component = line.split("~");
                
                if((username.equals(component[0]) || username.equals(component[2])) && password.equals(component[1])) {
                    input.close();
                    return true;
                }
            }     
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}