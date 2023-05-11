package com.bishop.dsl.bullsheet

import groovy.json.JsonSlurper

import static com.bishop.dsl.bullsheet.ExcelBuilder.workbook

class ExcelBuilderTest {

    def loadPopulationForZipCodeMap() {
        File populationFile = new File(getClass().getResource("/population-by-zip-code.json").toURI())
        JsonSlurper jsonSlurper = new JsonSlurper()
        return jsonSlurper.parse(populationFile)
    }

    def buildWorkbook(populationForZipCodeMap) {
        int zipCodeCount = populationForZipCodeMap.size()
        def allPopulationCells = "B2:B${zipCodeCount + 1}"
        workbook {
            sheet {
                name "Population by Zip Code"
                // Add a heading, which will be frozen in place.
                heading {
                    header "Zip Code"
                    header "Population"
                }
                // Add the population by zip code data.
                populationForZipCodeMap.each { populationForZipCode ->
                    row {
                        cell populationForZipCode.zipCode
                        cell populationForZipCode.population, { format "#,###" }
                    }
                }
                // Add the total population and average population values to rows 2 and 3.
                row 2, {
                    cell "D","Total Population", { weight "bold" }
                    formula "E", "sum($allPopulationCells)", {
                        format "#,###"
                        border "thin"
                        fillBackground "light_yellow"
                    }
                }
                row 3, {
                    cell "D", "Average Population", { weight "bold" }
                    formula "E", "average($allPopulationCells)", {
                        format "#,###.##"
                        border "thin"
                        fillBackground "#bbddbb"
                    }
                }
                // Add the top 10 heading to row 2.
                row 2, {
                    cell "G", "Top 10", { weight "bold" }
                    cell "H", "Zip Code", { weight "bold" }
                    cell "I", "Population", { weight "bold" }
                    cell "K", "Bottom 10", { weight "bold" }
                    cell "L", "Zip Code", { weight "bold" }
                    cell "M", "Population", { weight "bold" }
                }
                // Add the top 10 zip codes by population to rows 3 through 12.
                (3..12).each { rowNumber ->
                    row rowNumber, {
                        cell "G", rowNumber - 2
                        formula "H", "index(A2:A${zipCodeCount + 1},match(I$rowNumber,B2:B${zipCodeCount + 1},0))"
                        formula "I", "large(B2:B${zipCodeCount + 1},G$rowNumber)", { format "#,###" }
                        cell "K", rowNumber - 2
                        formula "L", "index(A2:A${zipCodeCount + 1},match(M$rowNumber,B2:B${zipCodeCount + 1},0))"
                        formula "M", "small(B2:B${zipCodeCount + 1},K$rowNumber)", { format "#,###" }
                    }
                }
            }
            saveAs "/Users/Michael/Documents/test-sheet.xlsx"
        }
    }

    static void main(String[] args) {
        ExcelBuilderTest builder = new ExcelBuilderTest()
        def populationForZipCodeMap = builder.loadPopulationForZipCodeMap()
        builder.buildWorkbook(populationForZipCodeMap)
    }

}
