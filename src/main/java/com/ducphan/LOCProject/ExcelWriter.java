package com.ducphan.LOCProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

public class ExcelWriter {
	
	public Info info = null;
	public int indexOfFile;
	
	
	public HashMap<String, List<String>> fileChanged = null;
	public HashMap<String, List<String>> fileAdd = null;
	public HashMap<String, List<String>> fileDelete = null;
	
	public HashMap<String, HashMap<String, List<Double>>> map = null;
	public HashMap<String, CellStyle> colors = null;
	
	public HashMap<String, String> authorEmail = null;

	public ExcelWriter(Info info, int indexOfFile) {
	
		this.info = info;
		this.indexOfFile = indexOfFile;
	
	}
	

	//Check if a file is added to the project
		private boolean isAdd(String fileName, String author) {
			if (!fileAdd.containsKey(fileName))
				return false;
			List<String> l = fileAdd.get(fileName);
			return l.contains(author);
		}
		
		public HashMap<String, List<String>> getFileChanged() {
			return fileChanged;
		}



		public HashMap<String, List<String>> getFileAdd() {
			return fileAdd;
		}



		public HashMap<String, List<String>> getFileDelete() {
			return fileDelete;
		}

		public void setFileModification(FileModificationInfo modification) {
			this.fileAdd = modification.getFileAdd();
			this.fileChanged = modification.getFileChanged();
			this.fileDelete = modification.getFileDelete();
		}

		public HashMap<String, HashMap<String, List<Double>>> getMap() {
			return map;
		}

		public void setMap(HashMap<String, HashMap<String, List<Double>>> map) {
			this.map = map;
		}

		public HashMap<String, CellStyle> getColors() {
			return colors;
		}

		public void setColors(HashMap<String, CellStyle> colors) {
			this.colors = colors;
		}

		public HashMap<String, String> getAuthorEmail() {
			return authorEmail;
		}

		public void setAuthorEmail(HashMap<String, String> authorEmail) {
			this.authorEmail = authorEmail;
		}

		//Check if a file is deleted from the project
		private boolean isDelete(String fileName, String author) {
			if (!fileDelete.containsKey(fileName))
				return false;
			List<String> l = fileDelete.get(fileName);
			return l.contains(author);
		}

		//Check if a file is modified in the project
		private boolean isChange(String fileName, String author) {
			if (!fileChanged.containsKey(fileName))
				return false;
			List<String> l = fileChanged.get(fileName);
			return l.contains(author);
		}
	

	//Method to write output to Excel
		public void excelWriter(Workbook workbook, boolean output) {
			String project[] = { "Project name", "Start date", "End date" };

			String columns[] = { "File name", "Number of comment", "Number of lines", "Percentage of comment (%)", "Authors",
					"Email", "LOC Added", "LOC Deleted", "LOC Changed", "File Added", "File Deleted", "File Modified" };
			String checkSign = "\u2713";
			String[] tokens = info.getUrl().split("/");
			String[] gitProject = tokens[4].split("\\.");
			// Create a Sheet
			String index = gitProject[0];
			if (indexOfFile != 0) index+= indexOfFile;
			Sheet sheet = workbook.createSheet(index);
			

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

			if (output == true) {
				int rowNum = 3;
				// Create a Row
				Row headerRow = sheet.createRow(rowNum);
				int lastColumn = 0;
				// Create cells
				for (int i = 0; i < columns.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(columns[i]);
					cell.setCellStyle(headerCellStyle);
					lastColumn = i;
				}

				for (String fileName : map.keySet()) {
					rowNum += 1;
					Row r = sheet.createRow(rowNum);
					Cell cell0 = r.createCell(0);
					cell0.setCellValue(fileName);
					cell0.setCellStyle(fileNameStyle);
					HashMap<String, List<Double>> authorsAndInfo = map.get(fileName);
					Set<String> authors = authorsAndInfo.keySet();
					int temp = rowNum;

					if (authorsAndInfo.containsKey("thePercentage")) {
						List<Double> percentage = authorsAndInfo.get("thePercentage");
						r.createCell(1).setCellValue(percentage.get(0));
						r.createCell(2).setCellValue(percentage.get(1));
						r.createCell(3).setCellValue(percentage.get(2));
						authorsAndInfo.remove("thePercentage");
					}

					ArrayList<String> l = new ArrayList<String>();
					for (String author : authors) {
						l.add(author);
					}

					if (!l.isEmpty()) {
						String name = l.get(0);

						Cell cell4 = r.createCell(4);
						cell4.setCellValue(name);
						cell4.setCellStyle(colors.get(name));
						Cell cell5 = r.createCell(5);
						cell5.setCellValue(authorEmail.get(name));
						cell5.setCellStyle(colors.get(name));

						List<Double> list = authorsAndInfo.get(name);
						r.createCell(6).setCellValue(list.get(0));
						r.createCell(7).setCellValue(list.get(1));
						r.createCell(8).setCellValue(list.get(2));
						if (isAdd(fileName, name))
							r.createCell(9).setCellValue(checkSign);
						if (isDelete(fileName, name))
							r.createCell(10).setCellValue(checkSign);
						if (isChange(fileName, name))
							r.createCell(11).setCellValue(checkSign);
						l.remove(name);

						for (int i = rowNum; i < rowNum + l.size(); i++) {
							Row row = sheet.createRow(i + 1);
							String next = l.get(i - rowNum);
							Cell cellname = row.createCell(4);
							cellname.setCellValue(next);
							cellname.setCellStyle(colors.get(next));
							Cell cellemail = row.createCell(5);
							cellemail.setCellValue(authorEmail.get(next));
							cellemail.setCellStyle(colors.get(next));

							List<Double> listL = authorsAndInfo.get(next);
							row.createCell(6).setCellValue(listL.get(0));
							row.createCell(7).setCellValue(listL.get(1));
							row.createCell(8).setCellValue(listL.get(2));
							if (isAdd(fileName, next))
								row.createCell(9).setCellValue(checkSign);
							if (isDelete(fileName, next))
								row.createCell(10).setCellValue(checkSign);
							if (isChange(fileName, next))
								row.createCell(11).setCellValue(checkSign);
						}
					}
					rowNum += l.size();
					if (l.size() > 0) {
						for (int i = 0; i <= 3; i++) {
							sheet.addMergedRegion(new CellRangeAddress(temp, temp + l.size(), i, i));
						}

					}
				}

				for (int i = 0; i < columns.length; i++) {
					sheet.autoSizeColumn(i);
				}
				RegionUtil.setBorderBottom(BorderStyle.THICK, new CellRangeAddress(0, 1, 0, 2), sheet);
				RegionUtil.setBorderLeft(BorderStyle.THICK, new CellRangeAddress(0, 1, 0, 2), sheet);
				RegionUtil.setBorderRight(BorderStyle.THICK, new CellRangeAddress(0, 1, 0, 2), sheet);
				RegionUtil.setBorderTop(BorderStyle.THICK, new CellRangeAddress(0, 1, 0, 2), sheet);

				RegionUtil.setBorderBottom(BorderStyle.THICK, new CellRangeAddress(3, rowNum, 0, lastColumn), sheet);
				RegionUtil.setBorderLeft(BorderStyle.THICK, new CellRangeAddress(3, rowNum, 0, lastColumn), sheet);
				RegionUtil.setBorderRight(BorderStyle.THICK, new CellRangeAddress(3, rowNum, 0, lastColumn), sheet);
				RegionUtil.setBorderTop(BorderStyle.THICK, new CellRangeAddress(3, rowNum, 0, lastColumn), sheet);

			} else {
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
				cell.setCellValue("Wrong Username or Password");
				cell.setCellStyle(errorStyle);

				for (int i = 0; i < columns.length; i++) {
					sheet.autoSizeColumn(i);
				}
			}
		}
}
