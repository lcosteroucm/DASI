package icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.tareas;

import java.util.List;

import icaro.aplicaciones.MRS.informacion.ListaIds;
import icaro.aplicaciones.MRS.informacion.Rescatador;
import icaro.aplicaciones.MRS.informacion.Robot;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.ControlEvaluacionVictimas;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.EvaluacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.informacion.MsgAsignacionObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionRescatadorMRS.objetivos.AlcanzarVictima;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * Esta tarea resuelve el objetivo 'InformarSoyElMejorRobot',
 * enviando un mensaje al resto de agentes rescatadores.
 * Seguidamente se inicializa el componente de movimiento hacia la victima asignada.
 * El siguiente objetivo que se fija es 'AlcanzarVictima'.
 */
public class InformarAutoasignacionVictima extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {	
		

		/* 
		 * params[0] -> nombre del agente
		 * params[1] -> yo
		 * params[2] -> fc
		 * params[3] -> MisObjetivos
		 * --------------------------------
		 * params[4] -> Objetivo Actual
		 * params[5] -> Objetivo que focalizar al salir.
		 * params[6] -> robots
		 * params[7] -> controlEvaluacionVictimas
		 * params[8] -> evaluacionObjetivo
		 */
		String agentId 	= (String) params[0];
		Robot yo 		= (Robot) params[1];
		Focus fc 		= (Focus) params[2];
		MisObjetivos mo = (MisObjetivos) params[3];
		
		Objetivo obj	 = (Objetivo) params[4];
		Objetivo obj2	 = (Objetivo) params[5];
		ListaIds lr 	 = (ListaIds) params[6];
		
		ControlEvaluacionVictimas ce = (ControlEvaluacionVictimas) params[7];
		EvaluacionObjetivo eo 		 = (EvaluacionObjetivo) params[8];
		//----------------------------------------------------------------------
		
		//Enviar mensaje de que soy el mejor al resto de robots
		List<String> agtes = lr.getNames();
		this.comunicator = this.getComunicator();
		
		for(String s : agtes){
			comunicator.enviarInfoAotroAgente(new MsgAsignacionObjetivo(agentId, 
					eo.getVictimaName()), s);
		}
		
		//Actualizar info: (eliminar objetivo, eliminar lista de evaluaciones)
		obj.setSolved();
		this.getEnvioHechos().eliminarHechoWithoutFireRules(obj);
		this.getEnvioHechos().eliminarHechoWithoutFireRules(eo);
		ce.eliminaVictima(eo.getVictimaName());
		ce.setRobotAsignado(agentId);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(ce);
		fc.setFoco(obj2);
		this.getEnvioHechos().actualizarHechoWithoutFireRules(fc);
		
		
		//Mando mover a mi componente de movimiento
		((Rescatador)yo).compInternoMovimineto.setItHechos(this.getEnvioHechos());
		((Rescatador)yo).compInternoMovimineto.setDestino(eo.victimaObjetivo.getCoordenadasIniciales());
		
		//Creo lel objetivo de alcanzar a la victima
		Objetivo obj3 = new AlcanzarVictima(eo.victimaName);
		obj3.setSolving();
		mo.addObjetivo(obj3);
		
		this.getEnvioHechos().actualizarHechoWithoutFireRules(mo);
		this.getEnvioHechos().insertarHecho(obj3);
		
		
		//----------------------------------------------------------------------
		// Informar mediante trazas
		trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
		trazas.aceptaNuevaTraza(new InfoTraza(this.identAgente,
				"Recibido el mensaje de que soy el mejor robot para la victima : "
				+ eo.getVictimaName(),
				InfoTraza.NivelTraza.info));
	}
}
