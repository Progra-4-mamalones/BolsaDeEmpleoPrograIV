package progra4.model;

import jakarta.persistence.*;
import lombok.*;

//https://chatgpt.com/g/g-p-69acadd6dd948191887bba5ef7a43fc1-progra-iv-proyecto-1/project

@Entity
@Table(name = "administrador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificacion;

    private String clave;
}
