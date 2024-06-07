package challenge.empresas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.entity.Empresa;
import challenge.request.EmpresaRequest;
import challenge.response.RespuestaDTO;

@RestController
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@GetMapping("/empresas-ultimo-mes")
	public ResponseEntity<RespuestaDTO<List<Empresa>>> obtenerEmpresasUltimoMes() {
		RespuestaDTO<List<Empresa>> respuesta = empresaService.obtenerEmpresasUltimoMes();
		HttpStatus httpStatus = HttpStatus.valueOf(respuesta.getStatus());
		return ResponseEntity.status(httpStatus).body(respuesta);
	}

	@PostMapping("/empresas")
	public ResponseEntity<RespuestaDTO<Empresa>> agregarEmpresa(@RequestBody EmpresaRequest request) {
		RespuestaDTO<Empresa> respuesta = empresaService.agregarEmpresa(request);
		HttpStatus httpStatus = HttpStatus.valueOf(respuesta.getStatus());
		return ResponseEntity.status(httpStatus).body(respuesta);
	}

}