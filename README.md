# Groovy DSL Project
This project contains the source code that accompanies the presentation "Domain-Specific
Languages with Groovy" presentation.

## Email DSL
Source code is in com.bishop.dsl.email. The DSL is provided by the Email class. An example
script can be found in EmailDemo. You'll need to fill in the recipient and sender
email addresses in the DSL script. The example uses SendGrid to send emails. You can
create a SendGrid account for free. You'll need to create and verify a sender, and
create an API key. The API key is stored in ```src/main/resources/application.properties```
under the property ```sendgrid.api-key```. **Do not commit the application.properties file**
or you'll expose the API key, which will get your SendGrid accouht suspended. The
```.gitignore``` file in this repo is already set up to ignore the application properties
file.

## Simulation DSL
Source code is in com.bishop.dsl.simulation. This is roughly based on a DSL I 
developed while at NASA. The DSL is defined in Simulation. SimulationScriptBinding
provides the binding for scripts. The file test.sim contains a sample script. 
SimScriptRunner will execute it. You'll need to pass the file name as an argument.

## Stock market DSL
Source code is in com.bishop.dsl.stock. The DSL is provided in StockScriptLanguage.
A sample script can be found in test.sl. Test data can be found in
/src/main/resource/sl-test-data.json. StockScriptRunner will run the script.
It requires two arguments: the name of the file containing the script and the
name of the file containing the test data.

## Excel spreadsheet generator prototype DSL
Source code is in com.bishop.dsl.bullsheet. The DSL is provided in multiple classes
in the package, rooted at ExcelBuilder. ExcelBuilderTest is a test class that 
uses the DSL to generate a spreadsheet using data in src/main/resources/population-by-zip-code.json.