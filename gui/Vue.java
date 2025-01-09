package gui;

import Metro.Conditions;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import Metro.JTextFieldAutoCompletion;
import Metro.Station;
import Metro.Trajet;
import Metro.Metro;

import java.awt.Container;

public class Vue extends JFrame {

    protected static final int EXIT_ON_CLOSE = 0;
    JTextFieldAutoCompletion value, value2;
    Container principal;

    public Vue() throws FileNotFoundException {
        JFrame f = new JFrame();
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        this.principal = this.getContentPane();
        JPanel menuPlugins = new JPanel();
        JPanel menu = new JPanel();

        LinkedList<Station> stationList = Metro.build();

        // création des zones de texte pour la station de départ et d'arrivée
        // ains que le bouton entrer

        JLabel l1 = new JLabel("Départ: ");
        JLabel l2 = new JLabel("Arrivée: ");
        JLabel changement = new JLabel("Nombre de changements: ");
        value = new JTextFieldAutoCompletion(25);
        value2 = new JTextFieldAutoCompletion(25);
        JButton entrer = new JButton("Entrer");
        entrer.setBounds(200, 150, 70, 20);
        String[] nombre = { "infini", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        JComboBox ligne_ferme = new JComboBox(nombre);

        List<String> zone_ecriture = new ArrayList<String>();
        String[] str = Metro.StationNameList(stationList);
        for (int i = 0; i < str.length; i++) {
            zone_ecriture.add(str[i]);
        }

        // Ajout des composant à la fenêtre

        value.setDataCompletion(zone_ecriture);
        value2.setDataCompletion(zone_ecriture);

        menuPlugins.setLayout(new BoxLayout(menuPlugins, BoxLayout.LINE_AXIS));
        f.add(menuPlugins, BorderLayout.NORTH);
        f.add(menu);
        menuPlugins.add(l1);
        menuPlugins.add(value);
        menuPlugins.add(l2);
        menuPlugins.add(value2);

        menu.add(changement);
        menu.add(entrer);
        menu.add(ligne_ferme);

        // actionListener permettant d'afficher les tations de chaque ligne de métro

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JDialog jdial = new JDialog();
                jdial.setTitle("Ligne 1");
                jdial.setSize(1200, 250);
                JPanel p = new JPanel();
                jdial.add(p);
                p.setLayout(new FlowLayout());
                String comp = event.getActionCommand();
                LinkedList<String> Ligne = Metro.getLigne(stationList);

                // on sélectionne le numéro de ligne
                String[] num = new String[Ligne.size()];
                for (int i = 0; i < Ligne.size(); i++) {
                    String[] aux = Ligne.get(i).split(" ");
                    num[i] = aux[1];
                }

                for (int z = 0; z < num.length; z++) {
                    if (comp.equals(Ligne.get(z))) {
                        LinkedList<String> sta = Metro.getStationPerLigne(stationList, num[z]);
                        for (int j = 0; j < sta.size(); j++) {
                            JButton b = new JButton(sta.get(j));
                            p.add(b);
                        }
                    }
                }
                jdial.setVisible(true);
            }
        };

        // ajout des menus à la barre de menus
        JMenuBar menuBar = new JMenuBar();
        JMenu Toutes_les_lignes = new JMenu("Toutes les lignes");
        LinkedList<String> Ligne = Metro.getLigne(stationList);
        for (int i = 0; i < Ligne.size(); i++) {
            JMenuItem item = new JMenuItem(Ligne.get(i));
            item.addActionListener(l);
            Toutes_les_lignes.add(item);
        }

        // création de la sélection des stations fermées
        JMenu diversMenu = new JMenu("Sélectionner/Désélectionner une/des station(s) à fermer");
        LinkedList<Station> Stationfermer = new LinkedList<>();

        // récupération des lignes
        LinkedList<String> menuLigne = Metro.getLigne(stationList);
        String[] num = new String[Ligne.size()];

        // récupération du numéro de ligne
        for (int i = 0; i < Ligne.size(); i++) {
            String[] aux = Ligne.get(i).split(" ");
            num[i] = aux[1];
        }

        // Pour chaque numero de ligne on en récupère les stations
        for (int j = 0; j < num.length; j++) {
            LinkedList<String> nom_stations = Metro.getStationPerLigne(stationList, num[j]);
            JMenu jm = new JMenu(menuLigne.get(j));
            diversMenu.add(jm);

            // Pour chaque station, création d'une checkbox

            for (int k = 0; k < nom_stations.size(); k++) {
                JCheckBox jc = new JCheckBox(nom_stations.get(k));
                final int z = j;

                // Ajout de l'addActionListener permettant d'ajout une station à la liste des
                // stations fermées lors d'un click

                ActionListener stationfermelistener = new ActionListener() {
                    public void actionPerformed(ActionEvent o) {

                        if (jc.isSelected()) {
                            Station st = Metro.getStation(stationList, jc.getText());
                            Stationfermer.add(st);
                        }
                        if (!jc.isSelected()) {
                            for (Station station : Stationfermer) {
                                if (station.nom.equals(jc.getText())) {
                                    Stationfermer.remove(station);
                                }
                            }
                        }
                    }
                };
                jc.addActionListener(stationfermelistener);
                jm.add(jc);
            }
        }

        // ActionListener déclenché lors de l'appui sur le bouton entrer
        ActionListener enterListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String t = value.getText();
                String t2 = value2.getText();
                Station d = Metro.getStation(stationList, t);
                Station a = Metro.getStation(stationList, t2);
                if (t.equals("")) {
                    JOptionPane.showMessageDialog(f, "Vous devez saisir un depart", "Alerte",
                            JOptionPane.WARNING_MESSAGE);
                } else if (t2.equals("")) {
                    JOptionPane.showMessageDialog(f, "Vous devez saisir une arrivée", "Alerte",
                            JOptionPane.WARNING_MESSAGE);
                } else if (d == null) {
                    JOptionPane.showMessageDialog(f, "Vous devez saisir un départ correcte", "Alerte",
                            JOptionPane.WARNING_MESSAGE);
                } else if (a == null) {
                    JOptionPane.showMessageDialog(f, "Vous devez saisir une arrivée correcte", "Alerte",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JDialog jdial = new JDialog();
                    JPanel p = new JPanel();
                    p.setLayout(new BorderLayout());
                    jdial.setTitle("Trajet le plus court entre " + t + " et " + " " + t2);
                    for (Station S : Stationfermer) {
                        if (S != null) {
                            Conditions.ajouterStationFermer(S);
                        }
                    }
                    String nbChangement = (String) ligne_ferme.getSelectedItem();
                    if (!nbChangement.equals("infini")) {
                        int x = Integer.parseInt((String) ligne_ferme.getSelectedItem());
                        Conditions.nbChangementMax = x;
                    }
                    else{
                        Conditions.nbChangementMax =-1;
                    }

                    LinkedList<Trajet> trajet = Metro.listTrajet(new LinkedList<Station>(), new LinkedList<Trajet>(), d,
                            a, new Trajet("", 0, d, a, new LinkedList<String>(), new LinkedList<Station>(), 0));
                    if (trajet.isEmpty()) {
                        JOptionPane.showMessageDialog(f, "Il n'existe pas de trajet avec ce nombre de changements",
                                "Alerte",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    try {
                        Trajet tr = Metro.TrajetCourt(trajet);

                        String s = t + " ----------> " + tr.compteStation() + " station(s) " + "----------> " + t2;
                        JLabel l = new JLabel(s);
                        p.add(l, BorderLayout.NORTH);

                        JButton button = new JButton("Voir les stations");
                        ActionListener buttonListener = new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        new GUI(tr).setVisible(true);
                                    }
                                });
                            }
                        };
                        button.addActionListener(buttonListener);
                        p.add(button, BorderLayout.SOUTH);
                        jdial.add(p);
                        jdial.setSize(300, 150);
                        jdial.setVisible(true);
                    } catch (NoSuchElementException n) {
                        System.out.println("Il n'est pas de trajet avec ce nombre de changements");
                    }
                }
            }

        };
        entrer.addActionListener(enterListener);
        menuBar.add(Toutes_les_lignes);
        menuBar.add(diversMenu);

        // Construction et injection de la barre de menu
        f.setJMenuBar(menuBar);
        f.setVisible(true);

    }
}
