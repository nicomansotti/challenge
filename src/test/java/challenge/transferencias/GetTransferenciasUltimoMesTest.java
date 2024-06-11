package challenge.transferencias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
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

import challenge.response.RespuestaDTO;
import challenge.response.TransferenciaResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetTransferenciasUltimoMesTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TransferenciaService transferenciaService;

    private static final String URL = "/transferencias-ultimo-mes";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransferenciasUltimoMes() {
        TransferenciaResponse response1 = new TransferenciaResponse();
        response1.setMonto(new BigDecimal("1000.00"));
        response1.setFecha(new Date());
        response1.setNombre("Empresa1");

        TransferenciaResponse response2 = new TransferenciaResponse();
        response2.setMonto(new BigDecimal("2000.00"));
        response2.setFecha(new Date());
        response2.setNombre("Empresa2");

        List<TransferenciaResponse> transferencias = Arrays.asList(response1, response2);
        RespuestaDTO<List<TransferenciaResponse>> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setObjeto(transferencias);
        respuestaDTO.setStatus(200);

        when(transferenciaService.getTransferenciasUltimoMes()).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RespuestaDTO<List<TransferenciaResponse>>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.GET, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<List<TransferenciaResponse>> responseBody = responseEntity.getBody();
        assertThat(responseBody.getObjeto()).isNotNull();
        assertThat(responseBody.getObjeto()).hasSize(2);
        assertThat(responseBody.getObjeto().get(0).getNombre()).isEqualTo("Empresa1");
        assertThat(responseBody.getObjeto().get(0).getMonto()).isEqualByComparingTo(new BigDecimal("1000.00"));
    }

    @Test
    void testGetTransferenciasUltimoMesSinTransferencias() {
        RespuestaDTO<List<TransferenciaResponse>> respuestaDTO = new RespuestaDTO<>();
        respuestaDTO.setObjeto(List.of());
        respuestaDTO.setStatus(200);
        respuestaDTO.setMensaje("No se encontraron transferencias realizadas en el último mes.");
        respuestaDTO.setCodigo("TRANSFERENCIA-MSJ-001");

        when(transferenciaService.getTransferenciasUltimoMes()).thenReturn(respuestaDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<RespuestaDTO<List<TransferenciaResponse>>> responseEntity = restTemplate.exchange(
                URL, 
                HttpMethod.GET, 
                entity, 
                new ParameterizedTypeReference<>() {});

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        RespuestaDTO<List<TransferenciaResponse>> responseBody = responseEntity.getBody();
        assertThat(responseBody.getObjeto()).isNotNull();
        assertThat(responseBody.getObjeto()).isEmpty();
        assertThat(responseBody.getMensaje()).isEqualTo("No se encontraron transferencias realizadas en el último mes.");
        assertThat(responseBody.getCodigo()).isEqualTo("TRANSFERENCIA-MSJ-001");
    }
    
}
