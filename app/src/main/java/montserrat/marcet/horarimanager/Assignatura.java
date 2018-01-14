package montserrat.marcet.horarimanager;

public class Assignatura {

    String name;
    boolean checked;

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

