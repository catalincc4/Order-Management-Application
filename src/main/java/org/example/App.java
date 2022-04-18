package org.example;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application  {

    private static Scene scene;

    static{
        Font.loadFont(FontAwesomeIconView.TTF_PATH,10);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("primary-view.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Window window= scene.getWindow();
       if(fxml.equals("primary-view")){
           window.setHeight(440);
           window.setWidth(300);
       }else if(fxml.equals("order-view")){
           window.setHeight(840);
           window.setWidth(1220);
       }
       else
       {
           window.setHeight(440);
           window.setWidth(600);
       }
       }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}