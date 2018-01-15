package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {

    public static final String ID_CARGAR = "uouou";
    private Button mLogOutBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> horariNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        ListView horariList=(ListView)findViewById(R.id.HorariList);
        horariNameList=new ArrayList<String>();
        horariNameList=recuperarhoraris();
        if(horariNameList.isEmpty()){
            TextView txt_carrega=(TextView) findViewById(R.id.txt_carrega_horari) ;
            txt_carrega.setText("No hi ha cap horari guardat");
        }else{
            ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,horariNameList);
            horariList.setAdapter(adapter);
        }
        horariList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(ChoiceActivity.this,ViewActivity.class);
                 in.putExtra(ViewActivity.ID_ASIGNATURES,cargarDatos(horariNameList.get(i)));
                 startActivity(in);
            }
        });

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

    private ArrayList<Assignattura> cargarDatos(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois;
        ArrayList<Assignattura> a=null;
        try {
            fis = openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            a=(ArrayList<Assignattura>)ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return a;

    }

    private ArrayList<String> recuperarhoraris() {
        //TODO aqui va el codi per recuperar els horaris guardats en el movil o en el server
        ArrayList<String> lS=new ArrayList<>();
        lS.add("guillem.tmp");
        return lS;
    }

}
