package montserrat.marcet.horarimanager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class Tabla {



    private TableLayout tabla;
    private ArrayList<TableRow> filas;
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS;
    private int defaultCellHeight =270;
    private int defaultCellWidth =180;


    public Tabla(Activity actividad, TableLayout tabla) {
        this.actividad = actividad;
        this.tabla = tabla;
        rs = this.actividad.getResources();
        FILAS = COLUMNAS = 0;
        filas = new ArrayList<TableRow>();
    }



    public void agregarCabecera(int recursocabecera) {
        TableRow.LayoutParams layoutCelda;
        TableRow fila = new TableRow(actividad);
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        String[] arraycabecera = rs.getStringArray(recursocabecera);
        COLUMNAS = arraycabecera.length;
        for (int i = 0; i < arraycabecera.length; i++) {
            Button button = new Button(actividad);
            layoutCelda = new TableRow.LayoutParams(defaultCellWidth, TableRow.LayoutParams.WRAP_CONTENT);
            button.setText(arraycabecera[i]);
            button.setTextSize(12);
            button.setHeight(150);
            button.setWidth(defaultCellWidth);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setTextAppearance(actividad, R.style.estilo_celda);
            button.setBackgroundResource(R.drawable.tabla_celda_cabecera);
            button.setLayoutParams(layoutCelda);
            fila.addView(button);
        }
        fila.setMinimumHeight(defaultCellHeight);
        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }

    public void agregarFilaTabla(ArrayList<String> elementos) {
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);

        for (int i = 0; i < elementos.size(); i++) {
            celdaButton button = new celdaButton(actividad);
            button.setText(String.valueOf(elementos.get(i)));
            button.setGravity(Gravity.CENTER);
            button.setTextAppearance(actividad, R.style.estilo_celda);
            button.setTextSize(12);
            button.setHeight(defaultCellHeight);
            button.setWidth(defaultCellWidth);
            button.setBackgroundResource(R.drawable.tabla_celda);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(button.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(layoutCelda);
            //button.setMinHeight(350);
            fila.addView(button);
        }

        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }



    public int getFilas() {
        return FILAS;
    }


    public int getColumnas() {
        return COLUMNAS;
    }


    private int obtenerAnchoPixelesTexto(String texto) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(80);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }

    public celdaButton get(int fila, int columna) {
        return (celdaButton) filas.get(fila).getChildAt(columna);

    }

    public boolean pintarTabla(List<Assignattura> asignatures) {
        List<Classe> classes;
        celdaButton button;
        boolean solapament;
        for (int dia = 0; dia < 5; dia++) {
            for (int hora = 0; hora < 13; hora++) {
                button = (this.get(hora + 1, dia + 1));
                button.setText("");
                button.setBackgroundResource(R.drawable.tabla_celda);
                button.removeClasse();
            }
        }
        solapament = false;
        int indice=255;
        int color;
        for (Assignattura a : asignatures) {
            color=Color.rgb(0,0,indice);
            if (a.getGrupoElegido() != null) {
                classes = a.getGrupoElegido().getClasses();
                for (Classe c : classes) {
                    for (int hora = c.getHoraInici(); hora < c.getHoraFin(); hora++) {
                        button = this.get(hora + 1, c.getDia() + 1);
                        String nom = button.getText().toString();
                        if (!button.addClasse(c)) {
                            button.setText(nom + "\n" + a.getNom() + " " + a.getGrupoElegido().getNom());
                            button.setBackgroundColor(Color.RED);
                            solapament = true;
                        } else {
                            button.setBackgroundColor(color);
                            if(hora==c.getHoraInici()){
                                button.setText(a.getNom() + " " + a.getGrupoElegido().getNom());
                            }
                        }

                    }
                }

            }
            indice-=30;
        }
        return solapament;
    }


    public void iniciar() {

        this.agregarCabecera(R.array.cabecera_tabla);
        for (int i = 0; i < 13; i++) {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add((i + 8) + "-" + (i + 9));
            elementos.add("                ");
            elementos.add("                ");
            elementos.add("                ");
            elementos.add("                ");
            elementos.add("                ");
            this.agregarFilaTabla(elementos);
        }

    }


    public void setOnclickListener(View.OnClickListener listener) {
        for(int fila=1;fila<this.getFilas();fila++){
            for(int colum=1;colum<getColumnas();colum++){
                get(fila,colum).setOnClickListener(listener);
            }
        }
    }
}

class celdaButton extends android.support.v7.widget.AppCompatButton{

    Classe classes;


    public celdaButton(Context context) {
        super(context);
    }

    public celdaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public celdaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean addClasse(Classe c){
        if(classes==null){
            classes=c;
            return true;
        }
        else return false;
    }


    public void removeClasse() {
        this.classes=null;
    }

    public Classe getClasse() {
        return classes;
    }
}

