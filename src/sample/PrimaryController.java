package sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField inputBox;
    @FXML
    private ListView<String> listeView;


    ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public static int gcd(int a, int b)  // Euklids algoritme
    {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void rotasjon(int [] a, int k){
        int n = a.length;
        if (n < 2) return;         // ingen rotasjon
        if ((k %= n) < 0) k += n;                     // motsatt vei?

        int s = gcd(n, k);                            // største felles divisor

        for (int e = 0; e < s; e++)                   // antall sykler
        {
            int verdi = a[e];                          // hjelpevariabel

            for (int i = e - k, j = e; i != e; i -= k)  // løkke
            {
                if (i < 0) i += n;                        // sjekker fortegnet til i
                a[j] = a[i];
                j = i;                       // kopierer og oppdaterer j
            }

            a[e + k] = verdi;                           // legger tilbake verdien
        }
    }

    public static String arrayToString(int [] a){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; i < a.length-1; i++) {
            stringBuilder.append(a[i]).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(']');

        return stringBuilder.toString();
    }

    @FXML
    private void test (){
        String [] input = inputBox.getText().split("-");
        if (input.length < 8){
            Alert error = new Alert(Alert.AlertType.ERROR,"Du har skrevet inn for få tall!");
            error.showAndWait();
        } else if (input.length > 8){
            Alert error = new Alert(Alert.AlertType.ERROR,"Du har skrevet inn for få tall!");
            error.showAndWait();
        } else {
            int [] listeArray = new int [input.length];

            for (int i = 0; i < input.length; i++) {
                listeArray[i] = Integer.parseInt(input[i]);
            }
            observableList.add(arrayToString(listeArray));
            //maks 8 rekker for 8 tall
            for (int i = 0; i < listeArray.length-1; i++) {
                rotasjon(listeArray,1);
                System.out.println(arrayToString(listeArray));
                observableList.add(arrayToString(listeArray));
            }
            listeView.setItems(observableList);
        }

    }


}
