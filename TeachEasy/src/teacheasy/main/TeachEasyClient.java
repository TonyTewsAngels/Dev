package teacheasy.main;

import teacheasy.data.PageObject.PageObjectType;
import teacheasy.debug.EditorRuntimeDataDummyUI.KeyHandler;
import teacheasy.debug.EditorRuntimeDataDummyUI.MenuEventHandler;
import teacheasy.debug.EditorRuntimeDataDummyUI.MouseEventHandler;
import teacheasy.runtime.EditorRunTimeData;
import teacheasy.runtime.editor.LessonInfoWindow;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TeachEasyClient extends Application {
    Text text, text1, botText, propText, titleText, page1, page2, page3;
    Image textIm;
    
    EditorRunTimeData editorRuntimeData;
    
    Group contentPanel;
    
    Rectangle r;
    
    Button textBtn;  
    Button imageBtn; 
    Button videoBtn;    
    Button tickBtn;
    Button graphicBtn;
    Button audioBtn;
    Button questionBtn;
    Button previewBtn;
     
    Button nextPageBtn;
    Button prevPageBtn;
    
    ComboBox<Integer> pageList;
   
    
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
        Scene scene = new Scene(innerGrid, bounds.getMaxX(), bounds.getMaxY());
        
        /* Mouse and Key event listeners */
        scene.setOnKeyPressed(new KeyHandler());
        scene.addEventFilter(MouseEvent.ANY, new MouseEventHandler());
    
        /* Instantiate content of Group */
        GridPane topGrid = new GridPane();
        MenuBar menuBar = new MenuBar();
        HBox topBar = new HBox(5);
        HBox titleBox = new HBox();
        
        HBox preview = new HBox();
        contentPanel = new Group();
        VBox propertiesPanel = new VBox();
        HBox bottomBar = new HBox();   
        GridPane botGrid = new GridPane();
        VBox paddingBox = new VBox(5);
        
        titleBox.setMinWidth(400);
        titleBox.setMaxWidth(400);
        titleBox.setOnMouseClicked(new TitleBoxClickHandler(this));
        
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
        Menu menuPreview = new Menu("Preview");
        Menu menuHelp = new Menu("Help");
        Menu menuDebug = new Menu("Debug");
        
        /* Add an open button to the file menu */
        MenuItem menuItemOpen = new MenuItem("Open");
        menuItemOpen.setId("FileOpen");
        menuItemOpen.setOnAction(new MenuEventHandler());
        
        /* Add a close button to the file menu */
        MenuItem menuItemClose = new MenuItem("Close");
        menuItemClose.setId("FileClose");
        menuItemClose.setOnAction(new MenuEventHandler());
        
        /* Add a new button to the file menu */
        MenuItem menuItemNew = new MenuItem("New");
        menuItemNew.setId("FileNew");
        menuItemNew.setOnAction(new MenuEventHandler());
        
        /* Add a save button to the file menu */
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setId("FileSave");
        menuItemSave.setOnAction(new MenuEventHandler());
        
        /* Add a new page button to the edit menu */
        MenuItem menuItemNewPage = new MenuItem("New Page");
        menuItemNewPage.setId("EditNewPage");
        menuItemNewPage.setOnAction(new MenuEventHandler());
        
        /* Add a remove page button to the edit menu */
        MenuItem menuItemRemovePage = new MenuItem("Remove Page");
        menuItemRemovePage.setId("EditRemovePage");
        menuItemRemovePage.setOnAction(new MenuEventHandler());
        
        /* Add a remove object button to the edit menu */
        MenuItem menuItemRemoveObject = new MenuItem("Remove Object");
        menuItemRemoveObject.setId("EditRemoveObject");
        menuItemRemoveObject.setOnAction(new MenuEventHandler());
        
        Menu menuItemNewObject = new Menu("Add...");
        
        MenuItem menuItemNewImage = new MenuItem("Add New Image");
        menuItemNewImage.setId("EditNewImage");
        menuItemNewImage.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewImage);
        
        MenuItem menuItemNewVideo = new MenuItem("Add New Video");
        menuItemNewVideo.setId("EditNewVideo");
        menuItemNewVideo.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewVideo);
        
        MenuItem menuItemNewAudio = new MenuItem("Add New Audio");
        menuItemNewAudio.setId("EditNewAudio");
        menuItemNewAudio.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewAudio);
        
        MenuItem menuItemNewText = new MenuItem("Add New Text Box");
        menuItemNewText.setId("EditNewText");
        menuItemNewText.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewText);
        
        MenuItem menuItemNewGraphic = new MenuItem("Add New Graphic");
        menuItemNewGraphic.setId("EditNewGraphic");
        menuItemNewGraphic.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewGraphic);
        
        MenuItem menuItemNewMultiChoice = new MenuItem("Add New Multiple Choice Question");
        menuItemNewMultiChoice.setId("EditNewMultiChoice");
        menuItemNewMultiChoice.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewMultiChoice);
        
        MenuItem menuItemNewAnswerBox = new MenuItem("Add New Answer Box");
        menuItemNewAnswerBox.setId("EditNewAnswerBox");
        menuItemNewAnswerBox.setOnAction(new MenuEventHandler());
        menuItemNewObject.getItems().add(menuItemNewAnswerBox);
        
        /* Add a next page button to the debug menu */
        MenuItem menuItemNextPage = new MenuItem("Next Page");
        menuItemNextPage.setId("DebugNextPage");
        menuItemNextPage.setOnAction(new MenuEventHandler());
        
        /* Add a next page button to the debug menu */
        MenuItem menuItemPrevPage = new MenuItem("Prev Page");
        menuItemPrevPage.setId("DebugPrevPage");
        menuItemPrevPage.setOnAction(new MenuEventHandler());
        
        /* Add a next page object button to the debug menu */
        MenuItem menuItemNextObject = new MenuItem("Next Object");
        menuItemNextObject.setId("DebugNextObject");
        menuItemNextObject.setOnAction(new MenuEventHandler());
        
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemClose);
        menuEdit.getItems().addAll(menuItemNewPage, menuItemRemovePage, menuItemNewObject, menuItemRemoveObject);
        menuDebug.getItems().addAll(menuItemNextObject, menuItemNextPage, menuItemPrevPage);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuPreview, menuHelp, menuDebug);
        
        
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
        
        r = new Rectangle();
        r.setWidth(937.5);
        r.setHeight(527.34);
        r.setFill(Color.WHITE);
        r.setEffect(new DropShadow());
        
        /* Create Buttons */     
        textBtn = new Button();    
        imageBtn = new Button();  
        videoBtn = new Button();    
        tickBtn = new Button();
        graphicBtn = new Button();
        audioBtn = new Button();
        questionBtn = new Button();
        previewBtn = new Button();
        
        nextPageBtn = new Button();
        prevPageBtn = new Button();
        
        pageList = new ComboBox<Integer>();
        pageList.valueProperty().addListener(new PageListListener());        
          
        /* Make buttons transparent */
        textBtn.setStyle("-fx-background-color: transparent;");
        imageBtn.setStyle("-fx-background-color: transparent;");
        videoBtn.setStyle("-fx-background-color: transparent;");
        tickBtn.setStyle("-fx-background-color: transparent;");
        tickBtn.setStyle("-fx-background-color: transparent;");
        graphicBtn.setStyle("-fx-background-color: transparent;");
        audioBtn.setStyle("-fx-background-color: transparent;");
        questionBtn.setStyle("-fx-background-color: transparent;");
        previewBtn.setStyle("-fx-background-color: transparent;");
        
        nextPageBtn.setStyle("-fx-background-color: transparent;");
        prevPageBtn.setStyle("-fx-background-color: transparent;");
        
        /* Pop up info for buttons */
        textBtn.setTooltip(new Tooltip("Insert text box"));
        imageBtn.setTooltip(new Tooltip("Insert image"));
        videoBtn.setTooltip(new Tooltip("Insert video"));
        tickBtn.setTooltip(new Tooltip("Insert multiple choice"));
        graphicBtn.setTooltip(new Tooltip("Insert graphics"));
        audioBtn.setTooltip(new Tooltip("Insert audio clip"));
        questionBtn.setTooltip(new Tooltip("Insert question box"));
        
        nextPageBtn.setTooltip(new Tooltip("Next Page"));
        prevPageBtn.setTooltip(new Tooltip("Previous Page"));
        pageList.setTooltip(new Tooltip("Select a Page"));
        
        /* Set the button ids */     
        textBtn.setId("textBtn");
        imageBtn.setId("imgBtn");
        videoBtn.setId("vidBtn");
        tickBtn.setId("tickBtn");
        graphicBtn.setId("graphicBtn");
        audioBtn.setId("audioBtn");
        questionBtn.setId("questionBtn");
        
        nextPageBtn.setId("nextPageBtn");
        prevPageBtn.setId("prevPageBtn");
        
        /* Dummy Page Content */
        page1 = new Text(1, 1,"page1");
        page2 = new Text(1, 1,"page2");
        page3 = new Text(1, 1,"page3"); 
        
        
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
        
        //Arrows
        Image arImST_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_ST_BOTTOM_RECT_DarkBlue_L-01.png"));
        Image arImHO_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_HO_BOTTOM_RECT_DarkBlue_L-01.png"));
        Image arImPRE_R = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_PRE_BOTTOM_RECT_DarkBlue_L-01.png"));
        
        Image arImST_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_ST_BOTTOM_RECT_DarkBlue_L-02.png"));
        Image arImHO_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_HO_BOTTOM_RECT_DarkBlue_L-02.png"));
        Image arImPRE_L = new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Arrow_PRE_BOTTOM_RECT_DarkBlue_L-02.png"));
        
        //Preview Button
        Image prevIm= new Image(getClass().getResourceAsStream("/teacheasy/topIcons/Choice_PRE_TOP_CIRC_Blue_T-01.png"));
        
        ImageView Prev = new ImageView(prevIm);
        previewBtn.setGraphic(Prev);   
        
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
        
        final ImageView arST_L = new ImageView(arImST_L);
        final ImageView arHO_L = new ImageView(arImHO_L);
        final ImageView arPRE_L = new ImageView(arImPRE_L);
        
        final ImageView arST_R = new ImageView(arImST_R);
        final ImageView arHO_R = new ImageView(arImHO_R);
        final ImageView arPRE_R = new ImageView(arImPRE_R);
        
        
        /* Skin buttons */
        textBtn.setGraphic(textBoxST); 
        imageBtn.setGraphic(imBoxST); 
        videoBtn.setGraphic(vidBoxST); 
        tickBtn.setGraphic(mulBoxST); 
        graphicBtn.setGraphic(graphicBoxST); 
        audioBtn.setGraphic(audioBoxST); 
        questionBtn.setGraphic(queBoxST);

        nextPageBtn.setGraphic(arST_R);
        prevPageBtn.setGraphic(arST_L);
        
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
        
        x = 40;
        y = 40;
        
        arST_L.setFitWidth(x);
        arST_L.setFitHeight(y);
        arHO_L.setFitWidth(x);
        arHO_L.setFitHeight(y);
        arPRE_L.setFitWidth(x);
        arPRE_L.setFitHeight(y);
        
        arST_R.setFitWidth(x);
        arST_R.setFitHeight(y);
        arHO_R.setFitWidth(x);
        arHO_R.setFitHeight(y);
        arPRE_R.setFitWidth(x);
        arPRE_R.setFitHeight(y);
        
        /* Setup the button event handling */

        /* Mouse Pressed */
        textBtn.setOnMousePressed(new ButtonEventHandler(textBtn, textBoxPR));
        imageBtn.setOnMousePressed(new ButtonEventHandler(imageBtn, imBoxPR));
        videoBtn.setOnMousePressed(new ButtonEventHandler(videoBtn, vidBoxPR));
        tickBtn.setOnMousePressed(new ButtonEventHandler(tickBtn, mulBoxPR));
        graphicBtn.setOnMousePressed(new ButtonEventHandler(graphicBtn, graphicBoxPR));
        audioBtn.setOnMousePressed(new ButtonEventHandler(audioBtn, audioBoxPR));
        questionBtn.setOnMousePressed(new ButtonEventHandler(questionBtn, queBoxPR));
        nextPageBtn.setOnMousePressed(new ButtonEventHandler(nextPageBtn, arPRE_R));
        prevPageBtn.setOnMousePressed(new ButtonEventHandler(prevPageBtn, arPRE_L));

        /* Mouse Released */      
        textBtn.setOnMouseReleased(new ButtonEventHandler(textBtn, textBoxHO)); 
        imageBtn.setOnMouseReleased(new ButtonEventHandler(imageBtn, imBoxHO)); 
        videoBtn.setOnMouseReleased(new ButtonEventHandler(videoBtn, vidBoxHO)); 
        tickBtn.setOnMouseReleased(new ButtonEventHandler(tickBtn, mulBoxHO)); 
        graphicBtn.setOnMouseReleased(new ButtonEventHandler(graphicBtn, graphicBoxHO)); 
        audioBtn.setOnMouseReleased(new ButtonEventHandler(audioBtn, audioBoxHO)); 
        questionBtn.setOnMouseReleased(new ButtonEventHandler(questionBtn, queBoxHO));
        nextPageBtn.setOnMouseReleased(new ButtonEventHandler(nextPageBtn, arHO_R));
        prevPageBtn.setOnMouseReleased(new ButtonEventHandler(prevPageBtn, arHO_L));
        
        /* Mouse Entered */
        textBtn.setOnMouseEntered(new ButtonEventHandler(textBtn, textBoxHO)); 
        imageBtn.setOnMouseEntered(new ButtonEventHandler(imageBtn, imBoxHO)); 
        videoBtn.setOnMouseEntered(new ButtonEventHandler(videoBtn, vidBoxHO)); 
        tickBtn.setOnMouseEntered(new ButtonEventHandler(tickBtn, mulBoxHO)); 
        graphicBtn.setOnMouseEntered(new ButtonEventHandler(graphicBtn, graphicBoxHO)); 
        audioBtn.setOnMouseEntered(new ButtonEventHandler(audioBtn, audioBoxHO)); 
        questionBtn.setOnMouseEntered(new ButtonEventHandler(questionBtn, queBoxHO));
        nextPageBtn.setOnMouseEntered(new ButtonEventHandler(nextPageBtn, arHO_R));
        prevPageBtn.setOnMouseEntered(new ButtonEventHandler(prevPageBtn, arHO_L));
               
        /* Mouse Exited */
        textBtn.setOnMouseExited(new ButtonEventHandler(textBtn, textBoxST)); 
        imageBtn.setOnMouseExited(new ButtonEventHandler(imageBtn, imBoxST)); 
        videoBtn.setOnMouseExited(new ButtonEventHandler(videoBtn, vidBoxST)); 
        tickBtn.setOnMouseExited(new ButtonEventHandler(tickBtn, mulBoxST)); 
        graphicBtn.setOnMouseExited(new ButtonEventHandler(graphicBtn, graphicBoxST)); 
        audioBtn.setOnMouseExited(new ButtonEventHandler(audioBtn, audioBoxST)); 
        questionBtn.setOnMouseExited(new ButtonEventHandler(questionBtn, queBoxST)); 
        nextPageBtn.setOnMouseExited(new ButtonEventHandler(nextPageBtn, arST_R));
        prevPageBtn.setOnMouseExited(new ButtonEventHandler(prevPageBtn, arST_L));
        
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
        
        propertiesPanel.setMinWidth(250);
        
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
        preview.getChildren().addAll(previewBtn);
        topBar.getChildren().addAll(textBtn,imageBtn,videoBtn,tickBtn,graphicBtn,audioBtn,questionBtn);
        
        /* Add Dummy page to content panel */
        contentPanel.getChildren().addAll(r);
        
        botGrid.add(bottomBar,1,0);
        botGrid.add(TE, 3, 0);
        
        /* Set alignment of bottomBar content */
        bottomBar.setAlignment(Pos.BASELINE_CENTER);
        bottomBar.getChildren().addAll(prevPageBtn, pageList, nextPageBtn);
        
        /* Setup the window */
        primaryStage.setTitle("TE_GUI_3");
        primaryStage.setScene(scene);
        
        Bounds contentPanelBounds = contentPanel.getBoundsInParent();
        Rectangle2D contentRect = new Rectangle2D(contentPanelBounds.getMinX(), 
                                                  contentPanelBounds.getMinY(),
                                                  contentPanelBounds.getMaxX(),
                                                  contentPanelBounds.getMaxY());
        
        editorRuntimeData = new EditorRunTimeData(contentPanel, contentRect, propertiesPanel);
        
        /* Show the window */
        primaryStage.show();
        
        updateUI();
    }
    
    public void updateUI() {        
        /* 
         * Enable or disable the relevant buttons based on whether
         * a lesson is open.
         */
        if(editorRuntimeData.isLessonOpen()) {
            textBtn.setDisable(false);
            imageBtn.setDisable(false);
            videoBtn.setDisable(false);
            audioBtn.setDisable(false);
            graphicBtn.setDisable(false);
            tickBtn.setDisable(false);
            questionBtn.setDisable(false);
            
            if(!editorRuntimeData.isNextPage()) {
                nextPageBtn.setDisable(true);
            } else {
                nextPageBtn.setDisable(false);
            }
            
            if(!editorRuntimeData.isPrevPage()) {
                prevPageBtn.setDisable(true);
            } else {
                prevPageBtn.setDisable(false);
            }
            
            pageList.setDisable(false);
            pageList.getItems().clear();
            
            for(int i = 0; i < editorRuntimeData.getPageCount(); i++) {
                pageList.getItems().add(new Integer(i+1));
            }
            
            pageList.setValue(new Integer(editorRuntimeData.getCurrentPageNumber() + 1));
            
            titleText.setText(editorRuntimeData.getLesson().lessonInfo.getLessonName());
        } else {
            textBtn.setDisable(true);
            imageBtn.setDisable(true);
            videoBtn.setDisable(true);
            audioBtn.setDisable(true);
            graphicBtn.setDisable(true);
            tickBtn.setDisable(true);
            questionBtn.setDisable(true);
            
            nextPageBtn.setDisable(true);
            prevPageBtn.setDisable(true);
            
            pageList.setDisable(true);
            
            pageList.getItems().clear();
            
            titleText.setText("No Lesson Open");
        }
    }
    
    /** Next page button functionality */
    public void nextPageButtonPressed() {
        editorRuntimeData.nextPage();
        updateUI();
    }
    
    /** Previous page button functionality */
    public void prevPageButtonPressed() {
        editorRuntimeData.prevPage();
        updateUI();
    }
    
    /** File->Open menu option functionality */
    public void fileOpenPressed() {
        /* Open the file */
        if(editorRuntimeData.openLesson()) {
            /* Opened Successfully */
        } else {
            System.out.print("Parse Failed");
        }
        
        /* Redraw the window */
        updateUI();
    }
    
    /** File->Close menu option functionality */
    public void fileClosePressed() {
        /* Close the current lesson */
        editorRuntimeData.closeLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    /** File->New menu option functionality */
    public void fileNewPressed() {
        /* Close the current lesson */
        editorRuntimeData.newLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    /** File->Save menu option functionality */
    public void fileSavePressed() {
        /* Close the current lesson */
        editorRuntimeData.saveLesson();
        
        /* Re-draw the window */
        updateUI();
    }
    
    /** Mouse pressed */
    public void mousePressed(double x, double y) {
        float relX = (float)(x - contentPanel.getLayoutX())/(float)(r.getWidth());
        float relY = (float)(y - contentPanel.getLayoutY())/(float)(r.getHeight());
        
        if(relX >= 0.0f && relX <= 1.0f && relY >= 0.0f && relY <= 1.0f) {
            editorRuntimeData.mousePressed(relX, relY);
        } else {
            /** Click is not in the group */
        }
    }
    
    /** Mouse released */
    public void mouseReleased(double x, double y) {
        float relX = (float)(x - contentPanel.getLayoutX())/(float)(r.getWidth());
        float relY = (float)(y - contentPanel.getLayoutY())/(float)(r.getHeight());
        
        if(relX >= 0.0f && relX <= 1.0f && relY >= 0.0f && relY <= 1.0f) {
            editorRuntimeData.mouseReleased(relX, relY, true);
        } else {
            editorRuntimeData.mouseReleased(relX, relY, false);
        }
    }
    
    /**
     * Menu Event Handler Class
     */
    public class MenuEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            /* Cast the source of the event to a menu item */
            MenuItem menuItem = (MenuItem) e.getSource();
            
            /* Act based on the ID of the menu item */
            if(menuItem.getId().equals("FileOpen")) {
                fileOpenPressed();
            } else if(menuItem.getId().equals("FileClose")) {
                fileClosePressed();
            } else if(menuItem.getId().equals("FileNew")) {
                fileNewPressed();
            } else if(menuItem.getId().equals("FileSave")) {
                fileSavePressed();
            } else if (menuItem.getId().equals("EditNewPage")) {
                editorRuntimeData.newPage();
                updateUI();
            } else if (menuItem.getId().equals("EditRemovePage")) {
                editorRuntimeData.removePage();
                updateUI();
            } else if (menuItem.getId().equals("EditNewImage")) {
                editorRuntimeData.newObject(PageObjectType.IMAGE);
                updateUI();
            } else if (menuItem.getId().equals("EditNewVideo")) {
                editorRuntimeData.newObject(PageObjectType.VIDEO);
                updateUI();
            } else if (menuItem.getId().equals("EditNewAudio")) {
                editorRuntimeData.newObject(PageObjectType.AUDIO);
                updateUI();
            } else if (menuItem.getId().equals("EditNewText")) {
                editorRuntimeData.newObject(PageObjectType.TEXT);
                updateUI();
            } else if (menuItem.getId().equals("EditNewMultiChoice")) {
                editorRuntimeData.newObject(PageObjectType.MULTIPLE_CHOICE);
                updateUI();
            } else if (menuItem.getId().equals("EditNewAnswerBox")) {
                editorRuntimeData.newObject(PageObjectType.ANSWER_BOX);
                updateUI();
            } else if (menuItem.getId().equals("EditNewGraphic")) {
                editorRuntimeData.newObject(PageObjectType.GRAPHIC);
                updateUI();
            } else if (menuItem.getId().equals("EditRemovePage")) {
                editorRuntimeData.removePage();
                updateUI();
            } else if (menuItem.getId().equals("EditRemoveObject")) {
                editorRuntimeData.removeObject();
                updateUI();
            } else if(menuItem.getId().equals("DebugNextObject")) {
                editorRuntimeData.nextObject();
                updateUI();
            } else if(menuItem.getId().equals("DebugNextPage")) {
                editorRuntimeData.nextPage();
                updateUI();
            } else if(menuItem.getId().equals("DebugPrevPage")) {
                editorRuntimeData.prevPage();
                updateUI();
            }
        }
    }
    
    public class ButtonEventHandler implements EventHandler<MouseEvent> {
        private Button button;
        private ImageView image;
        
        ButtonEventHandler(Button nButton, ImageView nImage) {
            this.button = nButton;
            this.image = nImage;
        }
        
        @Override
        public void handle(MouseEvent me) {
            button.setGraphic(image);
            
            if(me.getEventType() == MouseEvent.MOUSE_PRESSED) {
                System.out.println(button.getId());
                
                if(editorRuntimeData.isLessonOpen()) {
                    switch(button.getId()) {
                        case "textBtn":
                            editorRuntimeData.newObject(PageObjectType.TEXT);
                            break;
                        case "imgBtn":
                            editorRuntimeData.newObject(PageObjectType.IMAGE);
                            break;
                        case "vidBtn":
                            editorRuntimeData.newObject(PageObjectType.VIDEO);
                            break;
                        case "tickBtn":
                            editorRuntimeData.newObject(PageObjectType.MULTIPLE_CHOICE);
                            break;
                        case "graphicBtn":
                            editorRuntimeData.newObject(PageObjectType.GRAPHIC);
                            break;
                        case "audioBtn":
                            editorRuntimeData.newObject(PageObjectType.AUDIO);
                            break;
                        case "questionBtn":
                            editorRuntimeData.newObject(PageObjectType.ANSWER_BOX);
                            break;
                        case "nextPageBtn":
                            nextPageButtonPressed();
                            break;
                        case "prevPageBtn":
                            prevPageButtonPressed();
                            break;
                    }
                }
            }
        }
    }
    
    public class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent ke) {            
            if(ke.getCode() == KeyCode.DELETE) {
                editorRuntimeData.removeObject();
            } else if(ke.getCode() == KeyCode.O && ke.isControlDown()) {
                fileOpenPressed();
            } else if(ke.getCode() == KeyCode.N) {
                editorRuntimeData.newPage();
                updateUI();
            } else if(ke.getCode() == KeyCode.C && ke.isControlDown()) {
                editorRuntimeData.copyObject();
            } else if(ke.getCode() == KeyCode.V && ke.isControlDown()) {
                editorRuntimeData.pasteObject();
            }
        }
    }
    
    public class MouseEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent me) {
            if(me.getEventType() == MouseEvent.MOUSE_PRESSED) {
                mousePressed(me.getSceneX(), me.getSceneY());
            } else if(me.getEventType() == MouseEvent.MOUSE_RELEASED) {
                mouseReleased(me.getSceneX(), me.getSceneY());
            }
        }
    }
    
    public class PageListListener implements ChangeListener<Integer> {
        @Override
        public void changed(ObservableValue<? extends Integer> ov,
                            Integer oldVal, Integer newVal) {
            if(newVal != null) {
                if((newVal - 1) != editorRuntimeData.getCurrentPageNumber()) {
                    editorRuntimeData.setCurrentPage(newVal - 1);
                    updateUI();
                }
            }
        }
    }
    
    public class TitleBoxClickHandler implements EventHandler<MouseEvent> {
        private TeachEasyClient clientRef;
        
        public TitleBoxClickHandler(TeachEasyClient nClientRef) {
            this.clientRef = nClientRef;
        }
        
        @Override
        public void handle(MouseEvent me) {
            if(editorRuntimeData.isLessonOpen()) {
                new LessonInfoWindow(editorRuntimeData.getLesson().lessonInfo, clientRef);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
