
package Controllers;

import Connection.DBConnection;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rkson
 */
public class UpdateStudentController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> branch;
    @FXML
    private TextField mobile;
    @FXML
    private ComboBox<String> gender;

    private String mobile_num;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize branch...
        branch.getItems().add("Computer Science");
        branch.getItems().add("Mechanical");
        branch.getItems().add("Electrical");
        branch.getItems().add("Electronics");
        // Initialize gender...
        gender.getItems().add("Male");
        gender.getItems().add("Female");
        gender.getItems().add("Others"); 
        // TODO
        mobile_num = Controllers.ViewStudentsController.send_mobile();
        System.out.println(mobile_num);
        loadData();
    }    
    
    @FXML
    private void updateStudentBtn(ActionEvent event) {
        String std_name = name.getText();
        String std_branch = branch.getValue();
        String std_mobile = mobile.getText();
        String std_gender = gender.getValue();
        
        if (isValid()){
            
            try{
                                 
                Connection con = DBConnection.initializeDatabase();
                PreparedStatement st = con.prepareStatement("update student set name=?,branch=?,"
                        + "mobile=?,gender=? where mobile='"+mobile_num+"'");
                st.setString(1, std_name);
                st.setString(2, std_branch);
                st.setString(3, std_mobile);
                st.setString(4, std_gender);
                st.executeUpdate();
                st.close();
                
                con.close();

                System.out.println("Update Successfully!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("Awesome!");
                alert.setContentText("Student's informations are updated in database successfully!");
                alert.showAndWait();
                
                Stage nStage = (Stage) name.getScene().getWindow();
                nStage.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        }    
    }
    
    private boolean isValid(){
        boolean valid = true;
        
        if(name.getText().isEmpty() || branch.getValue()==null || mobile.getText().isEmpty()
                || branch.getValue()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("All fields are required!");
            alert.setContentText("All text fields are required. please fill all text field!");
            alert.show();
            valid = false;
        }
        return valid;
    }  
    
    private void loadData(){
        try {
            Connection con = DBConnection.initializeDatabase();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM student where mobile='"+mobile_num+"'");
            
            while(rs.next()){
                name.setText(rs.getString("name"));
                branch.setValue(rs.getString("branch"));
                mobile.setText(rs.getString("mobile"));
                gender.setValue(rs.getString("gender"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
