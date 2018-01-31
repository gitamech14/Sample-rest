package utilities;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/featurefiles/Get.feature"}, format = {
                "pretty", "html:target/site/cucumber-pretty", "json:src/test/resources/dashboard/cucumber.json"})
public class FunctionalTestCases {

}