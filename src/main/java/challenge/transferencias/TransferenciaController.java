package challenge.transferencias;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.response.RespuestaDTO;
import challenge.response.TransferenciaResponse;

@RestController
public class TransferenciaController {

	@Autowired
	private TransferenciaService transferenciaService;

	@GetMapping("/transferencias-ultimo-mes")
	public ResponseEntity<RespuestaDTO<List<TransferenciaResponse>>> getTransferenciasUltimoMes() {
		RespuestaDTO<List<TransferenciaResponse>> respuesta = transferenciaService.getTransferenciasUltimoMes();
		HttpStatus httpStatus = HttpStatus.valueOf(respuesta.getStatus());
		return ResponseEntity.status(httpStatus).body(respuesta);
	}

}
