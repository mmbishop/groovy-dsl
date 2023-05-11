package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class Sheet {

    private final XSSFSheet sheet
    private final XSSFWorkbook workbook
    private int rowIndex
    private final int sheetIndex
    private List<Row> rowList = []

    Sheet(XSSFSheet sheet, XSSFWorkbook workbook, int sheetIndex) {
        this.sheet = sheet
        this.workbook = workbook
        this.sheetIndex = sheetIndex
        rowIndex = 0
    }

    XSSFRow row(@DelegatesTo(Row) Closure callable) {
        XSSFRow xssfRow = sheet.createRow(rowIndex++)
        Row row = new Row(xssfRow, workbook)
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = row
        callable.call()
        rowList << row
        xssfRow
    }

    XSSFRow row(int rowNumber, @DelegatesTo(Row) Closure callable) {
        XSSFRow xssfRow
        Row row = rowList.find { it.row.rowNum == rowNumber - 1 }
        if (row) {
            xssfRow = row.row
        }
        else {
            xssfRow = sheet.createRow(rowNumber - 1)
            row = new Row(xssfRow, workbook)
        }
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = row
        callable.call()
        rowList << row
        xssfRow
    }

    XSSFRow heading(@DelegatesTo(Heading) Closure callable) {
        XSSFRow xssfRow = sheet.createRow(rowIndex++)
        Heading heading = new Heading(xssfRow, workbook)
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = heading
        callable.call()
        rowList << heading
        sheet.createFreezePane(0, rowIndex)
        xssfRow
    }

    void name(String name) {
        workbook.setSheetName(sheetIndex, name)
    }

    void setColumnWidths() {
        int maxColumnCount = 0
        rowList.each { row ->
            if (row.getCellCount() > maxColumnCount) {
                maxColumnCount = row.getCellCount()
            }
        }
        for (i in 0..maxColumnCount) {
            sheet.autoSizeColumn(i)
        }
    }

}
