package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class Heading extends Row {

    Heading(XSSFRow row, XSSFWorkbook workbook) {
        super(row, workbook)
    }

    XSSFCell header(String text) {
        XSSFCell cell = row.createCell(cellIndex++)
        cell.setCellValue(text)
        new Cell(cell, workbook).weight("bold")
        cell
    }

}
