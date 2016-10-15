package controllers;

import aws.SQSConnection;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import scala.util.parsing.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 06/10/2016.
 */
public class HireFireController extends Controller {


    public Result recibir(String hirefire) {

        System.out.println(hirefire);

        SQSConnection sqs = new SQSConnection();

        String cola = sqs.queueSize();

        RespuestaJSON rta = new RespuestaJSON("web", cola);

        List<RespuestaJSON> lista = new ArrayList<>();

        lista.add(rta);

        JsonNode json = Json.toJson(lista);

        return ok(json);

    }

    public class RespuestaJSON{
        public String name;
        public String quantity;

        public RespuestaJSON(String pName, String pQuantity){
            name = pName;
            quantity = pQuantity;
        }
    }
}
