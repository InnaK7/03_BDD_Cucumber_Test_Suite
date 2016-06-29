package com.tasj;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.tasj.PreconditionHelpers.TaskType;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.tasj.CustomConditions.exactTexts;
import static com.tasj.PreconditionHelpers.convertToTaskArray;
import static com.tasj.PreconditionHelpers.given;

public class TodosStepdefs {
    public static ElementsCollection tasks = $$("#todo-list>li");
    public static SelenideElement newTask = $("#new-todo");

    @Given("^open TodoMVC page$")
    public void openTodoMVCPage() {
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @Given("^tasks:$")
    public void tasks(DataTable tasksData) {
        Map<String, TaskType> givenTasks = tasksData.asMap(String.class, TaskType.class);
        ArrayList<String> names = new ArrayList<String>(givenTasks.keySet());
        ArrayList<TaskType> types = new ArrayList<TaskType>(givenTasks.values());
        given(convertToTaskArray(types, names));
    }

    @When("^add tasks: (.*)$")
    public void addTasks(List<String> taskTexts) {
        for(String text: taskTexts) {
            newTask.setValue(text).pressEnter();
        }
    }

    @When("^edit task '(.*)' to have text '(.*)', then press Enter$")
    public void editTaskAndPressEnter(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText).pressEnter();
    }

    @When("^edit task '(.*)' to have text '(.*)', then press Tab$")
    public void editTaskAndPressTab(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText).pressTab();
    }

    @When("^edit task '(.*)' to have text '(.*)', then press Escape$")
    public void editTaskAndPressEscape(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText).pressEscape();
    }

    @When("^start editing task '(.*)' to have text '(.*)'$")
    public static  void startEditingTask(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        tasks.find(cssClass("editing")).$(".edit").setValue(newTaskText);
    }

    @When("^delete task '(.*)'$")
    public void deleteTask(String taskText) {
        tasks.find(exactText(taskText)).hover().find(".destroy").click();
    }

    @When("^toggle task '(.*)'$")
    public void toggleTask(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    @When("^toggle all tasks$")
    public void toggleAllTasks() {
        $("#toggle-all").click();
    }

    @When("^clear completed tasks$")
    public void clearCompletedTasks() {
        $("#clear-completed").click();
    }

    @And("^click outside$")
    public void clickOutside() {
        newTask.click();
    }

    @Then("^tasks are: (.*)$")
    public void tasksAre(List<String> taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    @Then("^there are no tasks left$")
    public void thereAreNoTasks() {
        tasks.shouldBe(empty);
    }

    @And("^items left counter shows (\\d+)$")
    public void itemsLeftCounter(int count) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(count)));
    }

}
