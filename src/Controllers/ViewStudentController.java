package Controllers;

import Connection.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author rkson
 */
public class ViewStudentController implements Initializable {

    @FXML
    private Label name_lb;
    @FXML
    private Label branch_lb;
    @FXML
    private Label mobile_lb;
    @FXML
    private Label gender_lb;

    private String mobile_num;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mobile_num = Controllers.ViewStudentsController.send_mobile();
        System.out.println(mobile_num+"2");
        loadData();
    }    

    private void loadData(){
        try {
            Connection con = DBConnection.initializeDatabase();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM student where mobile='"+mobile_num+"'");
            
            while(rs.next()){
                name_lb.setText(rs.getString("name"));
                branch_lb.setText(rs.getString("branch"));
                mobile_lb.setText(rs.getString("mobile"));
                gender_lb.setText(rs.getString("gender"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
