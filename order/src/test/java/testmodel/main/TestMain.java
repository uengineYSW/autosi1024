package testmodel.main;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import testmodel.common.CucumberSpingConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = { "pretty", "html:target/cucumber" },
    features = "src/test/resources/features",
    extraGlue = {
        "testmodel/common", "testmodel.common.CucumberSpingConfiguration",
    }
)
public class TestMain {}
