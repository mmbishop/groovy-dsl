package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class Workbook {

    private final XSSFWorkbook workbook
    private int sheetIndex = 0

    Workbook(XSSFWorkbook workbook) {
        this.workbook = workbook
    }

    XSSFSheet sheet(@DelegatesTo(Sheet) Closure callable) {
        XSSFSheet xssfSheet = workbook.createSheet()
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        Sheet sheet = new Sheet(xssfSheet, workbook, sheetIndex++)
        callable.delegate = sheet
        callable.call()
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook)
        sheet.setColumnWidths()
        xssfSheet
    }

    void saveAs(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream(new File(fileName))
            workbook.write(out)
        }
        catch (Exception e) {
            e.printStackTrace()
        }
    }

}
