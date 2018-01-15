package montserrat.marcet.horarimanager.Objectes;

import java.io.Serializable;

public class Assignatura implements Serializable{

    String codi;
    String idioma;
    String name;

    boolean checked;

    public Assignatura() {
    }

    public Assignatura(String codi, String idioma, String name) {
        this.codi = codi;
        this.idioma = idioma;
        this.name = name;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Assignatura(String name) {
        this.name = name;
    }

    public Assignatura(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked(){
        this.checked=!this.checked;
    }


}

