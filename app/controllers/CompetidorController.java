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

        Competidor competidor = Competidor.FINDER.byId(id);
        Competencia competencia = Competencia.FINDER.byId(idComp);

        Form<Inscripcion> form = Form.form(Inscripcion.class).bindFromRequest();
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("imagen");

        JsonNode json = request().body().asJson();

        String nombre = form.field("nombre").value();
        String descripcion = form.field("descripcion").value();
        String fileName = "";
        String contentType = "";
        File file = null;

        if (picture != null) {
            fileName = picture.getFilename();
            contentType = picture.getContentType();
            file = picture.getFile();

        } else {
            form.reject("imagen", "Por favor, adjunte una imagen");
        }

        if(nombre.isEmpty()){
            form.reject("nombre", "Por favor, escriba un nombre válido");
        }

        if(descripcion.isEmpty()){
            form.reject("descripcion", "Por favor, escriba una descripcion válida");
        }

        if(form.hasErrors()){
            return badRequest(views.html.inscripcion.crearInscipcion.render(competidor, competencia, form));
        }
        else{

            S3Connection conexion = S3Connection.getInstance();
            if(fileName.endsWith(".jpg")){
                fileName = competidor.nombre.trim() + "-" + competencia.nombre.replace(" ", "-") + "-" + competencia.inscripciones.size() + ".jpg";
            }
            else if(fileName.endsWith(".png")){
                fileName = competidor.nombre.trim() + "-" + competencia.nombre.replace(" ", "-") + "-" + competencia.inscripciones.size() + ".png";
            }

            System.out.println(fileName);

            conexion.uploadFile(file, fileName);
            Inscripcion inscripcion = new Inscripcion(nombre, descripcion, competencia, fileName, conexion.s3Bucket, competidor);
            competidor.inscripciones.add(inscripcion);
            inscripcion.save();
            competidor.save();

            return redirect(routes.CompetidorController.verInscripciones(id));

        }

    }

    public Result crearInscripcion(long id, long idComp) {
        Competidor competidor = Competidor.FINDER.byId(id);
        Competencia competencia = Competencia.FINDER.byId(idComp);
        Form<Inscripcion> form = Form.form(Inscripcion.class);
        return ok(views.html.inscripcion.crearInscipcion.render(competidor, competencia, form));
    }

    public Result verCompetencia(long id, long idComp) {
        return play.mvc.Results.TODO;
    }

    public Result mostrar(long id) {
        Competidor competidor = Competidor.FINDER.byId(id);
        return ok(views.html.competidor.verCompetidor.render(competidor));
    }
}
