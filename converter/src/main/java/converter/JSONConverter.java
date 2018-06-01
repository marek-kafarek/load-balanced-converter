package converter;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@SpringBootApplication
public class JSONConverter {

  private static Logger log = LoggerFactory.getLogger(JSONConverter.class);

  @Value("${server.port}")
  private int serverPort;

  @RequestMapping(value = "/convertXMLToJSON")
  public String convertXMLToJSON(@RequestParam("path") String path) {
    log.info("Access /converter");

    String xmlContent;
    try {
      xmlContent = FileUtils.readFileToString(new File(path), "UTF-8");
    } catch (IOException e) {
      String errorStatus = String.format("Could not read file %s", path);
      log.error(errorStatus, e);
      return errorStatus;
    }
    JSONObject xmlJSONObj = XML.toJSONObject(xmlContent);
    String jsonContent = xmlJSONObj.toString(4);

    File outputFile = new File(path.replace(".xml", ".json"));

    try {
      FileUtils.writeStringToFile(outputFile, jsonContent, "UTF-8");
    } catch (IOException e) {
      String errorStatus = String.format("Could not write to file %s", outputFile);
      log.error(errorStatus, e);
      return errorStatus;
    }

    return String.format("Successfully created new JSON file %s, response from local server port %s", outputFile, serverPort);
  }

  @RequestMapping(value = "/")
  public String home() {
    log.info("Access /");
    return "Successful access";
  }

  public static void main(String[] args) {
    SpringApplication.run(JSONConverter.class, args);
  }
}
