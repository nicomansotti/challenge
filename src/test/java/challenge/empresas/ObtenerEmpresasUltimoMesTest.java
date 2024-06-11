package challenge.empresas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import challenge.response.RespuestaDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ObtenerEmpresasUltimoMesTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private EmpresaService empresaService;

    private static final String URL = "/empresas-ultimo-mes";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerEmpresasUltimoMes() {
        Empresa empresa1 = new Empresa("Empresa1");
        Empresa empresa2 = new Empresa("Empresa2");

        List<Empresa> empresas = Arrays.asList(empresa1, empresa2);
        RespuestaDTO<List<Empresa>> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setObjeto(empresas);
        respuestaDTO.setStatus(200);

        when(empresaService.obtenerEmpresasUltimoMes()).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RespuestaDTO<List<Empresa>>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.GET, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<List<Empresa>> responseBody = responseEntity.getBody();
        assertThat(responseBody.getObjeto()).isNotNull();
        assertThat(responseBody.getObjeto()).hasSize(2);
        assertThat(responseBody.getObjeto().get(0).getNombre()).isEqualTo("Empresa1");
        assertThat(responseBody.getObjeto().get(1).getNombre()).isEqualTo("Empresa2");
    }

    @Test
    void testObtenerEmpresasUltimoMesSinEmpresas() {
        RespuestaDTO<List<Empresa>> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setObjeto(List.of());
        respuestaDTO.setStatus(200);
        respuestaDTO.setMensaje("No se encontraron empresas adheridas hace menos de un mes.");
        respuestaDTO.setCodigo("EMPRESA-MSJ-002");

        when(empresaService.obtenerEmpresasUltimoMes()).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RespuestaDTO<List<Empresa>>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.GET, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<List<Empresa>> responseBody = responseEntity.getBody();
        assertThat(responseBody.getObjeto()).isNotNull();
        assertThat(responseBody.getObjeto()).isEmpty();
        assertThat(responseBody.getMensaje()).isEqualTo("No se encontraron empresas adheridas hace menos de un mes.");
        assertThat(responseBody.getCodigo()).isEqualTo("EMPRESA-MSJ-002");
    }
   
}
