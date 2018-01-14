package montserrat.marcet.horarimanager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    Spinner sp_graus;
    String [] graus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        TextView tv_assig = (TextView) findViewById(R.id.tv_assig);
        tv_assig.setText("PMA");
        TextView tv_idioma = (TextView) findViewById(R.id.tv_idioma);
        tv_idioma.setText("CAST");
        TextView tv_aula = (TextView) findViewById(R.id.tv_aula);
        tv_aula.setText("TR1 2.01");
        TextView tv_prof = (TextView) findViewById(R.id.tv_prof);
        tv_prof.setText("A.FERNANDEZ");
        TextView tv_tipus = (TextView) findViewById(R.id.tv_tipus);
        tv_tipus.setText("TEORIA");
        TextView tv_codi = (TextView) findViewById(R.id.tv_codi);
        tv_codi.setText("320041");



        ViewActivity.Tabla tabla = new ViewActivity.Tabla(this, (TableLayout)findViewById(R.id.tabla));
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

        sp_graus = (Spinner) findViewById(R.id.sp_graus);
        graus = getResources().getStringArray(R.array.llista_graus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_graus.setAdapter(adapter);
        setSpinnerListener();

    }

    private void setSpinnerListener() {

        sp_graus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) Toast.makeText(getApplicationContext(),"Has triat "+graus[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
         * Obtiene el ancho en píxeles de un texto en un String
         * @param texto Texto
         * @return Ancho en píxeles del texto
         */
        private int obtenerAnchoPixelesTexto(String texto)
        {
            Paint p = new Paint();
            Rect bounds = new Rect();
            p.setTextSize(50);

            p.getTextBounds(texto, 0, texto.length(), bounds);
            return bounds.width();
        }

/*

        */
/**
         * Elimina una fila de la tabla
         * @param indicefilaeliminar Indice de la fila a eliminar
         *//*

        public void eliminarFila(int indicefilaeliminar)
        {
            if( indicefilaeliminar > 0 && indicefilaeliminar < FILAS )
            {
                tabla.removeViewAt(indicefilaeliminar);
                FILAS--;
            }
        }
*/

        /**
         * Devuelve las filas de la tabla, la cabecera se cuenta como fila
         * @return Filas totales de la tabla
         *//*
        public int getFilas()
        {
            return FILAS;
        }

        *//**
         * Devuelve las columnas de la tabla
         * @return Columnas totales de la tabla
         *//*
        public int getColumnas()
        {
            return COLUMNAS;
        }

        *//**
         * Devuelve el número de celdas de la tabla, la cabecera se cuenta como fila
         * @return Número de celdas totales de la tabla
         *//*
        public int getCeldasTotales()
        {
            return FILAS * COLUMNAS;
        }*/


    }
}
