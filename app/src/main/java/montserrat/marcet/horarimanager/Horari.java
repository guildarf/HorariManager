package montserrat.marcet.horarimanager;

/**
 * Created by sakum on 14/01/2018.
 */


class Horari {
    public final static int DILLUNS=0;
    public final static int DIMARTS=1;
    public final static int DIMECRES=2;
    public final static int DIJOUS=3;
    public final static int DIVENDRES=4;

    public final static int FROM8_9=0;
    public final static int FROM9_10=1;
    public final static int FROM10_11=2;
    public final static int FROM11_12=3;
    public final static int FROM12_13=4;
    public final static int FROM13_14=5;
    public final static int FROM14_15=6;
    public final static int FROM15_16=7;
    public final static int FROM16_17=8;
    public final static int FROM17_18=9;
    public final static int FROM18_19=10;
    public final static int FROM19_20=11;
    public final static int FROM20_21=12;

    String grup;
    boolean[][] horari;

    public Horari(String grup,boolean[][] horari) {
        this.grup=grup;
        this.horari = horari;
    }

    public Horari(String grup, int dia,int[] hores) {
        this.grup=grup;
        horari=new boolean[DIVENDRES+1][FROM20_21+1];
        for(int i=0;i<hores.length;i++){
            horari[dia][hores[i]]=true;
        }
    }

    public String getGrup() {
        return grup;
    }
}
