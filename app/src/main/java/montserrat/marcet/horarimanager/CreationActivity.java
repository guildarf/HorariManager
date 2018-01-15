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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreationActivity extends AppCompatActivity {

    private static final String TAG = "CreationActivity";
    ArrayList<Assignattura> asignatures;
    boolean solapament;
    private Tabla tabla;
    String nomHorari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        nomHorari = getIntent().getStringExtra(SubjectsActivity.HORARI_NOM);
        Log.e(TAG, "onCreate: horari nom="+nomHorari );
        asignatures = (ArrayList<Assignattura>) getIntent().getSerializableExtra(SubjectsActivity.ID_ASIGNSELECT);
        Classe c1=new Classe("Laboratori","Tr1.01","Javier Alonso1","",0, 0, 1,"Catala","");
        Classe c2=new Classe("Laboratori","Tr1.02","Javier Alonso2","",1, 1, 1,"Catala","");
        Classe c3=new Classe("Laboratori","Tr1.03","Javier Alonso3","",2, 2, 1,"Catala","");
        Classe c4=new Classe("Laboratori","Tr1.04","Javier Alonso4","",3, 3, 1,"Catala","");
        Classe c5=new Classe("Laboratori","Tr1.06","Javier Alonso5","",4, 4, 1,"Catala","");
        Classe c6=new Classe("Laboratori","Tr1.07","Javier Alonso6","",0, 5, 2,"Catala","");
        Classe c7=new Classe("Laboratori","Tr1.08","Javier Alonso7","",1, 6, 2,"Catala","");
        Classe c8=new Classe("Laboratori","Tr1.09","Javier Alonso8","",2, 7, 2,"Catala","");
        Classe c9=new Classe("Problemes","Tr1.010","Javier Alonso9","",3, 8, 2,"Catala","");
        Classe c10=new Classe("Problemes","Tr1.11","Javier Alonso10","",4, 9, 2,"Catala","");
        Classe c11=new Classe("Problemes","Tr1.12","Javier Alonso11","",0, 10, 3,"Catala","");
        Classe c12=new Classe("Problemes","Tr1.13","Javier Alonso12","",1, 11, 3,"Catala","");
        Classe c13=new Classe("Teoria","Tr1.14","Javier Alonso13","",2, 12, 3,"Catala","");
        Classe c14=new Classe("Teoria","Tr1.15","Javier Alonso14","",3, 13, 4,"Catala","");
        Classe c15=new Classe("Teoria","Tr1.16","Javier Alonso15","",4, 0, 4,"Catala","");


        asignatures.get(0).addGrupo(new Grups("101", c1));
        asignatures.get(0).addGrupo(new Grups("102", c2));
        asignatures.get(0).addGrupo(new Grups("103", c3));
        asignatures.get(0).addGrupo(new Grups("104", c4));
        asignatures.get(1).addGrupo(new Grups("101", c5));
        asignatures.get(1).addGrupo(new Grups("102", c6));
        asignatures.get(1).addGrupo(new Grups("103", c7));
        asignatures.get(2).addGrupo(new Grups("101", c8));
        asignatures.get(2).addGrupo(new Grups("102", c9));
        asignatures.get(2).addGrupo(new Grups("103", c10));
        asignatures.get(3).addGrupo(new Grups("101", c11));
        asignatures.get(3).addGrupo(new Grups("102", c12));
        asignatures.get(3).addGrupo(new Grups("103", c13));
        asignatures.get(4).addGrupo(new Grups("101", c14));
        asignatures.get(4).addGrupo(new Grups("102", c15));
        asignatures.get(4).addGrupo(new Grups("103", c11));


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
        FileOutputStream fos;
        ObjectOutputStream oos;
        FileInputStream fis;
        ObjectInputStream ois;
        HashSet<String> horarisGuardats;
        String horariName;
        try{
            fis=openFileInput(ChoiceActivity.LLISTA_HORARIS_GUARDATS);
            ois=new ObjectInputStream(fis);
            horarisGuardats=(HashSet) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            horarisGuardats=new HashSet<>();
        }

        try {
            horariName=nomHorari + ".tmp";
            fos = openFileOutput(horariName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(asignatures);
            horarisGuardats.add(horariName);
            Log.v("","HorariGuarda");

            fos=openFileOutput(ChoiceActivity.LLISTA_HORARIS_GUARDATS, Context.MODE_PRIVATE);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(horarisGuardats);
            oos.close();
            Log.v("","llista guardada");
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



