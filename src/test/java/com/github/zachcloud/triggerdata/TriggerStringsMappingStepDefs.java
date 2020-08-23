package com.github.zachcloud.triggerdata;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class TriggerStringsMappingStepDefs {

    private TriggerStringsMapping mapping;

    @When("Trigger Strings are read")
    public void trigger_Strings_are_read() {
        this.mapping = new TriggerStringsMapping();
    }

    @Then("Trigger Strings entry {string} should have description {string}")
    public void trigger_Strings_entry_should_have_description(String entry, String description) {
        Assert.assertEquals(description, mapping.getDescription(entry));
    }

    @Then("Trigger Strings entry {string} should have editor data {string}")
    public void trigger_Strings_entry_should_have_editor_data(String entry, String editorData) {
        Assert.assertEquals(editorData, mapping.getEditorData(entry).replace("\"",""));
    }

    @Then("Trigger Strings entry {string} should have hint {string}")
    public void trigger_Strings_entry_should_have_hint(String entry, String hint) {
        Assert.assertEquals(hint, mapping.getHint(entry).replace("\"",""));
    }
}
