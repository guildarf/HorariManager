package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SubjectsActivity extends AppCompatActivity {

    public static final int MAX_GRAUS = 6;
    public static final int MAX_QUATRIS = 8;
    Spinner sp_graus;
    String [] graus;
    String [] quatrimestres;
    String [][][] assignatures; //primera dimensio grau segona dimensio quatrimestres tercera dimensio assignatura;
    ExpandableListView subjectList;
    MyExpandableListAdapter subjectadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        sp_graus = (Spinner) findViewById(R.id.sp_graus);
        graus = getResources().getStringArray(R.array.llista_graus);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_spinner_item,graus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_graus.setAdapter(adapter);
        sp_graus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=0) Toast.makeText(getApplicationContext(),"Has triat "+graus[position], Toast.LENGTH_SHORT).show();
                    actualitzar_adapter(position-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String [][] asignaturestemp={
                getResources().getStringArray(R.array.electronica),
                getResources().getStringArray(R.array.electrica),
                getResources().getStringArray(R.array.mecanica),
                getResources().getStringArray(R.array.quimica),
                getResources().getStringArray(R.array.textil),
                getResources().getStringArray(R.array.disseny)
        };
        assignatures=new String[MAX_GRAUS][MAX_QUATRIS][];
        for (int i=0;i<MAX_GRAUS;i++){ //for per assignatura
            for (int j=0;j<MAX_QUATRIS;j++){ //for per quatri
                assignatures[i][j]=asignaturestemp[i][j].split(";");
            }
        }

        quatrimestres=getResources().getStringArray(R.array.llista_quatrimestres);




        subjectList=(ExpandableListView)findViewById(R.id.subject_list);
        subjectadapter=new MyExpandableListAdapter(quatrimestres, new String[MAX_QUATRIS][1],this.getBaseContext());
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

    private void actualitzar_adapter(int grau) {
        if(grau<0||grau>MAX_GRAUS){
            subjectadapter.changeChildren(new String[MAX_QUATRIS][1]);
        }
        else {
            subjectadapter.changeChildren(assignatures[grau]);
        }
        for(int i=0;i<MAX_QUATRIS;i++){
            subjectList.collapseGroup(i);
        }
    }
}
