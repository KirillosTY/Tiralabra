package UI;

import FileHandler.BinaryWriter;
import FileHandler.Decoder;
import Logic.HuffmanGenerator;
import Logic.LZW;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;

import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class UIMain extends Application {

    @FXML
    private ToggleGroup algChooser;
    @FXML
    private RadioButton huffPuff;
    @FXML
    private RadioButton lZW;
    @FXML
    private Button openFile;
    @FXML
    public Button encode;
    @FXML
    public Button decode;
    @FXML
    private HBox buttons;
    @FXML
    private Label openNameLoc;
    @FXML
    private Label saveNameLoc;
    @FXML
    private Stage mainStage;

    private FilePointer openFileLoc;

    private boolean huffOrNo;

    private String openLoc;

    private String saveLoc;
    @FXML
    private BinaryWriter writer;

    private Decoder reader;

    private String textRead;

    private HuffmanGenerator hufferPuff;

    private LZW lempeli;


    public UIMain() {
        huffOrNo = true;
        writer = new BinaryWriter();
        reader = new Decoder();
        hufferPuff = new HuffmanGenerator();
        lempeli = new LZW();

    }

    @FXML
    public void fileOpener() throws IOException {

        openFileLoc = new FilePointer();
        openFileLoc.openLocation((Stage) openFile.getScene().getWindow());
        openNameLoc.setText(openFileLoc.pathToOpen);
        saveNameLoc.setText(openFileLoc.pathToSave);
        settings();

    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UIMain.fxml"));

            Image a = new Image("pepe.jpg");
            Scene set = new Scene(root);

            primaryStage.setTitle("File compressor 9001");

            primaryStage.setScene(set);

            primaryStage.show();


        } catch (IOException e) {

        }


    }

    public void encode() {

        String pathChosen = openNameLoc.getText();


        if (!pathChosen.equals("Choose a file first!") && !pathChosen.equals("") && !pathChosen.equals("null")) {
            disableButtons();
            writer = new BinaryWriter();
            double time = System.nanoTime();

            textRead = writer.loadingTextFile(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString());
            System.out.println("Lukeminen: " + ((System.nanoTime() - time) / 1e9));
            if (huffPuff.isSelected()) {


                hufferPuff.count(textRead);
                hufferPuff.treeForming();
                hufferPuff.binaryCalculations();


                writer.setCoded(hufferPuff.getCoded());
                writer.setCodes(hufferPuff.getCodes());
                writer.writingCodedBinary(textRead, Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());

                openFileLoc.pathToOpen = openFileLoc.pathToSave;
                openFileLoc.pathToSave = "";
                openNameLoc.setText(openFileLoc.pathToSave);
                saveNameLoc.setText("");
            } else {

                writer.setListLZW(lempeli.compress(textRead));
                writer.encoderWriterLZW(openFileLoc.pathToSave);
                openFileLoc.pathToOpen = openFileLoc.pathToSave;
                openFileLoc.pathToSave = "";
                openNameLoc.setText(openFileLoc.pathToSave);
                saveNameLoc.setText("");

            }
        }

    }

    public void disableButtons(){
        encode.setDisable(true);
        decode.setDisable(true);
        encode.setStyle("-fx-text-fill: white; -fx-background-color: darkred;-fx-background-radius: 1em 1em 1em 1em;-fx-effect: innershadow( gaussian ,red, 5,0.2, 3,3);");
        decode.setStyle("-fx-text-fill: white; -fx-background-color: darkred;-fx-background-radius: 1em 1em 1em 1em;-fx-effect: innershadow( gaussian ,red, 5,0.2, 3,3);");
    }

    public void decode() {

        String pathChosen = openNameLoc.getText();

        if (!pathChosen.equals("Choose a file first!") && !pathChosen.equals("") && !pathChosen.equals("null")) {
            disableButtons();
            if (huffPuff.isSelected()) {

                reader.readFileHuff(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString(),
                        Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());


                openNameLoc.setText("");
                saveNameLoc.setText("");
            } else {

                reader.readFileLZW(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString(),
                        Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());

                openNameLoc.setText("");
                saveNameLoc.setText("");
            }

        }
    }

    public void checkIfDoneReleaseButtons(){

        new AnimationTimer() {
            long time = 0;


            @Override
            public void handle(long now) {

                if(now-time > 10000 ){

                    if(writer.jobDone && reader.workDone){
                        encode.setDisable(false);
                        decode.setDisable(false);
                        decode.setStyle("-fx-text-fill: white; -fx-background-color: #408905;-fx-background-radius: 1em 1em 1em 1em;" +
                                "-fx-effect: innershadow( gaussian ,green,5 , 0.2 , 3, 3)");
                        encode.setStyle("-fx-text-fill: white; -fx-background-color: #408905;-fx-background-radius: 1em 1em 1em 1em;" +
                                "-fx-effect: innershadow( gaussian ,green,5 , 0.2 , 3, 3)");
                    } else {
                        disableButtons();
                    }

                     time = now;
                }


            }
        }.start();



    }

    public void settings() {

        Platform.runLater((new Runnable() {
            @Override
            public void run() {
                checkIfDoneReleaseButtons();
            }
        }));


    }


}
