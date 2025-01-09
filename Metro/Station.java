
package Metro;
import java.util.HashMap;
import java.util.LinkedList;

public class Station {
    LinkedList<String> ligne;
    HashMap<Station, Integer> correspondences;
    public String nom;

    public Station(String nom, String ligne) {
        this.nom = nom;
        correspondences = new HashMap();
        this.ligne = new LinkedList();
        this.ligne.add(ligne);
    }

    String cor() {
        String s = "";
        for (var i : correspondences.entrySet()) {
            s += i.getKey().nom + " " + i.getValue() + " ";
        }
        return s;
    }

    public String toString() {
        return nom + " ligne: " + ligne.toString() + " correspondences: " + cor();
    }

}

