package com.ducphan.LOCProject;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class AssignColor {
	public HashMap<String, CellStyle> colors = new HashMap<String, CellStyle>();

	public HashMap<String, CellStyle> getColors() {
		return colors;
	}

	public void setColors(HashMap<String, CellStyle> colors) {
		this.colors = colors;
	}

	public void assignColor(ArrayList<String> authors, Workbook workbook) {
		HashMap<String, CellStyle> colorMap = new HashMap<String, CellStyle>();
		ArrayList<CellStyle> indexedColors = new ArrayList<CellStyle>();

		CellStyle s1 = workbook.createCellStyle();
		s1.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
		s1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indexedColors.add(s1);

		CellStyle s2 = workbook.createCellStyle();
		s2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		s2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indexedColors.add(s2);

		CellStyle s3 = workbook.createCellStyle();
		s3.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		s3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indexedColors.add(s3);

		CellStyle s4 = workbook.createCellStyle();
		s4.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		s4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indexedColors.add(s4);

		CellStyle s5 = workbook.createCellStyle();
		s5.setFillForegroundColor(IndexedColors.CORAL.getIndex());
		s5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indexedColors.add(s5);

		int colorsSize = indexedColors.size();
		for (int i = 0; i < authors.size(); i++) {
			int index = i % colorsSize;
			colorMap.put(authors.get(i), indexedColors.get(index));

		}

		this.colors = colorMap;
	}
}
