package model;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Employee {

    private int id;

    private String firstName;

    private String lastName;
    private Long age;


    private Long noOfChildrens;
    private boolean spouse;

public Employee() {
}
    public Employee(String firstName,String lastName,Long age,Long noOfChildrens,boolean spouse){
        this.firstName=firstName;
        this.lastName=lastName;
        this.age=age;
        this.noOfChildrens=noOfChildrens;
        this.spouse=spouse;

    }
    private Address address;
    private List<PhoneNumber> phoneNumbers;
private List<String> hobbies = new ArrayList<>();

}



