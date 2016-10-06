package worker;

import akka.actor.ActorRef;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by John on 19/09/2016.
 */
public class Worker {

    private static Worker instancia;

    public ActorRef actor;

    public static Worker getInstance(){

        if(instancia == null){
            instancia = new Worker();
        }
        return instancia;
    }

    public Worker(){

        actor = Akka.system().actorOf(WorkerActor.props);

        Akka.system().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
                Duration.create(2, TimeUnit.SECONDS),     //Frequency 30 minutes
                actor,
                "tick",
                Akka.system().dispatcher(),
                null
        );

    }


}
