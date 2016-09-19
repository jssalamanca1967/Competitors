package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Competencia;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by John on 18/08/2016.
 */
public class CompetenciaController extends Controller {

    public Result crear() {
        JsonNode nProduct = request().body().asJson();
        Competencia competencia = Json.fromJson( nProduct , Competencia.class ) ;
        Competencia comp = new Competencia(competencia.nombre);
        comp.save();
        return ok(Json.toJson(comp));
    }

    public Result darCompetencias(){
        List<Competencia> competencias = Competencia.FINDER.all();
        return ok(Json.toJson(competencias));
    }

    public Result mostrar(long id) {
        Competencia competencia = Competencia.FINDER.byId(id);
        return ok(Json.toJson(competencia));
    }
}
