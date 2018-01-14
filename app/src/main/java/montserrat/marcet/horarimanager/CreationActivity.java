package montserrat.marcet.horarimanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class CreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        ArrayList<Assignatura> asigns = (ArrayList<Assignatura>) getIntent().getSerializableExtra(SubjectsActivity.ID_ASIGNSELECT);


        for(Assignatura a:asigns){
           EditText edi=(EditText)findViewById(R.id.editText);
           edi.append(a.getName()+"\n");
        }



    }
}
