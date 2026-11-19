package domain;

public class Employee {
    private String employeeId;
    private String name;
    private boolean administrator;

    public Employee(String employeeId, String name, boolean administrator) {
        this.employeeId = employeeId;
        this.name = name;
        this.administrator = administrator;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isAdministrator() { return administrator; }
    public void setAdministrator(boolean administrator) { this.administrator = administrator; }
}
