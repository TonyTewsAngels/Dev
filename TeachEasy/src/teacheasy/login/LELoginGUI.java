package teacheasy.login;

import java.io.File;
import java.io.FileNotFoundException;

import teacheasy.main.LearnEasyClient;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LELoginGUI  extends Application {
    private TextField userText;
    private PasswordField passText;
    private Label failLabel;    
    
    private Stage primaryStage;

    //Application starting point
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
    	//Set title
    	primaryStage.setTitle("Login GUI");
    
    	//Set GUI Layout
    	GridPane grid = new GridPane();
    	VBox Vtext = new VBox();
    	VBox logoBox = new VBox();
    	VBox buttonBox = new VBox();
    	Button loginBtn = new Button("Login");
    	
    	failLabel = new Label("User name or password not recognised.");
    	failLabel.setTextFill(Color.web("#BB0000"));
    	failLabel.setVisible(false);

    	loginBtn.setPrefWidth(300);
    	loginBtn.setOnAction(new LoginButtonHandler());
    	
    	buttonBox.getChildren().add(loginBtn);
    	buttonBox.setAlignment(Pos.CENTER);
    	    	
    	//Column constraints
    	ColumnConstraints farLeft = new ColumnConstraints();
        farLeft.setFillWidth(true);
        farLeft.setHgrow(Priority.SOMETIMES);
        grid.getColumnConstraints().add(farLeft);
    
        ColumnConstraints contentCol = new ColumnConstraints();
        contentCol.setMaxWidth(1000);
        grid.getColumnConstraints().add(contentCol);
        
        ColumnConstraints centerRow = new ColumnConstraints();
        centerRow.setFillWidth(true);
        centerRow.setHgrow(Priority.SOMETIMES);
        grid.getColumnConstraints().add(centerRow);
    	
    	//Row constraints
        RowConstraints topRow = new RowConstraints();
        topRow.setFillHeight(true);
        topRow.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().add(topRow);
        
        RowConstraints welRow = new RowConstraints();
        welRow.setMinHeight(80);
        grid.getRowConstraints().add(welRow);
        
        RowConstraints pleaseRow = new RowConstraints();
        pleaseRow.setMinHeight(30);
        grid.getRowConstraints().add(pleaseRow);
        
        RowConstraints userRow = new RowConstraints();
        userRow.setMinHeight(20);
        grid.getRowConstraints().add(userRow);
        
        RowConstraints passRow = new RowConstraints();
        passRow.setMinHeight(20);
        grid.getRowConstraints().add(passRow);
        
        RowConstraints buttonRow = new RowConstraints();
        passRow.setMinHeight(30);
        grid.getRowConstraints().add(buttonRow);
        
        RowConstraints botRow = new RowConstraints();
        botRow.setFillHeight(true);
        botRow.setVgrow(Priority.ALWAYS);
        grid.getRowConstraints().add(botRow);
        
        //Set grid colour
        grid.setStyle("-fx-background-color:  rgb(74, 104, 177);");
        Vtext.setStyle("-fx-background-color: rgb(74, 104, 177);");
        
    	//Welcome Labels
        Label wel = new Label("Welcome to LearnEasy");
    	wel.setFont(new Font("Myriad", 30));
    	
    	Label wel_2 = new Label("Please Log In");
    	wel_2.setFont(new Font("Myriad", 15));
    
    	//TextFileds
    	userText = new TextField();
    	passText = new PasswordField();
    	
    	userText.setPromptText("Username or Email");
    	passText.setPromptText("Password");
    	
    	//Add LE Logo
    	int x = 143;
    	int y = 143;
    	
    	// Dimensions: 1000 x 1000 (Use x,y to make appropriate size)
    	Image LE = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/LE_V4_1_1.png"));
    
    	ImageView Logo = new ImageView(LE);
    	Logo.setFitWidth(x);
    	Logo.setFitHeight(y);
    	Logo.setEffect(new DropShadow());	
    	
    	/* Logo fade in */
    	FadeTransition ft = new FadeTransition(Duration.millis(7000),Logo);
    	ft.setFromValue(0);
    	ft.setToValue(1.0);
    	ft.setCycleCount(1);
    	ft.play();
    	
    	//Add text to Vtext
    	Vtext.getChildren().addAll(wel_2);
    	Vtext.setAlignment(Pos.CENTER);
    	
    	//Add logo to logoBox
    	logoBox.getChildren().addAll(Logo);
    	logoBox.setAlignment(Pos.CENTER);
    	
    	//Add content to grid
    	grid.add(logoBox,1,0);
    	grid.add(wel,1,1);
    	grid.add(Vtext, 1, 2);
    	grid.add(userText,1,3);
    	grid.add(passText,1,4);
    	grid.add(buttonBox, 1, 5);
    	grid.add(failLabel, 1, 6);
    
    	//Add to scene
    	Scene appScene = new Scene(grid,600,400);

    	//Add scene to stage
    	primaryStage.setScene(appScene);
    	primaryStage.setResizable(false);
    	primaryStage.show();
    }
    
    public static void main(String[] args)  {
        launch(args);
    }
    
    public class LoginButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent arg0) {
            failLabel.setVisible(false);
            
            if(LoginChecker.checkLELogin(userText.getText(), passText.getText())) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        new LearnEasyClient().start(new Stage());
                    }
                });
                
                primaryStage.close();

            } else {
                userText.clear();
                passText.clear();
                
                failLabel.setVisible(true);
            }
        }
    }
}
