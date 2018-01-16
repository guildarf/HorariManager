package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectsActivity extends AppCompatActivity {

    public static final String ID_ASIGNSELECT = "hola";
    public static final String HORARI_NOM = "horari_nom";
    private static final String TAG = "Subjects activity";
    PlaDocent plaDocent;
    Spinner sp_graus;
    String [] graus;
    List<String> quatrimestres;
    HashMap<String,List<AssignaturaCheckbox>> assignatures;//Mapa d'assignatures per quatrimestres es sobreescriu cada cop que es canvia de grau
    ExpandableListView subjectList;
    MyExpandableListAdapter subjectadapter;
    List<Assignattura> asignSelec;
    int numSelect=0,position;
    Button neteja;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        initData();

        setSpinnerListener();

        omplirData(-1);

        setValidaListener();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setValidaListener() {

        final EditText mEdit = (EditText)findViewById(R.id.id_anomena_horai);

        Button btn_validate = (Button) findViewById(R.id.btn_validate);
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: nom horari"+mEdit.getText());
                if (mEdit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.falta_nom, Toast.LENGTH_SHORT).show();
                } else if (numSelect<5){
                    Toast.makeText(getApplicationContext(),R.string.minim_5, Toast.LENGTH_SHORT).show();}
                  else if (numSelect>10){
                    Toast.makeText(getApplicationContext(),R.string.max_10, Toast.LENGTH_SHORT).show();}
                        else {
                        Intent i=new Intent(SubjectsActivity.this, CreationActivity.class);
                        i.putExtra(ID_ASIGNSELECT, (Serializable) asignSelec);
                        i.putExtra(HORARI_NOM,mEdit.getText().toString());
                        startActivity(i);
                        }

            }
        });

    }

    private void setSpinnerListener() {

        sp_graus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) Toast.makeText(getApplicationContext(),getString(R.string.assig_triada)+" "+ graus[position], Toast.LENGTH_SHORT).show();
                else  Toast.makeText(getApplicationContext(),getString(R.string.escollir_grau_abans), Toast.LENGTH_SHORT).show();
                omplirData(position-1);
                subjectadapter.notifyDataSetChanged();//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initData() {
        asignSelec=new ArrayList<>();

        neteja=(Button)findViewById(R.id.btn_cleaner);

        plaDocent=new PlaDocent(getResources());

        graus = plaDocent.getGraus();

        sp_graus = (Spinner) findViewById(R.id.sp_graus);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_graus.setAdapter(adapter);


        quatrimestres=Arrays.asList(plaDocent.getQuatris());

        assignatures=new HashMap<>();

        subjectList=(ExpandableListView)findViewById(R.id.subject_list);
        subjectadapter = new MyExpandableListAdapter(this,quatrimestres,assignatures);
        subjectList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long l) {
                AssignaturaCheckbox selected=((AssignaturaCheckbox)subjectadapter.getChild(groupPos,childPos));
                selected.toggleChecked();
                subjectadapter.notifyDataSetChanged();
                if(asignSelec.contains(selected)){
                    numSelect--;
                    asignSelec.remove(selected);
                    subjectadapter.notifyDataSetChanged();//
                }else{
                    numSelect++;
                    asignSelec.add(selected);
                    subjectadapter.notifyDataSetChanged();//
                }
                if(numSelect!=0){
                    neteja.setText(String.format(getString(R.string.btn_cleaner) +" ("+numSelect+")"));
                    subjectadapter.notifyDataSetChanged();//
                }else{
                    neteja.setText(R.string.btn_cleaner);
                }
                return false;
            }
        });
        subjectList.setAdapter(subjectadapter);


    }

    private void omplirData(int grau) {


        if(grau==-1) {
            for(String q:quatrimestres) {
                assignatures.put(q, new ArrayList<AssignaturaCheckbox>());
            }
        }
        else{
            Map<String,List<Assignattura>> m=plaDocent.getAssignaturesGrau(graus[grau]);

            for(String s:quatrimestres){
                List<Assignattura> l=m.get(s);
                List ll=new ArrayList();
                for(Assignattura a:l){
                    ll.add(new AssignaturaCheckbox(a));
                }
                assignatures.put(s,ll);
            }
        }


        subjectadapter.notifyDataSetChanged();

        for (int i=0;i<quatrimestres.size();i++){

            if(subjectList.isGroupExpanded(i))subjectList.collapseGroup(i);
        }


    }

    public void onClickNeteja(View view) {

        numSelect=0;
        for(Assignattura a:asignSelec){
            ((AssignaturaCheckbox)a).setChecked(false);
        }
        subjectadapter.notifyDataSetChanged();
        asignSelec.clear();
        neteja.setText("Neteja");

    }
}

class AssignaturaCheckbox extends Assignattura{
    boolean isChecked;

    public AssignaturaCheckbox(String codi, String nom, String idioma) {
        super(codi, nom, idioma);
        this.isChecked = false;
    }

    public AssignaturaCheckbox(Assignattura a) {
        super(a.getCodi(), a.getNom(), a.getIdioma());
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void toggleChecked() {
        isChecked=!isChecked;
    }
}