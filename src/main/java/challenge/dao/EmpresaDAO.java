package challenge.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import challenge.entity.Empresa;

@Repository
public interface EmpresaDAO extends JpaRepository<Empresa, Long> {

	Optional<Empresa> findByNombre(String nombre);
	
	@Query("SELECT e FROM Empresa e WHERE e.fechaAlta >= :fechaInicio")
    List<Empresa> findEmpresasAdheridasDesde(@Param("fechaInicio") Date fechaInicio);
	
}
