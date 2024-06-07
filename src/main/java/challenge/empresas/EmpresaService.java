package challenge.empresas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challenge.dao.EmpresaDAO;
import challenge.entity.Empresa;
import challenge.request.EmpresaRequest;
import challenge.response.RespuestaDTO;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaDAO empresaDAO;

	public RespuestaDTO<Empresa> agregarEmpresa(EmpresaRequest empresaRequest) {
		RespuestaDTO<Empresa> respuesta = new RespuestaDTO<>();
		if (Objects.nonNull(empresaRequest.getNombre())) {
			
			Optional<Empresa> existeEmpresa = empresaDAO.findByNombre(empresaRequest.getNombre());
			if(existeEmpresa.isPresent()) {
				respuesta.setCodigo("EMPRESA-ERR-001");
				respuesta.setMensaje("Error al agregar la empresa. Ya existe una empresa con este nombre.");
				respuesta.setStatus(400);
				return respuesta;
			}
			
			Empresa empresa = new Empresa(empresaRequest.getNombre());
			empresaDAO.save(empresa);
			respuesta.setObjeto(empresa);
			respuesta.setCodigo("EMPRESA-MSJ-001");
			respuesta.setMensaje("Se agregó la empresa.");
			respuesta.setStatus(200);
			return respuesta;
		} else {
			respuesta.setCodigo("EMPRESA-ERR-002");
			respuesta.setMensaje("Error al agregar la empresa. Debes especificar el nombre");
			respuesta.setStatus(400);
			return respuesta;
		}
	}

	 public RespuestaDTO<List<Empresa>> obtenerEmpresasUltimoMes() {
	        RespuestaDTO<List<Empresa>> respuesta = new RespuestaDTO<>();
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.MONTH, -1);
	        Date fechaHaceUnMes = calendar.getTime();
	        List<Empresa> empresas = empresaDAO.findEmpresasAdheridasDesde(fechaHaceUnMes);
	        respuesta.setObjeto(empresas);
	        respuesta.setStatus(200);
	        if (empresas.isEmpty()) {
	            respuesta.setMensaje("No se encontraron empresas adheridad hace menos de un mes.");
	            respuesta.setCodigo("EMPRESA-MSJ-002");
	        }
	        return respuesta;
	    }
	
}
