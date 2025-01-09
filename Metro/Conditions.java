package Metro;

import java.util.LinkedList;

public class Conditions {
   public static int nbChangementMax = -1;
    static LinkedList<Station> stationFermer = new LinkedList<>();
    static LinkedList<String> ligneFermer = new LinkedList<>();

    public static void ajouterStationFermer(Station ajoutStation) {
        stationFermer.add(ajoutStation);
    }

    void ajouterLigneFermer(String ligne) {
        ligneFermer.add(ligne);
    }

    public void retirerStationFermer(String retirer) {
        for (int i = 0; i < stationFermer.size(); i++) {
            if (retirer.equals(stationFermer.get(i).nom)) {
                stationFermer.remove(i);
                break;
            }
        }
    }

    void retirerLigneFermer(String retirer) {
        for (int i = 0; i < ligneFermer.size(); i++) {
            if (retirer.equals(ligneFermer.get(i))) {
                ligneFermer.remove(i);
                break;
            }
        }
    }

    
}