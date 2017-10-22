package pe.edu.cibertec.jsf.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_resultado")
public class Resultado {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	private Pais pais;

	private Integer partidosJugados;
	private Integer partidosGanados;
	private Integer partidosPerdidos;
	private Integer partidosEmpatados;
	private Integer golesAnotados;
	private Integer puntos;

	public static Resultado getNuevo(Integer pais) {
		Resultado res = new Resultado();
		res.setId(null);
		res.setPais(new Pais());
		res.getPais().setId(pais);
		res.setPartidosJugados(0);
		res.setPartidosGanados(0);
		res.setPartidosPerdidos(0);
		res.setPartidosEmpatados(0);
		res.setGolesAnotados(0);
		return res;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Integer getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(Integer partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public Integer getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(Integer partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public Integer getPartidosPerdidos() {
		return partidosPerdidos;
	}

	public void setPartidosPerdidos(Integer partidosPerdidos) {

		this.partidosPerdidos = partidosPerdidos;
	}

	public Integer getPartidosEmpatados() {
		return partidosEmpatados;
	}

	public void setPartidosEmpatados(Integer partidosEmpatados) {
		this.partidosEmpatados = partidosEmpatados;
	}

	public Integer getGolesAnotados() {
		return golesAnotados;
	}

	public void setGolesAnotados(Integer golesAnotados) {
		this.golesAnotados = golesAnotados;
	}

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}

}
