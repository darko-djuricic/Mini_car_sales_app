package model;

import java.util.ArrayList;

public class Automobili {
    static int ID=0;

    private int id;
    private String marka;
    private String model;
    private int godiste;
    private int kubikaza;
    public String karoserija;
    public ArrayList<String> gorivo;

    public Automobili(String marka, String model, int godiste, int kubikaza, String karoserija, ArrayList<String> gorivo) {
        this.marka = marka;
        this.model = model;
        this.godiste = godiste;
        this.kubikaza = kubikaza;
        this.karoserija = karoserija;
        this.gorivo = gorivo;
        id=dodelaID();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int dodelaID() {
        ArrayList<Automobili> automobilis=Data.readFromJson("automobili.json");
        boolean provera=true;

        for (Automobili a:automobilis) {
            if(ID==a.id){
                provera=false;
                break;
            }
        }

        if(!provera){
            ID++;
            System.out.println("UDJOHHHHH");
            return dodelaID();
        }

        return ID;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public int getKubikaza() {
        return kubikaza;
    }

    public void setKubikaza(int kubikaza) {
        this.kubikaza = kubikaza;
    }

    public String getKaroserija() {
        return karoserija;
    }

    public void setKaroserija(String karoserija) {
        this.karoserija = karoserija;
    }

    public ArrayList<String> getGorivo() {
        return gorivo;
    }

    public void setGorivo(ArrayList<String> gorivo) {
        this.gorivo = gorivo;
    }

    @Override
    public String toString() {
        return "Automobili{" +
                "marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", godiste=" + godiste +
                ", kubikaza=" + kubikaza +
                ", karoserija='" + karoserija + '\'' +
                ", gorivo=" + gorivo +
                '}';
    }
}
