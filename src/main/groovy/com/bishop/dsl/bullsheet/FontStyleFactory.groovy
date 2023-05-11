package com.bishop.dsl.bullsheet

class FontStyleFactory {

    static FontStyle getFontStyle(String fontStyleName) {
        switch (fontStyleName) {
            case "bold":
                return new BoldFontStyle()
            default:
                throw new IllegalArgumentException("Invalid font style")
        }
    }

}
