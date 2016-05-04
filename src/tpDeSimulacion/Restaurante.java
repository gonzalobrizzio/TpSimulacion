package tpDeSimulacion;

import java.util.List;
import java.util.Random;

import org.joda.time.LocalTime;


public class Restaurante {
	
	static List <Cliente> clientesAsociadosEnEspera;
	static List <Cliente> clientesNoAsociadosEnEspera;
	private static final LocalTime HV = new LocalTime(100,0,0,0);
	static LocalTime TPLL = new LocalTime(9,10,0,0);
	static LocalTime Ia, Ta;
 	static LocalTime T = new LocalTime(9,0,0,0);
 	static LocalTime TF = new LocalTime(0,0,0,0);
	static int M = 10;
	static int N = 10;
	static TiempoDeSalida[] TPS;
	static int NM = 0;
	static int [] NMM;
	static int NMO = 0;
	static int NSA = 0;
	static int NSNA = 0;
	static int NS= 0;
	static int NA = 0;
	double PPA = 0, PTOM = 0, PMSU = 0;
	
	
	 public static void main (String [] args) {
		 
		 	
	        System.out.println ("Bienvenido a la simulación del restaurante");
	        
		 	TPS = new TiempoDeSalida[M];
		 	NMM = new int [N];
		 	
		 	for (TiempoDeSalida tiempoSalida : TPS){
		 		tiempoSalida.setHorarioDeSalida(HV);
		 	}
		 	
		 	while(true){
		 		int i = buscarMenorTPS();
		 		
		 		if(TPLL.compareTo(TPS[i].getHorarioDeSalida()) <= 0){
		 			//Estoy en una llegada
		 			T = TPLL;
		 			Cliente cliente = new Cliente();
		 			Ia = IA();
		 			TPLL = sumarHoras(T, Ia);
		 			NS++;
		 			Random R = new Random();
		 			if(R.nextDouble() <= 0.4){
		 				//Es un asociado
		 				NA++;
		 		
		 				if(NSA >= 5){
		 					//Puede arrepentirse
		 					if( R.nextDouble() <= 0.3){
		 						//Se arrepiente
		 						NS--;
		 						continue;
		 					}
		 					
		 					if(NSA == 0){
			 					//No se arrepintio, y se analiza si es atendido
			 					if(NS <= M && NS <= 4*N){
			 						//Es atendido
			 						Ta = TA();
			 						TPS[i].setHorarioDeSalida(sumarHoras(T,Ta));
			 						TPS[i].setCliente(cliente);
			 						ocuparMozo(TPS[i].getCliente());
			 						NMO++;
			 					}
		 					}//Fin de NSA == 0
		 					else{
		 						//No fue atendido, pobre diablo
		 						NSA++;
		 						clientesAsociadosEnEspera.add(cliente);
		 					}
		 					
		 				}//Fin de NSA >=5
		 			
		 				
		 			}//Fin de asociados
		 			else{
		 				
		 				if(NSNA >= 5 ){
		 					if(R.nextDouble() <= 0.3){
		 						//Se arrepiente
		 						NS--;
		 						continue;
		 					}
		 					
		 					if(NSNA == 0){
			 					//No se arrepintio, y se analiza si es atendido
			 					if(NS <=M && NS <=4*N){
			 						//Es atendido
			 						Ta = TA();
			 						TPS[i].setHorarioDeSalida(sumarHoras(T,Ta));
			 						TPS[i].setCliente(cliente);
			 						ocuparMozo(TPS[i].getCliente());
			 						NMO++;
			 				}
			 				else {
			 						NSNA++;
			 						clientesNoAsociadosEnEspera.add(cliente);
			 					}
		 					}
		 					}	
		 				}	
		 		}//Fin comparación de hora de llegada y salida
		 		else{
		 			T = TPS[i].getHorarioDeSalida();
		 			NS--;
		 			liberarMozo(TPS[i].getCliente());
		 			NMO--;
		 			if(NS >= M || NS >= N*4){
		 				if(NSA >= 0){
		 					NSA--;
			 				TPS[i].setCliente(clientesAsociadosEnEspera.get(0));
							clienteAsociadosEnEspera.remove(0);
		 				}
		 				else{
		 					NSNA--;
			 				TPS[i].setCliente(clientesNoAsociadosEnEspera.get(0));
							clientesNoAsociadosEnEspera.remove(0);
		 				}
		 				
		 				Ta = TA();
		 				TPS[i].setHorarioDeSalida(sumarHoras(T,Ta));
		 				ocuparMozo(TPS[i].getCliente());
		 				NMO++;
		 			}
		 			else{
		 				TPS[i].setHorarioDeSalida(HV);
		 			}
		 			
		 		}
		 	if(T.compareTo(TF) <=0){
		 		TPLL = HV;
		 		if(NS == 0){
		 			//impresionDeResultados
		 			break;
		 		}
			}//Fin de Tiempo y Tiempo final
		 	
		 	
		 }//Fin del while	    
}

	public static LocalTime IA() {
		Random R = new Random();
		double Hora = (2-0.03125)* R.nextDouble() + 0.03125;
		LocalTime intervalo = new LocalTime (Hora,0,0);
		return intervalo;
	}


	private static LocalTime TA() {
		Random R = new Random();
		
		while(true) {
		double random1 = R.nextDouble();
		double random2 = R.nextDouble();
		if((random1 * 0.03 + 0.47) >= (random2 * 12)) {
			LocalTime intervalo = new LocalTime (random1,0,0);
			return intervalo;
			}
		}
	}


	private static void liberarMozo(Cliente cliente) {
		
		int nro = cliente.getNroMozo();
		NMM[nro]--;
		
		if(NMM[nro] == 3){
			NM--;
		}
		
	}


	private static LocalTime sumarHoras(LocalTime t, LocalTime ta) {

		int horas = t.getHourOfDay() + ta.getHourOfDay();
		int minutos = t.getMinuteOfHour() + ta.getMinuteOfHour();
		int segundos = t.getSecondOfMinute() + ta.getSecondOfMinute();
		
		LocalTime horaSumada = new LocalTime(horas, minutos, segundos);

		return horaSumada;
		
	}


	private static void ocuparMozo(Cliente cliente) {
		int i = 0;
		for(int nMesasDelMozo : NMM){
			if(nMesasDelMozo < 4){
				nMesasDelMozo++;
				cliente.setNroMozo(i);
				if(nMesasDelMozo == 4){
					NM++;
				}
				break;
			}
		i++;	
		}
	}

	public static int buscarMenorTPS() {
		
		if(M == 1){
			return 0;
		}
		
		int i = 1;
		int v = 0;
		LocalTime horaMinima = TPS[0].getHorarioDeSalida();
		
		for(TiempoDeSalida tiempoDeSalida : TPS){
			
			if(horaMinima.compareTo(tiempoDeSalida.getHorarioDeSalida()) > 0){
				horaMinima = tiempoDeSalida.getHorarioDeSalida();
				v = i;
			}
			i++;
		}
		
		return v;
		
		
	} 

}
