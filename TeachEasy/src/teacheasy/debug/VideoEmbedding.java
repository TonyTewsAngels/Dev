package teacheasy.debug;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VideoEmbedding extends Application {    
  public static void main(String[] args) { 
	  launch(args); 
	  }

  @Override public void start(Stage stage) throws Exception {
    WebView webview = new WebView();
    webview.getEngine().load(
    /*Vimeo and Youtube not working yet*/
    		
      //"http://player.vimeo.com/video/24577973?player_id=player&autoplay=1&title=0&byline=0&portrait=0&api=1&maxheight=480&maxwidth=800"
      //"http://www.youtube.com/embed/KKYYAbGpw6A"
    		"http://www.dailymotion.com/embed/video/x2h6dse"
    );
    webview.setPrefSize(640, 390);

    stage.setScene(new Scene(webview));
    stage.show();
  }    
}