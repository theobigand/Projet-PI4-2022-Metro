package Metro;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.*;

public class Metro {
    public static LinkedList<Station> build() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("metro.txt"));
        LinkedList<Station> stationList = new LinkedList<>();
        ;
        String ligne = "";

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            // System.out.println(str);
            try {
                str = new String(str.getBytes(), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                System.out.print("erreur");
            }
            if (str.matches("%\\s*[a-zA-Z]*\\s*[a-zA-Z]*\\s*[0-9]+\\s*[a-zA-Z]*")) {
                Matcher matcher = Pattern.compile("[0-9]+\\s*[a-zA-Z]*").matcher(str);
                if (matcher.find()) {
                    ligne = matcher.group().toLowerCase().replaceAll(" ", "");
                }
            } else if (str.equals("### connections")) {
                break;
            } else if (!str.equals("%")) {
                if (!stationIsIn(stationList, str))
                    stationList.add(new Station(str, ligne));
                else
                    getStation(stationList, str).ligne.add(ligne);
            }
        }

        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            try {
                str = new String(str.getBytes(), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                System.out.print("erreur");
            }
            if (!str.matches("%\\s*[a-zA-Z]*\\s*[a-zA-Z]*\\s*[0-9]+\\s*[a-zA-Z]*") && !str.equals("%")) {
                String[] strTab = str.split(":|>");
                Station x = getStation(stationList, strTab[0]);
                Station y = getStation(stationList, strTab[1]);
                x.correspondences.put(y, 2);
                y.correspondences.put(x, 2);
            }
        }
        return stationList;

    }

    public static LinkedList<Trajet> listTrajet(LinkedList<Station> dejaVisitee, LinkedList<Trajet> listTrajet, Station n,
            Station arriver, Trajet t) {
        LinkedList<Station> copieDejaVisitee = (LinkedList<Station>) dejaVisitee.clone();
        if (changementDeLigne(t, n)) {
            t.temps += 5;
            t.nbChangement++;
        }
        if (n.nom.equals(arriver.nom)) {
            t.listStation.add(n);
            t.listLigne.add(ligneEnCommun(t, n));
            listTrajet.add(new Trajet(t.chemin + n.nom, t.temps, t.depart, t.arriver,
                    (LinkedList<String>) t.listLigne.clone(),
                    (LinkedList<Station>) t.listStation.clone(), t.nbChangement));
            if (Trajet.tempsMin > t.temps || Trajet.tempsMin == -1)
                Trajet.tempsMin = t.temps;
        } else if (t.temps < Trajet.tempsMin || Trajet.tempsMin == -1) {
            dejaVisitee.add(n);
            String chemin = t.chemin + n.nom + " -> ";
            if (!t.listStation.isEmpty()) {
                String ligneCommun = ligneEnCommun(t, n);
                t.listLigne.add(ligneCommun);
            }
            t.listStation.add(n);
            for (var i : n.correspondences.entrySet()) {
                if (!stationIsIn(copieDejaVisitee, i.getKey().nom)
                        && !stationIsIn(Conditions.stationFermer, i.getKey().nom)) {
                    int nbChangement = t.nbChangement;
                    if (changementDeLigne(t, i.getKey()))
                        nbChangement++;
                    if (Conditions.nbChangementMax == -1 || nbChangement <= Conditions.nbChangementMax) {
                        int temps = t.temps + i.getValue();
                        copieDejaVisitee.add(n);
                        listTrajet(
                                copieDejaVisitee, listTrajet, i.getKey(), arriver,
                                new Trajet(chemin, temps, t.depart, t.arriver,
                                        (LinkedList<String>) t.listLigne.clone(),
                                        (LinkedList<Station>) t.listStation.clone(), t.nbChangement));
                    }
                }
            }
        }
        return listTrajet;
    }

    static String ligneEnCommun(Trajet t, Station b) {
        if (!t.listLigne.isEmpty()) {
            String ligne = t.listLigne.getLast();
            for (String s2 : b.ligne) {
                if (s2.equals(ligne))
                    return s2;
            }
        }
        Station a = t.listStation.getLast();
        for (String s : a.ligne) {
            for (String s2 : b.ligne) {
                if (s.equals(s2) && !ligneIsIn(Conditions.ligneFermer, s))
                    return s;
            }
        }
        return null;
    }

    static boolean changementDeLigne(Trajet t, Station s) {
        if (t.listLigne.isEmpty())
            return false;
        String ligne = t.listLigne.getLast();
        for (String s2 : s.ligne) {
            if (s2.equals(ligne))
                return false;
        }
        return true;
    }

    static String cheminCourt(LinkedList<Trajet> listTrajet) {
        if (listTrajet.isEmpty())
            return "Il n'y a pas de trajet possible avec ces conditions.";
        Trajet t = listTrajet.getFirst();
        for (Trajet tra : listTrajet) {
            if (t.temps > tra.temps)
                t = tra;
        }
        return "Le trajet le plus court pour aller de " + t.depart.nom + " à " + t.arriver.nom + " est: " + t.chemin
                + ", en "
                + t.temps + " minutes. La liste des lignes parcouru est " + t.ligne();
    }

    static boolean stationIsIn(LinkedList<Station> stationList, String nom) {
        for (Station s : stationList) {
            if (s.nom.equals(nom))
                return true;
        }
        return false;
    }

    static boolean ligneIsIn(LinkedList<String> ligneList, String nom) {
        for (String s : ligneList) {
            if (s.equals(nom))
                return true;
        }
        return false;
    }

    public static boolean isLigneIn(LinkedList<String> stationList, String str) {
        for (int i = 0; i < stationList.size(); i++) {
            if (stationList.get(i).equals("Ligne " + str)) {
                return true;
            }
        }
        return false;
    }

    public static Trajet TrajetCourt(LinkedList<Trajet> listTrajet) {
        Trajet t = listTrajet.getFirst();
        for (Trajet tra : listTrajet) {
            if (t.temps > tra.temps)
                t = tra;
        }
        return t;
    }

    public static LinkedList<String> getLigne(LinkedList<Station> stationList) {
        LinkedList<String> res = new LinkedList<>();
        for (int i = 0; i < stationList.size(); i++) {
            for (int j = 0; j < stationList.get(i).ligne.size(); j++) {
                if (!Metro.isLigneIn(res, stationList.get(i).ligne.get(j))) {
                    res.add("Ligne " + stationList.get(i).ligne.get(j));
                }
            }
        }
        return res;
    }

    public static Station getStation(LinkedList<Station> stationList, String nom) {
        for (Station s : stationList) {
            if (s.nom.equals(nom))
                return s;
        }
        return null;
    }

    public static LinkedList<String> getStationPerLigne(LinkedList<Station> stationList, String base) {
        LinkedList<String> res = new LinkedList<>();
        for (int i = 0; i < stationList.size(); i++) {
            for (int j = 0; j < stationList.get(i).ligne.size(); j++) {
                if (stationList.get(i).ligne.get(j).equals(base)) {
                    res.add(stationList.get(i).nom);
                }
            }
        }
        return res;
    }

    public static String[] StationNameList(LinkedList<Station> stationList) {

        int x = 0;
        for (Station station : stationList) {
            if (station != null) {
                x += 1;
            }
        }
        String[] str = new String[x];
        for (int i = 0; i < x; i++) {
            str[i] = stationList.get(i).nom;
        }
        return str;
    }
}