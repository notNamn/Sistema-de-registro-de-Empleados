package prueba.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import prueba.Conexion.DatabaseConnection;

/**
 *
 * @author HP
 */
public class EmployeeDAO {

    private DatabaseConnection conectar;
    private String nombreBaseDatos = "login_system";

    public EmployeeDAO(DatabaseConnection conectar) {
        this.conectar = new DatabaseConnection();
    }

    // metodo para guardar 
    // idEmployee, name, numberPhone, gender, position, date, salary, image
//    public void guardar(Employee employee ){
//        String query = "INSERT INTO employee (name, numberPhone, gender, position, date, salary, image) "
//                +" VALUES (?,?,?,?,?,?,?)";
//        try(Connection conn = conectar.conectar(nombreBaseDatos);
//            PreparedStatement psmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS) ) {
//            psmt.setString(1, employee.getName());
//            psmt.setInt(2, employee.getNumberPhone());
//            psmt.setString(3, employee.getGender());
//            psmt.setString(4, employee.getPosition());
//            psmt.setDate(5, employee.getDate());
//            psmt.setDouble(6, employee.getSalary());
//            psmt.setString(7, employee.getImage());
//            
//            int columnas = psmt.executeUpdate(query);
//            if(columnas == 0){
//                throw  new SQLException("Error al crear el employee, no hay filas afectadas.");
//            }
//            try (ResultSet generateKey = psmt.getGeneratedKeys()) {
//                if (generateKey.next()) {
//                    employee.setIdEmployee(generateKey.getInt(1));
//                }
//            }
//            
//        } catch (SQLException e) {
//            System.out.println("Error al guardar: "+ e.getMessage());
//        } finally {
//            conectar.desconectar();
//        }
//    }
    public void guardar(Employee employee) {
        String query = "INSERT INTO employee (name, numberPhone, gender, position, date, salary, image) "
                + "VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = conectar.conectar(nombreBaseDatos); PreparedStatement psmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            psmt.setString(1, employee.getName());
            psmt.setInt(2, employee.getNumberPhone());
            psmt.setString(3, employee.getGender());
            psmt.setString(4, employee.getPosition());
            psmt.setDate(5, employee.getDate());
            psmt.setDouble(6, employee.getSalary());
            psmt.setString(7, employee.getImage());

            int filasAfectadas = psmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("Error al crear el empleado, no hay filas afectadas.");
            }

            try (ResultSet generateKey = psmt.getGeneratedKeys()) {
                if (generateKey.next()) {
                    employee.setIdEmployee(generateKey.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        } finally {
            conectar.desconectar();
        }
    }

    // mostrar produto por id
    // idEmployee, name, numberPhone, gender, position, date, salary, image
    public Employee getEmployeeById(int id) throws SQLException {
        String query = "SELECT idEmployee, name, numberPhone, gender, position, date, salary, image "
                + "FROM employee WHERE idEmployee = ?";
        try (Connection conn = conectar.conectar(nombreBaseDatos); PreparedStatement psmt = conn.prepareCall(query)) {
            psmt.setInt(1, id);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(rs.getInt("idEmployee"),
                            rs.getString("name"), rs.getInt("numberPhone"), rs.getString("gender"),
                            rs.getString("position"), rs.getDate("date"), rs.getDouble("salary"),
                            rs.getString("image"));
                }
            } catch (SQLException e) {
                System.out.println("Error al obetener el producto por ID: " + e.getMessage());
            }
        } finally {
            conectar.desconectar();
        }
        return null;
    }

    // mostrar todos los productos
    // idEmployee, name, numberPhone, gender, position, date, salary, image
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList();
        String query = "SELECT * FROM employee";
        try (Connection conn = conectar.conectar(nombreBaseDatos); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employees.add(new Employee(rs.getInt("idEmployee"),
                        rs.getNString("name"),
                        rs.getInt("numberPhone"),
                        rs.getString("gender"),
                        rs.getString("position"),
                        rs.getDate("date"),
                        rs.getDouble("salary"),
                        rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al listar todos los  employees: " + e.getMessage());
        } finally {
            conectar.desconectar();
        }
        return employees;
    }

    // actualizar un employee
    // idEmployee, name, numberPhone, gender, position, date, salary, image
//    public void actualizarEmployee(Employee emplo){
//        String query = "UPDATE employee SET "+ 
//                "name = ?, numberPhone = ?, gender = ?, position = ?, date = ?, image = ? "+ 
//                "WHERE idEmployee = ?";
//        try(Connection conn = conectar.conectar(nombreBaseDatos);
//            PreparedStatement psmt = conn.prepareStatement(query)  ) {
//            psmt.setString(1, emplo.getName());
//            psmt.setInt(2, emplo.getNumberPhone());
//            psmt.setString(3, emplo.getGender());
//            psmt.setString(4, emplo.getPosition());
//            psmt.setDate(5, emplo.getDate());
//            psmt.setString(6, emplo.getImage());
//            psmt.setInt(7, emplo.getIdEmployee()); // ID EMPLOYEEE
//            
//            int filasAfectadas = psmt.executeUpdate();
//            if(filasAfectadas == 0){
//                throw  new SQLException("No se actualiza ninguna fila");
//            }
//                    
//        } catch (SQLException e) {
//            System.out.println("Error al actualizar employee: " + e.getMessage());
//        } finally {
//            conectar.desconectar();
//        }
//        
//    }
    public void actualizarEmployee(Employee emplo) {
        String query = "UPDATE employee SET "
                + "name = ?, numberPhone = ?, gender = ?, position = ?, date = ?, salary = ?, image = ? "
                + "WHERE idEmployee = ?";
        try (Connection conn = conectar.conectar(nombreBaseDatos); PreparedStatement psmt = conn.prepareStatement(query)) {
            psmt.setString(1, emplo.getName());
            psmt.setInt(2, emplo.getNumberPhone());
            psmt.setString(3, emplo.getGender());
            psmt.setString(4, emplo.getPosition());
            psmt.setDate(5, emplo.getDate());
            psmt.setDouble(6, emplo.getSalary()); // Actualizar el salario
            psmt.setString(7, emplo.getImage());
            psmt.setInt(8, emplo.getIdEmployee()); // ID EMPLOYEEE

            int filasAfectadas = psmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se actualiza ninguna fila");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar employee: " + e.getMessage());
        } finally {
            conectar.desconectar();
        }
    }

    // eliminar employee
    // idEmployee, name, numberPhone, gender, position, date, salary, image
    public void eliminarEmployee(Employee employee) {
        String query = "DELETE FROM employee WHERE idEmployee = ?";
        try (Connection conn = conectar.conectar(nombreBaseDatos); PreparedStatement psmt = conn.prepareStatement(query)) {
            psmt.setInt(1, employee.getIdEmployee());
            psmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar el employe: " + e.getMessage());
        } finally {
            conectar.desconectar();
        }
    }

    // realizar el buacador 
    public List<Employee> searchEmployees(String searchText) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE "
                + "name like ? OR "
                + "numberPhone like ? OR "
                + "gender like ? OR "
                + "position like ? OR "
                + "salary LIKE ?";
        try (Connection conn = conectar.conectar(nombreBaseDatos); PreparedStatement psmt = conn.prepareStatement(query)) {
            String wildsearch = "%" + searchText + "%";
            psmt.setString(1, wildsearch);
            psmt.setString(2, wildsearch);
            psmt.setString(3, wildsearch);
            psmt.setString(4, wildsearch);
            psmt.setString(5, wildsearch);

            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(new Employee(
                            rs.getInt("idEmployee"),
                            rs.getString("name"),
                            rs.getInt("numberPhone"),
                            rs.getString("gender"),
                            rs.getString("position"),
                            rs.getDate("date"),
                            rs.getDouble("salary"),
                            rs.getString("image")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la bsuqueda: " + e.getMessage());
        } finally {
            conectar.desconectar();
        }
        return employees;
    }
}
