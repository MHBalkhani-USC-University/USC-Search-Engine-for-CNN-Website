package ir.project.usc;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class UI extends Application {

    public final static String QUIT_HANDLER = "quit";

    public void run(String args[]){

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Stage");

        Button bt = new Button("Button");
        bt.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED,this.getHandler(QUIT_HANDLER));

        RadioButton rb = new RadioButton("Radio");

        //Close Button
//        primaryStage.setOnCloseRequest(e->closeWindows());

        //Grid Pane
//        GridPane gp = new GridPane();
//        gp.setPadding(new Insets(10,10,10,10));
//        gp.setVgap(8);
//        gp.setHgap(10);
//
//        Label lb = new Label("label");
//        GridPane.setConstraints(lb,0,0);

        //Check Box
        //Choice Box
//        ChoiceBox<String> cb = new ChoiceBox<>();
//        cb.getItems().addAll("1","2","3");


        StackPane sp = new StackPane();
        sp.getChildren().add(bt);
        sp.getChildren().add(rb);

        Scene sc = new Scene(sp,300,300);

        primaryStage.setScene(sc);
        primaryStage.show();


    }

    public EventHandler getHandler(String type){

        EventHandler<javafx.scene.input.MouseEvent> eventHandler = new EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent e) {
                System.out.println("Hello World");
                //circle.setFill(Color.DARKSLATEBLUE);
            }
        };

        return eventHandler;

    }

}
