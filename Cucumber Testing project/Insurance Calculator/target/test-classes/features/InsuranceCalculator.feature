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

Feature: Calculate Insurance Premium
  As a user
  I want to calculate the insurance premium for a car
  So that I can get the correct insurance amount

  Scenario: Calculate basic insurance premium for a sedan
    Given a car with model "Model S" and type "Sedan" and price 30000 and insurance type "Basic"
    When I calculate the insurance premium
    Then the insurance premium should be 2400.0

  Scenario: Calculate premium insurance premium for an SUV
    Given a car with model "Model X" and type "SUV" and price 50000 and insurance type "Premium"
    When I calculate the insurance premium
    Then the insurance premium should be 6000.0
