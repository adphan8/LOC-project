package com.ducphan.LOCProject;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WrongDateToExcel {
	public Info info = null;
	int indexOfFile;
	
	public WrongDateToExcel (Info info, int i){
		this.info = info;
		this.indexOfFile = i;
	}
	 
	//Method to handle exceptions and output to excel file
		public void wrongDateToExcel(Workbook workbook, boolean date) {
			String project[] = { "Project name", "Start date", "End date" };
			String[] tokens = info.getUrl().split("/");
			String[] gitProject = tokens[4].split("\\.");
			String name = gitProject[0];
			if (indexOfFile != 0) name+= indexOfFile;
			Sheet sheet = workbook.createSheet(name);
			
			

			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			// headerFont.setColor(IndexedColors.ORANGE.getIndex());

			// Create a CellStyle with the font
			CellStyle style = workbook.createCellStyle();
			style.setBorderBottom(BorderStyle.THICK);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderRight(BorderStyle.THICK);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(BorderStyle.THICK);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderBottom(BorderStyle.THICK);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			CellStyle headerCellStyle = style;
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle fileNameStyle = workbook.createCellStyle();
			fileNameStyle.setFont(headerFont);

			Row title = sheet.createRow(0);
			for (int i = 0; i < project.length; i++) {
				Cell cell = title.createCell(i);
				cell.setCellValue(project[i]);
				cell.setCellStyle(headerCellStyle);
			}

			Row firstRow = sheet.createRow(1);
			
			firstRow.createCell(0).setCellValue(gitProject[0]);
			firstRow.createCell(1).setCellValue(info.getStartDate());
			firstRow.createCell(2).setCellValue(info.getEndDate());

			int rowNum = 3;
			// Create a Row
			Row headerRow = sheet.createRow(rowNum);
			// Create cells
			CellStyle errorStyle = workbook.createCellStyle();
			errorStyle.setBorderBottom(BorderStyle.THICK);
			errorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			errorStyle.setBorderRight(BorderStyle.THICK);
			errorStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			errorStyle.setBorderTop(BorderStyle.THICK);
			errorStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			errorStyle.setBorderBottom(BorderStyle.THICK);
			errorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			Font font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.RED.getIndex());
			errorStyle.setFont(font);
			Cell cell = headerRow.createCell(0);
			if (date == true) {
				cell.setCellValue("Time interval is too early");
			} else {
				cell.setCellValue("Nothing to output.");
//				throw new OutOfDateRangeException("Out Of Range");
			}
			cell.setCellStyle(errorStyle);

			for (int i = 0; i < project.length; i++) {
				sheet.autoSizeColumn(i);
			}

		}

}
