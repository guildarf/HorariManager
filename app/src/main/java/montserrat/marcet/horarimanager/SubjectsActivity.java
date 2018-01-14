package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectsActivity extends AppCompatActivity {

    PlaDocent plaDocent;
    Spinner sp_graus;
    String [] graus;
    List<String> quatrimestres;
    HashMap<String,List<Assignatura>> assignatures;//Mapa d'assignatures per quatrimestres es sobreescriu cada cop que es canvia de grau
    Map<String,String[]> rawData;
    ExpandableListView subjectList;
    MyExpandableListAdapter subjectadapter;


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
                if (mEdit.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.falta_nom, Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(SubjectsActivity.this, CreationActivity.class));
                }

            }
        });

    }

    private void setSpinnerListener() {

        sp_graus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) Toast.makeText(getApplicationContext(),"Has triat "+graus[position], Toast.LENGTH_SHORT).show();
                omplirData(position-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initData() {

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

                ((Assignatura)subjectadapter.getChild(groupPos,childPos)).toggleChecked();
                subjectadapter.notifyDataSetChanged();

                return false;
            }
        });
        subjectList.setAdapter(subjectadapter);


    }

    private void omplirData(int grau) {


        if(grau==-1) {
            for(String q:quatrimestres){
                assignatures.put(q,new ArrayList<Assignatura>());
            }
            return;
        }

        assignatures.putAll(plaDocent.getAssignaturesGrau(graus[grau]));


        subjectadapter.notifyDataSetChanged();

        for (int i=0;i<quatrimestres.size();i++){

            if(subjectList.isGroupExpanded(i))subjectList.collapseGroup(i);
        }


    }

}
