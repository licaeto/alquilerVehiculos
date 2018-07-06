package tests;


import excepciones.DAOExcepcion;
import modelo.AlquilerVehiculos;
//import java.time.LocalDateTime;

public class DAOPersistenceTester {
	public static void main(String[] args) throws DAOExcepcion {
		AlquilerVehiculos av = new AlquilerVehiculos();
		System.out.println(av.getSucursales().toString());
	}
}
