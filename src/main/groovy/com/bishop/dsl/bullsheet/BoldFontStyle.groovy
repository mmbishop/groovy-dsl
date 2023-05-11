package com.bishop.dsl.bullsheet

import org.apache.poi.xssf.usermodel.XSSFFont

class BoldFontStyle implements FontStyle {

    @Override
    XSSFFont applyTo(XSSFFont font) {
        font.setBold(true)
        return font
    }

}
