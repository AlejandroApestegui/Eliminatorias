package pe.edu.cibertec.jsf.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pe.edu.cibertec.jsf.domain.Juego;
import pe.edu.cibertec.jsf.domain.Pais;
import pe.edu.cibertec.jsf.domain.Resultado;
import pe.edu.cibertec.jsf.util.JPAUtil;

@ManagedBean
@SessionScoped
public class CResultado {

	private Juego juego;
	private List<Pais> lstPaises;
	private List<Resultado> lstResultados;
	private String mensaje;

	@PostConstruct
	public void init() {
		juego = new Juego();
		System.out.println("ini");
		JPAUtil.getEntityManager();
		EntityManager entityManager = JPAUtil.getEntityManager();
		lstPaises = entityManager.createQuery("select a from Pais a").getResultList();
		lstResultados = entityManager
				.createQuery("select a from Resultado a order by a.puntos desc, a.golesAnotados desc").getResultList();
	}

	public String guardar() {

		System.out.println("askdhsajk");
		mensaje = "";

		if (juego.pais1 == juego.pais2) {
			mensaje = "Los países seleccionados deben ser distintos";
			return null;
		}

		if (juego.goles1 < 0 || juego.goles2 < 0) {
			mensaje = "Los goles son en números positivos";
			return null;
		}

		EntityManager entityManager = JPAUtil.getEntityManager();
		System.out.println(juego.pais1);
		System.out.println(juego.pais2);

		List l1 = entityManager.createQuery("select a from Resultado a where a.pais.id = ?")
				.setParameter(1, juego.pais1).getResultList();
		Resultado resultado1 = l1.size() > 0 ? (Resultado) l1.get(0) : Resultado.getNuevo(juego.pais1);

		List l2 = entityManager.createQuery("select a from Resultado a where a.pais.id = ?")
				.setParameter(1, juego.pais2).getResultList();
		Resultado resultado2 = l2.size() > 0 ? (Resultado) l2.get(0) : Resultado.getNuevo(juego.pais2);

		resultado1.setPartidosJugados(resultado1.getPartidosJugados() + 1);
		resultado2.setPartidosJugados(resultado2.getPartidosJugados() + 1);

		if (juego.goles1 > juego.goles2) {
			resultado1.setPartidosGanados(resultado1.getPartidosGanados() + 1);
			resultado2.setPartidosPerdidos(resultado2.getPartidosPerdidos() + 1);
		} else if (juego.goles1 < juego.goles2) {
			resultado2.setPartidosGanados(resultado2.getPartidosGanados() + 1);
			resultado1.setPartidosPerdidos(resultado1.getPartidosPerdidos() + 1);
		} else {
			resultado1.setPartidosEmpatados(resultado1.getPartidosEmpatados() + 1);
			resultado2.setPartidosEmpatados(resultado2.getPartidosEmpatados() + 1);
		}

		resultado1.setGolesAnotados(resultado1.getGolesAnotados() + juego.goles1);
		resultado2.setGolesAnotados(resultado2.getGolesAnotados() + juego.goles2);
		EntityTransaction entityTransaction = entityManager.getTransaction();

		resultado1.setPuntos((resultado1.getPartidosGanados() * 3) + (resultado1.getPartidosEmpatados()));
		resultado2.setPuntos((resultado2.getPartidosGanados() * 3) + (resultado2.getPartidosEmpatados()));

		entityTransaction.begin();

		entityManager.merge(resultado1);
		entityManager.merge(resultado2);

		entityTransaction.commit();

		lstResultados = entityManager
				.createQuery("select a from Resultado a order by a.puntos desc, a.golesAnotados desc").getResultList();

		juego = new Juego();

		return "index";
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public List<Pais> getLstPaises() {
		return lstPaises;
	}

	public void setLstPaises(List<Pais> lstPaises) {
		this.lstPaises = lstPaises;
	}

	public List<Resultado> getLstResultados() {
		return lstResultados;
	}

	public void setLstResultados(List<Resultado> lstResultados) {
		this.lstResultados = lstResultados;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
