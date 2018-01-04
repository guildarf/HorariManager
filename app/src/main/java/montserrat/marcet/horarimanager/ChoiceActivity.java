package montserrat.marcet.horarimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ChoiceActivity extends AppCompatActivity {

    private Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

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
}
