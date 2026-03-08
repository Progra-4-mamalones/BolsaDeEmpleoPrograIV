package progra4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "puesto_caracteristica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuestoCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nivel;

    @ManyToOne
    @JoinColumn(name = "puesto_id")
    private Puesto puesto;

    @ManyToOne
    @JoinColumn(name = "caracteristica_id")
    private Caracteristica caracteristica;
}