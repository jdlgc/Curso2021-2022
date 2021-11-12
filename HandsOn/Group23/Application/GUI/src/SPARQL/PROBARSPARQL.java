package SPARQL;

import java.util.ArrayList;
public class PROBARSPARQL {
    public static void main (String[] args){
        SPARQL c=new SPARQL();
        ArrayList ole;
        String [] aux ;
        //c.query();
        System.out.println("--Districts--");
        ole=c.Districts();
        for(int i=0;i<ole.size();i++)
        {
            System.out.println(ole.get(i));
        }
        System.out.println("----Neigobordhoods---");
        ole=c.Neighborhoods();
        for(int i=0;i<ole.size();i++)
        {
            System.out.println(ole.get(i));
        }
        System.out.println("----");
        c.Description("Gaztambide");
        System.out.println("----");
        c.CallesBarriosBicimadStation("Gaztambide");
        System.out.println("----");
        c.CallesBarriosBikeStand("Gaztambide");
        System.out.println("----");
        String put=c.SameAs("El Zofío");
        System.out.println(put);
        System.out.println("----");
        c.Foto(put);
        System.out.println("-------------");
        ole=c.BarriosInDistricts("Centro");
        for(int i=0;i<ole.size();i++)
        {
            System.out.println(ole.get(i));
        }
    }
}