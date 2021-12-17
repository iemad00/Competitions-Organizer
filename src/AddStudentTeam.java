import java.util.ArrayList;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.poi.util.ArrayUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddStudentTeam {
	Stage MainStage;
	Scene scene;
	Excel db;
	TextField studentName = new TextField();
	TextField studentMajor = new TextField();
	TextField studentID = new TextField();
	TextField teamName = new TextField();
	TextField teamRank = new TextField();
	TextField TFnumOfStudents = new TextField();
	Stage window;
	Comp_Details compDetails = new Comp_Details();
	Label status = new Label("");
	int compIndex;
	
	AddStudentTeam(int compIndex){
		this.compIndex = compIndex;
	}
	
	static void stage(Stage stage, Scene scene, String title) {
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
		}
	
	 public GridPane TeamGridPane() {
			status.setText("");

 		GridPane gridPane = new GridPane();


 		ToggleGroup group = new ToggleGroup();


 		gridPane.setAlignment(Pos.CENTER);

 		gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
 		gridPane.setHgap(5.5);

 		gridPane.setVgap(5.5);
 		gridPane.setPrefSize(420, 200);
 		gridPane.add(new Label("Team  Name:"), 0, 0);
 		gridPane.add(teamName, 1, 0);

 		gridPane.add(new Label("Number of students"),0,1);
 		gridPane.add(TFnumOfStudents,1,1);
		
		Button btBack = new Button("Back");
		Button btAdd = new Button("Add Student/s");

		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(btBack,btAdd);
		gridPane.addRow(4, buttons);
		
		HBox st = new HBox(5);
		st.getChildren().addAll(new Label("status: ") , status);
		gridPane.addRow(5, st);
		
		btBack.setOnAction(e -> compDetails.start2(window));
		btAdd.setOnAction(e-> {
			  if(teamName.getText().equals("") || TFnumOfStudents.getText().equals("")) 
					status.setText("Error occurred");


			  else {					
					try {
						int x = Integer.parseInt(TFnumOfStudents.getText());
						if(x > 5 || x <= 0) {
							throw new Exception();
						}
						stage(MainStage, new Scene(StudentsAddGridPane()),"Students adding in team");
					}catch(Exception a){
						status.setText("Error occurred");
					}
			  }
		});
		
		return gridPane;
	}
	 
	 public GridPane StudentsAddGridPane() {
			status.setText("");
		  GridPane gridPane = new GridPane();	

		  try {
			    gridPane.setAlignment(Pos.CENTER);
				gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
				gridPane.setHgap(5.5);
				gridPane.setVgap(5.5);
				gridPane.setPrefSize(400, 200);
		
				
				TextField name1 = new TextField(),name2 = new TextField(),name3 = new TextField(),name4 = new TextField(),name5 = new TextField();
				TextField id1 = new TextField(),id2 = new TextField(),id3 = new TextField(),id4 = new TextField(),id5 = new TextField();
				TextField major1 = new TextField(),major2 = new TextField(),major3 = new TextField(),major4 = new TextField(),major5 = new TextField();

				int numberOfSt =Integer.parseInt(TFnumOfStudents.getText());
				
				if(numberOfSt == 1) {
					gridPane.add(new Label("					Student 1"), 0, 0);
					gridPane.add(new Label("Student Name:"), 0, 1);
					gridPane.add(name1, 1, 1);				
					gridPane.add(new Label("Student ID: "), 0, 2);
					gridPane.add(id1, 1, 2);					
					gridPane.add(new Label("Major: "), 0, 3);
					gridPane.add(major1, 1, 3);
					
				}else if(numberOfSt == 2) {
					gridPane.add(new Label("					Student 1"), 0, 0);
					gridPane.add(new Label("Student Name:"), 0, 1);
					gridPane.add(name1, 1, 1);				
					gridPane.add(new Label("Student ID: "), 0, 2);
					gridPane.add(id1, 1, 2);					
					gridPane.add(new Label("Major: "), 0, 3);
					gridPane.add(major1, 1, 3);
					
					gridPane.add(new Label("					Student 2"), 0, 4);
					gridPane.add(new Label("Student Name:"), 0, 5);
					gridPane.add(name2, 1, 5);				
					gridPane.add(new Label("Student ID: "), 0, 6);
					gridPane.add(id2, 1, 6);					
					gridPane.add(new Label("Major: "), 0, 7);
					gridPane.add(major2, 1,7);
					
				}else if(numberOfSt == 3) {
					gridPane.add(new Label("					Student 1"), 0, 0);
					gridPane.add(new Label("Student Name:"), 0, 1);
					gridPane.add(name1, 1, 1);				
					gridPane.add(new Label("Student ID: "), 0, 2);
					gridPane.add(id1, 1, 2);					
					gridPane.add(new Label("Major: "), 0, 3);
					gridPane.add(major1, 1, 3);
					
					gridPane.add(new Label("					Student 2"), 0, 4);
					gridPane.add(new Label("Student Name:"), 0, 5);
					gridPane.add(name2, 1, 5);				
					gridPane.add(new Label("Student ID: "), 0, 6);
					gridPane.add(id2, 1, 6);					
					gridPane.add(new Label("Major: "), 0, 7);
					gridPane.add(major2, 1,7);
					
					gridPane.add(new Label("					Student 3"), 0, 8);
					gridPane.add(new Label("Student Name:"), 0, 9);
					gridPane.add(name3, 1, 9);				
					gridPane.add(new Label("Student ID: "), 0, 10);
					gridPane.add(id3, 1, 10);					
					gridPane.add(new Label("Major: "), 0, 11);
					gridPane.add(major3, 1,11);
					
				}else if(numberOfSt == 4) {
					gridPane.add(new Label("					Student 1"), 0, 0);
					gridPane.add(new Label("Student Name:"), 0, 1);
					gridPane.add(name1, 1, 1);				
					gridPane.add(new Label("Student ID: "), 0, 2);
					gridPane.add(id1, 1, 2);					
					gridPane.add(new Label("Major: "), 0, 3);
					gridPane.add(major1, 1, 3);
					
					gridPane.add(new Label("					Student 2"), 0, 4);
					gridPane.add(new Label("Student Name:"), 0, 5);
					gridPane.add(name2, 1, 5);				
					gridPane.add(new Label("Student ID: "), 0, 6);
					gridPane.add(id2, 1, 6);					
					gridPane.add(new Label("Major: "), 0, 7);
					gridPane.add(major2, 1,7);
					
					gridPane.add(new Label("					Student 3"), 0, 8);
					gridPane.add(new Label("Student Name:"), 0, 9);
					gridPane.add(name3, 1, 9);				
					gridPane.add(new Label("Student ID: "), 0, 10);
					gridPane.add(id3, 1, 10);					
					gridPane.add(new Label("Major: "), 0, 11);
					gridPane.add(major3, 1,11);
					
					gridPane.add(new Label("					Student 4"), 0, 12);
					gridPane.add(new Label("Student Name:"), 0, 13);
					gridPane.add(name4, 1, 13);				
					gridPane.add(new Label("Student ID: "), 0, 14);
					gridPane.add(id4, 1, 14);					
					gridPane.add(new Label("Major: "), 0, 15);
					gridPane.add(major4, 1,15);
					
				}else if(numberOfSt == 5) {
					gridPane.add(new Label("					Student 1"), 0, 0);
					gridPane.add(new Label("Student Name:"), 0, 1);
					gridPane.add(name1, 1, 1);				
					gridPane.add(new Label("Student ID: "), 0, 2);
					gridPane.add(id1, 1, 2);					
					gridPane.add(new Label("Major: "), 0, 3);
					gridPane.add(major1, 1, 3);
					
					gridPane.add(new Label("					Student 2"), 0, 4);
					gridPane.add(new Label("Student Name:"), 0, 5);
					gridPane.add(name2, 1, 5);				
					gridPane.add(new Label("Student ID: "), 0, 6);
					gridPane.add(id2, 1, 6);					
					gridPane.add(new Label("Major: "), 0, 7);
					gridPane.add(major2, 1,7);
					
					gridPane.add(new Label("					Student 3"), 0, 8);
					gridPane.add(new Label("Student Name:"), 0, 9);
					gridPane.add(name3, 1, 9);				
					gridPane.add(new Label("Student ID: "), 0, 10);
					gridPane.add(id3, 1, 10);					
					gridPane.add(new Label("Major: "), 0, 11);
					gridPane.add(major3, 1,11);
					
					gridPane.add(new Label("					Student 4"), 0, 12);
					gridPane.add(new Label("Student Name:"), 0, 13);
					gridPane.add(name4, 1, 13);				
					gridPane.add(new Label("Student ID: "), 0, 14);
					gridPane.add(id4, 1, 14);					
					gridPane.add(new Label("Major: "), 0, 15);
					gridPane.add(major4, 1,15);
					
					gridPane.add(new Label("					Student 5"), 0, 16);
					gridPane.add(new Label("Student Name:"), 0, 17);
					gridPane.add(name5, 1, 17);				
					gridPane.add(new Label("Student ID: "), 0, 18);
					gridPane.add(id5, 1, 18);					
					gridPane.add(new Label("Major: "), 0, 19);
					gridPane.add(major5, 1,19);
					
				}



				
				

				
				Button btBack = new Button("Back");
				btBack.setOnAction(e -> compDetails.start2(window));
				Button btAdd = new Button("Add Team");

				HBox buttons = new HBox(20);
				buttons.getChildren().addAll(btBack,btAdd);
				gridPane.addRow(numberOfSt* 4 + 1, buttons);
				HBox st = new HBox(5);
				st.getChildren().addAll(new Label("status: ") , status);
				gridPane.addRow(numberOfSt* 4 + 2, st);
				
				btBack.setOnAction(e-> {
				stage(MainStage, new Scene(TeamGridPane()),"Add Team");
				});
				
				btAdd.setOnAction(e -> {
					try {
						
						String[] names = new String[numberOfSt];
						int[] ides = new int[numberOfSt];
						String[] majors = new String[numberOfSt];
						
						
						if(numberOfSt == 1) {
							if(name1.getText().equals("") || id1.getText().equals("") || major1.getText().equals("")) {
								throw new Exception();
							}else {
								names[0] = name1.getText();
								ides[0] = Integer.parseInt(id1.getText());
								majors[0] = major1.getText();
								
								if(db.addTeam(compIndex, ides, names, majors, teamName.getText()))
									compDetails.start2(window);
								else 
									throw new Exception();							
							}
							
						}else if(numberOfSt == 2) {
							
							if(name1.getText().equals("") || id1.getText().equals("") || major1.getText().equals("") || name2.getText().equals("") || id2.getText().equals("") || major2.getText().equals("")) {
								throw new Exception();
							}else {
								names[0] = name1.getText();
								ides[0] = Integer.parseInt(id1.getText());
								majors[0] = major1.getText();
								
								names[1] = name2.getText();
								ides[1] = Integer.parseInt(id2.getText());
								majors[1] = major2.getText();
								
								if(db.addTeam(compIndex, ides, names, majors, teamName.getText()))
									compDetails.start2(window);
								else 
									throw new Exception();							
							}
							
						}else if(numberOfSt == 3) {
							
							if(name1.getText().equals("") || id1.getText().equals("") || major1.getText().equals("") ||
									name2.getText().equals("") || id2.getText().equals("") || major2.getText().equals("") ||
									name3.getText().equals("") || id3.getText().equals("") || major3.getText().equals("")) {
								throw new Exception();
							}else {
								names[0] = name1.getText();
								ides[0] = Integer.parseInt(id1.getText());
								majors[0] = major1.getText();
								
								names[1] = name2.getText();
								ides[1] = Integer.parseInt(id2.getText());
								majors[1] = major2.getText();
								
								names[2] = name3.getText();
								ides[2] = Integer.parseInt(id3.getText());
								majors[2] = major3.getText();
								
								if(db.addTeam(compIndex, ides, names, majors, teamName.getText()))
									compDetails.start2(window);
								else 
									throw new Exception();							
							}
							
							
						}else if(numberOfSt == 4) {
							
							if(name1.getText().equals("") || id1.getText().equals("") || major1.getText().equals("") ||
									name2.getText().equals("") || id2.getText().equals("") || major2.getText().equals("") ||
									name3.getText().equals("") || id3.getText().equals("") || major3.getText().equals("") || 
									name4.getText().equals("") || id4.getText().equals("") || major4.getText().equals("")) {
								throw new Exception();
							}else {
								names[0] = name1.getText();
								ides[0] = Integer.parseInt(id1.getText());
								majors[0] = major1.getText();
								
								names[1] = name2.getText();
								ides[1] = Integer.parseInt(id2.getText());
								majors[1] = major2.getText();
								
								names[2] = name3.getText();
								ides[2] = Integer.parseInt(id3.getText());
								majors[2] = major3.getText();
								
								names[3] = name4.getText();
								ides[3] = Integer.parseInt(id4.getText());
								majors[3] = major4.getText();
								
								if(db.addTeam(compIndex, ides, names, majors, teamName.getText()))
									compDetails.start2(window);
								else 
									throw new Exception();							
							}
							
							
						}else if(numberOfSt == 5) {
							
							if(name1.getText().equals("") || id1.getText().equals("") || major1.getText().equals("") ||
									name2.getText().equals("") || id2.getText().equals("") || major2.getText().equals("") ||
									name3.getText().equals("") || id3.getText().equals("") || major3.getText().equals("") || 
									name4.getText().equals("") || id4.getText().equals("") || major4.getText().equals("") || 
									name5.getText().equals("") || id5.getText().equals("") || major5.getText().equals("")) {
								throw new Exception();
							}else {
								names[0] = name1.getText();
								ides[0] = Integer.parseInt(id1.getText());
								majors[0] = major1.getText();
								
								names[1] = name2.getText();
								ides[1] = Integer.parseInt(id2.getText());
								majors[1] = major2.getText();
								
								names[2] = name3.getText();
								ides[2] = Integer.parseInt(id3.getText());
								majors[2] = major3.getText();
								
								names[3] = name4.getText();
								ides[3] = Integer.parseInt(id4.getText());
								majors[3] = major4.getText();
								
								names[4] = name5.getText();
								ides[4] = Integer.parseInt(id5.getText());
								majors[4] = major5.getText();
								
								if(db.addTeam(compIndex, ides, names, majors, teamName.getText()))
									compDetails.start2(window);
								else 
									throw new Exception();							
							}
							
						}

						
						
					}catch(Exception k) {
						status.setText("Error occurred");

					}					
				});
				
			  
					
		  }catch(Exception e) {
				status.setText("Error occurred");
		  }
			return gridPane;

		}
	 
	
	 
	public GridPane StudentGridPane() {
		status.setText("");

		GridPane gridPane = new GridPane();

		ToggleGroup group = new ToggleGroup();


		gridPane.setAlignment(Pos.CENTER);

		gridPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		gridPane.setHgap(5.5);

		gridPane.setVgap(5.5);
		gridPane.setPrefSize(400, 200);

		// Place nodes in the gridPane
		gridPane.add(new Label("Student Name:"), 0, 0);
		gridPane.add(studentName, 1, 0);

		gridPane.add(new Label("Student ID: "), 0, 1);
		gridPane.add(studentID, 1, 1);

		gridPane.add(new Label("Major: "), 0, 2);
		gridPane.add(studentMajor, 1, 2);

		
		Button btBack = new Button("Back");
		btBack.setOnAction(e -> compDetails.start2(window));
		Button btAdd = new Button("Add Student");

		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(btBack,btAdd);
		gridPane.addRow(4, buttons);
		HBox st = new HBox(5);
		st.getChildren().addAll(new Label("status: ") , status);
		gridPane.addRow(5, st);

		btAdd.setOnAction(e-> {
	try {
		
		if(db.addStudent(compIndex, Integer.parseInt(studentID.getText()), studentName.getText(), studentMajor.getText()))			
			compDetails.start2(window);
		else 
			status.setText("Error occurred");
			

	} catch (Exception e1) {
		status.setText("Error occurred");
	}
		});
		return gridPane;
	}
	
	
	public void addStStart(Stage primaryStage) {
		status.setText("");

		window = primaryStage;
		status.setText("");
		try {
			db = new Excel();
		}catch(Exception e) {
			System.out.println("Error: The excel file is missed!");
	        Platform.exit();
	        System.exit(0);
		}	
			
		MainStage = primaryStage;		
		
		if(! db.isTeamBased(compIndex)) {
			scene = new Scene(StudentGridPane());
			stage(MainStage,scene,"Add Student");

		}else {
			scene = new Scene(TeamGridPane());
			stage(MainStage,scene,"Add Team");
		}
		
		
	}



}
