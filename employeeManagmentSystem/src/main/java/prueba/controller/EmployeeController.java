package prueba.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import prueba.Model.Employee;
import prueba.Model.EmployeeDAO;
import prueba.Conexion.DatabaseConnection;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TableView<Employee> tableEmployeeGeneral;

    @FXML
    private TableColumn<Employee, Integer> tableEmployeeId;

    @FXML
    private TableColumn<Employee, String> tableEmployeeName;

    @FXML
    private TableColumn<Employee, String> tableEmployeeNumberPhone;

    @FXML
    private TableColumn<Employee, String> tableEmployeeGender;

    @FXML
    private TableColumn<Employee, String> tableEmployeePosition;

    @FXML
    private TableColumn<Employee, Date> tableEmployeeDate;

    @FXML
    private TextField textEmployeeId;

    @FXML
    private TextField textEmployeeName;

    @FXML
    private TextField textEmployeeNumberPhone;

    @FXML
    private ComboBox<String> comboxEmployeeGender;

    @FXML
    private ComboBox<String> comboxEmployeePosition;

    @FXML
    private Button btnEmployeeClear;

    @FXML
    private Button btnEmployeeDelete;

    @FXML
    private Button btnEmployeeUpdate;

    @FXML
    private Button btnEmployeeAdd;

    @FXML
    private Button btnImportImg;

    @FXML
    private ImageView employeeImg;

    @FXML
    private TextField searchEmployee;

    private ObservableList<Employee> employeeData;
    private EmployeeDAO employeeDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeDAO = new EmployeeDAO(new DatabaseConnection());
        employeeData = FXCollections.observableArrayList();
        setupTable();
        loadEmployeeData();

        tableEmployeeGeneral.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEmployeeDetails(newValue));

        comboxEmployeeGender.setItems(FXCollections.observableArrayList("Male", "Female"));
        comboxEmployeePosition.setItems(FXCollections.observableArrayList("Manager", "Developer", "Analyst"));

        // Implementación de la búsqueda
        searchEmployee.textProperty().addListener((observable, oldValue, newValue) -> handleSearchEmployee(newValue));
    }

    private void setupTable() {
        // Vinculando las columnas de la tabla con las propiedades observables del modelo Employee
        tableEmployeeId.setCellValueFactory(cellData -> cellData.getValue().idEmployeeProperty().asObject());
        tableEmployeeName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableEmployeeNumberPhone.setCellValueFactory(cellData
                -> cellData.getValue().numberPhoneProperty().asString()
        );
        tableEmployeeGender.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        tableEmployeePosition.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        tableEmployeeDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Asignando la lista observable a la tabla
        tableEmployeeGeneral.setItems(employeeData);
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeData.clear();
        employeeData.addAll(employees);
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            textEmployeeId.setText(String.valueOf(employee.getIdEmployee()));
            textEmployeeName.setText(employee.getName());
            textEmployeeNumberPhone.setText(String.valueOf(employee.getNumberPhone()));
            comboxEmployeeGender.setValue(employee.getGender());
            comboxEmployeePosition.setValue(employee.getPosition());
            if (employee.getImage() != null && !employee.getImage().isEmpty()) {
                employeeImg.setImage(new Image(employee.getImage()));
            }
        } else {
            clearFields();
        }
    }

//    @FXML
//    private void handleAddEmployee() {
//        Employee newEmployee = new Employee(
//                textEmployeeName.getText(),
//                Integer.parseInt(textEmployeeNumberPhone.getText()),
//                comboxEmployeeGender.getValue(),
//                comboxEmployeePosition.getValue(),
//                new Date(System.currentTimeMillis()), // Assuming current date
//                0, // Assuming salary field not used
//                employeeImg.getImage().getUrl()
//        );
//        employeeDAO.guardar(newEmployee);
//        loadEmployeeData();
//    }
//    @FXML
//    private void handleAddEmployee() {
//        try {
//            String name = textEmployeeName.getText();
//            int numberPhone = Integer.parseInt(textEmployeeNumberPhone.getText());
//            String gender = comboxEmployeeGender.getValue();
//            String position = comboxEmployeePosition.getValue();
//            Date date = new Date(System.currentTimeMillis()); // Fecha actual
//            double salary = 0.0; // Suponiendo que no se usa el salario o se establece en 0
//            String image = (employeeImg.getImage() != null) ? employeeImg.getImage().getUrl() : null;
//
//            Employee newEmployee = new Employee(name, numberPhone, gender, position, date, salary, image);
//            employeeDAO.guardar(newEmployee);
//            loadEmployeeData();
//            clearFields();
//        } catch (Exception e) {
//            System.out.println("Error al agregar el empleado: " + e.getMessage());
//            // Aquí podrías mostrar un mensaje de alerta si algo falla
//        }
//    }
    @FXML
    private void handleAddEmployee() {
        try {
            String name = textEmployeeName.getText();
            int numberPhone = Integer.parseInt(textEmployeeNumberPhone.getText());
            String gender = comboxEmployeeGender.getValue();
            String position = comboxEmployeePosition.getValue();
            Date date = new Date(System.currentTimeMillis()); // Fecha actual
            double salary = 0.0; // Suponiendo que no se usa el salario o se establece en 0
            String image = (employeeImg.getImage() != null) ? employeeImg.getImage().getUrl() : null;

            Employee newEmployee = new Employee(name, numberPhone, gender, position, date, salary, image);
            employeeDAO.guardar(newEmployee);

            // Actualiza la tabla
            loadEmployeeData();

            // Limpieza de campos después de agregar un empleado
            clearFields();
        } catch (Exception e) {
            System.out.println("Error al agregar el empleado: " + e.getMessage());
            // Aquí podrías mostrar un mensaje de alerta si algo falla
        }
    }

    private void clearFields() {
        textEmployeeId.clear();
        textEmployeeName.clear();
        textEmployeeNumberPhone.clear();
        comboxEmployeeGender.setValue(null);
        comboxEmployeePosition.setValue(null);
        employeeImg.setImage(null);
    }

    @FXML
    private void handleUpdateEmployee() {
        Employee selectedEmployee = tableEmployeeGeneral.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            selectedEmployee.setName(textEmployeeName.getText());
            selectedEmployee.setNumberPhone(Integer.parseInt(textEmployeeNumberPhone.getText()));
            selectedEmployee.setGender(comboxEmployeeGender.getValue());
            selectedEmployee.setPosition(comboxEmployeePosition.getValue());
            selectedEmployee.setImage(employeeImg.getImage().getUrl());
            employeeDAO.actualizarEmployee(selectedEmployee);
            loadEmployeeData();
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee selectedEmployee = tableEmployeeGeneral.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeDAO.eliminarEmployee(selectedEmployee);
            loadEmployeeData();
        }
    }

    @FXML
    private void handleClearFields() {
        clearFields();
    }

//    private void clearFields() {
//        textEmployeeId.clear();
//        textEmployeeName.clear();
//        textEmployeeNumberPhone.clear();
//        comboxEmployeeGender.setValue(null);
//        comboxEmployeePosition.setValue(null);
//        employeeImg.setImage(null);
//    }
    @FXML
    private void handleImportImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            employeeImg.setImage(new Image(selectedFile.toURI().toString()));
        }
    }

    @FXML
    private void handleSearchEmployee(String searchText) {
        List<Employee> filteredEmployees = employeeDAO.searchEmployees(searchText);
        employeeData.clear();
        employeeData.addAll(filteredEmployees);
    }
}
