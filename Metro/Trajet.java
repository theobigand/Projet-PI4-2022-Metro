package Metro;
import java.util.LinkedList;

public class Trajet {
    public int temps;
    public String chemin;
    Station depart, arriver;
    static int tempsMin = -1;
    public LinkedList<String> listLigne;
    public LinkedList<Station> listStation;
    int nbChangement;

    public Trajet(String chemin, int temps, Station depart, Station arriver, LinkedList<String> listLigne,
            LinkedList<Station> listStation, int nbChangement) {
        this.temps = temps;
        this.chemin = chemin;
        this.depart = depart;
        this.arriver = arriver;
        this.listLigne = listLigne;
        this.listStation = listStation;
        this.nbChangement = nbChangement;
    }

    public int getNbChangement() {
        return nbChangement;
    }

    public String toString() {
        return "Un trajet pour aller de " + depart.nom + " à " + arriver.nom + " est: " + chemin + ", en "
                + temps + " minutes.";
    }

    public String ligne() {
        String s = "";
        for (int i = 0; i < listLigne.size(); i++) {
            if (i < listLigne.size() - 1)
                s += listLigne.get(i) + " -> ";
            else
                s += listLigne.get(i) + ".";
        }
        return s;
    }

    public int compteStation() { // compte le nombre de Station d'un trajet
        int x = 0;
        for (String i : listLigne) {
            x += 1;
        }
        return x;
    }

}
