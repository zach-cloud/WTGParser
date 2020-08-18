package com.github.zachcloud.triggerdata;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class TriggerDataMappingStepDefs {

    private TriggerDataMapping mapping;

    @When("Trigger Data is read")
    public void trigger_Data_is_read() {
        this.mapping = new TriggerDataMapping();
    }

    @Then("Trigger Data Entry {string} should have {int} arguments")
    public void trigger_Data_Entry_should_have_arguments(String key, int value) {
        Assert.assertEquals(value, mapping.get(key));
    }

}
