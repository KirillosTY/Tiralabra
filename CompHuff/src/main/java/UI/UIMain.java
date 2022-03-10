package UI;

import FileHandler.BinaryWriter;
import FileHandler.Decoder;
import Logic.HuffmanGenerator;
import Logic.LZW;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class UIMain extends Application {

    @FXML
    private ToggleGroup algChooser;
    @FXML
    private RadioButton huffPuff;
    @FXML
    private RadioButton LZW;
    @FXML
    private Button openFile;
    @FXML
    private Button encode;
    @FXML
    private Button decode;
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





    public UIMain()  {
        huffOrNo = true;
        writer = new BinaryWriter();
        reader = new Decoder();
        hufferPuff = new HuffmanGenerator();
        lempeli  = new LZW();
    }

    @FXML
    public void  fileOpener() throws IOException {

        openFileLoc = new FilePointer();
        openFileLoc.openLocation((Stage) openFile.getScene().getWindow());
        openNameLoc.setText(openFileLoc.pathToOpen);
        saveNameLoc.setText(openFileLoc.pathToSave);


    }

    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UIMain.fxml"));

            Scene set = new Scene(root);

            primaryStage.setTitle("File compressor 9001");

            primaryStage.setScene(set);

            primaryStage.show();

        } catch (IOException e){

        }



    }

    public void encode(){
        writer = new BinaryWriter();
        double time = System.nanoTime();


        textRead = writer.loadingTextFile(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString());
        System.out.println("Lukeminen: "+ ((System.nanoTime()-time)/1e9));
        if(huffPuff.isSelected()){

            time = System.nanoTime();
            hufferPuff.count(textRead);
            hufferPuff.treeForming();
            hufferPuff.binaryCalculations();
            System.out.println("Puu yms: "+ ((System.nanoTime()-time)/1e9));
            time = System.nanoTime();
            writer.setCoded(hufferPuff.getCoded());
            writer.setCodes(hufferPuff.getCodes());
            writer.writingCodedBinary(textRead, Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());
            System.out.println("Kirjoitus: "+ ((System.nanoTime()-time)/1e9));
        } else {

            writer.setListLZW(lempeli.compress(textRead));
            writer.encoderWriterLZW(openFileLoc.pathToSave);


        }

    }

    public void decode(){

        if(huffPuff.isSelected()){

           reader.readFileHuff(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString(),
                   Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());


        } else {

            reader.readFileLZW(Paths.get(openFileLoc.pathToOpen).toAbsolutePath().toString(),
                    Paths.get(openFileLoc.pathToSave).toAbsolutePath().toString());

        }

    }

    public void settings(){

        Platform.runLater(()->{


        });


    }




}
