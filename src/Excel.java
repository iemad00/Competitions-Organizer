import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excel {
    private FileInputStream inFile;
    private FileOutputStream outFile;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private String nb,id,name,major,rank;
    
    
    Excel() throws Exception{
		inFile = new FileInputStream("Competitions Participations.xlsx");
		wb = WorkbookFactory.create(inFile);
    }
    
    Excel(String nb,String id,String name,String major,String rank){ //for table view
    	this.nb = nb;
    	this.id = id;
    	this.name = name;
    	this.major = major;
    	this.rank = rank;
    }
    
    
    //It will return the names of sheets as an array
    public String[] compNames() {   	
		int nbOfSheets = wb.getNumberOfSheets();
    	String[] names = new String[nbOfSheets];
    	for(int i = 0;i<nbOfSheets;i++) {
    		sh = wb.getSheetAt(i);
    		names[i] = sh.getSheetName();

    	}   	
    	return names;
    }
    
    //Returns number of sheets
    public int numOfComps() {
    	return wb.getNumberOfSheets();
    }
    
    //This method gives you an array of competition information, the first index represent the competition name,second> URL, third> Date
    public String[] compInfo(int compIndex) {
    	String[] info = new String[3];
    	sh = wb.getSheetAt(compIndex);
    	info[0] = String.valueOf(sh.getRow(0).getCell(1)); //Comp name
    	info[1] = String.valueOf(sh.getRow(1).getCell(1)); //Comp URL
    	info[2] = String.valueOf(sh.getRow(2).getCell(1)); //Comp Date
    	return info;
    }
    
    //==========================================================
    //To get a specific info
    public String compName(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(0).getCell(1)));
    }   
    public String getSheetName(int compIndex) {
    	return wb.getSheetName(compIndex);
    }
    public int compIndex(String sheetName) {
    	sh = wb.getSheet(sheetName);
		int nbOfSheets = wb.getNumberOfSheets();
		for(int i=0;i<nbOfSheets;i++) {
			if(wb.getSheetAt(i) == wb.getSheet(sheetName))
				return i;
		}		
    	return -1; //if the sheetName not found
    } 
    public String compURL(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(1).getCell(1)));
    }
    public String compDate(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	return (String.valueOf(sh.getRow(2).getCell(1)));
    }
    //==========================================================
       
    //Here I check the cell that shows team name, if it appear true, else false
    public boolean isTeamBased(int compIndex) {
    	sh = wb.getSheetAt(compIndex);
    	if(sh.getRow(4).getCell(5) != null)
    		return true;
    	return false;
    }
    
    
    public boolean createComp(String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
    	if(compIndex(sheetName) != -1) //if there is same sheet name
    		return false;
    	
    	sh = wb.createSheet(sheetName);
    	
        
        sh.setColumnWidth(0, 4500);
        sh.setColumnWidth(1, 4000);
        sh.setColumnWidth(2, 4500);
        sh.setColumnWidth(3, 4000);

        
		row = sh.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Competition Name");		
		cell = row.createCell(1);
		cell.setCellValue(compName);
		
		row = sh.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Competition Link");		
		cell = row.createCell(1);
		cell.setCellValue(URL);
		
		row = sh.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("competition date");	
		cell = row.createCell(1);
		cell.setCellValue(date);
		
		row = sh.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("Student ID");	
		cell = row.createCell(2);
		cell.setCellValue("Student Name");	
		cell = row.createCell(3);
		cell.setCellValue("Major");	
		if(isTeamBased) {
	        sh.setColumnWidth(4, 4000);
	        sh.setColumnWidth(5, 4000);
			cell = row.createCell(4);
			cell.setCellValue("Team");	
			cell = row.createCell(5);
			cell.setCellValue("Team Name");
			cell = row.createCell(6);
			cell.setCellValue("Rank");
		}else {
			cell = row.createCell(4);
			cell.setCellValue("Rank");
		}
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	
    	return true;
    }
    
    public boolean EditComp(int compIndex,String sheetName,String compName,String URL,String date, boolean isTeamBased) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!wb.getSheetName(compIndex).equals(sheetName)) {//if the sheet name didn't change, ignore
    		if(compIndex(sheetName) != -1) //if there is same sheet name   			
    			return false;
    			
    	}
    		  	
    	sh = wb.getSheetAt(compIndex);
    	wb.setSheetName(compIndex, sheetName);
        sh.setColumnWidth(0, 4500);
        sh.setColumnWidth(1, 4000);
        sh.setColumnWidth(2, 4500);
        sh.setColumnWidth(3, 4000);
       
		row = sh.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Competition Name");		
		cell = row.createCell(1);
		cell.setCellValue(compName);
		
		row = sh.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Competition Link");		
		cell = row.createCell(1);
		cell.setCellValue(URL);
		
		row = sh.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("competition date");	
		cell = row.createCell(1);
		cell.setCellValue(date);
		
		if(isTeamBased(compIndex) != isTeamBased) { //Here to remove the competitors, if the type changed
			int end = sh.getLastRowNum();
			for(int i = 5; i <= end;i++) {
				sh.removeRow(sh.getRow(i));
			}
		}	
		
		row = sh.createRow(4);
		cell = row.createCell(1);
		cell.setCellValue("Student ID");	
		cell = row.createCell(2);
		cell.setCellValue("Student Name");	
		cell = row.createCell(3);
		cell.setCellValue("Major");	

		if(isTeamBased) {
	        sh.setColumnWidth(4, 4000);
	        sh.setColumnWidth(5, 4000);
			cell = row.createCell(4);
			cell.setCellValue("Team");	
			cell = row.createCell(5);
			cell.setCellValue("Team Name");
			cell = row.createCell(6);
			cell.setCellValue("Rank");
		}else {
			cell = row.createCell(4);
			cell.setCellValue("Rank");
		}
		

		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	
    	return true;
    }
    
    public boolean deleteAllCompetitiors(int compIndex) throws Exception {
    	sh = wb.getSheetAt(compIndex);
		int end = sh.getLastRowNum();
    	if(end == 4)
    		return false;
    	
		for(int i = 5; i <= end;i++) {
			try {
				sh.removeRow(sh.getRow(i));
				
			}catch(Exception e) {}
		}		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
		return true;
    }
    
    public boolean addStudent(int compIndex,int stID, String stName,String stMajor) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);
		
		//To determine if there are any same id number before
		if(idInComp(compIndex,stID))
			return false;
		
		row = sh.createRow(sh.getLastRowNum() + 1);
    	cell = row.createCell(0);
		cell.setCellValue(sh.getLastRowNum() - 4);
		
    	cell = row.createCell(1);
		cell.setCellValue(stID);

    	cell = row.createCell(2);
		cell.setCellValue(stName);
		
    	cell = row.createCell(3);
		cell.setCellValue(stMajor);
		
		
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    private int howManyTeams(int compIndex) {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return -1;
    	
    	if(!isTeamBased(compIndex))
    		return 0;
    	int temp =0;
    	String teamName = null;
    	sh = wb.getSheetAt(compIndex);
    	for(int i = 5; i<=sh.getLastRowNum();i++) {
    		String nextTeam = String.valueOf(sh.getRow(i).getCell(5));
    		if(teamName != nextTeam) {
    			temp++;
    			teamName = nextTeam;
    		}
    	}
    	return temp;
    }
    
    public boolean addTeam(int compIndex,int[] stID,String[] stName,String[] stMajor,String teamName) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!isTeamBased(compIndex))
    		return false;
    	if(stID.length != stName.length || stID.length != stMajor.length) //if there are miss information
    		return false;
    	
		int end = sh.getLastRowNum();
    	sh = wb.getSheetAt(compIndex);
    	
    	//to determine if there same team name before
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
				return false;
		}
		
		
    	int howManyTeams= howManyTeams(compIndex);
    	for(int i = 0;i<stName.length;i++) {
        	
    		row = sh.createRow(sh.getLastRowNum() + 1);
        	cell = row.createCell(0);
    		cell.setCellValue(sh.getLastRowNum() - 4);
    		
        	cell = row.createCell(1);
    		cell.setCellValue(stID[i]);

        	cell = row.createCell(2);
    		cell.setCellValue(stName[i]);
    		
        	cell = row.createCell(3);
    		cell.setCellValue(stMajor[i]);
    		
        	cell = row.createCell(4);
    		cell.setCellValue(howManyTeams + 1);
    		
        	cell = row.createCell(5);
    		cell.setCellValue(teamName);
    	}	
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    private boolean idInComp(int compIndex,int stID) {
    	sh = wb.getSheetAt(compIndex);
		int end = sh.getLastRowNum();
		
		//To determine if there are any same id number before
		for(int i = 5; i <= end;i++) { 
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == stID)
					return true;
			}catch(Exception e){}
		}
		
    	return false;
    }
    

    
    public boolean editStudent(int compIndex,int rowNb,int pastStID,int newStID, String stName,String stMajor,String stRank) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	
    	sh = wb.getSheetAt(compIndex);	
    	
		//To determine if there are any same id number before
		if(!idInComp(compIndex,pastStID))
			return false;
			
		
		//To make sure if there a duplicated IDes or not
		int end = sh.getLastRowNum();
		int rowNumber = -1;
		for(int i = 5;i <= end;i++) {
			try {
				if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == pastStID)
					rowNumber = Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(0))) + 4;
			}catch(Exception e) {}

		}
		if(pastStID!=newStID)
		for(int i = 5;i<=end;i++) {
			if(i != rowNumber)
				try {
					if(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)))  == newStID)
						return false;
				}catch(Exception e) {}
		}
		
		
		row = sh.getRow(rowNb);
		
    	cell = row.createCell(1);
		cell.setCellValue(newStID);

    	cell = row.createCell(2);
		cell.setCellValue(stName);
		
    	cell = row.createCell(3);
		cell.setCellValue(stMajor);
		
		if(stRank.equals("")) {
			if(! isTeamBased(compIndex)) 
		    	row.removeCell(row.getCell(4));			
			else {
				String teamName = new DataFormatter().formatCellValue(row.getCell(5));
				rankTeam(compIndex,teamName,-1);			
				
			}
		} else {
		try {
			int rank = Integer.parseInt(stRank);
			if(! isTeamBased(compIndex)) {
		    	cell = row.createCell(4);
				cell.setCellValue(rank);
			}else {
				String teamName = new DataFormatter().formatCellValue(row.getCell(5));
				rankTeam(compIndex,teamName,rank);
			}
		}catch(Exception e) {
			return false;
		}
		}	
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
    private boolean rankTeam(int compIndex,String teamName,int rank) throws IOException {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(! isTeamBased(compIndex))
    		return false;
		
		if(!teamInComp(compIndex,teamName)) //if the team name not there
			return false;
				
		if(rank == -1) {
	    	sh = wb.getSheetAt(compIndex);		
			int end = sh.getLastRowNum();
			
			for(int i = 5;i<=end;i++) {
				if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
					sh.getRow(i).removeCell(sh.getRow(i).getCell(6));
			}
		}else {
	    	sh = wb.getSheetAt(compIndex);		
			int end = sh.getLastRowNum();
			
			for(int i = 5;i<=end;i++) {
				if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
					sh.getRow(i).createCell(6).setCellValue(rank);
			}
		}		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    

    
    public boolean editTeamName(int compIndex,String oldName,String newName) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	if(!isTeamBased(compIndex))
    		return false;
    	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		
		if(!teamInComp(compIndex,oldName)) //if the team name not there
			return false;
	
		if(teamInComp(compIndex,newName)) //if the team name complicated
			return false;
		
		for(int i = 5; i <= end; i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(oldName)) {
				sh.getRow(i).createCell(5).setCellValue(newName);
			}
		}
		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;  	
    }
    
    public boolean deleteComp(int compIndex) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex > nbOfSheets-1) //if the index out of array
    		return false;
    	
    	wb.removeSheetAt(compIndex);		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    

    
    public boolean teamInComp(int compIndex,String teamName) {
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
				return true;
		}
		return false;
    }
    
    
    public int[] getTeamStIdes(int compIndex,String teamName) {
    	if(! isTeamBased(compIndex)) 
    		return new int[0];
		ArrayList<Integer> temp = new ArrayList<Integer>();
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName)) 
				temp.add(Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1))));
			
		}
		
		int[] ides = new int[temp.size()];
		for(int i = 0 ; i< temp.size() ; i++) 
			ides[i] = temp.get(i);			
		return ides;
    }
    

    public int getTeamRank(int compIndex,String teamName) {  	  	
    	if(! isTeamBased(compIndex)) 
    		return -1;
    	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(String.valueOf(sh.getRow(i).getCell(5)).equals(teamName))
				if(sh.getRow(i).getCell(6) == null) {
					return -1;
				}else {
					return Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(6)));
				}
		}
		return -1;		
    }
    
    public int getStRank(int compIndex,int id) {  	  	
    	if(isTeamBased(compIndex)) 
    		return -1;  	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)).equals(String.valueOf(id)))
				if(sh.getRow(i).getCell(4) == null) {
					return -1;
				}else {
					return Integer.parseInt(new DataFormatter().formatCellValue(sh.getRow(i).getCell(4)));
				}
		}

		return -1;		
    }
    
    public String getStName(int compIndex, int id) {
    	if(isTeamBased(compIndex)) 
    		return null;  	
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		for(int i = 5; i <= end;i++) {
			if(new DataFormatter().formatCellValue(sh.getRow(i).getCell(1)).equals(String.valueOf(id)))
				if(sh.getRow(i).getCell(2) == null) {
					return null;
				}else {
					return new DataFormatter().formatCellValue(sh.getRow(i).getCell(2));
				}
		}

		return null;	
    }
    
    public String[][] getStInfo(int compIndex) {
    	String[][] info;
    	sh = wb.getSheetAt(compIndex);		
    	int numOfSt = sh.getLastRowNum() - 4;
    	if(!isTeamBased(compIndex)) {
        	info = new String[numOfSt+1][5];
        	info[0][0] = "#";
        	info[0][1] = "Student ID";
        	info[0][2] = "Student Name";
        	info[0][3] = "Major";
        	info[0][4] = "Rank";

        	for(int i = 5;i<= sh.getLastRowNum();i++) {  		
        		for(int j = 0;j< 5;j++) {
        			info[i-4][j] = new DataFormatter().formatCellValue(sh.getRow(i).getCell(j));
        		}   		
        	}
    	}else {
    		info = new String[numOfSt+1][7]; 
        	info[0][0] = "#";
        	info[0][1] = "Student ID";
        	info[0][2] = "Student Name";
        	info[0][3] = "Major";
        	info[0][4] = "Team #";
        	info[0][5] = "Team Name";
        	info[0][6] = "Rank";

        	for(int i = 5;i<= sh.getLastRowNum();i++) {  		
        		for(int j = 0;j< 7;j++) {
        			info[i-4][j] = new DataFormatter().formatCellValue(sh.getRow(i).getCell(j));
        		}   		
        	}
    	}
    	return info;
    }
    
    
    
    public long dueDate(int compIndex) {

    	sh = wb.getSheetAt(compIndex);		
    	String compDate =String.valueOf(sh.getRow(2).getCell(1));
    	String[] date = new String[3];
    	if(compDate.contains("-"))
    		date = compDate.split("-");
    	else if(compDate.contains("/"))
    		date = compDate.split("/");   	
    	int cYear =Integer.parseInt(date[2]);
    	int cMon = 0;
    	int cDay =Integer.parseInt(date[0]);
    	
    	try {
    		cMon = Integer.parseInt(date[1]);
    	}catch(Exception e) {
    		if(date[1].toLowerCase().equals("jan")) {
    			cMon = 1;
    		}else if(date[1].toLowerCase().equals("feb"))
    			cMon = 2;
    		else if(date[1].toLowerCase().equals("mar"))
    			cMon = 3;
    		else if(date[1].toLowerCase().equals("apr"))
    			cMon = 4;
    		else if(date[1].toLowerCase().equals("may"))
    			cMon = 5;
    		else if(date[1].toLowerCase().equals("jun"))
    			cMon = 6;
    		else if(date[1].toLowerCase().equals("jul"))
    			cMon = 7;
    		else if(date[1].toLowerCase().equals("aug"))
    			cMon = 8;
    		else if(date[1].toLowerCase().equals("sep"))
    			cMon = 9;
    		else if(date[1].toLowerCase().equals("oct"))
    			cMon = 10;
    		else if(date[1].toLowerCase().equals("nov"))
    			cMon = 11;
    		else if(date[1].toLowerCase().equals("dec"))
    			cMon = 12;
    		
    	}
    	long noOfDaysBetween = -1;
    	try {
    		LocalDate dateBefore = LocalDate.now();
    		LocalDate dateAfter = LocalDate.of(cYear, cMon, cDay);
    		noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);	
    	}catch(Exception e) {
    		
    	}
	
    	return noOfDaysBetween;
    }
    

    
    public boolean isRanked(int compIndex) {
    	sh = wb.getSheetAt(compIndex);		
		int end = sh.getLastRowNum();
		if(isTeamBased(compIndex)) {
	    	for(int i=5;i<=end;i++) {
	    		if(sh.getRow(i).getCell(6) == null || new DataFormatter().formatCellValue(sh.getRow(i).getCell(6)).equals("-"))
	    			return false;
	    	}    	
		}else {
	    	for(int i=5;i<=end;i++) {
	    		if(sh.getRow(i).getCell(4) == null || new DataFormatter().formatCellValue(sh.getRow(i).getCell(4)).equals("-"))
	    			return false;
	    	}
		}
    	return true;
    }
    
    public String[] allDueSoon() {
		int nbOfSheets = wb.getNumberOfSheets();
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0 ; i < nbOfSheets;i++) {
			if(! isRanked(i) && dueDate(i) <= 5 && dueDate(i) >= 0) {
				temp.add(getSheetName(i));
			}
		}   	
    	String[] names = new String[temp.size()];
    	for(int i = 0 ; i < temp.size();i++) 
    		names[i] = temp.get(i); 	
    	return names;
    }
    

}
