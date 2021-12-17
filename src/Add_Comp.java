import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;

public class Add_Comp implements EventHandler<ActionEvent> {
		Scene scene;
		
		RadioButton teamB, individual;
		Button create1, backtomain;
		
		Label addedOrNo;

	    TextField compName  = new TextField();
	    TextField compLink = new TextField();
	    TextField compDate = new TextField();
	    
		VBox createButton = new VBox(5);
	    HBox createAnswers1 = new HBox(5);
	    HBox createAnswers2 = new HBox(5);
		BorderPane pane = new BorderPane();
		Main_View mainView;
	    Stage window;
	    
	static ArrayList<String> compeINFO = new ArrayList<>();

	public void addCompStart(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		window = primaryStage;
		mainView = new Main_View();
		createButton.setAlignment(Pos.CENTER);
		window.setTitle("Create competition");
		HBox viewQues = new HBox(10);
    	viewQues.getChildren().addAll(new Label("competition Name: "),compName);
    	viewQues.setAlignment(Pos.CENTER);
    	
    	HBox viewQues2 = new HBox(5);
    	viewQues2.getChildren().addAll(new Label("competition URL Link: "),compLink);
    	viewQues2.setAlignment(Pos.CENTER);
    	
    	HBox viewQues3 = new HBox(5);
    	viewQues3.getChildren().addAll(new Label("competition's date: "),compDate);
    	viewQues3.setAlignment(Pos.CENTER);
    	
    	addedOrNo = new Label();
    	HBox addedOr = new HBox(5);
    	addedOr.getChildren().addAll(addedOrNo);
    	addedOr.setAlignment(Pos.CENTER);
    	
    	
    	HBox buttons = new HBox(20);
    	create1 = new Button("Create");
    	backtomain = new Button("Back To main");

    	teamB = new RadioButton("TeamBased");
    	individual = new RadioButton("Individual");
    	
     	
        buttons.getChildren().addAll(create1,backtomain,new Label(""),teamB,individual);
        buttons.setAlignment(Pos.CENTER);

		compName.setPromptText("Write the competition Name");
		compLink.setPromptText("competition URL Link");
		compDate.setPromptText(" DAY/MONTH/YEAR ");
		
    	createButton.getChildren().addAll(viewQues ,viewQues2,viewQues3,addedOr);
        pane.setPadding(new Insets(10,10,10,10));
    	pane.setTop(createButton);
    	pane.setBottom(buttons);
    	
    	create1.setOnAction(this);
    	teamB.setOnAction(this);
    	individual.setOnAction(this);
    	backtomain.setOnAction(this);
    	VBox layout = new VBox(10);
    	layout.getChildren().add(pane);
        scene = new Scene(layout, 420, 200);
    	primaryStage.setScene(scene);
    	primaryStage.show();

	}
	

	
	@Override
	public void handle(ActionEvent event) {
	    //public boolean createComp(String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
		boolean bool;
		try {
			Excel x = new Excel();
			
			if (event.getSource() == create1) {
				try {
		        	compeINFO.add(compName.getText() + ":" + compLink.getText() + ":" + compDate.getText());
		        	if(teamB.isSelected())
		        		bool = true;
		        	else
		        		bool = false;
		        	
		        	String compNamesh = compName.getText();
		        	String excelsheetName = "";
		        	
		        	for(int i = 0; i < compNamesh.length()-1; i++) {
		        		System.out.println(compNamesh.charAt(i));
		        		excelsheetName = excelsheetName + compNamesh.charAt(i) + "";
		        	}
		        	
		        	boolean create = x.createComp(excelsheetName, compName.getText(), compLink.getText(), compDate.getText(), bool);
		        	if(create == true)
		        		addedOrNo.setText("Successfully Added!");
		        	else
		        		addedOrNo.setText("Faild: Please Change The competition name");		        		
		        		teamB.setSelected(false);
		        		individual.setSelected(false);

		            	compName.clear();
		            	compLink.clear();
		            	compDate.clear();
		            	compeINFO.clear();
				}catch(Exception e) {
	        		addedOrNo.setText("Error occurred! ");

				}

	        	

			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(event.getSource() == teamB) {
			if(teamB.isSelected()) 
				individual.setSelected(false);
		}
		
		if(event.getSource() == individual) {
			if(individual.isSelected()) 
				teamB.setSelected(false);
		}
		
		if (event.getSource() == backtomain) {
			mainView.start(window);
		}

		
	}


	


}
