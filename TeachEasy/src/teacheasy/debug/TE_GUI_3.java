
/*
 * Lewis Thresh
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */

package teacheasy.debug;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


/**
 * 
 * TeachEasy GUI based on BorderLayout TE_GUI_3 (Check Lucid chart diagram)
 * 
 * @author Lewis Thresh
 * @version 1.0 18 April 2015
 */

public class TE_GUI_3 extends Application {
    Text text, text1, botText, propText, titleText, page1, page2, page3;
    Image textIm;
	
	/**
     * Override the start method inside the JavaFX Application
     * class. This is called to start the program.
     */
    @Override
    public void start(Stage primaryStage) {
    	
        /* Get the screen size */
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        /* Instantiate the scene and group */
        GridPane innerGrid = new GridPane();
        Scene scene = new Scene(innerGrid, 700, 700);
	
        /* Instantiate content of Group */
        GridPane topGrid = new GridPane();
        MenuBar menuBar = new MenuBar();
        HBox topBar = new HBox(5);
        HBox titleBox = new HBox();
        HBox preview = new HBox();
        Group contentPanel = new Group();
        VBox propertiesPanel = new VBox();
        HBox bottomBar = new HBox();   
        GridPane botGrid = new GridPane();
        VBox paddingBox = new VBox(5);
        
        /* Set layout colours */
        paddingBox.setStyle("-fx-background-color: rgb(215,215,215);");
        titleBox.setStyle("-fx-background-color: rgb(22,169,254);-fx-border-color:black; fx-border-width: 8; -fx-border-style: solid;");
        topGrid.setStyle("-fx-background-color: rgb(215,215,215);-fx-border-color:black; fx-border-width: 8; -fx-border-style: solid;");
        propertiesPanel.setStyle("-fx-background-color:  rgb(215,215,215); -fx-border-color:black; fx-border-width: 8; -fx-border-style: solid;");
        contentPanel.setStyle("-fx-background-color: rgb(101, 4, 7); -fx-border-color:black; fx-border-width: 8; -fx-border-style: solid;");
        innerGrid.setStyle("-fx-background-color: rgb(241,241,241);");
        bottomBar.setStyle("-fx-background-color:rgb(113,113,113);");
        botGrid.setStyle("-fx-background-color: rgb(113,113,113); -fx-border-color:black; fx-border-width: 8; -fx-border-style: solid;");
        
    
        titleBox.setEffect(new DropShadow());
        
        
        /* Setup Menubar */
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");
        menuBar.setPrefWidth(10000);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        
        
        /* DUMMY CONTENT */
        
        /* Text */
        text1 = new Text(1, 1,"Top Bar");
        text1.setFont(Font.font("Verdana", 20));
        text1.setId("text1");
        
        botText = new Text(1, 1,"bottemBar");
        botText.setFont(Font.font("Verdana", 20));
        botText.setId("botText");
        
        propText = new Text(1, 1,"propText");
        propText.setFont(Font.font("Verdana", 20));
        propText.setId("botText");
        
        titleText = new Text(1, 1,"TITLE");
        titleText.setFont(Font.font("Verdana", 30));
        titleText.setId("titleText");
        
        
        /* Page */
        
        Rectangle r = new Rectangle();
        r.setWidth(937.5);
        r.setHeight(527.34);
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());
        
        /* Create Buttons */     
        Button butt = new Button("Dummy");
        final Button text = new Button();    
        final Button image = new Button();  
        final Button video = new Button();    
        final Button tick = new Button();
        final Button graphic = new Button();
        final Button audio = new Button();
        final Button question = new Button();
        Button prev = new Button("");
          
        /* Make buttons transparent */
        text.setStyle("-fx-background-color: transparent;");
        image.setStyle("-fx-background-color: transparent;");
        video.setStyle("-fx-background-color: transparent;");
        tick.setStyle("-fx-background-color: transparent;");
        tick.setStyle("-fx-background-color: transparent;");
        graphic.setStyle("-fx-background-color: transparent;");
        audio.setStyle("-fx-background-color: transparent;");
        question.setStyle("-fx-background-color: transparent;");
        prev.setStyle("-fx-background-color: transparent;");
        
        
        /* Pop up info for buttons */
        text.setTooltip(new Tooltip("Insert text box"));
        image.setTooltip(new Tooltip("Insert image"));
        video.setTooltip(new Tooltip("Insert video"));
        tick.setTooltip(new Tooltip("Insert multiple choice"));
        graphic.setTooltip(new Tooltip("Insert graphics"));
        audio.setTooltip(new Tooltip("Insert audio clip"));
        question.setTooltip(new Tooltip("Insert question box"));
        
        /* Set the buttons to use a button event handler */
        

        
        /* Set button IDs NOTE: THESE CAUSE AS NULLPOINTER EXCEPTION!!!!!!!!!!!!	*/
     
        /*text.setId("textBtn");
        image.setId("imgBtn");
        video.setId("vidBtn");
        tick.setId("tickBtn");
        graphic.setId("graphBtn");
        audio.setId("audioBtn");*/
        
      
      

        /* Dummy Page Content */
        
        page1 = new Text(1, 1,"page1");
        page2 = new Text(1, 1,"page2");
        page3 = new Text(1, 1,"page3"); 
        
        Pagination pages = new Pagination(5);
        
        
        /* Re-skin buttons */
        
        /* Import Images */
        
        //Text Box
        Image textImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Textbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image textImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Textbox_HO_TOP_CIRC_Blue_T-01.png"));
        Image textImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Textbox_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Image
        Image imImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Image_ST_TOP_CIRC_Blue_T-01.png"));
        Image imImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Image_HO_TOP_CIRC_Blue_T-01.png"));
        Image imImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Image_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Video
        Image vidImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Video_ST_TOP_CIRC_Blue_T-01.png"));
        Image vidImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Video_HO_TOP_CIRC_Blue_T-01.png"));
        Image vidImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Video_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Audio
        Image audioImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Audio_ST_TOP_CIRC_Blue_T-01.png"));
        Image audioImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Audio_HO_TOP_CIRC_Blue_T-01.png"));
        Image audioImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Audio_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Graphics
        Image graphicImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Shape_ST_TOP_CIRC_Blue_T-01.png"));
        Image graphicImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Shape_HO_TOP_CIRC_Blue_T-01.png"));
        Image graphicImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Shape_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Question Box
        Image queImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Questionbox_ST_TOP_CIRC_Blue_T-01.png"));
        Image queImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Questionbox_HO_TOP_CIRC_Blue_T-01.png"));
        Image queImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Questionbox_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Multiple Choice
        Image mulImST = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Choice_ST_TOP_CIRC_Blue_T-01.png"));
        Image mulImHO = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Choice_HO_TOP_CIRC_Blue_T-01.png"));
        Image mulImPR = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Choice_PRE_TOP_CIRC_Blue_T-01.png"));
        
        //Preview Button
        Image prevIm= new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Choice_PRE_TOP_CIRC_Blue_T-01.png"));
        
        ImageView Prev = new ImageView(prevIm);
        prev.setGraphic(Prev);   
        
        Prev.setFitWidth(80);
        Prev.setFitHeight(80);
        
        //TE Logo
        Image Logo = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/TE_4_0.png"));
        ImageView TE = new ImageView(Logo);
        TE.setFitWidth(80);
        TE.setFitHeight(80);
        
        /* Image Views */
        final ImageView textBoxST = new ImageView(textImST);   
        final ImageView textBoxHO = new ImageView(textImHO);
        final ImageView textBoxPR= new ImageView(textImPR);
        
        final ImageView imBoxST = new ImageView(imImST);
        final ImageView imBoxHO = new ImageView(imImHO);
        final ImageView imBoxPR= new ImageView(imImPR);

        final ImageView vidBoxST = new ImageView(vidImST);
        final ImageView vidBoxHO = new ImageView(vidImHO);
        final ImageView vidBoxPR= new ImageView(vidImPR);
        
        final ImageView audioBoxST = new ImageView(audioImST);
        final ImageView audioBoxHO = new ImageView(audioImHO);
        final ImageView audioBoxPR= new ImageView(audioImPR);
        
        final ImageView graphicBoxST = new ImageView(graphicImST);
        final ImageView graphicBoxHO = new ImageView(graphicImHO);
        final ImageView graphicBoxPR= new ImageView(graphicImPR);
        
        final ImageView queBoxST = new ImageView(queImST);
        final ImageView queBoxPR = new ImageView(queImPR);
        final ImageView queBoxHO= new ImageView(queImHO);
        
        final ImageView mulBoxST = new ImageView(mulImST);
        final ImageView mulBoxHO = new ImageView(mulImHO);
        final ImageView mulBoxPR= new ImageView(mulImPR);
        
        
        /* Skin buttons */
        
        text.setGraphic(textBoxST); 
        image.setGraphic(imBoxST); 
        video.setGraphic(vidBoxST); 
        tick.setGraphic(mulBoxST); 
        graphic.setGraphic(graphicBoxST); 
        audio.setGraphic(audioBoxST); 
        question.setGraphic(queBoxST);

        
        
        /*Button dimensions*/

        int x = 70;
        int y = 70;
        
        textBoxST.setFitWidth(x);
        textBoxST.setFitHeight(y);
        textBoxHO.setFitWidth(x);
        textBoxHO.setFitHeight(y);
        textBoxPR.setFitWidth(x);
        textBoxPR.setFitHeight(y);
        
        imBoxST.setFitWidth(x);
        imBoxST.setFitHeight(y);
        imBoxHO.setFitWidth(x);
        imBoxHO.setFitHeight(y);
        imBoxPR.setFitWidth(x);
        imBoxPR.setFitHeight(y);
        
        vidBoxST.setFitWidth(x);
        vidBoxST.setFitHeight(y);
        vidBoxHO.setFitWidth(x);
        vidBoxHO.setFitHeight(y);
        vidBoxPR.setFitWidth(x);
        vidBoxPR.setFitHeight(y);
        
        audioBoxST.setFitWidth(x);
        audioBoxST.setFitHeight(y);
        audioBoxHO.setFitWidth(x);
        audioBoxHO.setFitHeight(y);
        audioBoxPR.setFitWidth(x);
        audioBoxPR.setFitHeight(y);
        
        graphicBoxST.setFitWidth(x);
        graphicBoxST.setFitHeight(y);
        graphicBoxHO.setFitWidth(x);
        graphicBoxHO.setFitHeight(y);
        graphicBoxPR.setFitWidth(x);
        graphicBoxPR.setFitHeight(y);
        
        queBoxST.setFitWidth(x);
        queBoxST.setFitHeight(y);
        queBoxHO.setFitWidth(x);
        queBoxHO.setFitHeight(y);
        queBoxPR.setFitWidth(x);
        queBoxPR.setFitHeight(y);
        
        mulBoxST.setFitWidth(x);
        mulBoxST.setFitHeight(y);
        mulBoxHO.setFitWidth(x);
        mulBoxHO.setFitHeight(y);
        mulBoxPR.setFitWidth(x);
        mulBoxPR.setFitHeight(y);
        
        
        /* Event Handler */
        
        
     /*  text.setOnAction(new buttonEventHandler());
       image.setOnAction(new buttonEventHandler());
        
       
        
        class buttonEventHandler implements EventHandler<ActionEvent> {
        	
        	
        	
        	@Override
        	public void handle(ActionEvent e) {
        		
        		Button button = (Button)e.getSource();
        		
        		// Change image depending on the button ID 
        		
        		if(button.isPressed())	{
	        		if(button.getId().equals("textBtn"))  {
	        			text.setGraphic(textBoxPR); 	
	        		}
	        		else if (button.getId().equals("imgBtn"))  {
	        			image.setGraphic(imBoxPR); 	
	        		}
	        		
        		}
        		
        		
        		else if(button.isHover())	{
	        		if(button.getId().equals("textBtn"))  {
	        			text.setGraphic(textBoxHO); 	
	        		}
	        		else if (button.getId().equals("imgBtn"))  {
	        			image.setGraphic(imBoxHO); 	
	        		}
        		}
        		
        		else{
        			if(button.getId().equals("textBtn"))  {
	        			text.setGraphic(textBoxST); 	
	        		}
	        		else if (button.getId().equals("imgBtn"))  {
	        			image.setGraphic(imBoxST); 	
	        		}
        		}
        		
        	}
        }*/
        
        
        
        // Mouse Pressed
        
        text.setOnMousePressed(new EventHandler<MouseEvent>() {
        	
        	public void handle(MouseEvent event) {

        		text.setGraphic(textBoxPR); 
        }
        });
        
        image.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		image.setGraphic(imBoxPR); 
        }
        });
        
        video.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		video.setGraphic(vidBoxPR); 
        }
        });
        
        tick.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		tick.setGraphic(mulBoxPR); 
        }
        });
        
        graphic.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		graphic.setGraphic(graphicBoxPR);
        }
        });
        
        audio.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		audio.setGraphic(audioBoxPR);
        }
        });
        
        question.setOnMousePressed(new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent event) {	
        		question.setGraphic(queBoxPR);
        }
        });
        
      
        
        //Mouse Released
        
	   text.setOnMouseReleased(new EventHandler<MouseEvent>() {	
	        public void handle(MouseEvent event) {
        		text.setGraphic(textBoxST); 
		}
		});
	   
	   image.setOnMouseReleased(new EventHandler<MouseEvent>() {
		   	public void handle(MouseEvent event) {
       		
               image.setGraphic(imBoxST); 
		}
		});
		   
		   video.setOnMouseReleased(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {
	
	              video.setGraphic(vidBoxST); 
		}
		});
		   
		   tick.setOnMouseReleased(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {
	
	             tick.setGraphic(mulBoxST); 
		}
		});
		   
		   graphic.setOnMouseReleased(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {
			   		
	            graphic.setGraphic(graphicBoxST);
		}
		});
		   
		   audio.setOnMouseReleased(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {

	            audio.setGraphic(audioBoxST);
		}
		});
		   
		   question.setOnMouseReleased(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {

	            question.setGraphic(queBoxST);
		}
		});
	   
	   
		   
		//Mouse Entered
		    
		   text.setOnMouseEntered(new EventHandler<MouseEvent>() {	
		        public void handle(MouseEvent event) {
		    		text.setGraphic(textBoxHO); 
			}
			});
		   
		   image.setOnMouseEntered(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {
		   		
		           image.setGraphic(imBoxHO); 
			}
			});
			   
			   video.setOnMouseEntered(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		              video.setGraphic(vidBoxHO); 
			}
			});
			   
			   tick.setOnMouseEntered(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		             tick.setGraphic(mulBoxHO); 
			}
			});
			   
			   graphic.setOnMouseEntered(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		            graphic.setGraphic(graphicBoxHO);
		}
		});
			   
			   audio.setOnMouseEntered(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
  
		            audio.setGraphic(audioBoxHO);
		}
		});
			   
			   question.setOnMouseEntered(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
 
		            question.setGraphic(queBoxHO);
		}
		});
	   
			   
		//Mouse Exited
		    
		   text.setOnMouseExited(new EventHandler<MouseEvent>() {	
		        public void handle(MouseEvent event) {
		    		text.setGraphic(textBoxST); 
			}
			});
		   
		   image.setOnMouseExited(new EventHandler<MouseEvent>() {
			   	public void handle(MouseEvent event) {
		   		
		           image.setGraphic(imBoxST); 
			}
			});
			   
			   video.setOnMouseExited(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		              video.setGraphic(vidBoxST); 
			}
			});
			   
			   tick.setOnMouseExited(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		             tick.setGraphic(mulBoxST); 
			}
			});
			   
			   graphic.setOnMouseExited(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {
		
		            graphic.setGraphic(graphicBoxST);
		}
		});
			   
			   audio.setOnMouseExited(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {

		            audio.setGraphic(audioBoxST);
		}
		});
			   
			   question.setOnMouseExited(new EventHandler<MouseEvent>() {
				   	public void handle(MouseEvent event) {

		            question.setGraphic(queBoxST);
		}
		});
	        
        

        
        /* Top Bar Constraints */
        
        ColumnConstraints topFarLeft = new ColumnConstraints();
        topFarLeft.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topFarLeft);
        
        ColumnConstraints stretchLeft = new ColumnConstraints();
        stretchLeft.setFillWidth(true);
        stretchLeft.setHgrow(Priority.SOMETIMES);
        topGrid.getColumnConstraints().add(stretchLeft);
        
        ColumnConstraints topCentre = new ColumnConstraints();
        topCentre.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topCentre);
        
        ColumnConstraints stretchRight = new ColumnConstraints();
        stretchRight.setFillWidth(true);
        stretchRight.setHgrow(Priority.SOMETIMES);
        topGrid.getColumnConstraints().add(stretchRight);
        
        ColumnConstraints topFarRight = new ColumnConstraints();
        topFarRight.setMaxWidth(50);
        topGrid.getColumnConstraints().add(topFarRight);
        
        
        /* innerGrid row constraints */
        RowConstraints topRow = new RowConstraints();
        topRow.setMaxHeight(900);
        innerGrid.getRowConstraints().add(topRow);
        
        RowConstraints innerRow = new RowConstraints();
        innerRow.setFillHeight(true);
        innerRow.setVgrow(Priority.ALWAYS);
        innerGrid.getRowConstraints().add(innerRow);
        
        RowConstraints content = new RowConstraints();
        content.setMaxHeight(900);
        innerGrid.getRowConstraints().add(content);
        
        RowConstraints innerBot = new RowConstraints();
        innerBot.setFillHeight(true);
        innerBot.setVgrow(Priority.ALWAYS);
        innerGrid.getRowConstraints().add(innerBot);
    	
        RowConstraints botBarRow = new RowConstraints();
        botBarRow.setMaxHeight(1000);
        innerGrid.getRowConstraints().add(botBarRow);
        
        
        /* innerGrid Column constraints */

        ColumnConstraints farLeft = new ColumnConstraints();
        farLeft.setFillWidth(true);
       // centerRow.setMaxWidth(100);
        farLeft.setHgrow(Priority.SOMETIMES);
        innerGrid.getColumnConstraints().add(farLeft);
   
        ColumnConstraints contentCol = new ColumnConstraints();
        contentCol.setMaxWidth(1000);
        innerGrid.getColumnConstraints().add(contentCol);
        
        ColumnConstraints centerRow = new ColumnConstraints();
        centerRow.setFillWidth(true);
       // centerRow.setMaxWidth(100);
        centerRow.setHgrow(Priority.SOMETIMES);
        innerGrid.getColumnConstraints().add(centerRow);
        
        ColumnConstraints propertiesCol = new ColumnConstraints();
        propertiesCol.setMaxWidth(50);
        innerGrid.getColumnConstraints().add(propertiesCol);
        
        /* botGrid Column Constraints */
         
        ColumnConstraints botStretchLeft = new ColumnConstraints();
        botStretchLeft.setFillWidth(true);
        botStretchLeft.setHgrow(Priority.SOMETIMES);
        botGrid.getColumnConstraints().add(botStretchLeft);
   
        ColumnConstraints botcentreCol = new ColumnConstraints();
        botcentreCol.setMaxWidth(1000);
        botGrid.getColumnConstraints().add(botcentreCol);
        
        ColumnConstraints botStretchRight = new ColumnConstraints();
        botStretchRight.setFillWidth(true);
       // centerRow.setMaxWidth(100);
        botStretchRight.setHgrow(Priority.SOMETIMES);
        botGrid.getColumnConstraints().add(botStretchRight);
        
        ColumnConstraints botLogoCol = new ColumnConstraints();
        botLogoCol.setMaxWidth(50);
        botGrid.getColumnConstraints().add(botLogoCol);
        
        
        
        /* Add content to panes */
        innerGrid.add(topGrid,0,0,4,1);
        innerGrid.add(contentPanel,1,1);
        innerGrid.add(propertiesPanel,3,1,1,3);
        innerGrid.add(botGrid,0,4,4,1);
        
        topGrid.add(menuBar,0,0,5,1);
        topGrid.add(paddingBox,0,1);
        topGrid.add(topBar,2,1);
        topGrid.add(preview,4,1);
        
        paddingBox.getChildren().addAll(titleBox);
        
        /* Set alignment of content of topGrid */
        topBar.setAlignment(Pos.BASELINE_CENTER);
        titleBox.setAlignment(Pos.CENTER);
        preview.setAlignment(Pos.BASELINE_RIGHT);
        
        paddingBox.setAlignment(Pos.CENTER_LEFT);
        
        titleBox.getChildren().addAll(titleText);
        preview.getChildren().addAll(prev);
        topBar.getChildren().addAll(text,image,video,tick,graphic,audio,question);
        
        /* Add Dummy page to content panel */
        contentPanel.getChildren().addAll(r);
        
        propertiesPanel.getChildren().addAll(propText, butt);     
        
        botGrid.add(bottomBar,1,0);
        botGrid.add(TE, 3, 0);        

        bottomBar.getChildren().addAll(pages);
        
        /* Set alignment of bottomBar content */
        bottomBar.setAlignment(Pos.BASELINE_CENTER);
        
        /* Setup the window */
        primaryStage.setTitle("TE_GUI_3");
        primaryStage.setScene(scene);
        
        /* Show the window */
		primaryStage.show();
    }
    
   	public static void main(String[] args) {
   		launch(args);
   	}
}