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

    String[] graus;
    private ArrayList<Assignattura> asignatures;
    TextView tv_assig;
    TextView tv_idioma;
    TextView tv_aula;
    TextView tv_prof;
    TextView tv_tipus;
    TextView tv_codi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        asignatures=(ArrayList<Assignattura>) getIntent().getSerializableExtra(ID_ASIGNATURES);

        tv_assig = (TextView) findViewById(R.id.tv_assig);
        tv_assig.setText("");
        tv_idioma = (TextView) findViewById(R.id.tv_idioma);
        tv_idioma.setText("");
        tv_aula = (TextView) findViewById(R.id.tv_aula);
        tv_aula.setText("");
        tv_prof = (TextView) findViewById(R.id.tv_prof);
        tv_prof.setText("");
        tv_tipus = (TextView) findViewById(R.id.tv_tipus);
        tv_tipus.setText("");
        tv_codi = (TextView) findViewById(R.id.tv_codi);
        tv_codi.setText("");


        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.iniciar();
        tabla.pintarTabla(asignatures);
        tabla.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celdaButton bb=(celdaButton)view;
                Classe c=bb.getClasse();
                tv_assig.setText(c.getAssignatura());
                tv_idioma.setText(c.getIdioma());
                tv_aula.setText(c.getAula());
                tv_prof.setText(c.getProfessor());
                tv_tipus.setText(c.getTipus());
                tv_codi.setText(c.getCodi());
            }
        });

        graus = getResources().getStringArray(R.array.llista_graus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(ViewActivity.this, ChoiceActivity.class));// do something here and don't write super.onBackPressed()
    }

}

