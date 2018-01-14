package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    private Button mLogOutBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> horariNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        ListView HorariList=(ListView)findViewById(R.id.HorariList);
        horariNameList=new ArrayList<String>();
        recuperarhoraris(horariNameList);
        if(horariNameList.isEmpty()){
            TextView txt_carrega=(TextView) findViewById(R.id.txt_carrega_horari) ;
            txt_carrega.setText("No hi ha cap horari guardat");
        }

        mLogOutBtn = (Button) findViewById(R.id.logout);
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // perque ens faci el signout del firebase
                startActivity(new Intent(ChoiceActivity.this, LoginActivity.class));
            }
        });

        Button btn_crear_horari = (Button) findViewById(R.id.btn_crear_horari);
        btn_crear_horari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoiceActivity.this, SubjectsActivity.class));
            }
        });

    }

    private void recuperarhoraris(ArrayList<String> horariNameList) {
        //TODO aqui va el codi per recuperar els horaris guardats en el movil o en el server
    }
}
