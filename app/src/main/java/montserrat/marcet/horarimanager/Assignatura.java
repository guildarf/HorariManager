package montserrat.marcet.horarimanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assignatura implements Serializable{



    String name;
    boolean checked;
    List<Horari> grupsHoraris;
    int grupSelected;

    public Assignatura(String name) {
        this.name = name;
        this.grupSelected=-1;
    }

    public Assignatura(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
        this.grupSelected=-1;
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

    public void addHorari(Horari horari){
        if(grupsHoraris==null) grupsHoraris=new ArrayList<>();
        grupsHoraris.add(horari);
    }

    public int getSelGroup() {
        return grupSelected;
    }

    public int getHorarisCount() {
        if (grupsHoraris==null) return -1;
        return grupsHoraris.size();
    }

    public Horari getHorari(int i) {
        return grupsHoraris.get(i);
    }
}

