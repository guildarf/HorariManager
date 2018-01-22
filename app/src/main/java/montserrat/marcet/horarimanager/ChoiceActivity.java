package montserrat.marcet.horarimanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class ChoiceActivity extends AppCompatActivity {

    public static final String ID_CARGAR = "uouou";
    public static final String LLISTA_HORARIS_GUARDATS = "Horaris guardats.tmp";
    private Button mLogOutBtn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> horariNameList;
    TextView txt_carrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        txt_carrega = (TextView) findViewById(R.id.txt_carrega_horari);
        ListView horariList=(ListView)findViewById(R.id.HorariList);
        horariNameList=new ArrayList<>(recuperarhoraris());
        if(horariNameList.isEmpty()){
            txt_carrega.setText(R.string.NoHorarisSeleccionats);
        }else{
            adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,horariNameList);
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

        horariList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder= new AlertDialog.Builder(ChoiceActivity.this);
                builder.setTitle(R.string.confirm);
                String msg=getString(R.string.confirmDeleteMessage);
                builder.setMessage( String.format(msg,horariNameList.get(pos)));

                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference base_dades_firebase = database.getReference(Constants.FIREBASE_CHILD_HORARIS); //ens conectem a la base de dades
                            base_dades_firebase.child(horariNameList.get(pos)).removeValue();


                        File dir = getFilesDir();
                        File file = new File(dir, horariNameList.get(pos));
                        boolean deleted = file.delete();

                        horariNameList.remove(pos);
                        adapter.notifyDataSetChanged();

                        guardarHorarisName();
                        if(horariNameList.isEmpty()){
                            txt_carrega.setText(R.string.NoHorarisSeleccionats);
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel,null);
                builder.create().show();

                return true;
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




    private void guardarHorarisName() {

        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos=openFileOutput(ChoiceActivity.LLISTA_HORARIS_GUARDATS, Context.MODE_PRIVATE);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(new HashSet<String>(horariNameList));
            oos.close();
            Log.v("","llista guardada");

        } catch (FileNotFoundException e) {
            Log.v("eeeeeee",e.getMessage());
        } catch (IOException e) {
            Log.v("eeeeeee2",e.getMessage());
        }




//        // Read from the database
//        base_dades_firebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });




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



    private HashSet<String> recuperarhoraris() {
        FileInputStream fis;
        ObjectInputStream ois;
        HashSet<String> horarisGuardats;
        try{
            fis=openFileInput(ChoiceActivity.LLISTA_HORARIS_GUARDATS);
            ois=new ObjectInputStream(fis);
            horarisGuardats=(HashSet) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            horarisGuardats=new HashSet<>();
        }
        return horarisGuardats;
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder= new AlertDialog.Builder(ChoiceActivity.this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirmarExit);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit me", true);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create();
        builder.show();

    }

}

