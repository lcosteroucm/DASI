package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;


import java.util.List;

import icaro.aplicaciones.MRS.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgEvaluacionRobot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.ConocerEquipo;
import icaro.aplicaciones.recursos.recursoPlanificadorRuta.ItfUsoRecursoPlanificadorRuta;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class ProcesarSolicitudAyuda extends TareaSincrona {

	/*
	 * Inicializa las estructuras necesarias para procesar la petición de ayuda
	 * 
	 * 0: Mensaje de ayuda
	 * 1: control evaluacion
	 * 2: lista de robots
	 * 3: Robot yo
	 */
	
	@Override
	public void ejecutar(Object... params) {
		//t1.ejecutar(agentId, robots, obj, fc, yo, ce, msg);
		
		String name = (String) params[0];
		ListaIds lr = (ListaIds) params[1];
		Objetivo o = (Objetivo) params[2];
		Focus f = (Focus) params[3];
		Robot yo = (Robot) params[4];
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas)params[5];
		SolicitudAyuda sa = (SolicitudAyuda)params[6];
		
		
		Victima minero = sa.getVictima();
		String mineroName = minero.getName();
				
		
		ce.addVictima(mineroName);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(ce);
		
		
		EvaluacionObjetivo eo = new EvaluacionObjetivo(minero, lr.size()+1);

		int coste = 0;
		
		//Actualizamos la información propia del propio robot
		try {
			ItfUsoRecursoPlanificadorRuta pr = (ItfUsoRecursoPlanificadorRuta)
					this.repoInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoPlanificadorRuta1");
			
			assert(pr != null);
			List<Coordenada> l = pr.getRuta(yo.getCoordenadasIniciales(), minero.getPosicion());
			
			eo.addEvaluacion(yo.getName(), l.size());
			
			this.getEnvioHechos().insertarHechoWithoutFireRules(eo);
			
			//Informamos al resto de robots de nuestra evaluacion
			MsgEvaluacionRobot msg = new MsgEvaluacionRobot(yo.getName(), minero.getName(),
															l.size());
			
			List<String> ls = lr.getNames();
			this.comunicator = this.getComunicator();
			for(String s : ls){
				comunicator.enviarInfoAotroAgente(msg, s);
			}
			
			coste = l.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Eliminamos el mensaje de solicitud de ayuda para no entrar a la regla otra vez
		this.getEnvioHechos().eliminarHecho(sa);
		
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibida la peticion de ayuda de " + minero + " evaluada con coste " + coste,
				InfoTraza.NivelTraza.info));
		
	}	
}