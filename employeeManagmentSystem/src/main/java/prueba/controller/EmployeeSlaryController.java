package prueba.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import prueba.Model.Employee;
import prueba.Model.EmployeeDAO;
import prueba.Conexion.DatabaseConnection;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeSlaryController  implements Initializable {

    @FXML
    private TextField detaillsId;

    @FXML
    private TextField detaiellsName;

    @FXML
    private Label detaillsPosition;

    @FXML
    private TextField detaillsSalary;

    @FXML
    private Button btnSalaryClear;

    @FXML
    private Button btnSalaryUpdate;

    @FXML
    private TableView<Employee> tableSalary;

    @FXML
    private TableColumn<Employee, Integer> tableSalaryId;

    @FXML
    private TableColumn<Employee, String> tableSalaryName;

    @FXML
    private TableColumn<Employee, String> tableSalaryPosition;

    @FXML
    private TableColumn<Employee, Double> tableSalarySalary;

    private ObservableList<Employee> employeeData;
    private EmployeeDAO employeeDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employeeDAO = new EmployeeDAO(new DatabaseConnection());
        employeeData = FXCollections.observableArrayList();

        setupTable();
        loadEmployeeData();

        tableSalary.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showEmployeeDetails(newValue));

        btnSalaryClear.setOnAction(event -> clearFields());
        // btnSalaryUpdate.setOnAction(event -> handleUpdateSalary());
    }

    private void setupTable() {
        tableSalaryId.setCellValueFactory(cellData -> cellData.getValue().idEmployeeProperty().asObject());
        tableSalaryName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableSalaryPosition.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        tableSalarySalary.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

        tableSalary.setItems(employeeData);
    }

    private void loadEmployeeData() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeData.clear();
        employeeData.addAll(employees);
    }

    private void showEmployeeDetails(Employee employee) {
        if (employee != null) {
            detaillsId.setText(String.valueOf(employee.getIdEmployee()));
            detaiellsName.setText(employee.getName());
            detaillsPosition.setText(employee.getPosition());
            detaillsSalary.setText(String.valueOf(employee.getSalary()));
        } else {
            clearFields();
        }
    }
    
    @FXML
    void handleUpdateSalary() {
        Employee selectedEmployee = tableSalary.getSelectionModel().getSelectedItem();
        
        if (selectedEmployee != null) {
            try {
                double newSalary = Double.parseDouble(detaillsSalary.getText());
                selectedEmployee.setSalary(newSalary);
                employeeDAO.actualizarEmployee(selectedEmployee);
                loadEmployeeData();
            } catch (NumberFormatException e) {
                System.out.println("Error: El salario debe ser un número válido.");
            }
        }
    }

    private void clearFields() {
        detaillsId.clear();
        detaiellsName.clear();
        detaillsPosition.setText("");
        detaillsSalary.clear();
    }
}
