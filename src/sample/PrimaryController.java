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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import sun.security.util.ArrayUtil;

import javax.swing.*;

public class PrimaryController {

    @FXML
    private TextField inputBox;
    @FXML
    private ListView<String> listeView;
    @FXML
    private Label antallRekkerLabel;


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

    void sort(int arr[]) {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
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

    static int fac(int a) {
        if (a == 1) {
            return a;
        } else {
            return a *= fac(a - 1);
        }
    }


    private static int regnUtBK(int n, int k) {      // binomialkoeffisient n = lengden på array, k = antall tall som blir printet ut
        if (n < k || k < 0) {
            throw new NumberFormatException("Parameterverdier er feil!");
        }
        int resultat = 0;

        resultat = (fac(n)) / (fac(k) * fac(n - k));

        return resultat;
    }

    private static int teller = 0;

    public static ArrayList<int[]> enumKCombos(int[] array, int k) {

        // Create an empty ArrayList to store all the k-combinations.
        // The k-combinations are stored as int arrays.
        ArrayList<int[]> comboList = new ArrayList<int[]>();

        // The process of enumerating the k-combinations can be done with a
        // recursive function where each recursion is passed a shorter array and
        // a smaller value of k. The base case of k = 1, and any larger values
        // call the recursive function with a decremented value of k.

        assert (k > 0);

        if (k > 1) {

            assert (array.length >= k);

            // Store the first member of the array.
            int[] first = new int[1];
            first[0] = array[0];
            array = Arrays.copyOfRange(array, 1, array.length);

            while (array.length + 1 >= k) {
                ArrayList<int[]> subComboList = new ArrayList<int[]>();
                // Call the recursive function and temporarily store the
                //   returned arrays.
                subComboList = enumKCombos(array, k - 1);

                // Concatenate the stored first member onto the front of the
                //   returned arrays.
                int[] subArray;
                for (int i = 0; i < subComboList.size(); i++) {
                    subArray = subComboList.get(i);
                    int[] concatenated = new int[subArray.length + 1];
                    concatenated[0] = first[0];
                    for (int j = 0; j < subArray.length; j++) {
                        concatenated[j + 1] = subArray[j];
                    }
                    comboList.add(concatenated);
                }

                // Add the newly-concatenated arrays to the comboList.
                // Replace first with array[0].
                first[0] = array[0];

                // Splice array to remove the first member.
                array = Arrays.copyOfRange(array, 1, array.length);
            }
        } else {
            // Return the individual members of array as individual 1-member
            //   arrays.
            for (int i = 0; i < array.length; i++) {
                comboList.add(Arrays.copyOfRange(array, i, i + 1));
            }
        }

        return comboList;
    }


    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int[] i : enumKCombos(a, 7)) {
            System.out.println(Arrays.toString(i));
            teller++;
        }
        System.out.println(teller);
    }

    @FXML
    private void emptyList() {
        observableList.clear();
        teller = 0;
        antallRekkerLabel.setText("Antall rekker produsert: " + teller);
    }


    @FXML
    private void createList() {
        String[] input = inputBox.getText().split("-");
        Boolean inneholderUgyldigTall = false;
        try {
            for (String s : input) {
                Integer.parseInt(s);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            inneholderUgyldigTall = true;
        }


        if (inneholderUgyldigTall) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Du har skrevet noe annet enn tall, dobbeltsjekk!");
            error.showAndWait();
        } else if (input.length < 8) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Du har skrevet inn for få tall!");
            error.showAndWait();
        } else if (input.length > 12) {
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
                for (int[] a : enumKCombos(listeArray, 7)) {
                    observableList.add(Arrays.toString(a));
                    teller++;
                }
                System.out.println("Antall rekker produsert: " + teller);
                antallRekkerLabel.setText("Antall rekker produsert: " + teller);
                FXCollections.sort(observableList);
                listeView.setItems(observableList);
                listeView.setEditable(true);
                listeView.setCellFactory(TextFieldListCell.forListView());
            }

        }

    }


}
