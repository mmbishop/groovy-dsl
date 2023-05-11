package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class Row {

    protected XSSFRow row
    protected XSSFWorkbook workbook
    protected int cellIndex

    Row(XSSFRow row, XSSFWorkbook workbook) {
        this.row = row
        this.workbook = workbook
        cellIndex = 0
    }

    XSSFCell cell(Object value) {
        createCell(cellIndex++, value)
    }

    XSSFCell cell(String column, String value) {
        createCell(getColumnIndex(column), value)
    }

    XSSFCell cell(String column, Number value) {
        createCell(getColumnIndex(column), value)
    }

    XSSFCell cell(String column, Date value) {
        createCell(getColumnIndex(column), value)
    }

    XSSFCell cell(Object value, @DelegatesTo(Cell) Closure attributes) {
        createCellWithAttributes(cellIndex++, value, attributes)
    }

    XSSFCell cell(String column, Object value, @DelegatesTo(Cell) Closure attributes) {
        createCellWithAttributes(getColumnIndex(column), value, attributes)
    }

    XSSFCell cell(Map m) {
        if (m.column && m.value && m.attributes) {
            cell(m.column as String, m.value, m.attributes as Closure)
        }
        else if (m.column && m.value) {
            cell(m.column as String, m.value)
        }
        else if (m.value && m.attributes) {
            cell(m.value, m.attributes)
        }
        else {
            cell(m.value)
        }
    }

    private XSSFCell createCell(int columnIndex, Object value) {
        XSSFCell cell = row.createCell(columnIndex)
        if (columnIndex > cellIndex) {
            cellIndex = columnIndex
        }
        cell.setCellValue(value)
        cell
    }

    private XSSFCell createCellWithAttributes(int columnIndex, Object value, @DelegatesTo(Cell) Closure attributes) {
        XSSFCell cell = row.createCell(columnIndex)
        if (columnIndex > cellIndex) {
            cellIndex = columnIndex
        }
        cell.setCellValue(value)
        if (attributes) {
            attributes.resolveStrategy = Closure.DELEGATE_FIRST
            attributes.delegate = new Cell(cell, workbook)
            attributes.call()
        }
        cell
    }

    private int getColumnIndex(String columnName) {
        return ((int) columnName.charAt(0) - (int) 'A'.charAt(0))
    }

    XSSFCell formula(String formula) {
        createFormula(cellIndex++, formula)
    }

    XSSFCell formula(String column, String formula) {
        createFormula(getColumnIndex(column), formula)
    }

    XSSFCell formula(String formula, @DelegatesTo(Cell) Closure attributes) {
        createFormulaWithAttributes(cellIndex++, formula, attributes)
    }

    XSSFCell formula(String column, String formula, @DelegatesTo(Cell) Closure attributes) {
        createFormulaWithAttributes(getColumnIndex(column), formula, attributes)
    }

    private XSSFCell createFormula(int columnIndex, String formula) {
        XSSFCell cell = row.createCell(columnIndex)
        cell.setCellFormula(formula)
        cell
    }

    private XSSFCell createFormulaWithAttributes(int columnIndex, String formula, @DelegatesTo(Cell) Closure attributes) {
        XSSFCell cell = createFormula(columnIndex, formula)
        if (attributes) {
            attributes.resolveStrategy = Closure.DELEGATE_FIRST
            attributes.delegate = new Cell(cell, workbook)
            attributes.call()
        }
        cell
    }

    int getCellCount() {
        cellIndex
    }

}
