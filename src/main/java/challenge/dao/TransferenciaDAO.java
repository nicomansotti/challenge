package challenge.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import challenge.entity.Transferencia;

@Repository
public interface TransferenciaDAO extends JpaRepository<Transferencia, Long> {
	
	@Query("SELECT t FROM Transferencia t WHERE t.fecha >= :fechaInicio")
    List<Transferencia> findTransferenciasUltimoMes(@Param("fechaInicio") Date fechaInicio);

}
