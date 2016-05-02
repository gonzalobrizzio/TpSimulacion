package tpDeSimulacion;

import org.joda.time.LocalTime;

public class TiempoDeSalida {
	private Cliente cliente;
	private LocalTime horarioDeSalida;
	
	public LocalTime getHorarioDeSalida() {
		return horarioDeSalida;
	}
	
	public void setHorarioDeSalida(LocalTime horarioDeSalida) {
		this.horarioDeSalida = horarioDeSalida;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

}
