
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author rkson
 */
public class HomeController implements Initializable {

    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newStudent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/StudentEntry.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("New Student Entry");
        stage.show();
    }

    @FXML
    private void viewStudent(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/ViewStudents.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("View Student List");
        stage.show();
        Stage stage2 = (Stage) image.getScene().getWindow();
        stage2.close(); 
    }
    
}
