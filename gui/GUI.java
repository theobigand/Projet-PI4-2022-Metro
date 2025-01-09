package gui;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import Metro.Station;
import Metro.Trajet;

import java.awt.Graphics;
import java.io.FileNotFoundException;


public class GUI extends JFrame  {

	public GUI(Trajet tr) {
		

		this.setTitle("City Metro");
		this.setSize(800,600);
        this.setLocationRelativeTo(null);
        LinkedList<Station> liste=tr.listStation;
        LinkedList<String> listeLigne=tr.listLigne;


        //Création JLabel avec les noms des stations
        String [] Nom=new String [10];
        int [] compt=new int [10];
        String s="L"+listeLigne.get(0)+"  "+liste.get(0).nom;
        if(liste.get(0).nom.length()<20){
            for(int t=0;t<(20-liste.get(0).nom.length());t++){
                s+=" ";
            }
        }else{
            s=liste.get(0).nom.substring(0,18)+"..   ";
        }
        int j=1;
        int compteur=1;
        int i=0;
        int h=0;
        while(j<liste.size()){
            if(liste.get(j).nom.length()<20){
                s+=liste.get(j).nom;
                for(int t=0;t<(20-liste.get(j).nom.length());t++){
                    s+=" ";
                }
            }else{
                s+=liste.get(j).nom.substring(0,18)+"..    ";
            }compteur++;
            if (h<liste.size()){
                if(comparaison_ligne(listeLigne,h)==false){
                    compt[i]=compteur;
                    Nom[i]=s;
                    if(h+1>listeLigne.size()-1){
                        h--;
                    }
                    s="L"+listeLigne.get(h+1)+"  ";
                    i+=1;
                    compteur=0;
                }
            }j++;
            h++;
        }
        i=0;
        JPanel affichage = new JPanel();
        this.getContentPane().add(affichage);
        affichage.setLayout(null);

        int x=15;
        int y=20;
        
        JLabel temps = new JLabel("Votre temps de trajet total est de "+String.valueOf(tr.temps)+" minutes.");

        JLabel label0 = new JLabel(Nom[0]);
        JLabel label1 = new JLabel(Nom[1]);
        JLabel label2 = new JLabel(Nom[2]);
        JLabel label3 = new JLabel(Nom[3]);
        JLabel label4 = new JLabel(Nom[4]);
        JLabel label5 = new JLabel(Nom[5]);
        JLabel label6 = new JLabel(Nom[6]);
        JLabel label7 = new JLabel(Nom[7]);
        JLabel label8 = new JLabel(Nom[8]);
        JLabel label9 = new JLabel(Nom[9]);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
            int a=25;
            int b=50;
            int i=0;
            int var=0;
            super.paintComponent(g);
            while(compt[i]>0){
                for(int c=0;c<compt[i]-1;c++){
                    Couleur couleur=new Couleur(listeLigne.get(var));
                    g.setColor(couleur.c);
                    g.drawRect(a,b, 30, 30);    
                    a+=30;             
                    g.drawLine(a, b+15, a+70, b+15);
                    a+=70;
                    var++;
                }g.drawRect(a,b, 30, 30);   //Dessin de la dernère station de la ligne
                if(compt[i+1]>0){
                    a+=15; b+=70;
                    Couleur couleur=new Couleur(listeLigne.get(var));
                    g.setColor(couleur.c);
                    g.drawLine(a, b-40, a, b);
                    var++;
                }i++;
            }
            }
        };

        panel.add(temps);
        temps.setBounds(300,5,300,15);
        panel.add(label0);
        label0.setBounds(x,y,110*compt[0]+20,20);
        label0.setText(Nom[0]);
        x+=100*(compt[0]-1);  y+=80;
        panel.add(label1);
        label1.setBounds(x,y,110*compt[1]+20,20);
        x+=100*(compt[1]-1)+20;  y+=70;
        panel.add(label2);
        label2.setBounds(x,y,110*compt[2]+20,20);
        x+=100*(compt[2]-1)+10;  y+=70;
        panel.add(label3);
        label3.setBounds(x,y,110*compt[3]+20,20);
        x+=100*(compt[3]-1)+10;  y+=70;
        panel.add(label4);
        label4.setBounds(x,y,110*compt[4]+20,20);
        x+=100*(compt[4]-1)+10;  y+=70;
        panel.add(label5);
        label5.setBounds(x,y,110*compt[5]+20,20);
        x+=100*(compt[5]-1)+10;  y+=70;
        panel.add(label6);
        label6.setBounds(x,y,110*compt[6]+20,20);
        x+=100*(compt[6]-1)+10;  y+=70;
        panel.add(label7);
        label7.setBounds(x,y,110*compt[7]+20,20);
        x+=100*(compt[7]-1)+10;  y+=70;
        panel.add(label8);
        label8.setBounds(x,y,110*compt[8]+20,20);
        x+=100*(compt[8]-1)+10;  y+=70;
        panel.add(label9);
        label9.setBounds(x,y,110*compt[9]+20,20);
        x+=100*(compt[9]-1)+10;  y+=70;
        

        panel.setLayout(null);
        this.getContentPane().add(panel);
	}


    public boolean comparaison_ligne(LinkedList<String> listeLigne,int h){
        if (listeLigne.size()<=h+1)return false;
        if(listeLigne.get(h).equals(listeLigne.get(h+1))) return true;
        else return false;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                } catch (UnsupportedLookAndFeelException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    new Vue();
                } catch (FileNotFoundException e) {
                    System.out.println("Erreur !");
                }
            }
        });
    }
}
