package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ExcelBuilder {

    static Workbook workbook(@DelegatesTo(Workbook) Closure callable) {
        XSSFWorkbook workbook = new XSSFWorkbook()
        callable.resolveStrategy = Closure.DELEGATE_FIRST
        callable.delegate = new Workbook(workbook)
        callable.call()
        return new Workbook(workbook)
    }

}
