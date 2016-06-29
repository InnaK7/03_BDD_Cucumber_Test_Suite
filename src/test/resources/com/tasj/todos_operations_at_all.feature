@clean
Feature: Todos Operations At All

	Background:
		Given open TodoMVC page

	Scenario: add tasks
		When add tasks: a, b, c
		Then tasks are: a, b, c
		And items left counter shows 3

	Scenario: delete task
		Given tasks:
			| a | ACTIVE    |
			| b | ACTIVE    |
		When delete task 'a'
		Then tasks are: b
		And items left counter shows 1

	Scenario: edit task with <Enter>
		Given tasks:
			| a | ACTIVE    |
			| b | ACTIVE    |
		When edit task 'a' to have text 'a edited', then press Enter
		Then tasks are: a edited, b
		And items left counter shows 2

	Scenario: complete task
		Given tasks:
			| a | ACTIVE    |
			| b | ACTIVE    |
		When toggle task 'a'
		Then tasks are: a, b
		And items left counter shows 1

	Scenario: complete all tasks
		Given tasks:
			| a | ACTIVE    |
			| b | ACTIVE    |
			| c | COMPLETED |
		When toggle all tasks
		Then tasks are: a, b, c
		And items left counter shows 0

	Scenario: clear completed tasks
		Given tasks:
			| a | ACTIVE    |
			| b | COMPLETED |
			| c | COMPLETED |
		When clear completed tasks
		Then tasks are: a
		And items left counter shows 1

	Scenario: reopen task
		Given tasks:
			| a | COMPLETED |
		When toggle task 'a'
		Then tasks are: a
		And items left counter shows 1

	Scenario: reopen all tasks
		Given tasks:
			| a | COMPLETED |
			| b | COMPLETED |
		When toggle all tasks
		Then tasks are: a, b
		And items left counter shows 2

	Scenario: edit task with <Tab>
		Given tasks:
			| a | ACTIVE    |
			| b | COMPLETED |
		When edit task 'a' to have text 'a edited', then press Tab
		Then tasks are: a edited, b
		And items left counter shows 1

	Scenario: edit task and confirm editing with click outside
		Given tasks:
			| a | ACTIVE    |
		When start editing task 'a' to have text 'a edited'
		And click outside
		Then tasks are: a edited
		And items left counter shows 1

	Scenario: cancel task editing
		Given tasks:
			| a | ACTIVE    |
			| b | ACTIVE    |
		When edit task 'a' to have text 'a edited', then press Escape
		Then tasks are: a, b
		And items left counter shows 2

	Scenario: delete task by clearing its name
		Given tasks:
			| a | ACTIVE    |
		When edit task 'a' to have text ' ', then press Enter
		Then there are no tasks left




