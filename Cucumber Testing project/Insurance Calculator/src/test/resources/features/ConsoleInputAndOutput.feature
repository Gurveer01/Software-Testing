#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: Console Input and Output for Car Details
  As a user
  I want to input car details via console and calculate the insurance premium
  So that I can see the car details and the calculated premium

  Scenario: Input and display car details for a sedan
    Given the user inputs car model "Model S"
    And the user inputs car type "Sedan"
    And the user inputs car price 900000
    And the user inputs insurance type "Basic"
    When the user finishes inputting car details
    Then the car details should be saved correctly
    And the calculated insurance premium should be displayed as "Model = Model S, Type = Sedan, Price = 900000, Insurance Type = Basic, Insurance Premium = 72000.0"

