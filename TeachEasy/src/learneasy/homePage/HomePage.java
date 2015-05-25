package learneasy.homePage;

import java.util.prefs.Preferences;

public class HomePage {
    
    /* Homepage */
    private Preferences preference;
    
    public HomePage(){
        
    }
    
    public void setPreference(){
        /* Instantiate preferences */
       preference = Preferences.userRoot().node(this.getClass().getName());
       
       /* String to store the path of the recently opened lesson*/
       String recentlyOpened = "recentlyOpened";
       
       /*Set actual path*/
       preference.put(recentlyOpened, "//userfs/dbb503/w2k/workspace/Dev/TeachEasy");
   }
    
    private void createFileLink(){
      
    }

    public Preferences getPreference() {
        return preference;
    }

    public void setPreference(Preferences preference) {
        this.preference = preference;
    }
}
