package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class MyStepdefs {
    ObjectMapper objectMapper = new ObjectMapper();
    static Response response, delresponse;
    static Employee responseEmployee;
    Employee employee;
    Address address;
    PhoneNumber phoneNumber;

    @Given("employee details")
    public void employeeDetails() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createEmployee");
    }

    @When("create an employee details")
    public void createAnEmployeeDetails() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("createEmployee");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse"));
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().post(Endpoints.employeeEndpoint)
                .then().body(matchesJsonSchemaInClasspath("Post_Schema.json"))
                .statusCode(201).extract().response();
    }

    @Then("the employee details must be created")
    public void theEmployeeDetailsMustBeCreated() throws JsonProcessingException {
        responseEmployee = objectMapper.readValue(response.asString(), Employee.class);
        Assert.assertEquals(employee.getFirstName(), responseEmployee.getFirstName());
        Assert.assertEquals(employee.getLastName(), responseEmployee.getLastName());
        Assert.assertEquals(employee.getAge(), responseEmployee.getAge());
        Assert.assertEquals(employee.getNoOfChildrens(), responseEmployee.getNoOfChildrens());
        Assert.assertEquals(employee.getAddress().getCity(), responseEmployee.getAddress().getCity());
        Assert.assertEquals(employee.getAddress().getStreetAddress(), responseEmployee.getAddress().getStreetAddress());
        Assert.assertEquals(employee.getAddress().getState(), responseEmployee.getAddress().getState());
        Assert.assertEquals(employee.getAddress().getPostalCode(), responseEmployee.getAddress().getPostalCode());
        Assert.assertEquals(employee.getAddress().getCountry(), responseEmployee.getAddress().getCountry());
        Assert.assertEquals(employee.getHobbies(), responseEmployee.getHobbies());

    }

    @When("Get the list of employee")
    public void getTheListOfEmployee() {
        given()
                .when().get(Endpoints.employeeEndpoint)
                .then().statusCode(200).extract().response();
    }

    @Then("The list of employee details must be displayed")
    public void theListOfEmployeeDetailsMustBeDisplayed() throws JsonProcessingException {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createEmployee");

    }

    @When("Update the user")
    public void updateTheUser() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("updateEmployee");
        employee = new Employee(
                (String)jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean)jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject)jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);


        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().put(Endpoints.employeeEndpoint + "/" + responseEmployee.getId())
                .then().body(matchesJsonSchemaInClasspath("Post_Schema.json"))
                .statusCode(200).extract().response();
    }

    @Then("the user is updated")
    public void theUserIsUpdated() throws JsonProcessingException {
        responseEmployee = objectMapper.readValue(response.asString(), Employee.class);
        Assert.assertEquals(employee.getFirstName(), responseEmployee.getFirstName());
        Assert.assertEquals(employee.getLastName(), responseEmployee.getLastName());
        Assert.assertEquals(employee.getAge(), responseEmployee.getAge());
        Assert.assertEquals(employee.getNoOfChildrens(), responseEmployee.getNoOfChildrens());
        Assert.assertEquals(employee.getAddress().getCity(), responseEmployee.getAddress().getCity());
        Assert.assertEquals(employee.getAddress().getStreetAddress(), responseEmployee.getAddress().getStreetAddress());
        Assert.assertEquals(employee.getAddress().getState(), responseEmployee.getAddress().getState());
        Assert.assertEquals(employee.getAddress().getPostalCode(), responseEmployee.getAddress().getPostalCode());
        Assert.assertEquals(employee.getAddress().getCountry(), responseEmployee.getAddress().getCountry());
        Assert.assertEquals(employee.getHobbies(), responseEmployee.getHobbies());

    }

    @Then("the user is deleted")
    public void theUserIsDeleted() {
        delresponse = given()
                .when().delete(Endpoints.employeeEndpoint + "/" + responseEmployee.getId())
                .then().statusCode(200).extract().response();
    }

    @When("get the employee details without given endpoint")
    public void getTheEmployeeDetailsWithoutGivenEndpoint() throws JsonProcessingException {
        response = given()
                .when().get(Endpoints.employeeEndpoint1)
                .then().statusCode(404).extract().response();
    }

    @When("get the employee details without id given")
    public void getTheEmployeeDetailsWithoutIdGiven() {
        given()
                .when().get(Endpoints.employeeEndpoint)
                .then().statusCode(200).extract().response();

    }

    @Then("The list of employee details displayed")
    public void theListOfEmployeeDetailsDisplayed() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getError(), "Not Found");
        Assert.assertEquals(errorObject.getPath(), "/employee");
    }

    @When("creating an Employee with firstName Blank")
    public void creatingAEmployeeWithFirstNameBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("EmployeeWithfirstNameBlank");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long)jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().post(Endpoints.employeeEndpoint)
                .then().statusCode(400).extract().response();

    }

    @Then("Error is thrown when firstName is not given")
    public void errorIsThrownWhenFirstNameIsNotGiven() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "First Name is required");
    }


    @When("creating an Employee with lastName Blank")
    public void creatingAEmployeeWithLastNameBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("EmployeeWithlastNameBlank");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().post(Endpoints.employeeEndpoint)
                .then().statusCode(400).extract().response();
    }

    @Then("Error is Thrown when lastName is not given")
    public void errorIsThrownWhenLastNameIsNotGiven() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "Last Name is required");
    }

    @When("creating an Employee with address Blank")
    public void creatingAEmployeeWithAddressBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("EmployeewithAdressBlank");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String)jsonObject.get("lastName"),
                (Long)jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );

        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().post(Endpoints.employeeEndpoint)
                .then().statusCode(400).extract().response();
    }

    @Then("Error is Thrown when address is not given")
    public void errorIsThrownWhenAddressIsNotGiven() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "Address is required");
    }

    @When("creating an Employee with phoneNumber Blank")
    public void creatingAEmployeeWithPhoneNumberBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("EmployeewithphoneNumberBlank");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String)jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().post(Endpoints.employeeEndpoint)
                .then().statusCode(400).extract().response();

    }

    @Then("Error is Thrown when phoneNumber is not given")
    public void errorIsThrownWhenPhoneNumberIsNotGiven() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "Phone Number is required");
    }

    @When("Updating an Employee without ID")
    public void updatingAEmployeeWithoutID() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("updateEmployee");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));

        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().put(Endpoints.employeeEndpoint)
                .then().statusCode(405).extract().response();
    }

    @Then("Error is Thrown when ID is not given")
    public void errorIsThrownWhenIDIsNotGiven() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getError(), "Method Not Allowed");
        Assert.assertEquals(errorObject.getPath(), "/employees");

    }

    @When("Deleting an Employee without ID")
    public void deletingAEmployeeWithoutID() {
        delresponse = given()
                .when().delete(Endpoints.employeeEndpoint)
                .then().statusCode(405).extract().response();
    }

    @Then("Error is Thrown when ID is not given While Deleting a Employee")
    public void errorIsThrownWhenIDIsNotGivenWhileDeletingAEmployee() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(delresponse.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getError(), "Method Not Allowed");
        Assert.assertEquals(errorObject.getPath(), "/employees");

    }
    @When("Updating a Employee with firstName Blank")
    public void updatingAEmployeeWithFirstNameBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("UpdateEmployeefirstNameBlank");
        employee = new Employee(
                (String)jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long)jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")

        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);
        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));


        JSONArray hobbies1 = (JSONArray) jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().put(Endpoints.employeeEndpoint+ "/" + responseEmployee.getId())
                .then().statusCode(400).extract().response();

    }

    @Then("Error is thrown when firstName is not given While updating")
    public void errorIsThrownWhenFirstNameIsNotGivenWhileUpdating() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "First Name is required");
    }
    @When("Updating a Employee with address Blank")
    public void updatingAEmployeeWithAddressBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("UpdateEmployeeAddressBlank");
        employee = new Employee(
                (String)jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long) jsonObject.get("noOfChildrens"),
                (Boolean)jsonObject.get("spouse")
        );
        JSONArray phonenumber = (JSONArray) jsonObject.get("phoneNumbers");
        JSONObject phonenumber1 = (JSONObject) phonenumber.get(0);
        phoneNumber = new PhoneNumber();
        phoneNumber.setType((String) phonenumber1.get("type"));
        phoneNumber.setNumber((String) phonenumber1.get("number"));
        employee.setPhoneNumbers(Arrays.asList(phoneNumber));


        JSONArray hobbies1 = (JSONArray)jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().put(Endpoints.employeeEndpoint+ "/" + responseEmployee.getId())
                .then().statusCode(400).extract().response();

    }

    @Then("Error is Thrown when address is not given While updating")
    public void errorIsThrownWhenAddressIsNotGivenWhileUpdating() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "Address is required");
    }

    @When("Updating a Employee with phoneNumber Blank")
    public void updatingAEmployeeWithPhoneNumberBlank() {
        JSONObject jsonObject = (JSONObject) TestNGListener.data.get("UpdateEmployeephoneNumberBlank");
        employee = new Employee(
                (String) jsonObject.get("firstName"),
                (String) jsonObject.get("lastName"),
                (Long) jsonObject.get("age"),
                (Long)jsonObject.get("noOfChildrens"),
                (Boolean) jsonObject.get("spouse")
        );
        JSONObject address1 = (JSONObject) jsonObject.get("address");
        address = new Address(
                (String) address1.get("streetAddress"),
                (String) address1.get("city"),
                (String) address1.get("state"),
                (String) address1.get("country"),
                (String) address1.get("postalCode"));
        employee.setAddress(address);

        JSONArray hobbies1 = (JSONArray)jsonObject.get("hobbies");
        Arrays.asList((String) hobbies1.get(0), (String) hobbies1.get(1));
        employee.setHobbies(hobbies1);

        response = given()
                .body(employee)
                .when().put(Endpoints.employeeEndpoint+ "/" + responseEmployee.getId())
                .then().statusCode(400).extract().response();


    }

    @Then("Error is Thrown when phoneNumber is not given While updating")
    public void errorIsThrownWhenPhoneNumberIsNotGivenWhileUpdating() throws JsonProcessingException {
        ErrorObject errorObject = objectMapper.readValue(response.asString(), ErrorObject.class);
        Assert.assertEquals(errorObject.getMessage(), "Phone Number is required");

    }
}