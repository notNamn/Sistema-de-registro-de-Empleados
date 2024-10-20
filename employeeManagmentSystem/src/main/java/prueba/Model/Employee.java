package prueba.Model;

import javafx.beans.property.*;
import java.sql.Date;

public class Employee {
    private IntegerProperty idEmployee;
    private StringProperty name;
    private IntegerProperty numberPhone;
    private StringProperty gender;
    private StringProperty position;
    private ObjectProperty<Date> date;
    private DoubleProperty salary;
    private StringProperty image;

    // Constructor vacío
    public Employee() {
        this.idEmployee = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.numberPhone = new SimpleIntegerProperty();
        this.gender = new SimpleStringProperty();
        this.position = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.salary = new SimpleDoubleProperty();
        this.image = new SimpleStringProperty();
    }

    // Constructor completo
    public Employee(int idEmployee, String name, int numberPhone, String gender, String position, Date date, double salary, String image) {
        this.idEmployee = new SimpleIntegerProperty(idEmployee);
        this.name = new SimpleStringProperty(name);
        this.numberPhone = new SimpleIntegerProperty(numberPhone);
        this.gender = new SimpleStringProperty(gender);
        this.position = new SimpleStringProperty(position);
        this.date = new SimpleObjectProperty<>(date);
        this.salary = new SimpleDoubleProperty(salary);
        this.image = new SimpleStringProperty(image);
    }

    // Constructor para agregar un nuevo empleado
    public Employee(String name, int numberPhone, String gender, String position, Date date, double salary, String image) {
        this.name = new SimpleStringProperty(name);
        this.numberPhone = new SimpleIntegerProperty(numberPhone);
        this.gender = new SimpleStringProperty(gender);
        this.position = new SimpleStringProperty(position);
        this.date = new SimpleObjectProperty<>(date);
        this.salary = new SimpleDoubleProperty(salary);
        this.image = new SimpleStringProperty(image);
    }

    // Constructor para eliminar un empleado
    public Employee(int idEmployee) {
        this.idEmployee = new SimpleIntegerProperty(idEmployee);
    }


    public int getIdEmployee() {
        return idEmployee.get();
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee.set(idEmployee);
    }

    public IntegerProperty idEmployeeProperty() {
        return idEmployee;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getNumberPhone() {
        return numberPhone.get();
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone.set(numberPhone);
    }

    public IntegerProperty numberPhoneProperty() {
        return numberPhone;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public StringProperty positionProperty() {
        return position;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public double getSalary() {
        return salary.get();
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public StringProperty imageProperty() {
        return image;
    }
}
