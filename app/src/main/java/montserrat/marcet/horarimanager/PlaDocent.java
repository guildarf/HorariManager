package montserrat.marcet.horarimanager;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by sakum on 14/01/2018.
 */

class PlaDocent {
    String[] graus;
    String[] quatris;
    Map<String,Map<String,List<Assignatura>>> plaDocent; //primer string grau, segon string quatri

    public PlaDocent(Resources resources) {
        graus=resources.getStringArray(R.array.llista_graus);
        quatris= resources.getStringArray(R.array.llista_quatrimestres);
        plaDocent=new HashMap<>();
        //TODO que funcione aunque cambie el orden de los grados
        //TODO que recoja las asignaturas completas(con su horario y subgrupos) para pasarlo a la next activity
        plaDocent.put(graus[0],grau2Map(resources.getStringArray(R.array.electronica)));
        plaDocent.put(graus[1],grau2Map(resources.getStringArray(R.array.electrica)));
        plaDocent.put(graus[2],grau2Map(resources.getStringArray(R.array.mecanica)));
        plaDocent.put(graus[3],grau2Map(resources.getStringArray(R.array.quimica)));
        plaDocent.put(graus[4],grau2Map(resources.getStringArray(R.array.textil)));
        plaDocent.put(graus[5],grau2Map(resources.getStringArray(R.array.disseny)));




    }

    private Map<String,List<Assignatura>> grau2Map(String[] stringArray) {
        HashMap map=new HashMap();
        for (int i=0;i<quatris.length;i++) {
            map.put(quatris[i],quatri2List(stringArray[i]));
        }

        return map;

    }

    private List<Assignatura> quatri2List(String sSplit) {
        List asignaturas=new ArrayList();

        for(String s:sSplit.split(";")){
            asignaturas.add(new Assignatura(s));
        }

        return asignaturas;


    }

    public String[] getGraus() {
        return graus;
    }

    public String[] getQuatris() {
        return quatris;
    }

    public Map<String, Map<String, List<Assignatura>>> getPlaDocent() {
        return plaDocent;
    }

    public Map<String,List<Assignatura>> getAssignaturesGrau(String grau) {

        return plaDocent.get(grau);

    }
}
