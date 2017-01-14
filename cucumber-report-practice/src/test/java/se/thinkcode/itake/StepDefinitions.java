package se.thinkcode.itake;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.antiiimage.study.cucumber.report.Belly;

public class StepDefinitions {
    private Belly belly;
    private int waitingTime;
//There are three methods defined. One for each step in the example above. 
    //Each method is annotated with a Gherkin keyword followed by a regular expression that should match the step in the feature. A regular expression group is used to find a parameter that should be picked up from the example. 
    //The first method is getting a digit that describes how many cukes that has been eaten.
    //The steps must be located in the same package or a subpackage relative to the JUnit class that will execute Cucumber.
    @Given("^I have (\\d+) cukes in my belly$")
    public void i_have_cukes_in_my_belly(int cukes) throws Throwable {
        belly = new Belly();
        belly.eat(cukes);
    }

    @When("^I wait (\\d+) hour$")
    public void i_wait_hour(int waitingTime) throws Throwable {
        this.waitingTime = waitingTime;
    }

    @Then("^my belly should (.*)$")
    public void my_belly_should_growl(String expectedSound) throws Throwable {
        String actualSound = belly.getSound(waitingTime);
        assertThat(actualSound, is(expectedSound));
    }
}