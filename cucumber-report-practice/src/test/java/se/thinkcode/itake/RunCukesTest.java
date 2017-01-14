package se.thinkcode.itake;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "json:target/cucumber.json" })
public class RunCukesTest {
	// The class name ends with Test. This means that the Surefire plugin in
	// Maven will be able to pick it up and execute it
}