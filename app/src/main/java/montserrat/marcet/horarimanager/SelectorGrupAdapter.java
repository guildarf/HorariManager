package montserrat.marcet.horarimanager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sakum on 14/01/2018.
 */

public class SelectorGrupAdapter extends ArrayAdapter<Assignatura> {

    public SelectorGrupAdapter(@NonNull Context context, int resource, @NonNull List<Assignatura> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result=convertView;
        if(result==null){
            LayoutInflater inflater= LayoutInflater.from(getContext());
            result=inflater.inflate(R.layout.list_item_activity_creation,null);
        }
        TextView text=(TextView) result.findViewById(R.id.subject);
        RadioGroup rg= (RadioGroup) result.findViewById(R.id.RG_sbjt1);
        Assignatura assignatura=getItem(position);

        text.setText(assignatura.getName());

        for (int i=0;i<rg.getChildCount();i++){
            RadioButton rb=(RadioButton)rg.getChildAt(i);
            if(i<assignatura.getHorarisCount()){
                rb.setText(assignatura.getHorari(i).getGrup());
            } else if(assignatura.getHorarisCount()==-1&&i==0) {
                rb.setText("Horari unic");
                rb.setChecked(true);
            }
            else{
                    rb.setVisibility(View.INVISIBLE);
            }

        }
        rg.check(getItem(position).getSelGroup());

        return result;
    }
}
