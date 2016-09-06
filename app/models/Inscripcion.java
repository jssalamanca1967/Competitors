package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by John on 18/08/2016.
 */
@Entity
@Table(name = "inscripcion")
public class Inscripcion extends Model {

    public static Finder<Long,Inscripcion> FINDER = new Finder<>(Inscripcion.class);

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public Long id;

    public String nombre;

    public String descripcion;

    public String rutaImagen;

    @ManyToOne
    public Competencia competencia;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date created;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date updated;

    @PrePersist
    protected void onCreate() {
        created = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date(System.currentTimeMillis());
    }

    public Inscripcion(String nombre, String descripcion, Competencia competencia, String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.competencia = competencia;
        this.rutaImagen = rutaImagen;
    }
}
