package progra4.config;
import progra4.model.Administrador;
import progra4.model.Empresa;
import progra4.model.Oferente;
import progra4.repository.AdministradorRepository;
import progra4.repository.EmpresaRepository;
import progra4.repository.OferenteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministradorRepository administradorRepository;
    private final EmpresaRepository empresaRepository;
    private final OferenteRepository oferenteRepository;

    public UserDetailsServiceImpl(AdministradorRepository administradorRepository,
                                  EmpresaRepository empresaRepository,
                                  OferenteRepository oferenteRepository) {
        this.administradorRepository = administradorRepository;
        this.empresaRepository = empresaRepository;
        this.oferenteRepository = oferenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar en administrador por identificacion
        Administrador admin = administradorRepository.findByIdentificacion(username);
        if (admin != null) {
            return new UserDetailsImpl(admin.getIdentificacion(), admin.getClave(), "ADMIN", admin.getId());
        }

        // Buscar en empresa por correo
        Empresa empresa = empresaRepository.findByCorreo(username);
        if (empresa != null) {
            if (!empresa.isAprobada()) {
                throw new UsernameNotFoundException("Empresa no aprobada");
            }
            return new UserDetailsImpl(empresa.getCorreo(), empresa.getClave(), "EMPRESA", empresa.getId());
        }

        // Buscar en oferente por correo
        Oferente oferente = oferenteRepository.findByCorreo(username);
        if (oferente != null) {
            if (!oferente.isAprobado()) {
                throw new UsernameNotFoundException("Oferente no aprobado");
            }
            return new UserDetailsImpl(oferente.getCorreo(), oferente.getClave(), "OFERENTE", oferente.getId());
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}