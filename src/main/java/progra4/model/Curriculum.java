package progra4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "curriculum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String archivo;

    @OneToOne
    @JoinColumn(name = "oferente_id")
    private Oferente oferente;
}