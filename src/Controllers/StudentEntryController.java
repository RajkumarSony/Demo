
package Controllers;

import Connection.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rkson
 */
public class StudentEntryController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> branch;
    @FXML
    private TextField mobile;
    @FXML
    private ComboBox<String> gender;

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
    }

    @FXML
    private void addStudentBtn(ActionEvent event) {
        
        String std_name = name.getText();
        String std_branch = branch.getValue();
        String std_mobile = mobile.getText();
        String std_gender = gender.getValue();
        
        if (isValid()){
            try{                
                Connection con = DBConnection.initializeDatabase();
                PreparedStatement st = con.prepareStatement("insert into student values(?, ?, ?, ?)");
                st.setString(1, std_name);
                st.setString(2, std_branch);
                st.setString(3, std_mobile);
                st.setString(4, std_gender);
                
                int status = st.executeUpdate();
                if(status>0)
                {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Information dialog");
                    alert.setContentText("Student Data saved successfully");
                    alert.showAndWait();      
                }
                else
                {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog.");
                    alert.setHeaderText("Error Information");
                    alert.showAndWait();
                }
                con.close();
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
    
    @FXML
    private void resetBtn(ActionEvent event) {
        name.setText("");
        branch.setValue(null);
        mobile.setText("");
        gender.setValue(null);
    }

    
}
