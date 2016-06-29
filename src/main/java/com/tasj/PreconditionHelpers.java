package com.tasj;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.refresh;

public class PreconditionHelpers {

    public static class Task {

        public TaskType taskType;
        public String taskText;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }
    }

    public static Task aTask(String taskText, TaskType taskType) {
        return new Task(taskText, taskType);
    }

    public enum TaskType {
        ACTIVE("false"), COMPLETED("true");

        public String flag;

        TaskType(String flag) {
            this.flag = flag;
        }

        public String getFlag() {
            return flag;
        }
    }

    public static void setUpData(StringBuilder jsCommand) {
        executeJavaScript(jsCommand.toString());
        refresh();
    }

    public static StringBuilder createJSCommandString(Task... todos) {

        StringBuilder jsCommand = new StringBuilder();
        jsCommand.append("localStorage.setItem(\"todos-troopjs\", \"[");
        for (Task todo : todos) {
            jsCommand.append("{\\\"completed\\\":").append(todo.taskType.getFlag()).append(", \\\"title\\\":\\\"").append(todo.taskText).append("\\\"}, ");
        }
        if (todos.length != 0)
            jsCommand.deleteCharAt(jsCommand.length() - 2);
        jsCommand.append("]\")");

        return jsCommand;
    }

    public static Task[] convertToTaskArray(ArrayList<TaskType> taskTypes, ArrayList<String> taskTexts) {
        Task[] todos = new Task[taskTexts.size()];
        for (int i = 0; i < todos.length; i++) {
            todos[i] = aTask(taskTexts.get(i), taskTypes.get(i));
        }
        return todos;
    }

    public static void given(Task... todos) {
        setUpData(createJSCommandString(todos));
    }

}
