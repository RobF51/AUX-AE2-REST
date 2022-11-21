package serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciorest.modelo.entidad.Videojuego;

/**
 * Damos de alta un objeto que simula que los datos estan guardados en una BBDD
 * trabajando con una lista de videojuegos cargada en memoria.
 * 
 */
@Component
public class DaoVideojuego {
	public List<Videojuego> listaVideojuegos;
	public int contador;
	
	public DaoVideojuego() {
		System.out.println("DaoVideojuego -> Creando la lista de videojuegos!");
		listaVideojuegos = new ArrayList<Videojuego>();
		Videojuego v1 = new Videojuego(contador++, "PACMAN", "NAMCO", 90);// ID: 0
		Videojuego v2 = new Videojuego(contador++, "SONIC", "SEGA", 95);// ID: 1
		Videojuego v3 = new Videojuego(contador++, "PANG", "CAPCOM", 98);// ID: 2
		Videojuego v4 = new Videojuego(contador++, "ARKANOID", "TAITO", 85);// ID:3
		Videojuego v5 = new Videojuego(contador++, "CONTRA", "KONAMI", 87);// ID:4
		listaVideojuegos.add(v1);
		listaVideojuegos.add(v2);
		listaVideojuegos.add(v3);
		listaVideojuegos.add(v4);
		listaVideojuegos.add(v5);
	}

	/**
	 * Metodo que introduce un nuevo videojuego
	 * 
	 * @param v el videojuego que queremos introducir
	 */
	public void add(Videojuego v) {
		v.setId(contador++);
		listaVideojuegos.add(v);
	}

	/**
	 * Metodo que borra un videojuego.
	 * 
	 * @param posicion la posicion a borrar
	 */
	public Videojuego delete(int posicion) {
		try {
			return listaVideojuegos.remove(posicion);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("delete -> Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que modifica un videojuego
	 * 
	 * @param v contiene todos los datos que queremos modificar, pero v.getId()
	 *          contiene la posicion del array que queremos eliminar
	 */
	public Videojuego update(Videojuego v) {
		try {
			Videojuego vAux = listaVideojuegos.get(v.getId());
			vAux.setNombre(v.getNombre());
			vAux.setCompania(v.getCompania());
			vAux.setNota(v.getNota());

			return vAux;
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Devuelve un videojuego a partir de su id (Posicion en el array)
	 * 
	 * @param posicion la posicion del array que buscamos
	 * @return el videojuego que ocupe en la posicion del array, null en caso de que
	 *         no exista o se haya ido fuera de rango del array
	 */
	public Videojuego get(int posicion) {
		try {
			return listaVideojuegos.get(posicion);
		} catch (IndexOutOfBoundsException iobe) {
			System.out.println("Videojuego fuera de rango");
			return null;
		}
	}

	/**
	 * Metodo que devuelve todos los videojuegos del array
	 * 
	 * @return una lista con todas las personas del array
	 */
	public List<Videojuego> list() {
		return listaVideojuegos;
	}

	/**
	 * Metodo que devuelve todos los videojuegos. Como puede haber varias personas
	 * con el mismo nombre tengo que devolver una lista con todas las encontradas
	 * 
	 * @param nombre representa el nombre por el que vamos a hacer la busqueda
	 * @return una lista con las personas que coincidan en el nombre. La lista
	 *         estará vacia en caso de que no hay coincidencias
	 */
	public List<Videojuego> listByNombre(String nombre) {
		List<Videojuego> listaVideojuegosAux = new ArrayList<Videojuego>();
		for (Videojuego v : listaVideojuegos) {
			if (v.getNombre().equalsIgnoreCase(nombre)) {// contains()
				listaVideojuegosAux.add(v);
			}
		}
		return listaVideojuegosAux;
	}
}