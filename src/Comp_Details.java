import java.awt.Toolkit;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Comp_Details implements EventHandler<ActionEvent>{
	
	static Excel db;
	private ComboBox<String> combo = new ComboBox<String>();
    HBox selectComp = new HBox(20); 
    VBox compDetailsLayout = new VBox(10);    
    HBox compName = new HBox(5);
    HBox URL = new HBox(5);
    HBox Date = new HBox(5);
    Label name = new Label("");
    Label url = new Label("");
    Label date = new Label("");
    Text dueDate = new Text("");
    WebView web = new WebView();
    TableView<String[]> table = new TableView<String[]>();
    VBox compInfo = new VBox(5);  
    Button deleteComp,deleteSt,editComp,addStudent,editTeamName,sendEmail,reload,back,openWebsite;   
    Main_View mainView;    
    Stage window;
	BorderPane pane = new BorderPane();
	Scene scene;
	Label status = new Label("");
	boolean flag = false;
	
	//Scenes...
	Edit_Comp editCompScene;
	AddStudentTeam addSt;
	
	
    public void start2(Stage primaryStage) {
    	flag = false;
    	window = primaryStage;
    	mainView = new Main_View();
		try {
			db = new Excel();
		}catch(Exception e) {
			System.out.println("Error: The excel file is missed!");
	        Platform.exit();
	        System.exit(0);
		}	
		window.setTitle("KFUPM news team: View Competitions");
    	pane.setPadding(new Insets(10,10,10,10));
    	pane.setCenter(getLayout());
    	scene = new Scene(pane, 920, 500);
    	window.setScene(scene);
    	window.show();
		
	}
    	
    public VBox getLayout() {
    	try {
        	combo.getItems().clear();
    		name.setText("");
    		url.setText("");
    		date.setText("");
        	web.getEngine().load("");
        	status.setText("");
        	combo.getItems().addAll(db.compNames());
        	combo.setValue("Choose a question");
        	combo.setMinSize(250, 25);
        	combo.setOnAction(this);
        	selectComp.getChildren().addAll(new Label("Select a competition: "),combo);  
        	selectComp.setAlignment(Pos.CENTER);
            web.setMaxWidth(250);
            web.setMaxHeight(200);
            web.setZoom(0.3);

        	
        	compName.getChildren().addAll(new Label("Competition name: "),name);
        	URL.getChildren().addAll(new Label("Competition link: "),url);
        	Date.getChildren().addAll(new Label("Competition Date: "),date, dueDate);
        	
        	deleteComp = new Button("Delete Competition");
        	deleteComp.setOnAction(this);
        	editComp = new Button("Edit Competition");
        	editComp.setOnAction(this);
        	addStudent = new Button("Add Student/Team");
        	addStudent.setOnAction(this);
        	
        	openWebsite = new Button("Fullscreen Website");
        	openWebsite.setOnAction(this);

        	
        	editTeamName  = new Button("Edit Team Name");
        	editTeamName.setOnAction(this);
        	reload  = new Button("Reload window");
        	reload.setOnAction(e -> reloadWindow());
        	back  = new Button("Back");
        	back.setOnAction(this);
        	
        	deleteSt  = new Button("Remove all studnets");
        	deleteSt.setOnAction(this);
        	
        	sendEmail  = new Button("Send an email to student/s");
        	sendEmail.setOnAction(this);
        	
        	HBox editButtons = new HBox(20);
        	editButtons.getChildren().addAll(editComp,addStudent,editTeamName,sendEmail , openWebsite);
        	editButtons.setAlignment(Pos.CENTER);
        	
        	HBox deleteButtons = new HBox(20);
        	deleteButtons.getChildren().addAll(deleteComp,deleteSt);
        	deleteButtons.setAlignment(Pos.CENTER);

        	HBox rankAndReload = new HBox(20);
        	rankAndReload.getChildren().addAll(back,reload);	
        	rankAndReload.setAlignment(Pos.CENTER);

        	VBox buttons = new VBox(20);
        	buttons.getChildren().addAll(editButtons,deleteButtons,rankAndReload);

        	table.setPrefHeight(200);
        	table.setPrefWidth(700);
        	table.setEditable(true);
        	table.setOnKeyPressed(e -> System.out.println(e.getText()));
        	
        	HBox webAndTable = new HBox(10);
        	webAndTable.setPadding(new Insets(10,10,0,10));
        	webAndTable.getChildren().addAll(table,web);
        	compInfo.getChildren().addAll(compName,URL,Date,webAndTable,buttons);
        	HBox statusHbox = new HBox(5);
        	statusHbox.getChildren().addAll(new Label("Status: "), status);
        	compDetailsLayout.getChildren().addAll(selectComp,compInfo,statusHbox);

    	}catch(Exception e) {}
    	return compDetailsLayout;

    }
    
    

	@Override
	public void handle(ActionEvent event){
		status.setText("");
		try {
			if(event.getSource() == back) 
				mainView.start(window);
			
			if(event.getSource() == deleteSt) {
				if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) 
					status.setText("Choose a competition first!");
				else {
					
					areYouSure();
					if(flag == true) {
						try {
							int index = db.compIndex(combo.getValue());
							if(db.deleteAllCompetitiors(index)) {
								status.setText("Delete completed");
								reloadWindow();
							}
								
							else
								status.setText("Error occurred");

						} catch (Exception e) {
							status.setText("Error occurred");

						}
						flag = false;
					}
					
					
				}
			}
			
			if(event.getSource() == editTeamName) {
				if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) 
					status.setText("Choose a competition first!");

				else {
					int index = db.compIndex(combo.getValue());
					teamName(index);
				}
			}

			if(event.getSource() == deleteComp) {

				if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) {
					status.setText("Choose a competition first!");

				}else {
					
					areYouSure();
					if(flag == true) {
						int index = db.compIndex(combo.getValue());
						try {
							if(db.deleteComp(index)) {
								status.setText("Delete completed");
								reloadWindow();
							}
							else
								status.setText("Error occurred");

						} catch (Exception e) {
							status.setText("Error occurred");

						}
						flag = false;
					}

				}			
			}
			
			if(event.getSource() == sendEmail) {
				if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) 
					status.setText("Choose a competition first!");

				else {
					int index = db.compIndex(combo.getValue());
					sendEmail(index);
				}
			}
			
			
			if(event.getSource() == editComp) {
				int index = db.compIndex(combo.getValue());
				editCompScene = new Edit_Comp(index);
				try {
					editCompScene.editCompStart(window);

				}catch(Exception e) {
					status.setText("Choose a competition first!");

				}
			}
			
			if(event.getSource() == addStudent) {
				
				if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) 
					status.setText("Choose a competition first!");
				else {
					addSt = new AddStudentTeam(db.compIndex(combo.getValue()));
					addSt.addStStart(window);
				}

			}
			
			
			if (event.getSource() == combo) {
		        table.getColumns().clear(); //Remove all old columns
				int index = db.compIndex(combo.getValue());
				
				if((db.dueDate(index) >= 0 && db.dueDate(index) <= 5) && !db.isRanked(index)) {
					dueDate.setText("	The due date is soon, please rank the competitors");
					dueDate.setStyle("-fx-font: 20 arial;");
					dueDate.setFill(Color.RED);
					
				}else {
					dueDate.setText("");
				}
				
				try {
					String link = db.compURL(index);
					if(!link.contains("https://"))
						link = "https://" + link;
			    	web.getEngine().load(link);

				}catch(Exception e) {}

				name.setText(db.compName(index));
				url.setText(db.compURL(index));
				date.setText(db.compDate(index));
				TableView(index);

				
			}
			
		}catch(Exception e) {}
		
		if(event.getSource() == openWebsite) {
			if(combo.getValue().equals("Choose a question") || combo.getItems().get(0) == null) 
				status.setText("Choose a competition first!");

			else {
				int index = db.compIndex(combo.getValue());

				String link = db.compURL(index);
				if(!link.contains("https://"))
					link = "https://" + link;
				openWebsite(link);
			}

		}
		
	}
	
	public void reloadWindow() {
		Comp_Details c = new Comp_Details();
		c.start2(window);
	}
	
	@SuppressWarnings("unchecked")
	public void TableView(int index) {

		try {
			String[][] info = db.getStInfo(index);			
			ObservableList<String[]> data = FXCollections.observableArrayList();
	        data.addAll(Arrays.asList(info));
	        data.remove(0);//remove titles from data
	        
            TableColumn<String[], String> nb = new TableColumn<String[], String>("#");
            TableColumn<String[], String> id = new TableColumn<String[], String>("Student ID");
            TableColumn<String[], String> name = new TableColumn<String[], String>("Student Name");
            TableColumn<String[], String> major = new TableColumn<String[], String>("major");
            TableColumn<String[], String> teamNb = new TableColumn<String[], String>("Team #");
            TableColumn<String[], String> teamName = new TableColumn<String[], String>("Team Name");
            TableColumn<String[], String> rank = new TableColumn<String[], String>("Rank");
            
            //set the width
            nb.setPrefWidth(50);
            id.setPrefWidth(110);
            name.setPrefWidth(110);
            major.setPrefWidth(90);
            teamNb.setPrefWidth(90);
            teamName.setPrefWidth(90);
            rank.setPrefWidth(50);
            
            //Close the sort feature
            nb.setSortable(false);
            id.setSortable(false);
            name.setSortable(false);
            major.setSortable(false);
            teamNb.setSortable(false);
            teamName.setSortable(false);
            rank.setSortable(false);
            
            //Make these columns editable 
            id.setCellFactory(TextFieldTableCell.forTableColumn());
            name.setCellFactory(TextFieldTableCell.forTableColumn());
            major.setCellFactory(TextFieldTableCell.forTableColumn());
            rank.setCellFactory(TextFieldTableCell.forTableColumn());
            

            id.setOnEditCommit(e -> {
				try {
		            int rankCell;
		            if(db.isTeamBased(index))
		            	rankCell = 6;
		            else
		            	rankCell = 4;
					int rowNb = (Integer.parseInt(e.getRowValue()[0]) + 4);
					int odlStID = Integer.parseInt(e.getRowValue()[1]);
					int stID = Integer.parseInt(e.getNewValue());
					String stName = e.getRowValue()[2];
					String stMajor = e.getRowValue()[3];	
					String stRank =  e.getRowValue()[rankCell];
					if(db.editStudent(index, rowNb , odlStID , stID , stName, stMajor, stRank))
						status.setText("ID change done\nPlease reload the window to make sure that the changes are saved");
					else
						status.setText("Error occurred");


				} catch (Exception e1) {
					status.setText("Error occurred");
				}
				 
			});
            
            name.setOnEditCommit(e -> {
				try {
		            int rankCell;
		            if(db.isTeamBased(index))
		            	rankCell = 6;
		            else
		            	rankCell = 4;
					int rowNb = (Integer.parseInt(e.getRowValue()[0]) + 4);
					int stID = Integer.parseInt(e.getRowValue()[1]);
					String stName = e.getNewValue();
					String stMajor = e.getRowValue()[3];
					String stRank = e.getRowValue()[rankCell];
					if(db.editStudent(index, rowNb , stID , stID , stName, stMajor, stRank))
						status.setText("Name change done\nPlease reload the window to make sure that the changes are saved");
					else
						status.setText("Error occurred");


				} catch (Exception e1) {
					status.setText("Error occurred");
				}
			});
            
            major.setOnEditCommit(e -> {
				try {
		            int rankCell;
		            if(db.isTeamBased(index))
		            	rankCell = 6;
		            else
		            	rankCell = 4;
					int rowNb = (Integer.parseInt(e.getRowValue()[0]) + 4);
					int stID = Integer.parseInt(e.getRowValue()[1]);
					String stName = e.getRowValue()[2];
					String stMajor = e.getNewValue();
					String stRank = e.getRowValue()[rankCell];
					if(db.editStudent(index, rowNb , stID , stID , stName, stMajor, stRank))
						status.setText("major change done\nPlease reload the window to make sure that the changes are saved");
					else
						status.setText("Error occurred");


				} catch (Exception e1) {
					status.setText("Error occurred");
				}
			});
            
            rank.setOnEditCommit(e -> {
				try {
		            int rankCell;
		            if(db.isTeamBased(index))
		            	rankCell = 6;
		            else
		            	rankCell = 4;
					int rowNb = (Integer.parseInt(e.getRowValue()[0]) + 4);
					int stID = Integer.parseInt(e.getRowValue()[1]);
					String stName = e.getRowValue()[2];
					String stMajor = e.getRowValue()[3];
					String stRank = e.getNewValue();
					if(db.editStudent(index, rowNb , stID , stID , stName, stMajor, stRank))
						status.setText("Rank done\nPlease reload the window to make sure that the changes are saved");
					else
						status.setText("Error occurred");

				} catch (Exception e1) {
					status.setText("Error occurred");
				}
				
				
			});


            
            //This if statement is used to fill the data from the 2D array (info)
            if(db.isTeamBased(index)) {
                table.getColumns().addAll(nb,id,name,major,teamNb,teamName,rank);
	            nb.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[0]));
	                }
	            });
	            id.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[1]));
	                }
	            });
	            name.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[2]));
	                }
	            });
	            major.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[3]));
	                }
	            });
	            teamNb.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[4]));
	                }
	            });
	            teamName.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[5]));
	                }
	            });
	            rank.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[6]));
	                }
	            });

            }else {
                table.getColumns().addAll(nb,id,name,major,rank);
	            nb.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[0]));
	                }
	            });
	            id.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[1]));
	                }
	            });
	            name.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[2]));
	                }
	            });
	            major.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[3]));
	                }
	            });
	            rank.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
	                @Override
	                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
	                    return new SimpleStringProperty((p.getValue()[4]));
	                }
	            });
            
	            
            
            }



	        table.setItems(data);
		}catch(Exception e) {}
		
		
		
	}

    public void teamName(int index) {

    	if(! db.isTeamBased(index)) {
    		status.setText("This is an individual competition!");
    	}
    	else {
        	Stage teamName = new Stage();
        	teamName.initStyle(StageStyle.UTILITY);
        	teamName.initModality(Modality.APPLICATION_MODAL);
        	teamName.setTitle("Change team name");

        	Button change = new Button("Change name");    	
        	Button cancel = new Button("Cancel");
        	
        	HBox buttons = new HBox(10);
        	buttons.getChildren().addAll(cancel , change);
        	
        	VBox layout = new VBox(10);
        	layout.setPadding(new Insets(10,10,10,10));
        	layout.setAlignment(Pos.CENTER);
        	
        	Text text = new Text();    	
        	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
        	text.setText("Change Team Name");
        	text.setTextAlignment(TextAlignment.CENTER);
        	
        	TextField oldName = new TextField(); 	
        	oldName.setPromptText("Enter the old team name");
        	
        	
        	TextField newName = new TextField(); 	
        	newName.setPromptText("Enter the new team name");
        	Label status2 = new Label();
        	layout.getChildren().addAll(text,oldName,newName,buttons,status2);
        	
        	Scene teamNamescene = new Scene(layout);
        	
        	
        	change.setOnAction(e -> {
        		try {
    				if(db.editTeamName(0, oldName.getText(), newName.getText())) {
    					teamName.close();
    					reloadWindow();
    				}
    				else {
    					status2.setText("Team not found!");
    				}
    			} catch (Exception e1) {
    				status2.setText("Error occurred");
    			}  		
        	});

        	cancel.setOnAction(e -> teamName.close());

        	
        	teamName.setScene(teamNamescene);
        	teamName.showAndWait();
    	}


    }
    
    public void areYouSure() {
        	Stage areYouSure = new Stage();
        	areYouSure.initStyle(StageStyle.UTILITY);
        	areYouSure.initModality(Modality.APPLICATION_MODAL);
        	areYouSure.setTitle("Sure?");

        	Button delete = new Button("Yes");    	
        	Button cancel = new Button("Cancel");
        	
        	HBox buttons = new HBox(10);
        	buttons.getChildren().addAll(delete, cancel);
        	
        	VBox layout = new VBox(10);
        	layout.setPadding(new Insets(10,10,10,10));
        	layout.setAlignment(Pos.CENTER);
        	
        	Text text = new Text();    	
        	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
        	text.setText("Are you sure?");
        	text.setTextAlignment(TextAlignment.CENTER);
        	

        	layout.getChildren().addAll(text,buttons);
        	
        	Scene sure = new Scene(layout);
        	
        	
        	delete.setOnAction(e -> {
        		flag = true;
        		areYouSure.close();
        	});

        	cancel.setOnAction(e -> areYouSure.close());

        	
        	areYouSure.setScene(sure);
        	areYouSure.showAndWait();
    	
    }
    
    public void sendEmail(int index) {

    	Send_Email email = new Send_Email();
    	Stage sendEmail = new Stage();
    	sendEmail.initStyle(StageStyle.UTILITY);
    	sendEmail.initModality(Modality.APPLICATION_MODAL);
    	sendEmail.setTitle("Send Email");
    	
    	if(db.isTeamBased(index)) {
    		
        	Text text = new Text();    	
        	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
        	text.setText("Enter the team name");
        	text.setTextAlignment(TextAlignment.CENTER);
        	
        	Button send = new Button("Send");    	
        	Button cancel = new Button("Cancel");
        	
        	HBox buttons = new HBox(10);
        	buttons.getChildren().addAll(cancel , send);
        	
        	VBox layout = new VBox(10);
        	layout.setPadding(new Insets(10,10,10,10));
        	layout.setAlignment(Pos.CENTER);
        	
        	
        	TextField teamName = new TextField(); 	
        	Label status2 = new Label();
        	layout.getChildren().addAll(text,teamName,buttons,status2);
        	
        	Scene teamNamescene = new Scene(layout);
        	
        	
        	send.setOnAction(e -> {
        		try {
        			String tName = teamName.getText();
    				if(db.teamInComp(index, tName) && db.getTeamRank(index, tName) != -1) {  
    					int rank = db.getTeamRank(index, tName);
    					if(email.sendToTeam(db.compName(index), tName, rank, db.getTeamStIdes(index, tName))) {
        					sendEmail.close();
    					}else {
    	    				status2.setText("Error occurred");

    					}
    				}
    				else {
    					status2.setText("Error occurred");
    				}
    			} catch (Exception e1) {
    				status2.setText("Error occurred");
    			}  		
        	});

        	cancel.setOnAction(e -> sendEmail.close());
       	
        	sendEmail.setScene(teamNamescene);
        	sendEmail.showAndWait();        	 		
    	}
    	else {

        	Text text = new Text();    	
        	text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
        	text.setText("Enter the student id");
        	text.setTextAlignment(TextAlignment.CENTER);
        	
        	Button send = new Button("Send");    	
        	Button cancel = new Button("Cancel");
        	
        	HBox buttons = new HBox(10);
        	buttons.getChildren().addAll(cancel , send);
        	
        	VBox layout = new VBox(10);
        	layout.setPadding(new Insets(10,10,10,10));
        	layout.setAlignment(Pos.CENTER);
        	
        	
        	TextField idText = new TextField(); 	
        	Label status2 = new Label();
        	layout.getChildren().addAll(text,idText,buttons,status2);
        	
        	Scene teamNamescene = new Scene(layout);
        	
        	
        	send.setOnAction(e -> {
        		try {
        			int id = Integer.parseInt(idText.getText());
    				if(db.getStRank(index, id) != -1) {  
    					int rank = db.getStRank(index, id);
    					if(email.sendToSt(db.compName(index), db.getStName(index,id), rank, id)) {
        					sendEmail.close();
    					}else {
    	    				status2.setText("Error occurred");
    					}
    				}
    				else {
    					status2.setText("Error occurred");
    				}
    			} catch (Exception e1) {
    				status2.setText("Error occurred");
    			}  		
        	});
        	cancel.setOnAction(e -> sendEmail.close());      	
        	sendEmail.setScene(teamNamescene);
        	sendEmail.showAndWait();        

    	}
    	
    }
  
    public void openWebsite(String link) {
    	Stage webWindow = new Stage();
    	webWindow.setTitle(link);
    	Button back = new Button("Close");   	
    	VBox layout = new VBox(10);
    	layout.setPadding(new Insets(10,10,10,10));
    	layout.setAlignment(Pos.CENTER);
    	
        WebView website = new WebView();
        website.getEngine().load(link);

    	layout.getChildren().addAll(website,back);
    	
    	Scene webScene = new Scene(layout);
    	    	
    	back.setOnAction(e -> {
    		flag = true;
    		webWindow.close();
    	});

    	back.setOnAction(e -> webWindow.close());
  	
    	webWindow.setScene(webScene);
    	webWindow.showAndWait();
    }
}
