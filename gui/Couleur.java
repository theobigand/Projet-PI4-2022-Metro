package gui;
import java.awt.Color;

public class Couleur {

	public String ligne;
	public Color c;
	
	public Couleur(String ligne){
		this.ligne=ligne;
		switch(ligne){
			case "1":this.c=new Color(255,190,0);break;	//Bouton d'or
			case "2":this.c=new Color(0,85,200);break;		//Azur
			case "3":this.c=new Color(110,110,0);break;	//Olive
			case "3bis":this.c=new Color(130,200,230);break;	//Pervenche(Bleu pâle)
			case "4":this.c=new Color(160,0,110);break;	//Parme(Violet/rose)
			case "5":this.c=new Color(255,90,0);break;		//Orange
			case "6":this.c=new Color(130,220,115);break;	//Menthe
			case "7":this.c=new Color(255,130,180);break;	//Rose
			case "7bis":this.c=new Color(130,220,115);break;	//Menthe
			case "8":this.c=new Color(210,130,190);break;	//Lilas
			case "9":this.c=new Color(210,210,0);break;	//Acacia
			case "10":this.c=new Color(220,150,0);break;	//Ocre(Jaune pâle)
			case "11":this.c=new Color(90,35,10);break;	//Marron
			case "12":this.c=new Color(0,100,60);break; 	//Sapin
			case "13":this.c=new Color(130,200,230);break;	//Pervenche(Bleu pâle)
			case "14":this.c=new Color(100,0,130);break;  	//Iris(Violet Foncé)
		}
	}
}
