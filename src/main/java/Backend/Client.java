package Backend;

import java.util.UUID;

public class Client {
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String address;
    private String healthInsuranceNumber;
    private int age;
    private UUID uuid;

    public Client(String firstName, String lastName, String phoneNo, String email, String address, int age, UUID uuid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
        this.age = age;
        this.uuid = UUID.randomUUID();
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }
    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }
    public void getClientByUuid(UUID uuid) {
        this.uuid = uuid;
        // Search dans la database pour trouver le bon client
    }
}
