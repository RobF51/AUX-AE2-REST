package serviciorest.cliente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;
import serviciorest.cliente.servicio.ServicioProxyMensaje;
import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private ServicioProxyMensaje spm;

	@Autowired
	private ServicioProxyVideojuego spv;

	@Autowired
	private ApplicationContext context;

	// El objeto restTemplate será usado por los objetos ServicioProxy
	// para hacer las peticiones HTTP a nuestro servicio REST.

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// Método main que lanza la aplicación
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("****** Arrancando el cliente REST ******");
		System.out.println("*************  MENSAJE *****************");
		String mensaje = spm.obtener("mensaje");
		System.out.println("run -> Mensaje: " + mensaje);

		System.out.println("***********  MENSAJE HTML **************");
		String mensajeHTML = spm.obtener("mensajeHTML");
		System.out.println("run -> Mensaje: " + mensajeHTML);
		
		
		
		
		try (Scanner sc = new Scanner(System.in)) {

			boolean continuar = true;
			do {
		
		

				int codeOpc = 0;
				System.out.println("¿Qué desea hacer?: \n 1. Dar de alta un videojuego \n "
						+ "2. Dar de baja un videojuego por ID \n " + "3. Modificar un videojuego por ID \n "
						+ "4. Obtener un videojuego por ID \n " + "5. Listar todos los videojuegos \n" + "0. Salir \n");
				System.out.println("CLIENTE: Introduzca la opcion");
				codeOpc = sc.nextInt();
				//int codeOpcInt = Integer.parseInt(codeOpc);

				int id=0;
				
				switch (codeOpc) {
				case 0:
					System.out.println("******************************************");
					System.out.println("******** Parando el cliente REST *********");
					// Mandamos parar nuestra aplicacion Spring Boot
					pararAplicacion();
					continuar = false;
					break;

				case 1:
				
					
					Videojuego videojuego = new Videojuego();
                    System.out.println("*********** ALTA VIDEOJUEGO ***************");
                    
                    System.out.println("Introduzca el ID del videojuego");
                    int nuevoId = sc.nextInt();
                    videojuego.setId(nuevoId);
                    
                    
                    
                    System.out.println("Introduzca el nombre del videojuego");
                    String nuevoNombre = sc.nextLine();
                    videojuego.setNombre(nuevoNombre);
                    System.out.println("Introduzca la compania del videojuego");
                    String nuevaCompania = sc.nextLine();
                    videojuego.setCompania(nuevaCompania);
                    System.out.println("Introduzca la nota del videojuego");
                    int nuevaNota = sc.nextInt();
                    videojuego.setNota(nuevaNota);

                    Videojuego vAlta = spv.alta(videojuego);
                    System.out.println("run -> Persona dada de alta " + vAlta);
					
                    continuar = true;
				
					break;
				case 2:
					

					System.out.println("********** BORRAR VIDEOJUEGO **************");
					System.out.println("Introduzca un ID");
					id = sc.nextInt();
			
					boolean borrada = spv.borrar(id);
					System.out.println("un -> Persona con id " + id + " borrada? " + borrada);

					//System.out.println("******** BORRAR PERSONAS ERRONEA *******");
					//borrada = spv.borrar(20);
					//System.out.println("run -> Persona con id 20 borrada? " + borrada);
					continuar = true;
					break;
				case 3:
					Videojuego vModificar = new Videojuego();
                    System.out.println("** MODIFICAR VIDEOJUEGO **");
                    System.out.println("Introduzca el id del videojuego que quiere modificar");
                    int idModif = sc.nextInt();
                    vModificar.setId(idModif);

                    System.out.println("Introduzca el nuevo nombre del videojuego");
                    String nuevoNombreModif = sc.nextLine();
                    vModificar.setNombre(nuevoNombreModif);
                    System.out.println("Introduzca la nueva compania del videojuego");
                    String nuevaCompaniaModif = sc.nextLine();
                    vModificar.setCompania(nuevaCompaniaModif);
                    System.out.println("Introduzca la nueva nota del videojuego");
                    int nuevaNotaModif = sc.nextInt();
                    vModificar.setNota(nuevaNotaModif);

                    boolean modificada = spv.modificar(vModificar);
                    
                    System.out.println("run -> persona modificada con id " + idModif + ": " + modificada);
                    
                    continuar = true;
					break;
				case 4:
					System.out.println("************ GET VIDEOJUEGO ***************");
					
					System.out.println("Introduzca un ID");
					id = sc.nextInt();
					
					videojuego = spv.obtener(id);
					System.out.println("run -> Videojuego con id " + id + ": " + videojuego);
					
					continuar = true;
					break;
				case 5:
					
					System.out.println("********** LISTAR VIDEOJUEGOS ***************");
					List<Videojuego> listaVideojuegos = spv.listar(null);
					// Recorremos la lista y la imprimimos con funciones lambda
					// Tambien podriamos haber usado un for-each clasico de java
					listaVideojuegos.forEach((v) -> System.out.println(v));
					continuar = true;
					break;
				default:
					break;
				}

			} while (continuar);

		}

		catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);

		}
	}

	public void pararAplicacion() {
		// Para el servidor cuando acabemos
		SpringApplication.exit(context, () -> 0);

	}
}