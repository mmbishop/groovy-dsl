package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFFont

interface FontStyle {

    XSSFFont applyTo(XSSFFont font)

}