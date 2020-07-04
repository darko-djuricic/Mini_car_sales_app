package main;

import com.google.gson.Gson;
import model.Automobili;
import model.Data;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import static spark.Spark.*;

public class Launcher {
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(5000);
        final String path= "automobili.json";


        get("/",(request, response) -> {
            HashMap<String,Object> polja=new HashMap<>();
            polja.put("automobili", Data.readFromJson(path));
            return new ModelAndView(polja,"index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/dodajAutomobil",(request, response) -> {
            HashMap<String,Object> polja=new HashMap<>();
            polja.put("automobili", Data.readFromJson(path));
            return new ModelAndView(polja,"dodajAutomobil.hbs");
        },new HandlebarsTemplateEngine());

        path("/api",()->{
            post("/dodajAutomobile",(request, response) -> {
                HashMap<String,Object> polja=new HashMap<>();
                Gson gson = new Gson();
                try {
                    String marka=request.queryParams("marka");
                    String model=request.queryParams("model");
                    int godiste=Integer.parseInt(request.queryParams("godiste"));
                    int kubikaza=Integer.parseInt(request.queryParams("kubikaza"));
                    String karoserija=request.queryParams("karoserija");
                    String[] gor=request.queryParams("gorivo").split("%");
                    ArrayList<String> gorivo=new ArrayList<>();

                    if(gor.length<2)
                        gorivo.add(gor[0]);
                    else
                        for (String s:gor) {
                            gorivo.add(s);
                        }

                    Automobili noviAuto = new Automobili(marka,model,godiste,kubikaza,karoserija,gorivo);
                    ArrayList<Automobili> automobilis = Data.readFromJson(path);
                    automobilis.add(noviAuto);
                    Data.writeToJSON(automobilis, path);;
                    return "Uspesno ste dodali nov automobil";
                }
                catch (Exception e){
                    return "Neuspesno dodavanje automobila";

                }
            });
            post("/izmeniAutomobil",(request, response) -> {
                HashMap<String,Object> polja=new HashMap<>();
                Gson gson = new Gson();
                try {
                    String marka = request.queryParams("marka");
                    String model = request.queryParams("model");
                    int godiste = Integer.parseInt(request.queryParams("godiste"));
                    int kubikaza = Integer.parseInt(request.queryParams("kubikaza"));
                    String karoserija = request.queryParams("karoserija");
                    String gor=request.queryParams("gorivo");
                    ArrayList<String> gorivo=new ArrayList<>();

                    if(gor.trim().toLowerCase().equals("benzin+gas(tng)")){
                        gorivo.add("Benzin");
                        gorivo.add("Gas(TNG)");
                    }
                    else
                        gorivo.add(gor);

                    int id=Integer.parseInt(request.queryParams("id"));

                    Automobili pronadjen=null;
                    ArrayList<Automobili> automobilis=Data.readFromJson(path);

                    for (Automobili a:automobilis) {
                        if(a.getId()==id){
                            pronadjen=a;
                            break;
                        }
                    }

                    Automobili izmenjen=new Automobili(marka, model, godiste, kubikaza, karoserija, gorivo);
                    izmenjen.setId(id);
                    automobilis.set(automobilis.indexOf(pronadjen),izmenjen);
                    Data.writeToJSON(automobilis,path);
                    return "Uspesno ste izmenili automobil";

                }catch (Exception e){
                    e.printStackTrace();
                    return "Podaci nisu validni";
                }
            });
        });

        get("/izmeni",(request, response) -> {
            HashMap<String,Object> polja=new HashMap<>();
            int id=Integer.parseInt(request.queryParams("id"));
            ArrayList<Automobili> automobilis=Data.readFromJson(path);
            Automobili pronadjen=null;

            for (Automobili a:automobilis) {
                if(a.getId()==id){
                    pronadjen=a;
                    break;
                }
            }
            if(pronadjen.getGorivo().size()>1){
                ArrayList<String> gorivo=new ArrayList<>();
                gorivo.add(pronadjen.getGorivo().get(0)+"+"+pronadjen.getGorivo().get(1));
                pronadjen.setGorivo(gorivo);
            }

            polja.put("automobil",pronadjen);
            return new ModelAndView(polja,"izmeni.hbs");
        },new HandlebarsTemplateEngine());
    }
}
