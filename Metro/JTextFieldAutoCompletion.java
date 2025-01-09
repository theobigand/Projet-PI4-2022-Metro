package Metro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class JTextFieldAutoCompletion extends JTextField implements DocumentListener, ActionListener{

    /**
     * 
     */
    private static final long serialVersionUID = 4810213451949301347L;

    //Les Donn�es De L'AutoCompletion
    private List<String> data = new ArrayList<String>();

    //Un Constructeur Par D�faut
    public JTextFieldAutoCompletion() {
        //Par Defaut Le Nombre de caract�re visible dans le champs de texte est 25
        this(25);
    }
    /**
     * Un Constructeur Param�tr�
     * @param columns nombre de caract�re visible dans le champs de texte
     */
    public JTextFieldAutoCompletion(int columns) {
        //passer au constructeur � deux arguments le nombre de colonne visible dans le champs de texte et definir les donn�es de l'autocompletion � null.
        this(columns, null);
    }
    /**
     * Constructeur Param�tr� � deux arguments
     * @param columns nombre de caract�re visible dans le champs de texte
     * @param data les donn�es de l'autocompletion
     */
    public JTextFieldAutoCompletion(int columns, List<String> data) {
        super(columns);
        //ici on fait appel � la m�thode setDataCompletion pour definir les donn�es de l'autocompletion
        this.setDataCompletion(data);
        //je d�fini l'ecouteur de l'evenement de la saisie
        this.getDocument().addDocumentListener(this);
        //je d�fini j'ecouteur de la touche entrer
        this.addActionListener(this);
    }
    /**
     * Permet De Redefinir les donn�es de l'autocompletion
     * @param data les donn�es de l'autocompletion
     */
    public void setDataCompletion(List<String> data) {
        //on affecte seulement si data est d�ffirent � null
        if(data != null)
            this.data = data;
        //on va trier les donn�es de l'autocompletion 
        Collections.sort(this.data);
    }
    /**
     * Evenement D�clench� � chaque fois que l'utilisateur tape un caract�re quelconque, ou fasse une copier/coller dans le champs de texte.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Stub de la m�thode g�n�r� automatiquement
        //on arr�te l'ex�cution de l'evenement si l'utilisateur fasse une copier/coller
        if(e.getLength() != 1) return;

        //on r�cup�re la position du dernier carat�re saisie en comptant de z�ro, premier caract�re est en position 0, le deuxi�me � 2 etc..
        int pos = e.getOffset();
        String prefix = null;
        try {
            //on recup�re dans prefix ce qu'a saisi l'utilisateur jusqu'� pr�sent.
            prefix = this.getText(0, pos + 1);
        } catch (BadLocationException e1) {}

        //on fait une recherche sur la chaine qu'a saisi l'utilisateur dans les donn�es de l'autocompletion. 
        //la m�thode binarySearch retourne :
        //Soit l'index de l'element cherch� s'il est contenu dans la collection.
        //Soit le nombre d'element de la collection si tous les elements sont inf�rieurs � l'element qu'on cherche.
        //Soit un entier n�gatif qui repr�sente l'index de premier element sup�rieur de l'element qu'on cherche.
        int index = Collections.binarySearch(data, prefix);

        if(index < 0 && -index <= data.size()) {
            //Completion Trouv�
            //On r�cup�re le premier element sup�rieur � l'element cherch�. le signe - retourne la valeur absolue de la variable index. 
            String match = data.get(-index - 1);

            //on s'assure que la chaine dans la variable match commence par la chaine contenu dans la variable prefix c-�-d ce qu'a saisi l'utilisateur 
            if(match.startsWith(prefix)) {
                //si oui on met on place l'autocompletion sinon on fait rien :).
                SwingUtilities.invokeLater(new AutoCompletion(pos, match));
            }
        } else ;
            //Aucune Completion Trouv�

    }
    /**
     * Permet De Valider L'AutoCompletion En Cliquant Sur La Touche Entrer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Stub de la m�thode g�n�r� automatiquement
        setCaretPosition(getSelectionEnd());
    }
    @Override
    public void removeUpdate(DocumentEvent e) {}
    @Override
    public void changedUpdate(DocumentEvent e) {}

    private class AutoCompletion implements Runnable{
        private int pos;
        private String completion;

        public AutoCompletion(int pos, String completion) {
            this.pos = pos;
            this.completion = completion;
        }
        @Override
        public void run() {
            
            //On affecte la chaine trouv� pour l'autocompletion dans le champs de texte
            setText(completion);
            //on definit � partir d'o� va d�buter la s�l�ction des caract�res ajout� comme completion. 
            //j'ai pr�cis� qu'il va d�buter de la fin vers le dernier caract�re sasie par l'utilisateur
            setCaretPosition(completion.length());
            //j'ai appliqu� la s�l�ction jusqu'au dernier caract�re sasie par l'utilisateur
            moveCaretPosition(pos + 1);
        }       
    }
}