# load-balanced-converter

### Prerequisites:
* Java 1.8
* maven 3.2+ (optional if you want to build on your own)

### Build project with maven
Build command:

    $ mvn clean install
    
### Run application
First run converters
1. Go to $OWN_PROJECT_PATH/converter/target and tap following command to run converter service as a separate proccess:

    $ nohup java -jar converter-1.0-SNAPSHOT.jar &
    
2. Run other services on diffrent ports (given port numbers are mandatory!)

    $ nohup java -jar converter-1.0-SNAPSHOT.jar & --server.port=8092
    $ nohup java -jar converter-1.0-SNAPSHOT.jar & --server.port=9996
    
3. Run load balancer in $OWN_PROJECT_PATH/load-balancer/target:

    $ nohup java -jar load-balancer-1.0-SNAPSHOT.jar &
   
### Test your application
Now you can check your by executing http request:

    $ localhost:8888/convertXMLToJSON?"path=$PATH_TO_XML_FILE"
    
For example:

    $ localhost:8888/convertXMLToJSON?"path=/tmp/simple.xml"
    
The output should be new json file in the same directory as given to request.


