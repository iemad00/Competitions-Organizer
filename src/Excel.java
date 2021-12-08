import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
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
    
    Excel() throws Exception{
		inFile = new FileInputStream("Competitions Participations.xlsx");
		wb = WorkbookFactory.create(inFile);
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
    
    public boolean deleteComp(int compIndex) throws Exception {
		int nbOfSheets = wb.getNumberOfSheets();
    	if(compIndex >= nbOfSheets) //if there is same sheet name
    		return false;
    	wb.removeSheetAt(compIndex);		
		outFile = new FileOutputStream("Competitions Participations.xlsx");
		wb.write(outFile);
		outFile.flush();
		outFile.close();
    	return true;
    }
    
	public static void main(String[] args) throws Exception {				
		Excel e = new Excel();		
		System.out.println(e.compIndex("CyberHub"));	
		
		System.out.println(e.createComp("test1", "Emad Test", "www.google.com", "13/OCT/2021",true));
		System.out.println(e.createComp("test2", "Emad Test", "www.google.com", "13/OCT/2021",false));

	}
	
    
    
}
