package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectsActivity extends AppCompatActivity {

    public static final int MAX_GRAUS = 6;
    public static final int MAX_QUATRIS = 8;
    Spinner sp_graus;
    String [] graus;
    Map<String,String[]> rawData;
    List<String> quatrimestres;
    HashMap<String,List<String>> assignatures;
    ExpandableListView subjectList;
    MyExpandableListAdapter subjectadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        sp_graus = (Spinner) findViewById(R.id.sp_graus);
        graus = getResources().getStringArray(R.array.llista_graus);
        rawData=new HashMap<>();
        initData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_spinner_item,graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quatrimestres=Arrays.asList(getResources().getStringArray(R.array.llista_quatrimestres));
        assignatures=new HashMap<>();
        sp_graus.setAdapter(adapter);
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


        omplirData(-1);
        subjectList=(ExpandableListView)findViewById(R.id.subject_list);
        subjectadapter = new MyExpandableListAdapter(this,quatrimestres,assignatures);
        subjectList.setAdapter(subjectadapter);

        final EditText mEdit = (EditText)findViewById(R.id.id_anomena_horai);

        Button btn_validate = (Button) findViewById(R.id.btn_validate);
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String algo_escrit = mEdit.getText().toString();
                if (algo_escrit.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.falta_nom, Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(SubjectsActivity.this, CreationActivity.class));
                }

            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initData() {
       //TODO hacer que esto funcione siempre aunque cambien las carreras

        rawData.put(graus[0],getResources().getStringArray(R.array.electronica));
        rawData.put(graus[1],getResources().getStringArray(R.array.electrica));
        rawData.put(graus[2],getResources().getStringArray(R.array.mecanica));
        rawData.put(graus[3],getResources().getStringArray(R.array.quimica));
        rawData.put(graus[4],getResources().getStringArray(R.array.textil));
        rawData.put(graus[5],getResources().getStringArray(R.array.disseny));

    }

    private void omplirData(int grau) {


        if(grau==-1){

            for(String s:quatrimestres) {
                List sis=new ArrayList<String>();
                sis.add(s);
                assignatures.put(s,sis);
            }
        }
        else{
            String [] asi=rawData.get(graus[grau]);
            int i=0;
            for(String s:quatrimestres) {
                List sis=Arrays.asList(asi[i].split(";"));
                assignatures.put(s,sis);
                i++;
            }
            subjectadapter.notifyDataSetChanged();
        }


    }

}
