#Groovy DSL Project
This project contains the source code that accompanies the presentation "Domain-Specific
Languages with Groovy" presentation.

##Email DSL
Source code is in com.bishop.dsl.email. The DSL is provided by the Email class. An example
script can be found in EmailDemo. You'll need to fill in the recipient and sender
email addresses in the DSL script, and update src/main/application.properties with
a username and password for a Gmail account from which the emails will be sent.

##Simulation DSL
Source code is in com.bishop.dsl.simulation. This is roughly based on a DSL I 
developed while at NASA. The DSL is defined in Simulation. SimulationScriptBinding
provides the binding for scripts. The file test.sim contains a sample script. 
SimScriptRunner will execute it. You'll need to pass the file name as an argument.

##Stock market DSL
Source code is in com.bishop.dsl.stock. The DSL is provided in StockScriptLanguage.
A sample script can be found in test.sl. Test data can be found in
/src/main/resource/sl-test-data.json. StockScriptRunner will run the script.
It requires two arguments: the name of the file containing the script and the
name of the file containing the test data.