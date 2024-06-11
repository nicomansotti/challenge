package challenge.empresas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import challenge.entity.Empresa;
import challenge.request.EmpresaRequest;
import challenge.response.RespuestaDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgregarEmpresaTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmpresaService empresaService;

    private static final String URL = "/empresas";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarEmpresa() {
        EmpresaRequest request = new EmpresaRequest();
        request.setNombre("Empresa1");

        Empresa empresa = new Empresa("Empresa1");
        RespuestaDTO<Empresa> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setObjeto(empresa);
        respuestaDTO.setStatus(200);
        respuestaDTO.setMensaje("Se agregó la empresa.");
        respuestaDTO.setCodigo("EMPRESA-MSJ-001");

        when(empresaService.agregarEmpresa(any())).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpresaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RespuestaDTO<Empresa>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<Empresa> responseBody = responseEntity.getBody();
        assertThat(responseBody.getObjeto()).isNotNull();
        assertThat(responseBody.getObjeto().getNombre()).isEqualTo("Empresa1");
        assertThat(responseBody.getMensaje()).isEqualTo("Se agregó la empresa.");
        assertThat(responseBody.getCodigo()).isEqualTo("EMPRESA-MSJ-001");
    }

    @Test
    void testAgregarEmpresaNombreNulo() {
        EmpresaRequest request = new EmpresaRequest();
        request.setNombre(null);

        RespuestaDTO<Empresa> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setStatus(400);
        respuestaDTO.setMensaje("Error al agregar la empresa. Debes especificar el nombre");
        respuestaDTO.setCodigo("EMPRESA-ERR-002");

        when(empresaService.agregarEmpresa(any())).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpresaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RespuestaDTO<Empresa>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<Empresa> responseBody = responseEntity.getBody();
        assertThat(responseBody.getMensaje()).isEqualTo("Error al agregar la empresa. Debes especificar el nombre");
        assertThat(responseBody.getCodigo()).isEqualTo("EMPRESA-ERR-002");
    }

    @Test
    void testAgregarEmpresaNombreExistente() {
        EmpresaRequest request = new EmpresaRequest();
        request.setNombre("EmpresaExistente");

        RespuestaDTO<Empresa> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setStatus(400);
        respuestaDTO.setMensaje("Error al agregar la empresa. Ya existe una empresa con este nombre.");
        respuestaDTO.setCodigo("EMPRESA-ERR-001");

        when(empresaService.agregarEmpresa(any())).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpresaRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<RespuestaDTO<Empresa>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.POST, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<Empresa> responseBody = responseEntity.getBody();
        assertThat(responseBody.getMensaje()).isEqualTo("Error al agregar la empresa. Ya existe una empresa con este nombre.");
        assertThat(responseBody.getCodigo()).isEqualTo("EMPRESA-ERR-001");
    }

}
