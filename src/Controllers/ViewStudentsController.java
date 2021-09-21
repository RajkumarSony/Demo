
package Controllers;

import Connection.DBConnection;
import Model.ModelTable;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rkson
 */
public class ViewStudentsController implements Initializable {

    @FXML
    private TableView<ModelTable> table_view;
    @FXML
    private TableColumn<ModelTable, String> name_col;
    @FXML
    private TableColumn<ModelTable, String> branch_col;
    @FXML
    private TableColumn<ModelTable, String> mobile_col;
    @FXML
    private TableColumn<ModelTable, String> gender_col;
    @FXML
    private Button back;
    
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    private ObservableList<ModelTable> filteredData = FXCollections.observableArrayList(); 
    
    private static String mobile_num;
    private static String name_temp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
        System.out.println("done");
    }    
    
    @FXML
    private void backBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Home.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Demo App - Home");
        stage.show();
        Stage stage2 = (Stage) back.getScene().getWindow();
        stage2.close();
    }
    
    @FXML
    private void viewBtn(ActionEvent event) throws IOException {
        ModelTable select = table_view.getSelectionModel().getSelectedItem();
        int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            mobile_num = select.getMobile();
            name_temp = select.getName();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ViewStudent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(name_temp+"'s Information");
            stage.show();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made!");
            alert.setContentText("You have not selected Student to view. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteBtn(ActionEvent event) throws SQLException {
        ModelTable select = table_view.getSelectionModel().getSelectedItem();      
        int selectedIndex = table_view.getSelectionModel().getSelectedIndex();

        if(selectedIndex >= 0){
            Connection con = DBConnection.initializeDatabase();
            String query = "DELETE FROM student WHERE mobile = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, select.getMobile());
            pst.execute();
            pst.close();
            con.close();
            table_view.getItems().remove(selectedIndex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Awesome!");
            alert.setContentText("Student has beed deleted from the list successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made!");
            alert.setContentText("You have not selected Student to delete. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private void updateBtn(ActionEvent event) throws IOException {
        ModelTable select = table_view.getSelectionModel().getSelectedItem();
        int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            mobile_num = select.getMobile();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/UpdateStudent.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Student Details");
            stage.show();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR:");
            alert.setHeaderText("No selection was made!");
            alert.setContentText("You have not selected Student to update. Please try again.");
            alert.showAndWait();
        }
    }
    static String send_mobile(){
        return mobile_num;
    }

    @FXML
    private void reloadBtn(ActionEvent event) {
        table_view.getItems().clear();
        loadTable();
    }
    
    private void loadTable(){
        try {
            Connection con = DBConnection.initializeDatabase();
            ResultSet rs = con.createStatement().executeQuery("SELECT * from student;");
            while(rs.next()){
                oblist.add(new ModelTable(rs.getString("name"),rs.getString("branch"),
                        rs.getString("mobile"),rs.getString("gender")));
            }
            rs.close();
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        branch_col.setCellValueFactory(new PropertyValueFactory<>("branch"));
        mobile_col.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table_view.setItems(oblist);
    }
}
