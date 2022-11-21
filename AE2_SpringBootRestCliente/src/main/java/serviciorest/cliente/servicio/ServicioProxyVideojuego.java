package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;

//Con esta anotación damos de alta un objeto de tipo
//ServicioProxyPersona dentro del contexto de Spring
@Service
public class ServicioProxyVideojuego {

	//La URL base del servicio REST de videojuegos
	public static final String URL = "http://localhost:8080/videojuegos/";
	
	//Inyectamos el objeto de tipo RestTemplate que nos ayudará
	//a hacer las peticiones HTTP al servicio REST
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * el videojuego
	 * 
	 * @param id que queremos obtener
	 * @return retorna el videojeugo que estamos buscando, null en caso de que el videojuego
	 *  no se encuentre en el servidor (devuelva 404) o haya habido algún
	 * otro error.
	 */
	public Videojuego obtener(int id){
		try {
			
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			HttpStatus hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {	
				
				return re.getBody();
			}else {
				System.out.println("obtener -> Respuesta no contemplada");
				return null;
			}
		}catch (HttpClientErrorException e) {//Errores 4XX
			System.out.println("obtener -> El videojuego NO se ha encontrado, id: " + id);
		    System.out.println("obtener -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	/**
	 * Método que da de alta un videojuego
	 * 
	 * @param v el videojuego a dar de alta
	 * @return el videojuego con id actualizado
	 */
	public Videojuego alta(Videojuego v){
		try {
		
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL, v, Videojuego.class);
			System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {//Errores 4XX
			System.out.println("alta -> El videojuego NO se ha dado de alta, id: " + v);
		    System.out.println("alta -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	/**
	 * 
	 * Modifica un videojuego
	 * 
	 * @param v el videojuegoq ue queremos modificar
	 * @return true en caso de que se haya podido modificar 
	 * false en caso contrario.
	 */
	public boolean modificar(Videojuego v){
		try {
			
			restTemplate.put(URL + v.getId(), v, Videojuego.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("modificar -> El videojuego NO se ha modificado, id: " + v.getId());
		    System.out.println("modificar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	/**
	 * 
	 * Borra un videojuego
	 * 
	 * @param id el id del juego
	 * @return true en caso de que se haya podido borrar
	 * false en caso contrario.
	 */
	public boolean borrar(int id){
		try {
			
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("borrar -> El videojuego NO se ha borrado, id: " + id);
		    System.out.println("borrar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	/**
	 * Metodo que devuelve todas todos los juegos
	 * 
	 * @param nombre en caso de ser distinto de null, devolvera el listado
	 * filtrado por el nombre que le hayamos pasado en este parametro. En caso
	 * de que sea null, el listado sera completo
	 * @return el listado segun el parametro de entrada o 
	 * null en caso de algun error con el servicio REST
	 */
	public List<Videojuego> listar(String nombre){
		String queryParams = "";		
		if(nombre != null) {
			queryParams += "?nombre=" + nombre;
		}
		
		try {
			//Ej http://localhost:8080/personas?nombre=harry GET
			ResponseEntity<Videojuego[]> response =
					  restTemplate.getForEntity(URL + queryParams,Videojuego[].class);
			Videojuego[] arrayVideojuegos = response.getBody();
			return Arrays.asList(arrayVideojuegos);//convertimos el array en un ArrayList
		} catch (HttpClientErrorException e) {
			System.out.println("listar -> Error al obtener la lista de videojuegos");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
}
