package tpDeSimulacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalTime;


public class Restaurante {
	
	static double STaN = 0;
	static double STaA = 0;
	static double STPLLA = 0;
	static double STPSA = 0;
	static double STPLLN = 0;
	static double STPSN = 0;
	static double NTA = 0;
	static double NTN = 0;
	static int nroCliente = 1;
	static List <Cliente> clientesAsociadosEnEspera = new ArrayList<Cliente>();
	static List <Cliente> clientesNoAsociadosEnEspera = new ArrayList<Cliente>();
	private static final LocalTime HVmenor = new LocalTime(23,59,59,98);
	private static final LocalTime HVmayor = new LocalTime(23,59,59,99);
	static LocalTime TPLL = new LocalTime(9,10,0);
	static TiempoDeSalida[] TPS;
	static double[] ITOmesas;
	static double[] ITOmozos;
	static double[] STOmesas;
	static double[] STOmozos;
	static LocalTime Ia, Ta;
 	static LocalTime T = new LocalTime(9,0,0);
 	static LocalTime TF = new LocalTime(21,00,00);
	static int M = 12;
	static int N = 2;
	static int NM = 0;
	static int[] NMM;
	static int NMO = 0;
	static int NSA = 0;
	static int NSNA = 0;
	static int NS= 0;
	static int NA = 0;
	
	
	 public static void main (String [] args) throws InterruptedException { //Inicio main (1)
		 	
	        System.out.println ("Bienvenido a la simulacion del restaurante");
	        
		 	inicializarVariables();
		 	int i;
		 	
		 	while(true){

		 		i = buscarMenorTPS();
				 
		 		
		 		if(TPLL.compareTo(TPS[i].getHorarioDeSalida()) <= 0) {
		 			//Estoy en una llegada
		 			T = TPLL;	
		 			Cliente cliente = new Cliente();
		 			cliente.setNroCliente(nroCliente);
		 			nroCliente++;
		 			
		 			Ia = IA();	
		 			TPLL = sumarHoras(T, Ia);
		 			System.out.println ("El cliente " + cliente.getNroCliente() + " se animo a realizar una llegada. Dejo datos muy interesantes (llegada): ");
		 			System.out.println ("TPLL (" + TPLL.getHourOfDay() + ":" + TPLL.getMinuteOfHour() + ":" + TPLL.getSecondOfMinute() + ") =" + " T (" + T.getHourOfDay() + ":" + T.getMinuteOfHour() + ":" + T.getSecondOfMinute() + ") +Ia (" + Ia.getHourOfDay() + ":" + Ia.getMinuteOfHour() + ":" + Ia.getSecondOfMinute() + ")");
		 			Random R = new Random();
		 			if(R.nextDouble() <= 0.4) {
		 				NA++;
		 				if(NSA >= 5){
		 					if(R.nextDouble() <= 0.3) {
		 						continue;
		 						}
		 					}
		 				
		 				NS++;
		 				NTA++;
		 				STPLLA = STPLLA + pasarAMinutos(TPLL);
		 					
		 				if(NSA == 0){
			 				if(NS <= M && NS <= 4*N){
			 					Ta = TA();
			 					STaA = STaA + pasarAMinutos(Ta);
			 					int j = buscarHV();
			 					STOmesas[j] = STOmesas[j] + (pasarAMinutos(T) - ITOmesas[j]);
			 					TPS[j].setHorarioDeSalida(sumarHoras(T,Ta));
			 					TPS[j].setCliente(cliente);
				 				STPSA = STPSA + pasarAMinutos(TPS[j].getHorarioDeSalida());
			 					ocuparMozo(TPS[j].getCliente());
			 					NMO++;
			 					System.out.println ("El cliente " + cliente.getNroCliente() + ", un asociado cualquiera, es atendido. Dejo datos muy interesantes (salida): ");
			 					System.out.println ("TPS (" + TPS[j].getHorarioDeSalida().getHourOfDay() + ":" + TPS[j].getHorarioDeSalida().getMinuteOfHour() + ":" + TPS[j].getHorarioDeSalida().getSecondOfMinute() + ") =" + " T (" + T.getHourOfDay() + ":" + T.getMinuteOfHour() + ":" + T.getSecondOfMinute() + ") +Ta (" + Ta.getHourOfDay() + ":" + Ta.getMinuteOfHour() + ":" + Ta.getSecondOfMinute() + ")");
			 				} else {
			 					NSA++;
			 					clientesAsociadosEnEspera.add(cliente);
			 					}
			 				
		 					} 
						else {
			 					NSA++;
			 					clientesAsociadosEnEspera.add(cliente);
			 				}
		 				}
		 			else{ 
		 				if(NSNA >= 5 ){
		 					if(R.nextDouble() <= 0.3){
		 						continue;
		 					}
						}
		 					
		 				NS++;
 						STPLLN = STPLLN + pasarAMinutos(TPLL);
		 				NTN++;

		 				
		 				if(NSNA == 0){
			 					if(NS <= M && NS <=4*N){
			 						Ta = TA();
			 						STaN = STaN + pasarAMinutos(Ta);
			 						int j = buscarHV();
			 						TPS[j].setHorarioDeSalida(sumarHoras(T,Ta));
			 						TPS[j].setCliente(cliente);
					 				STPSN = STPSN + pasarAMinutos(TPS[j].getHorarioDeSalida());
			 						System.out.println ("El cliente " + cliente.getNroCliente() + ", no-Asociado está siendo atendido. Datos a tener en cuenta: (llegada) ");
			 						System.out.println ("TPS (" + TPS[j].getHorarioDeSalida().getHourOfDay() + ":" + TPS[j].getHorarioDeSalida().getMinuteOfHour() + ":" + TPS[j].getHorarioDeSalida().getSecondOfMinute() + ") =" + " T (" + T.getHourOfDay() + ":" + T.getMinuteOfHour() + ":" + T.getSecondOfMinute() + ") +Ta (" + Ta.getHourOfDay() + ":" + Ta.getMinuteOfHour() + ":" + Ta.getSecondOfMinute() + ")");
			 						
			 						ocuparMozo(TPS[j].getCliente());
			 						NMO++;
			 				}
			 				else {
				 					NSNA++;
				 					clientesNoAsociadosEnEspera.add(cliente);
			 					}
		 					} 
			 				else {
				 					NSNA++;
				 					clientesNoAsociadosEnEspera.add(cliente);
								}
		 					}
		 				}
		 		else {	
		 			//Empieza salida
		 			T = TPS[i].getHorarioDeSalida();
		 			NS--;
		 			liberarMozo(TPS[i].getCliente());
		 			NMO--;
		 		
	 				Ta = TA();
	 				TPS[i].setHorarioDeSalida(sumarHoras(T,Ta));
		 			
		 			if(NS >= M || NS >= N*4){
		 				if(NSA > 0){
		 					NSA--;
		 					STaA = STaA + pasarAMinutos(Ta);
			 				STPSA = STPSA + pasarAMinutos(TPS[i].getHorarioDeSalida());
			 				TPS[i].setCliente(clientesAsociadosEnEspera.get(0));
			 				clientesAsociadosEnEspera.remove(0);
		 				}
		 				else{
		 					NSNA--;
		 					STaN = STaN + pasarAMinutos(Ta);
			 				STPSN = STPSN + pasarAMinutos(TPS[i].getHorarioDeSalida());
			 				TPS[i].setCliente(clientesNoAsociadosEnEspera.get(0));
							clientesNoAsociadosEnEspera.remove(0);
		 				}
		 				

		 				ocuparMozo(TPS[i].getCliente());
		 				NMO++;
		 			}
		 			else{
		 				TPS[i].setHorarioDeSalida(HVmenor);
		 				ITOmesas[i] = pasarAMinutos(T);
		 				int nroMozo = TPS[i].getCliente().getNroMozo();
		 				if (NMM[nroMozo] == 0) {
		 					ITOmozos[nroMozo] = pasarAMinutos(T);
		 				}

		 			}
		 			
		 		}
		 	
			
			if(T.compareTo(TF) >= 0) {	
				
				if(TPLL == HVmayor) {
					System.out.println ("Yo, el cliente " + TPS[i].getCliente().getNroCliente() + ", me retiro debido al hóstil desalojo. ¡Exijo el libro de quejas!");
					System.out.println ("T (" + T.getHourOfDay() + ":" + T.getMinuteOfHour() + ":" + T.getSecondOfMinute() + ") y TF (" + TF.getHourOfDay() + ":" + TF.getMinuteOfHour() + ":" + TF.getSecondOfMinute() + ")");
				}
				TPLL = HVmayor;
		 		 		
		 		if(NS == 0){
		 			imprimirResultados();		 			
		 			System.out.println ("Fin de la simulación.");
		 			break;
		 		}
			}
		 }
}

	public static LocalTime TA() {
		Random R = new Random();
		double resultado = (2-0.03125)* R.nextDouble() + 0.03125;
		int hora = (int) resultado;
		int minutos = (int) ((resultado*60) - (60*hora));
		int segundos = (int) ((resultado*60*60) - (60*60*hora + 60*minutos));
		
				
		LocalTime intervalo = new LocalTime (hora, minutos, segundos);
		return intervalo;
	}


	private static LocalTime IA() {
		Random R = new Random();

		while(true) {
		double resultado = R.nextDouble();
		double random = R.nextDouble();
		if((resultado * 0.03 + 0.47) >= (random * 12)) {
			
			int hora = (int) resultado;
			int minutos = (int) ((resultado*60) - (60*hora));
			int segundos = (int) ((resultado*60*60) - (60*60*hora + 60*minutos));
			
			LocalTime intervalo = new LocalTime (hora, minutos, segundos);
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
		int horaOpcional = 0;
		int minutoOpcional = 0;
		
		if(segundos >= 60) {
			segundos = segundos - 60;
			minutoOpcional++;
		}
		
		if(minutos + minutoOpcional >= 60) {
			minutos = minutos - 60;
			horaOpcional++;
		}
		
		if(horas + horaOpcional >= 24) {
			horas = horas - 24;
		}
	
		LocalTime horaSumada = new LocalTime(horas + horaOpcional, minutos + minutoOpcional, segundos);

		return horaSumada;
		
	}

/*	private static LocalTime restarHoras(LocalTime t, LocalTime ta) {
		
		int horas = t.getHourOfDay() - ta.getHourOfDay();
		int minutos = t.getMinuteOfHour() - ta.getMinuteOfHour();
		int segundos = t.getSecondOfMinute() - ta.getSecondOfMinute();
		
		if(minutos < 0) {
			horas--;
			minutos = minutos + 60;
		}
		
		if(segundos < 0) {
			minutos--;
			segundos = segundos + 60;
		}
		
		LocalTime horaRestada = new LocalTime(horas, minutos, segundos);

		return horaRestada;
		
	}*/
	
	private static double pasarAMinutos(LocalTime t) {
		
		double horas = t.getHourOfDay();
		double minutitos = t.getMinuteOfHour();
		double segundos = t.getSecondOfMinute();
		
		double horasToMinutas = horas*60;
		double segundosToMinutas = segundos/60;
		
		double minutos = horasToMinutas + minutitos + segundosToMinutas;
		return(minutos);
	}
	

	private static void ocuparMozo(Cliente cliente) throws InterruptedException {
		int i = 0; 
		int posMinima = -1;
		int cantMesasMinimas = 8;
		
		for(i=0; i<N; i++) {
			if(NMM[i] < cantMesasMinimas) {
				posMinima = i;
				cantMesasMinimas = NMM[posMinima];
			}
		}
		
		NMM[posMinima]++;
		
		cliente.setNroMozo(posMinima);
		if(NMM[posMinima] == 4){
			NM++;
		}
		
		if(NMM[posMinima] == 1) {
			STOmozos[posMinima] = STOmozos[posMinima]+(pasarAMinutos(T)-ITOmozos[posMinima]);
		}

	}

	public static int buscarMenorTPS() {
		
		int i = 0;
		int posMinima = 0;
		LocalTime horaMinima = TPS[0].getHorarioDeSalida();
		
		for (i = 0; i < TPS.length; i++) {
			if(TPS[i].getHorarioDeSalida().compareTo(horaMinima) < 0) {
				posMinima = i;
				horaMinima = TPS[posMinima].getHorarioDeSalida();
			}		
		} 
		
		return posMinima;
	}
	
	public static int buscarHV() {
		
		for (int posMesaLibre = 0; posMesaLibre < TPS.length; posMesaLibre++) {
			if(TPS[posMesaLibre].getHorarioDeSalida().compareTo(HVmenor) == 0) {
				return(posMesaLibre);
			}
		}
		 throw new RuntimeException("No existe mesa disponible, error.");
	}


private static void inicializarVariables() {
 	TPS = new TiempoDeSalida[M];
 	STOmesas = new double[M];
 	ITOmesas = new double[M];
 	ITOmozos = new double[N];
 	STOmozos = new double[N];
 	NMM = new int [N];
	int i;
	
	 for(i = 0; i<M; i++) {
		 	TPS[i] = new TiempoDeSalida(HVmenor);
			 ITOmesas[i] = 9*60;
		 }
		 
		 for(i = 0; i<N; i++) {
			 ITOmozos[i] = 9*60;
		 }
}
	
private static void imprimirResultados() {
	
	int i;
	
    System.out.println ("Variables de resultados:");
    System.out.println ("Promedio de tiempo ocioso de cada mozo:");
    
    for(i=0; i<N; i++) {
    	if(STOmozos[i] == 0)
            System.out.println ("STOmozo" + (i+1) + ":" + "100%");
    	else
    		System.out.println ("STOmozo" + (i+1) + ": " + STOmozos[i]/pasarAMinutos(T)*100 + "%");
    }
    
    System.out.println ("Promedio de tiempo ocioso de cada mesa:");
    
    for(i=0; i<M; i++) {
    	if(STOmesas[i] == 0)
            System.out.println ("STOmesa" + (i+1) + ": " + "100%");
    	else
        System.out.println ("STOmesa" + (i+1) + ": " + STOmesas[i]/pasarAMinutos(T)*100 + "%");
    }
    
    System.out.println ("Valores: STPSA es " + STPSA + ", STPLLA es " + STPLLA +  " NTA es " + NTA + " y STaA da " + STaA);
    System.out.println ("Promedio de permanencia en el sistema de asociados: " + (STPSA - STPLLA)/NTA + "%");
    System.out.println ("Promedio de permanencia en el sistema de no-asociados: " + (STPSN - STPLLN)/NTN + "%");
    System.out.println ("Promedio de espera en cola de asociados: " + (STPSA - STPLLA - STaA)/NTA + "%");	
    System.out.println ("Promedio de espera en cola de no-asociados: " + (STPSN - STPLLN - STaN)/NTN + "%");
    System.out.println ("ASDASDASD");
    System.out.println ("---------------------------------------------------------------------------");
    System.out.println ("Habiendo utilizado las variables:");
    System.out.println ("Cant de mesas: " + M);   
    System.out.println ("Cant de mozos: " + N);
	}
}