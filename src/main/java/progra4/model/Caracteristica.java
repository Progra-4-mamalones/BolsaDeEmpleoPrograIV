package progra4.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

//Me kgo en Sandra

@Entity
@Table(name = "caracteristica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Caracteristica padre;

    @OneToMany(mappedBy = "padre")
    private List<Caracteristica> subCaracteristicas;
}