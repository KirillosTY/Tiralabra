package UI;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class FilePointer  {

    public String pathToOpen;
    public String pathToSave;


    public void openLocation(Stage primaryStage) throws IOException {

        FileChooser pickAFile = new FileChooser();
        pickAFile.setTitle("choose a file to compress:");

        pickAFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text file","*.txt"),
                new FileChooser.ExtensionFilter("pdf","*.pdf")
        );

        pathToOpen = ""+pickAFile.showOpenDialog(primaryStage);

        if(!pathToOpen.equals("null")){
            saveLocation(pickAFile, primaryStage);
        }

        primaryStage.setOnCloseRequest((loadText)->{
            loadText();
        });

    }

    public void loadText(){



    }

    private void saveLocation(FileChooser saveLoc, Stage sameStage){
        saveLoc.setTitle("Choose save location:");

        pathToSave = ""+saveLoc.showSaveDialog(sameStage);
        if(pathToOpen.equals("null")){
            pathToSave = pathToOpen+"Encoded";
        }
    }

    public void close(){
        exit();
    }




}
