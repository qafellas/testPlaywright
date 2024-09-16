package org.example.utilities;
import org.json.JSONObject;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.FileWriter;
import java.io.IOException;

public class XmlToJsonConverter {
    public static void main(String[] args) {
        try {
            // Load and parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("./target/results.xml");

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Create JSON object to hold results
            JSONObject resultJson = new JSONObject();
            int passed = 0;
            int failed = 0;
            int skipped = 0;

            // Get all testsuites
            NodeList testSuites = doc.getElementsByTagName("testsuite");

            for (int i = 0; i < testSuites.getLength(); i++) {
                Node testSuiteNode = testSuites.item(i);

                if (testSuiteNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element testSuiteElement = (Element) testSuiteNode;

                    // Get number of tests, failures, and skipped
                    int tests = Integer.parseInt(testSuiteElement.getAttribute("tests"));
                    int failures = Integer.parseInt(testSuiteElement.getAttribute("failures"));
                    int skip = Integer.parseInt(testSuiteElement.getAttribute("skipped"));

                    // Calculate passed tests
                    int passedTests = tests - (failures + skipped);

                    // Update counts
                    passed += passedTests;
                    failed += failures;
                    skipped += skip;
                }
            }

            // Create the final JSON object
            resultJson.put("passed", passed);
            resultJson.put("failures", failed);
            resultJson.put("skipped", skipped);

            // Print JSON result
            System.out.println(resultJson.toString(4)); // Pretty print JSON with indentation

            // Write JSON to a file
            try (FileWriter file = new FileWriter("./target/report.json")) {
                file.write(resultJson.toString(4)); // Pretty print JSON with indentation
                System.out.println("JSON data has been written to report.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
