package montserrat.marcet.horarimanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakum on 14/01/2018.
 */

public class Assignattura implements Serializable {
    String codi;
    String nom;
    String idioma;
    List<Grups> grups = new ArrayList<>();
    Grups grupoElegido;


    public Assignattura(String codi, String nom, String idioma) {
        this.codi = codi;
        this.nom = nom;
        this.idioma = idioma;
    }

    public Assignattura(String codi, String nom, String idioma,ArrayList<Grups>grups,Grups grupoElegido) {
        this.codi = codi;
        this.nom = nom;
        this.idioma = idioma;
        this.grups = grups;
        this.grupoElegido = grupoElegido;
    }

    public Assignattura() {}


    public Assignattura(String nom) {
        this.nom=nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Grups getGrupoElegido() {
        return grupoElegido;
    }

    public void setGrupoElegido(Grups grupoElegido) {
        this.grupoElegido = grupoElegido;
    }

    public List<Grups> getGrups() {
        return grups;
    }

    public void setGrups(ArrayList<Grups> grups) {
        this.grups = grups;
    }

    public void addGrupo(Grups g){
        grups.add(g);

    }

    public void setGrupoElegido(int i) {
        grupoElegido=grups.get(i);
    }
}

