package Admin;

import dbUtil.dbConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField s_n;
    @FXML
    private TextField price;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<StudentData> unittable;
    @FXML
    private TableColumn<StudentData, String> idcolum;
    @FXML
    private TableColumn<StudentData, String> namecolum;
    @FXML
    private TableColumn<StudentData, String> lastnamecolum;
    @FXML
    private TableColumn<StudentData, String> emailcolum;
    @FXML
    private TableColumn<StudentData, String> dobcolum;

    private dbConnection dc;
    private ObservableList<StudentData> data;
    private String sql = "SELECT * FROM students";


    @Override
    public void initialize(URL location, ResourceBundle rb) {

        this.dc = new dbConnection();
    }

    @FXML
    private void loadStudentData (ActionEvent event){
        try{
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()){
                this.data.add(new StudentData(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5)));
            }
        }catch (SQLException e){
            System.err.println("Error" + e);
        }

        this.idcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.namecolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("FirstName"));
        this.lastnamecolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("LastName"));
        this.emailcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.unittable.setItems(null);
        this.unittable.setItems(this.data);
    }

    @FXML
    private void addStudent (ActionEvent event){
    String sqlInsert = "INSERT INTO students (id, name, s_n, price, DOB) VALUES (?,?,?,?,?)";

    try{

        Connection conn = dbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sqlInsert);
        stmt.setString(1, this.id.getText());
        stmt.setString(2, this.name.getText());
        stmt.setString(3, this.s_n.getText());
        stmt.setString(4, this.price.getText());
        stmt.setString(5, this.dob.getEditor().getText());

        stmt.execute();
        conn.close();



    }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clearFields (ActionEvent event){
        this.id.setText("");
        this.name.setText("");
        this.s_n.setText("");
        this.price.setText("");
        this.dob.setValue(null);
    }
}




