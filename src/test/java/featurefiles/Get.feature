Feature: To test the Employee getter

Scenario Outline: Test GET
  Given scenario "<scenario>"
  When the id of employee is "<id>"
  Then the status code of "<expectedStatus>" and "<expectedJson>" are returned 

  Examples:
    | scenario 		  | id 	| expectedStatus |expectedJson|
    | Get an Employee |  E01 |  200           |get.json    |
