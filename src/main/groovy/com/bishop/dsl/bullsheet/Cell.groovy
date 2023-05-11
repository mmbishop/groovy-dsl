package com.bishop.dsl.bullsheet

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.DataFormat
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.util.POILogger
import org.apache.poi.util.SystemOutLogger
import org.apache.poi.xssf.usermodel.*

class Cell {

    private final SystemOutLogger logger = new SystemOutLogger()

    private XSSFCell cell
    private XSSFCellStyle cellStyle = null
    private XSSFWorkbook workbook

    Cell(XSSFCell cell, XSSFWorkbook workbook) {
        this.cell = cell
        this.workbook = workbook
    }

    def weight(String weightString) {
        XSSFCellStyle cellStyle = getCellStyle()
        XSSFFont font = workbook.createFont()
        FontStyleFactory.getFontStyle(weightString).applyTo(font)
        cellStyle.setFont(font)
        cell.setCellStyle(cellStyle)
    }

    def format(String formatString) {
        XSSFCellStyle cellStyle = getCellStyle()
        DataFormat format = workbook.createDataFormat()
        cellStyle.setDataFormat(format.getFormat(formatString))
        cell.setCellStyle(cellStyle)
    }

    def border(String borderType) {
        try {
            setAllBorders(BorderStyle.valueOf(borderType.toUpperCase()))
        }
        catch (IllegalArgumentException | NullPointerException e) {
            logger.log(POILogger.ERROR, "Invalid border style argument")
        }
    }

    private void setAllBorders(BorderStyle borderStyle) {
        XSSFCellStyle cellStyle = getCellStyle()
        cellStyle.setBorderBottom(borderStyle)
        cellStyle.setBorderLeft(borderStyle)
        cellStyle.setBorderRight(borderStyle)
        cellStyle.setBorderTop(borderStyle)
        cell.setCellStyle(cellStyle)
    }

    def borderColor(String colorName) {
        setAllBorderColors(getColorFrom(colorName))
    }

    private void setAllBorderColors(def color) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setTopBorderColor, color)
        setColor(cellStyle.&setLeftBorderColor, color)
        setColor(cellStyle.&setRightBorderColor, color)
        setColor(cellStyle.&setBottomBorderColor, color)
        cell.setCellStyle(cellStyle)
    }

    def topBorder(String borderType) {
        try {
            XSSFCellStyle cellStyle = getCellStyle()
            cellStyle.setBorderTop(BorderStyle.valueOf(borderType.toUpperCase()))
            cell.setCellStyle(cellStyle)
        }
        catch (IllegalArgumentException | NullPointerException e) {
            logger.log(POILogger.ERROR, "Invalid border style argument")
        }
    }

    def topBorderColor(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setTopBorderColor, getColorFrom(colorName))
        cell.setCellStyle(cellStyle)
    }

    def leftBorder(String borderType) {
        try {
            XSSFCellStyle cellStyle = getCellStyle()
            cellStyle.setBorderLeft(BorderStyle.valueOf(borderType.toUpperCase()))
            cell.setCellStyle(cellStyle)
        }
        catch (IllegalArgumentException | NullPointerException e) {
            logger.log(POILogger.ERROR, "Invalid border style argument")
        }
    }

    def leftBorderColor(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setLeftBorderColor, getColorFrom(colorName))
        cell.setCellStyle(cellStyle)
    }

    def rightBorder(String borderType) {
        try {
            XSSFCellStyle cellStyle = getCellStyle()
            cellStyle.setBorderRight(BorderStyle.valueOf(borderType.toUpperCase()))
            cell.setCellStyle(cellStyle)
        }
        catch (IllegalArgumentException | NullPointerException e) {
            logger.log(POILogger.ERROR, "Invalid border style argument")
        }
    }

    def rightBorderColor(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setRightBorderColor, getColorFrom(colorName))
        cell.setCellStyle(cellStyle)
    }

    def bottomBorder(String borderType) {
        try {
            XSSFCellStyle cellStyle = getCellStyle()
            cellStyle.setBorderBottom(BorderStyle.valueOf(borderType.toUpperCase()))
            cell.setCellStyle(cellStyle)
        }
        catch (IllegalArgumentException | NullPointerException e) {
            logger.log(POILogger.ERROR, "Invalid border style argument")
        }
    }

    def bottomBorderColor(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setBottomBorderColor, getColorFrom(colorName))
        cell.setCellStyle(cellStyle)
    }

    def fillForeground(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        def color = getColorFrom(colorName)
        if (color) {
            setColor(cellStyle.&setFillBackgroundColor, color)
            cell.setCellStyle(cellStyle)
        }
    }

    def fillBackground(String colorName) {
        XSSFCellStyle cellStyle = getCellStyle()
        setColor(cellStyle.&setFillForegroundColor, getColorFrom(colorName))
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
        cell.setCellStyle(cellStyle)
    }

    private XSSFCellStyle getCellStyle() {
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle()
        }
        cellStyle
    }

    def getColorFrom(String colorName) {
        if (colorName.startsWith("#")) {
            getDecodedColor(colorName)
        }
        else {
            IndexedColors.valueOf(colorName.toUpperCase()).getIndex()
        }
    }

    private XSSFColor getDecodedColor(String colorSpec) {
        try {
            byte[] colorBytes = Hex.decodeHex(colorSpec.substring(1).toCharArray())
            return new XSSFColor(colorBytes, null)
        }
        catch (DecoderException e) {
            String message = "Invalid color specification. Syntax is #rrggbb"
            logger.log(POILogger.ERROR, message, e)
            throw new InvalidColorSpecificationException(message, e);
        }
    }

    def setColor(def setColorMethod, def color) {
        setColorMethod(color)
    }

}
