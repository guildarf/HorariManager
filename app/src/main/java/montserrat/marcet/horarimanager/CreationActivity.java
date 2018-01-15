package montserrat.marcet.horarimanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreationActivity extends AppCompatActivity {

    ArrayList<Assignattura> asignatures;
    boolean solapament;
    private Tabla tabla;
    String nomHorari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
      //  nomHorari = getIntent().getStringExtra(SubjectsActivity.HORARI_NOM);
        nomHorari="guillem";
        asignatures = (ArrayList<Assignattura>) getIntent().getSerializableExtra(SubjectsActivity.ID_ASIGNSELECT);
        Classe c1 = new Classe(0, 0, 2);
        Classe c2 = new Classe(1, 0, 2);
        Classe c3 = new Classe(2, 0, 2);
        Classe c4 = new Classe(2, 1, 3);

        asignatures.get(0).addGrupo(new Grups("101", c1));
        asignatures.get(0).addGrupo(new Grups("102", c2));
        asignatures.get(1).addGrupo(new Grups("101", c3));
        asignatures.get(2).addGrupo(new Grups("101", c4));

        ListView list = (ListView) findViewById(R.id.asignaturas);
        SelectorGrupAdapter adapter = new SelectorGrupAdapter(this, R.layout.list_item_activity_creation, asignatures, createListener());
        list.setAdapter(adapter);


        tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.iniciar();

        Button btn_horari_ok = (Button) findViewById(R.id.btn_acceptar_horari);
        btn_horari_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!solapament) {
                    guardarDades();
                    Intent i = new Intent(CreationActivity.this, ViewActivity.class);
                    i.putExtra(ViewActivity.ID_ASIGNATURES, asignatures);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Siusplau,soluciona els solapaments abans de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarDades() {
        FileOutputStream fos = null;
        ObjectOutputStream oos;
        try {
            fos = openFileOutput(nomHorari + ".tmp", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(asignatures);
            oos.close();
        } catch (FileNotFoundException e) {
            Log.v("eeeeeee",e.getMessage());
        } catch (IOException e) {
            Log.v("eeeeeee2",e.getMessage());
        }


    }

    private RadioGroup.OnCheckedChangeListener createListener() {

        RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == -1) return;
                TextView tv = (TextView) ((View) radioGroup.getParent()).findViewById(R.id.subject);
                for (Assignattura a : asignatures) {
                    if (a.getNom().equals(tv.getText())) {
                        switch (i) {
                            case R.id.rd_btn1:
                                a.setGrupoElegido(0);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            case R.id.rd_btn2:
                                a.setGrupoElegido(1);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            case R.id.rd_btn3:
                                a.setGrupoElegido(2);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            case R.id.rd_btn4:
                                a.setGrupoElegido(3);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            case R.id.rd_btn5:
                                a.setGrupoElegido(4);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            case R.id.rd_btn6:
                                a.setGrupoElegido(5);
                                solapament=tabla.pintarTabla(asignatures);
                                break;
                            default:
                                break;
                        }
                    }
                }


            }
        };

        return listener;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by marcm on 12/01/2018.
     */

}



