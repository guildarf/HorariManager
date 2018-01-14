package montserrat.marcet.horarimanager;

import android.app.Activity;
import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CreationActivity extends AppCompatActivity {
        private final int MAX_ASSIGNATURES=10;
        private final int MAX_SUBGRUPS=6;
        ArrayList<Assignatura> asignatures;
        String[][] horari=new String[5][13];
    private Tabla tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        asignatures=(ArrayList<Assignatura>) getIntent().getSerializableExtra(SubjectsActivity.ID_ASIGNSELECT);
        asignatures.get(0).addHorari(new Horari("101",Horari.DILLUNS,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));
        asignatures.get(0).addHorari(new Horari("102",Horari.DIJOUS,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));

        asignatures.get(1).addHorari(new Horari("101",Horari.DIMARTS,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));
        asignatures.get(1).addHorari(new Horari("102",Horari.DIJOUS,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));

       // asignatures.get(2).addHorari(new Horari("101",Horari.DIMECRES,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));

       // asignatures.get(3).addHorari(new Horari("101",Horari.DIVENDRES,new int[]{Horari.FROM8_9,Horari.FROM9_10,Horari.FROM10_11}));

        ListView list=(ListView)findViewById(R.id.asignaturas);
        SelectorGrupAdapter adapter=new SelectorGrupAdapter(this,R.layout.list_item_activity_creation,asignatures,createListener());
        list.setAdapter(adapter);


        tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);
        for(int i = 0; i < 13; i++)
        {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add((i+8)+"-" + (i+9) );
            elementos.add("Casilla [" + i + ", 0]");
            elementos.add("Casilla [" + i + ", 1]");
            elementos.add("Casilla [" + i + ", 2]");
            elementos.add("Casilla [" + i + ", 3]");
            elementos.add("Casilla [" + i + ", 4]");
            tabla.agregarFilaTabla(elementos);
        }


    }

    private RadioGroup.OnCheckedChangeListener createListener() {

        RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==-1)return;
                TextView tv=(TextView)((View)radioGroup.getParent()).findViewById(R.id.subject);
                for(Assignatura a:asignatures){
                    if(a.getName().equals(tv.getText())){
                        switch (i){
                            case R.id.rd_btn1:a.grupSelected=0;
                                pintarTabla();break;
                            case R.id.rd_btn2:a.grupSelected=1;
                                pintarTabla();break;
                            case R.id.rd_btn3:a.grupSelected=2;
                                pintarTabla();break;
                            case R.id.rd_btn4:a.grupSelected=3;
                                pintarTabla();break;
                            case R.id.rd_btn5:a.grupSelected=4;
                                pintarTabla();break;
                            case R.id.rd_btn6:a.grupSelected=5;
                                pintarTabla();break;
                            default:break;
                        }
                    }
                }


            }
        };

        return listener;

    }

    private void pintarTabla() {
        boolean[][] horesActivas;
        TextView v;
        String asignatura;
        for(int dia=0;dia<5;dia++) {
            for (int hora = 0; hora < 13; hora++) {
                v = (TextView)((TableRow)tabla.tabla.getChildAt(hora+1)).getChildAt(dia+1);
                v.setText("");
                v.setBackgroundColor(Color.WHITE);
            }
        }
        for(Assignatura a:asignatures){
            asignatura=a.getName();
            horesActivas=a.getHorariSelected().getHores();
            for(int dia=0;dia<5;dia++){
                for(int hora=0;hora<13;hora++){
                    if(horesActivas[dia][hora]){
                        v= (TextView)((TableRow)tabla.tabla.getChildAt(hora+1)).getChildAt(dia+1);
                        String nom=v.getText().toString();
                        if(!nom.isEmpty()){
                            v.setText(nom+"\nVS\n"+asignatura+"\n"+a.getHorariSelected().getGrup());
                            v.setBackgroundColor(Color.RED);
                        }else{
                            v.setBackgroundColor(Color.BLUE);
                            v.setText(asignatura + "\n" + a.getHorariSelected().getGrup());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by marcm on 12/01/2018.
     */

    public static class Tabla {

            // Variables de la clase

            private TableLayout tabla;          // Layout donde se pintará la tabla
            private ArrayList<TableRow> filas;  // Array de las filas de la tabla
            private Activity actividad;
            private Resources rs;
            private int FILAS, COLUMNAS;        // Filas y columnas de nuestra tabla

            /**
             * Constructor de la tabla
             * @param actividad Actividad donde va a estar la tabla
             * @param tabla TableLayout donde se pintará la tabla
             */
            public Tabla(Activity actividad, TableLayout tabla)
            {
                this.actividad = actividad;
                this.tabla = tabla;
                rs = this.actividad.getResources();
                FILAS = COLUMNAS = 0;
                filas = new ArrayList<TableRow>();
            }


            /**
             * Añade la cabecera a la tabla
             * @param recursocabecera Recurso (array) donde se encuentra la cabecera de la tabla
             */
            public void agregarCabecera(int recursocabecera)
            {
                TableRow.LayoutParams layoutCelda;
                TableRow fila = new TableRow(actividad);
                TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                fila.setLayoutParams(layoutFila);

                String[] arraycabecera = rs.getStringArray(recursocabecera);
                COLUMNAS = arraycabecera.length;

                for(int i = 0; i < arraycabecera.length; i++)
                {
                    TextView texto = new TextView(actividad);
                    layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arraycabecera[i]), TableRow.LayoutParams.WRAP_CONTENT);
                    texto.setText(arraycabecera[i]);
                    texto.setGravity(Gravity.CENTER_HORIZONTAL);
                    texto.setTextAppearance(actividad, R.style.estilo_celda);
                    texto.setBackgroundResource(R.drawable.tabla_celda_cabecera);
                    texto.setLayoutParams(layoutCelda);

                    fila.addView(texto);
                }

                tabla.addView(fila);
                filas.add(fila);

                FILAS++;
            }

            /**
             * Agrega una fila a la tabla
             * @param elementos Elementos de la fila
             */
            public void agregarFilaTabla(ArrayList<String> elementos)
            {
                TableRow.LayoutParams layoutCelda;
                TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                TableRow fila = new TableRow(actividad);
                fila.setLayoutParams(layoutFila);

                for(int i = 0; i< elementos.size(); i++)
                {
                    TextView texto = new TextView(actividad);
                    texto.setText(String.valueOf(elementos.get(i)));
                    texto.setGravity(Gravity.CENTER_HORIZONTAL);
                    texto.setTextAppearance(actividad, R.style.estilo_celda);
                    texto.setBackgroundResource(R.drawable.tabla_celda);
                    layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(texto.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
                    texto.setLayoutParams(layoutCelda);

                    fila.addView(texto);
                }

                tabla.addView(fila);
                filas.add(fila);

                FILAS++;
            }


            /**
             * Elimina una fila de la tabla
             * @param indicefilaeliminar Indice de la fila a eliminar
             */
            public void eliminarFila(int indicefilaeliminar)
            {
                if( indicefilaeliminar > 0 && indicefilaeliminar < FILAS )
                {
                    tabla.removeViewAt(indicefilaeliminar);
                    FILAS--;
                }
            }

            /**
             * Devuelve las filas de la tabla, la cabecera se cuenta como fila
             * @return Filas totales de la tabla
             */
            public int getFilas()
            {
                return FILAS;
            }

            /**
             * Devuelve las columnas de la tabla
             * @return Columnas totales de la tabla
             */
            public int getColumnas()
            {
                return COLUMNAS;
            }

            /**
             * Devuelve el número de celdas de la tabla, la cabecera se cuenta como fila
             * @return Número de celdas totales de la tabla
             */
            public int getCeldasTotales()
            {
                return FILAS * COLUMNAS;
            }

            /**
             * Obtiene el ancho en píxeles de un texto en un String
             * @param texto Texto
             * @return Ancho en píxeles del texto
             */
            private int obtenerAnchoPixelesTexto(String texto)
            {
                Paint p = new Paint();
                Rect bounds = new Rect();
                p.setTextSize(75);

                p.getTextBounds(texto, 0, texto.length(), bounds);
                return bounds.width();
            }
        }
    }



