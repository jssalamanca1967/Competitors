package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Competencia;
import models.Competidor;
import models.Inscripcion;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.data.Form;

import java.io.File;
import java.util.List;


/**
 * Created by John on 18/08/2016.
 */
public class CompetidorController extends Controller {


    public Result verInscripciones(long id) {

        Competidor competidor = Competidor.FINDER.byId(id);
        return ok(views.html.competidor.verInscripciones.render(competidor));
    }

    public Result verInscripcion(long id, Long idIns) {
        Competidor competidor = Competidor.FINDER.byId(id);
        Inscripcion inscripcion = Inscripcion.FINDER.byId(idIns);
        return ok(views.html.competidor.verInscripcion.render(competidor, inscripcion));
    }

    public Result crear() {
        Form<Competidor> formulario = Form.form(Competidor.class);
        return ok(views.html.competidor.newCompetidor.render(formulario));
    }

    public Result guardarNuevo() {
        Form<Competidor> formulario = Form.form(Competidor.class).bindFromRequest();
        Competidor competidor = formulario.get();
        competidor.save();
        Long id = competidor.id;
        return redirect(routes.CompetidorController.mostrar(id));
    }

    public Result darCompetidores() {
        List<Competidor> lista = Competidor.FINDER.all();
        return ok(views.html.competidor.darCompetidores.render(lista));
    }

    public Result verCompetencias(long id) {
        Competidor competidor = Competidor.FINDER.byId(id);
        List<Competencia> competencias = Competencia.FINDER.all();
        return ok(views.html.competidor.varCompetencias.render(competidor,competencias));
    }

    public Result guardarInscripcion(long id, long idComp) {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");
        JsonNode json = request().body().asJson();

        String nombre = json.get("nombre").asText();
        String descripcion = json.get("descripcion").asText();

        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest();
        }

    }

    public Result crearInscripcion(long id, long idComp) {
        return play.mvc.Results.TODO;
    }

    public Result verCompetencia(long id, long idComp) {
        return play.mvc.Results.TODO;
    }

    public Result mostrar(long id) {
        Competidor competidor = Competidor.FINDER.byId(id);
        return ok(views.html.competidor.verCompetidor.render(competidor));
    }
}
