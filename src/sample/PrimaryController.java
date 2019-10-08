package sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sun.security.util.ArrayUtil;

import javax.swing.*;

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

    public static void rotasjon(int[] a, int k) {
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

    void sort(int arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    public static String arrayToString(int[] a) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');

        for (int i = 0; i < a.length - 1; i++) {
            stringBuilder.append(a[i]).append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append(']');

        return stringBuilder.toString();
    }


    @FXML
    private void test() {
        String[] input = inputBox.getText().split("-");
        Boolean inneholderUgyldigTall = false;

        if (input.length < 8) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Du har skrevet inn for få tall!");
            error.showAndWait();
        } else if (input.length > 8) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Du har skrevet inn for mange tall!");
            error.showAndWait();
        } else {

            int[] listeArray = new int[input.length];
            for (int i = 0; i < input.length; i++) {
                listeArray[i] = Integer.parseInt(input[i]);
                if (listeArray[i] < 0 || listeArray[i] > 34) {
                    inneholderUgyldigTall = true;
                }
            }

            Arrays.sort(listeArray);


            Set<Integer> lagring = new HashSet<>();

            for (int i : listeArray) {
                if (lagring.add(i) == false) {
                    inneholderUgyldigTall = true;
                }
            }



            if (inneholderUgyldigTall) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Noen av tallene dine er feil eller duplikat, dobbeltsjekk! ");
                error.showAndWait();
            } else {
                observableList.add(arrayToString(listeArray));
                //maks 8 rekker for 8 tall
                for (int i = 0; i < listeArray.length - 1; i++) {
                    rotasjon(listeArray, 1);
                    System.out.println(arrayToString(listeArray));
                    observableList.add(arrayToString(listeArray));
                }
                SortedList<ObservableList> sortedList = new SortedList(observableList);
                listeView.setItems(observableList);
            }

        }

    }


}
