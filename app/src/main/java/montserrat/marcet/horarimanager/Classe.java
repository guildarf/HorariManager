package montserrat.marcet.horarimanager;

import java.io.Serializable;

/**
 * Created by sakum on 14/01/2018.
 */

class Classe implements Serializable {

    String tipus;
    String aula;
    String professor;
    String assignatura;
    int dia;
    int horaInici;
    int durada;
    String idioma;
    public String codi;

    public Classe(String tipus, String aula, String professor, String assignatura, int dia, int horaInici, int durada, String idioma, String codi) {
        this.tipus = tipus;
        this.aula = aula;
        this.professor = professor;
        this.assignatura = assignatura;
        this.dia = dia;
        this.horaInici = horaInici;
        this.durada = durada;
        this.idioma = idioma;
        this.codi = codi;
    }

    public Classe(int dia, int horaInici, int durada) {
        this.dia = dia;
        this.horaInici = horaInici;
        this.durada = durada;
    }
    public Classe() {}

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(int horaInici) {
        this.horaInici = horaInici;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getAssignatura() {
        return assignatura;
    }

    public void setAssignatura(String assignatura) {
        this.assignatura = assignatura;
    }

    public int getHoraFin() {
        int result= horaInici+durada;
        if (result>13){
            return 13;
        }
        return result;
    }

}
