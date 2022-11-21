package serviciorest.controlador;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//El objeto creado a partir de esta clase servira como punto de entrada de 
//nuestro servicio REST

//Los metodos anotados seran "endpoints" (puntos de entrada a nuestra aplicacion).


@RestController
public class ControladorMensaje {
		
	//Los metodos de esta clase, gracias a la anotacion @RestController, nos
	//serviran a partir de ahora para recibir peticiones http de nuestros
	//clientes
	
	//Con la anotacion @GetMapping(value="mensaje"= estamos mapeando el 
	//recurso "mensaje" con el metodo http GET. Tendremos anotaciones
	//para todos los verbos HTTP (@PostMapping, @PutMapping, @DeleteMapping,
	//etc.)
	
	//La url completa sería "http://localhost:8080/mensaje" con el metodo GET

	@GetMapping(value = "mensaje")
	public String obtenerMensaje() {
		return "Esto es un mensaje para confirmar la conexion";
	}
	
	//Dentro de la anotacion tambien podemos poner el parámetro "produces"
	//que le estaremos indicando el "MIME Type" que produciremos.

	@GetMapping(value = "mensajeHTML", produces = MediaType.TEXT_HTML_VALUE)
	public String obtenerMensajeHTML() {
		//Esta clase es muy buena para concatenar Strings
		StringBuffer buffer = new StringBuffer();
		buffer.append("<!DOCTYPE html>");
		buffer.append("<html>");
		buffer.append("<head>");
		buffer.append("<title>Prueba html</title>");
		buffer.append("</head>");
		buffer.append("<body>");
		buffer.append("<h1 style='color:red'>ESTO SERIA UN MENSAJE EN HTML</h1>");
		buffer.append("</body>");
		buffer.append("</html>");
		
		return buffer.toString();
	}

}
