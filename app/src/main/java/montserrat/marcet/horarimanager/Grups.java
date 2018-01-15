package montserrat.marcet.horarimanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sakum on 14/01/2018.
 */

class Grups implements Serializable {

    String nom;
    ArrayList<Classe> classes;

    public Grups(String nom) {
        this.nom = nom;
        classes=new ArrayList<>();
    }

    public Grups(String nom, Classe classe) {
        this.nom = nom;
        classes=new ArrayList<>();
        classes.add(classe);

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Classe> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Classe> classes) {
        this.classes = classes;
    }

    public void addClasse(Classe c){
        classes.add(c);
    }
}
