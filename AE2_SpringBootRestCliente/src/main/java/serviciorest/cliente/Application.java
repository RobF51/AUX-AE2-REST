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

import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Application implements CommandLineRunner {



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
	
		
		
		
		try (Scanner sc = new Scanner(System.in)) {

			boolean continuar = true;
			do {
		
		
				//Hacemos un menu en switch para recoger los datos y mandarlos al servidor
				int codeOpc = 0;
				System.out.println("¿Qué desea hacer?: \n 1. Dar de alta un videojuego \n "
						+ "2. Dar de baja un videojuego por ID \n " 
						+ "3. Modificar un videojuego por ID \n "
						+ "4. Obtener un videojuego por ID \n " 
						+ "5. Listar todos los videojuegos \n" 
						+ "0. Salir \n");
				System.out.println("CLIENTE: Introduzca la opcion");
				codeOpc = sc.nextInt();
				

				int id=0;
				
				switch (codeOpc) {
				
				//Caso 0 donde mandamos parar al cliente
				case 0:
					System.out.println("******************************************");
					System.out.println("******** Parando el cliente REST *********");
					// Mandamos parar nuestra aplicacion Spring Boot
					pararAplicacion();
					continuar = false;
					break;
					
					
					//Caso 1 donde damos de alta un nuevo videojuego
				case 1:
				
					
					Videojuego videojuego = new Videojuego();
					
					
                    System.out.println("*********** ALTA VIDEOJUEGO ***************");
                    
                    System.out.println("Introduzca el ID del videojuego");
                    int nuevoId = sc.nextInt();
                    videojuego.setId(nuevoId);
                    
                    
                    sc.nextLine();
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
                    System.out.println("run -> Videojuego dado de alta " + vAlta);
					
                    continuar = true;
				
					break;
					
					
					//Caso 2 borramos un videojuego por ID
				case 2:
					

					System.out.println("********** BORRAR VIDEOJUEGO **************");
					System.out.println("Introduzca un ID");
					id = sc.nextInt();
			
					boolean borrada = spv.borrar(id);
					System.out.println("un -> Videojuego con id " + id + " borrada? " + borrada);

				
					continuar = true;
					break;
					
					//Caso  3 Modificamos la informaciona  cerca de un videojuego
				case 3:
					Videojuego vModificar = new Videojuego();
                    System.out.println("** MODIFICAR VIDEOJUEGO **");
                    System.out.println("Introduzca el id del videojuego que quiere modificar");
                    int idModif = sc.nextInt();
                    vModificar.setId(idModif);

                    sc.nextLine();
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
                    
                    System.out.println("run -> Videojuego modificado con id " + idModif + ": " + modificada);
                    
                    continuar = true;
					break;
					
					//obtener videojeugo poor ID
				case 4:
					System.out.println("************ GET VIDEOJUEGO ***************");
					
					System.out.println("Introduzca un ID");
					id = sc.nextInt();
					
					videojuego = spv.obtener(id);
					System.out.println("run -> Videojuego con id " + id + ": " + videojuego);
					
					
					continuar = true;
					break;
					
					//Obtener toda la lista de videojuegos
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