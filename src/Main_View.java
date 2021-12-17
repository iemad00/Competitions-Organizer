import java.awt.Toolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main_View extends Application {
	Stage window;
	Scene scene;
	BorderPane pane = new BorderPane();
	Button createComp,viewComp;
	Text text = new Text();
    VBox mainLayout = new VBox(50);
    HBox mainButtons = new HBox(20);
    
    //======= Views =======
    Comp_Details compDetails;
    Add_Comp addComp;
    
    

    @Override
    public void start(Stage primaryStage){	
    	
		try {
			Excel db = new Excel();
			if(db.allDueSoon().length>0) {
				String subTitle = "Please Update The Winners\nin These Competitions";
				String message = "";
				for(String name: db.allDueSoon()) 
					message = message + name + "\n";
				alertBox("Soon due date!" ,subTitle, message);
				
				
			}
		}catch(Exception e) {
			System.out.println("Error: The excel file is missed!");
	        Platform.exit();
	        System.exit(0);
		}	
		
		
		
		compDetails = new Comp_Details();
		addComp = new Add_Comp();
		window = primaryStage;
		window.setTitle("KFUPM news team");
    	pane.setPadding(new Insets(10,10,10,10));
    	pane.setCenter(getLayout());
    	scene = new Scene(pane, 600, 350);
    	window.setScene(scene);
    	window.show();
    }

    	
    	
    
    public VBox getLayout() {
    	text.setText("KFUPM NEWS TEAM");
    	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35)); 
    	
    	Font font = Font.font("verdana", FontWeight.BOLD, 12);
    	
    	createComp = new Button("Create a Competition");
    	createComp.setMinSize(150, 30);
    	createComp.setFont(font);
    	createComp.setOnAction(e -> addComp.addCompStart(window));
    	viewComp = new Button("Competitions Details");
    	viewComp.setMinSize(150, 30);
    	viewComp.setFont(font);
    	viewComp.setOnAction(e -> compDetails.start2(window));

    	mainButtons.getChildren().addAll(createComp,viewComp);
    	mainButtons.setAlignment(Pos.CENTER);

    	mainLayout.getChildren().addAll(text,mainButtons);	
    	mainLayout.setAlignment(Pos.CENTER);
    	
    	return mainLayout;
    }
    

    
    public void alertBox(String titel,String subTitle, String message) {
    	Toolkit.getDefaultToolkit().beep(); //beep sound

    	Stage alertWindow = new Stage();
    	alertWindow.initStyle(StageStyle.UTILITY);
    	alertWindow.initModality(Modality.APPLICATION_MODAL);
    	alertWindow.setTitle(titel);
    	//alertWindow.setMinWidth(300);

    	Button ok = new Button("OK");
    	ok.setOnAction(e -> alertWindow.close());
    	
    	VBox alertLayout = new VBox(10);
    	alertLayout.setPadding(new Insets(10,10,10,10));
    	alertLayout.setAlignment(Pos.CENTER);
    	
    	Text subTitleText = new Text();    	
    	subTitleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
    	subTitleText.setText(subTitle);
    	subTitleText.setTextAlignment(TextAlignment.CENTER);
    	
    	Text messageText = new Text();    	
    	messageText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12)); 
    	messageText.setText(message);
    	messageText.setTextAlignment(TextAlignment.CENTER);
    	alertLayout.getChildren().addAll(subTitleText,messageText,ok);

    	Scene alertScene = new Scene(alertLayout);
    	
    	alertWindow.setScene(alertScene);

    	alertWindow.showAndWait();
    }
    
    



    
	public static void main(String[] args) {	
        launch(args);
    }






}