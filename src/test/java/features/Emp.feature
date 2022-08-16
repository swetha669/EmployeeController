Feature: EmployeeController Feature

  Background: Create an Employee
    Given employee details

  Scenario: Verify that an Employee details
    When create an employee details
    Then the employee details must be created
    Scenario: Verify that Employee details
      When Get the list of employee
      Then The list of employee details must be displayed
  Scenario: Verify that an Employee is updated
    When Update the user
    Then the user is updated
  Scenario: Verify that an Employee is deleted
    Then the user is deleted
    Scenario: Verify that error is thrown when endpoint not given
      When get the employee details without given endpoint
      Then The list of employee details displayed
  Scenario: Verify that error is thrown when incorrect id given
    When get the employee details without id given

  Scenario: Verify that error is thrown when firstName is not given
    When creating an Employee with firstName Blank
    Then Error is thrown when firstName is not given
  Scenario: Verify that error is thrown when lastName is not given
    When creating an Employee with lastName Blank
    Then  Error is Thrown when lastName is not given

  Scenario: Verify that error is thrown when address is not given
    When creating an Employee with address Blank
    Then  Error is Thrown when address is not given

  Scenario: Verify that error is thrown when phoneNumber is not given
    When creating an Employee with phoneNumber Blank
    Then  Error is Thrown when phoneNumber is not given

  Scenario: Verify that error is thrown when ID is not given
    When Updating an Employee without ID
    Then  Error is Thrown when ID is not given
  Scenario: Verify that error is thrown when ID is not given
    When Deleting an Employee without ID
    Then  Error is Thrown when ID is not given
  Scenario: Verify that error is thrown when firstName is not given
    When Updating a Employee with firstName Blank
    Then Error is thrown when firstName is not given While updating
  Scenario: Verify that error is thrown when address is not given
    When Updating a Employee with address Blank
    Then  Error is Thrown when address is not given While updating

  Scenario: Verify that error is thrown when phoneNumber is not given
    When Updating a Employee with phoneNumber Blank
    Then  Error is Thrown when phoneNumber is not given
