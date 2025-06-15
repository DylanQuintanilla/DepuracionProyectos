package sv.edu.udb.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "empleados")

public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String dui;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 9, nullable = false)
    private String numeroTelefono;

    @Column(length = 50, nullable = false, unique = true)
    private String correo;

    //Dos formas de ocupar fechas, numero 1 es con el @Temporal el cual define si queremos fecha y hora, solo fecha o solo hora
    //numero 2, cambiandolo a tipo de variable LocalDate (en el caso de contrataciones) pero este no maneja hora
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contratacion> contrataciones;
}
