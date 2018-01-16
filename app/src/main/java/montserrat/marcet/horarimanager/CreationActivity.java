package montserrat.marcet.horarimanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

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
        Classe c2=new Classe("Laboratori","Tr1.02","Javier Alonso2","",1, 1, 1,"Anglès","");
        Classe c3=new Classe("Laboratori","Tr1.03","Javier Alonso3","",2, 2, 1,"Anglès","");
        Classe c4=new Classe("Laboratori","Tr1.04","Javier Alonso4","",3, 3, 1,"Catala","");
        Classe c5=new Classe("Laboratori","Tr1.06","Javier Alonso5","",4, 4, 1,"Castellà","");
        Classe c6=new Classe("Laboratori","Tr1.07","Javier Alonso6","",0, 5, 2,"Anglès","");
        Classe c7=new Classe("Laboratori","Tr1.08","Javier Alonso7","",1, 6, 2,"Castellà","");
        Classe c8=new Classe("Laboratori","Tr1.09","Javier Alonso8","",2, 7, 2,"Catala","");
        Classe c9=new Classe("Problemes","Tr1.010","Javier Alonso9","",3, 8, 2,"Catala","");
        Classe c10=new Classe("Problemes","Tr1.11","Javier Alonso10","",4, 9, 2,"Anglès","");
        Classe c11=new Classe("Problemes","Tr1.12","Javier Alonso11","",0, 10, 3,"Catala","");
        Classe c12=new Classe("Problemes","Tr1.13","Javier Alonso12","",1, 11, 3,"Castellà","");
        Classe c13=new Classe("Teoria","Tr1.14","Javier Alonso13","",2, 12, 3,"Anglès","");
        Classe c14=new Classe("Teoria","Tr2.15","Javier Alonso14","",3, 13, 4,"Castellà","");
        Classe c15=new Classe("Teoria","Tr2.16","Javier Alonso15","",4, 7, 4,"Anglès","");
        Classe c16=new Classe("Teoria","Tr2.16","Javier Alonso16","",2, 5, 1,"Catala","");
        Classe c17=new Classe("Teoria","Tr0.16","Javier Alonso17","",3, 4, 4,"Catala","");
        Classe c18=new Classe("Teoria","Tr0.16","Javier Alonso18","",1, 2, 1,"Anglès","");
        Classe c19=new Classe("Teoria","Tr0.16","Javier Alonso19","",0, 5, 3,"Castellà","");
        Classe c20=new Classe("Teoria","Tr0.16","Javier Alonso20","",2, 9, 2,"Catala","");
        Classe c21=new Classe("Laboratori","Tr1.01","Javier Alonso21","",0, 0, 1,"Catala","");
        Classe c22=new Classe("Laboratori","Tr1.02","Javier Alonso22","",1, 1, 1,"Anglès","");
        Classe c23=new Classe("Laboratori","Tr1.03","Javier Alonso23","",2, 2, 1,"Anglès","");
        Classe c24=new Classe("Laboratori","Tr1.04","Javier Alonso24","",3, 3, 1,"Catala","");
        Classe c25=new Classe("Laboratori","Tr1.06","Javier Alonso25","",0, 4, 1,"Castellà","");
        Classe c26=new Classe("Laboratori","Tr1.07","Javier Alonso26","",0, 5, 2,"Anglès","");
        Classe c27=new Classe("Laboratori","Tr1.08","Javier Alonso27","",1, 6, 2,"Castellà","");
        Classe c28=new Classe("Laboratori","Tr1.09","Javier Alonso28","",2, 7, 2,"Catala","");
        Classe c29=new Classe("Problemes","Tr1.010","Javier Alonso29","",3, 8, 2,"Catala","");
        Classe c30=new Classe("Problemes","Tr1.11","Javier Alonso30","",4, 9, 2,"Anglès","");
        Classe c31=new Classe("Problemes","Tr1.12","Javier Alonso31","",0, 10, 3,"Catala","");
        Classe c32=new Classe("Problemes","Tr1.13","Javier Alonso32","",0, 11, 3,"Castellà","");
        Classe c33=new Classe("Teoria","Tr1.14","Javier Alonso33","",2, 12, 3,"Anglès","");
        Classe c34=new Classe("Teoria","Tr2.15","Javier Alonso34","",0, 13, 4,"Castellà","");
        Classe c35=new Classe("Teoria","Tr2.16","Javier Alonso35","",2, 7, 4,"Anglès","");
        Classe c36=new Classe("Teoria","Tr2.16","Javier Alonso36","",3, 5, 1,"Catala","");
        Classe c37=new Classe("Teoria","Tr0.16","Javier Alonso37","",3, 4, 4,"Catala","");
        Classe c38=new Classe("Teoria","Tr0.16","Javier Alonso38","",1, 2, 1,"Anglès","");
        Classe c39=new Classe("Teoria","Tr0.16","Javier Alonso39","",3, 5, 3,"Castellà","");
        Classe c40=new Classe("Teoria","Tr0.16","Javier Alonso40","",1, 9, 2,"Catala","");


        asignatures.get(0).addGrupo(new Grups("101", c1));
        asignatures.get(0).addGrupo(new Grups("102", c2));
        asignatures.get(0).addGrupo(new Grups("103", c3));
        asignatures.get(0).addGrupo(new Grups("104", c4));

        asignatures.get(1).addGrupo(new Grups("101", c5));
        asignatures.get(1).addGrupo(new Grups("102", c6));
        asignatures.get(1).addGrupo(new Grups("103", c7));
        asignatures.get(1).addGrupo(new Grups("104", c8));

        asignatures.get(2).addGrupo(new Grups("101", c9));
        asignatures.get(2).addGrupo(new Grups("102", c10));
        asignatures.get(2).addGrupo(new Grups("103", c11));
        asignatures.get(2).addGrupo(new Grups("104", c12));

        asignatures.get(3).addGrupo(new Grups("101", c13));
        asignatures.get(3).addGrupo(new Grups("102", c14));
        asignatures.get(3).addGrupo(new Grups("103", c15));
        asignatures.get(3).addGrupo(new Grups("104", c16));

        asignatures.get(4).addGrupo(new Grups("101", c17));
        asignatures.get(4).addGrupo(new Grups("102", c18));
        asignatures.get(4).addGrupo(new Grups("103", c19));
        asignatures.get(4).addGrupo(new Grups("104", c20));

        /*asignatures.get(5).addGrupo(new Grups("101", c21));
        asignatures.get(5).addGrupo(new Grups("102", c22));
        asignatures.get(5).addGrupo(new Grups("103", c23));
        asignatures.get(5).addGrupo(new Grups("104", c24));

        asignatures.get(6).addGrupo(new Grups("101", c25));
        asignatures.get(6).addGrupo(new Grups("102", c26));
        asignatures.get(6).addGrupo(new Grups("103", c27));
        asignatures.get(6).addGrupo(new Grups("104", c28));

        asignatures.get(7).addGrupo(new Grups("101", c29));
        asignatures.get(7).addGrupo(new Grups("102", c30));
        asignatures.get(7).addGrupo(new Grups("103", c31));
        asignatures.get(7).addGrupo(new Grups("104", c32));

        asignatures.get(8).addGrupo(new Grups("101", c33));
        asignatures.get(8).addGrupo(new Grups("102", c34));
        asignatures.get(8).addGrupo(new Grups("103", c35));
        asignatures.get(8).addGrupo(new Grups("104", c36));

        asignatures.get(9).addGrupo(new Grups("101", c37));
        asignatures.get(9).addGrupo(new Grups("102", c38));
        asignatures.get(9).addGrupo(new Grups("103", c39));
        asignatures.get(9).addGrupo(new Grups("104", c40));*/


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



