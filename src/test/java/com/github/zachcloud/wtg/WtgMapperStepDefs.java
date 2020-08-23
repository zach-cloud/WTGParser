package com.github.zachcloud.wtg;

import com.github.zachcloud.wtg.structure.WtgRoot;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.net.URL;

public class WtgMapperStepDefs {

    private File wtgFile;
    private WtgRoot root;

    @Given("The file {string}")
    public void the_file(String filePath) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(filePath);
        wtgFile = new File(url.getPath());
    }

    @When("WTG File is read")
    public void wtg_File_is_read() {
        root = new WtgMapper().read(wtgFile);
    }

    @Then("There should be {int} trigger categories")
    public void there_should_be_trigger_categories(int count) {
        Assert.assertEquals(count, root.getTriggerCategories().size());
        Assert.assertEquals(count, root.getTriggerCategoryCount());
    }
    @Then("There should be {int} variables")
    public void there_should_be_variables(int count) {
        Assert.assertEquals(count, root.getVariableCount());
        Assert.assertEquals(count, root.getVariables().size());
    }
    @Then("There should be {int} triggers")
    public void there_should_be_triggers(int count) {
        Assert.assertEquals(count, root.getTriggerCount());
        Assert.assertEquals(count, root.getTriggers().size());
    }
}
