package progra4.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "puesto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Puesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Double salario;

    private String tipoPublicacion;

    private boolean activo;

    private LocalDateTime fecha;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToMany(mappedBy = "puesto", fetch = FetchType.EAGER)
    private List<PuestoCaracteristica> requisitos;
}