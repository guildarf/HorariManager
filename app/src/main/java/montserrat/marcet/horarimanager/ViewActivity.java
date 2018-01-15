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

    static final String ID_ASIGNATURES ="uououo" ;
    Spinner sp_graus;
    String[] graus;
    private ArrayList<Assignattura> asignatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        asignatures=(ArrayList<Assignattura>) getIntent().getSerializableExtra(ID_ASIGNATURES);

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


        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.iniciar();
        tabla.pintarTabla(asignatures);

        sp_graus = (Spinner) findViewById(R.id.sp_graus);
        graus = getResources().getStringArray(R.array.llista_graus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_graus.setAdapter(adapter);
        setSpinnerListener();

    }

    private void setSpinnerListener() {

        sp_graus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    Toast.makeText(getApplicationContext(), "Has triat " + graus[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(ViewActivity.this, ChoiceActivity.class));// do something here and don't write super.onBackPressed()
    }

}

