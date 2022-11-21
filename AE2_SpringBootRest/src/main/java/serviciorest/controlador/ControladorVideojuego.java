package serviciorest.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serviciorest.modelo.entidad.Videojuego;
import serviciorest.modelo.persistencia.DaoVideojuego;

@RestController
public class ControladorVideojuego {
	
	
	@Autowired
	private DaoVideojuego daoVideojuego;
	
	//GET
	@GetMapping(path="videojuegos/{id}",produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Videojuego> getVideojuego(@PathVariable("id") int id) {
		System.out.println("Buscando videojuego con id: " + id);
		Videojuego v = daoVideojuego.get(id);
		if(v != null) {
			return new ResponseEntity<Videojuego>(v,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	//POST 
	
	@PostMapping(path="videojuegos",consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego v) {
        for (int i = 0; i < daoVideojuego.contador; i++) {
            if (v.getId() == daoVideojuego.list().get(i).getId()) {
                return new ResponseEntity<Videojuego>(v,HttpStatus.CONFLICT);//409 CONFLICT , ID ya registrado
            }
        }
        System.out.println("altaVideojuego: objeto videojuego: " + v);
        daoVideojuego.add(v);
        return new ResponseEntity<Videojuego>(v,HttpStatus.CREATED);//201 CREATED
    }
	
	//GET LISTA VIDEOJUEGOS
	
	@GetMapping(path="videojuegos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Videojuego>> listarVideojuegos(
			@RequestParam(name="nombre",required=false) String nombre) {
		List<Videojuego> listaVideojuegos = null;
		//Si no me viene el nombre, devolvemos toda la lista
		if(nombre == null) {
			System.out.println("Listando los videojuegos");
			listaVideojuegos = daoVideojuego.list();			
		}else {
			System.out.println("Listando los videojuegos por nombre: " + nombre);
			listaVideojuegos = daoVideojuego.listByNombre(nombre);
		}
		System.out.println(listaVideojuegos);
		return new ResponseEntity<List<Videojuego>>(listaVideojuegos,HttpStatus.OK);
	}
	
	//PUT

	@PutMapping(path="videojuegos/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarVideojuego(
			@PathVariable("id") int id, 
			@RequestBody Videojuego v) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + v);
		v.setId(id);
		Videojuego vUpdate = daoVideojuego.update(v);
		if(vUpdate != null) {
			return new ResponseEntity<Videojuego>(HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	//DELETE
	//Aqui vamos a borar un videojuego a traves de un ID que le pasemos 
	
	@DeleteMapping(path="videojuegos/{id}")
	public ResponseEntity<Videojuego> borrarVideojuego(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		Videojuego v = daoVideojuego.delete(id);
		if(v != null) {
			return new ResponseEntity<Videojuego>(v,HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
}
