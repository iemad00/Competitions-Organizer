import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Edit_Comp implements EventHandler<ActionEvent> {
		Scene scene;		
		RadioButton teamB, individual;
		Button edit1, back;	
		Label addedOrNo;
	    TextField compName  = new TextField();
	    TextField compLink = new TextField();
	    TextField compDate = new TextField();	    
		VBox createButton = new VBox(5);
	    HBox createAnswers1 = new HBox(5);
	    HBox createAnswers2 = new HBox(5);
		BorderPane pane = new BorderPane();		
	    static ArrayList<String> compeINFO = new ArrayList<>();
	    Comp_Details compDitails;
	    int compIndex;
	    Excel db;
	    Edit_Comp(int compIndex){
	    	this.compIndex = compIndex;
	    }
	    
	public void editCompStart(Stage primaryStage) {
		
		try {
			db = new Excel();
		}catch(Exception e) {
    		addedOrNo.setText("Error occurred");

		}
			
			
    	compName.setText(db.compName(compIndex));
    	compLink.setText(db.compURL(compIndex));
    	compDate.setText(db.compDate(compIndex));
    	

    	
		compDitails = new Comp_Details();
		createButton.setAlignment(Pos.CENTER);
		
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
    	edit1 = new Button("Edit");
    	back = new Button("Back");

    	teamB = new RadioButton("TeamBased");
    	teamB.setSelected(db.isTeamBased(compIndex));
    	individual = new RadioButton("Individual");
    	individual.setSelected(!db.isTeamBased(compIndex));

     	
        buttons.getChildren().addAll(edit1,back,new Label(""),teamB,individual);
        buttons.setAlignment(Pos.CENTER);

		compName.setPromptText("Write the competition Name");
		compLink.setPromptText("competition URL Link");
		compDate.setPromptText(" DAY/MONTH/YEAR ");
		
    	createButton.getChildren().addAll(viewQues ,viewQues2,viewQues3,addedOr);
        pane.setPadding(new Insets(10,10,10,10));
    	pane.setTop(createButton);
    	pane.setBottom(buttons);
    	
    	edit1.setOnAction(this);
    	teamB.setOnAction(this);
    	individual.setOnAction(this);
    	back.setOnAction(e -> compDitails.start2(primaryStage));

        scene = new Scene(pane, 420, 200);
    	primaryStage.setScene(scene);
    	primaryStage.show();

	}
	


	@Override
	public void handle(ActionEvent event) {
	    //public boolean createComp(String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
		boolean bool;

			
			if (event.getSource() == edit1) {
				try {
		        	if(teamB.isSelected())
		        		bool = true;
		        	else
		        		bool = false;
		        	
		        	
		        	
		        	String compNamesh = compName.getText();
		        	String excelsheetName = "";
		        	
		        	for (int i = 0; i < compNamesh.length() - 1; i++) {
		        		excelsheetName = excelsheetName + compNamesh.charAt(i) + "";
		        	}
		        	
		        	boolean edit = db.EditComp(compIndex,excelsheetName, compName.getText(), compLink.getText(), compDate.getText(), bool);
		        	if (edit == true)
		        		addedOrNo.setText("Successfully Edited!");
		        	else
		        		addedOrNo.setText("Faild: The competition name is taken.");
		        	
		        	
		            	compeINFO.add(compName.getText() + "|" + compLink.getText() + "|" + compDate.getText());		        		
		        		teamB.setSelected(false);
		        		individual.setSelected(false);

		            	compName.clear();
		            	compLink.clear();
		            	compDate.clear();
		            	compeINFO.clear();
				}catch(Exception e) {
	        		addedOrNo.setText("Error occurred");

				}
				
				
	        	

			}
			
			if(event.getSource() == teamB) {
				if(teamB.isSelected()) 
					individual.setSelected(false);
			}
			
			if(event.getSource() == individual) {
				if(individual.isSelected()) 
					teamB.setSelected(false);
			}
			
		} 
		


		
	}



