package sv.edu.udb.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contrataciones")

public class Contratacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id",nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "cargo_id",nullable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "departamento_id",nullable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "tipoContratacion_id",nullable = false)
    private TipoContratacion tipoContratacion;

    @Column(nullable = false)
    private Date fechaContratacion;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salario;

}
