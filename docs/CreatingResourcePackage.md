# Stratoss LM Resource Package

## Resource Package Structure

Resource packages for Stratoss LM should contain (at a minimum) the following content for the SOL003 lifecycle driver to work correctly.

```
helloworld.zip
+--- Definitions
|    +--- lm
|         +--- resource.yaml
+--- Docs
|    +--- Readme.md
+--- Lifecycle
     +--- lifecycle.mf
     +--- sol003
          +--- scripts
               +--- CreateVnfRequest.js
               +--- VnfInstance.js
               +--- InstantiateVnfRequest.js
               +--- OperateVnfRequest-Start.js
               +--- OperateVnfRequest-Stop.js
               +--- TerminateVnfRequest.js
               +--- HealVnfRequest.js
               +--- ScaleVnfRequest.js
```
The `resource.yaml` file is the resource descriptor used by LM.

The scripts in the `lifecycle/sol003/scripts` directory can be used to override the default behaviour of the driver.

## Overview of Javascript Message Converters

#### Helper Functions
It is recommended that the scripts import the helper functions to ease script development. More information on how to import the functions and their definitions can be found in the [Javascript Function Reference](JavascriptFunctionReference.md)

#### Message Creation Logic
The `xxxVnfRequest.js` files are used by the driver to create the relevant messages to send to the SOL003-compliant VNFM. It is expected that whilst the driver contains default scripts aligned to the official ETSI specifications, individual VNFMs will require further customisation of these messages and this can be done through supplying these scripts in the resource package.

The scripts are standard Javascript files, as interpreted by the Java "Nashorn" Javascript Intepreter. The following variables are bound to the context and can be used within the scripts.

Variable Name | Variable Contents
--------------|------------------
logger | This is a fully configured SLF4J logger instance that can be used by the script to output logging information to the standard driver logs.
executionRequest | This is the full request information as received by the driver from LM.

The `executionRequest` object has the following internal structure

Field Name | Value
-----------|------
lifecycleName | The lifecycle operation name called in this driver request (should not be needed by the scripts).
systemProperties | A Map object of all the internal "system" properties used by this resource. These are the properties exported from the infrastructure definition that are not present in the resource descriptor.
properties | A Map object of all the properties on this resource instance (as defined by the resource descriptor).
deploymentLocation | The information about the deploymentLocation that this VNF instance should be deployed to. This also has a `properties` field which contains a map of all the information in the infrastructure properties supplied when creating this deployment location within LM.

**IMPORTANT** The message should be built in the script and then the final line of the script should return a JSON string representing the message to be sent. This can be achieved by creating a Javascript object in the script (called `message` in this example) and then having the following line as the last line of the script.
```js
JSON.stringify(message);
```

#### Message Parsing Logic
The `VnfInstance.js` script is used to parse the response information from a CreateVnf request to populate information back into the resource within LM.

The following variables are bound for use within this script.

Variable Name | Variable Contents
--------------|------------------
logger | This is a fully configured SLF4J logger instance that can be used by the script to output logging information to the standard driver logs.
message | The message returned from the VNFM, as a String.
outputs | A Java Map where all key/value pairs should be stored to be returned to LM.

Unlike the message creation logic, no return value is expected (or used) from this script. Any data to be returned to LM should be placed into the `outputs` map.

The message string can be parsed into a Javascript object using the following code.

```js
var parsedMessage = JSON.parse(message);
```
