package challenge.transferencias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.dao.TransferenciaDAO;
import challenge.entity.Transferencia;
import challenge.response.RespuestaDTO;
import challenge.response.TransferenciaResponse;

@Service
public class TransferenciaService {

	@Autowired
	TransferenciaDAO transferenciaDAO;

	public RespuestaDTO<List<TransferenciaResponse>> getTransferenciasUltimoMes() {
		RespuestaDTO<List<TransferenciaResponse>> respuesta = new RespuestaDTO<>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date fechaHaceUnMes = cal.getTime();
		List<Transferencia> transferencias = transferenciaDAO.findTransferenciasUltimoMes(fechaHaceUnMes);
		List<TransferenciaResponse> transferenciasResponse = mapToTransferenciaResponse(transferencias);
		respuesta.setObjeto(transferenciasResponse);
		respuesta.setStatus(200);
		if (transferencias.isEmpty()) {
			respuesta.setMensaje("No se encontraron transferencias realizadas en el último mes.");
			respuesta.setCodigo("TRANSFERENCIA-MSJ-001");
		}
		return respuesta;
	}

	private List<TransferenciaResponse> mapToTransferenciaResponse(List<Transferencia> transferencias) {
		return transferencias.stream().map(transferencia -> {
			TransferenciaResponse response = new TransferenciaResponse();
			response.setMonto(transferencia.getMonto());
			response.setFecha(transferencia.getFecha());
			response.setNombre(transferencia.getEmpresa().getNombre());
			return response;
		}).collect(Collectors.toList());
	}

}
