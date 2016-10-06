package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.cj.x.json.JsonArray;
import models.Competencia;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 18/08/2016.
 */
public class CompetenciaController extends Controller {

    /**
     * Recibe un JSON y crea las competencias de acuerdo al contenido de este
     * @return Result, indicando si se logró agregar correctamente las competencias
     */
    public Result crear() {
        JsonNode nCompetencia = request().body().asJson();

        if(nCompetencia.isArray()){
            List<Competencia> competencias = new ArrayList<>();
            for(JsonNode nNode : nCompetencia){
                Competencia competencia = Json.fromJson( nNode , Competencia.class ) ;
                Competencia comp = new Competencia(competencia.nombre);
                comp.save();
                competencias.add(comp);
            }
            return ok(Json.toJson(competencias));
        }
        else {
            Competencia competencia = Json.fromJson(nCompetencia, Competencia.class);
            Competencia comp = new Competencia(competencia.nombre);
            comp.save();
            return ok(Json.toJson(comp));
        }

    }

    /**
     * Retorna todas las competencias que se encuentran registradas en el sistema.
     * @return
     */
    public Result darCompetencias(){
        List<Competencia> competencias = Competencia.FINDER.all();
        return ok(Json.toJson(competencias));
    }

    public Result mostrar(long id) {
        Competencia competencia = Competencia.FINDER.byId(id);
        return ok(Json.toJson(competencia));
    }
}
