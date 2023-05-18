package controlador;
import java.util.Collections;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.Scanner;

import modelo.Asignaciones;
import modelo.Descansos;
import modelo.Dias;
import modelo.Mesas;
import modelo.Trabajadores;
import modelo.Turnos;
public class Principal {

static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		int counter;
		
		@SuppressWarnings("deprecation")
		Time amini = new Time(8,0,0);
		@SuppressWarnings("deprecation")
		Time amfin = new Time(16,0,0);
		@SuppressWarnings("deprecation")
		Time pmini = new Time(16,0,0);
		@SuppressWarnings("deprecation")
		Time pmfin = new Time(24,0,0);
		
		Turnos am = Turnos.insert(amini, amfin);
		Turnos pm = Turnos.insert(pmini, pmfin);
		
		do {
			System.out.println("---------- PIT BOSS 0.2 ----------\n|  ");
			System.out.println("| 1) Mesas");
			System.out.println("| 2) Empleados");
			System.out.println("| 3) Turnos");
			System.out.println("| 4) Descansos");
			System.out.println("| 5) Asignaciones");
			System.out.println("| 6) Calendario");
			System.out.println("| 7) Automatizar día");
			System.out.println("|\n");
			System.out.println("Introduzca un número: ");
			
			String opcion = sc.nextLine();
			
			if(opcion.equals("1")) {
				System.out.println("---------- MESAS ----------\n|  ");
				System.out.println("| 1) Mostrar todas");
				System.out.println("| 2) Buscar por juego");
				System.out.println("| 3) Borrar mesa");
				System.out.println("| 4) Modificar mesa");
				System.out.println("| 5) Crear mesa");
				System.out.println("| 6) Borrar todas las mesas [No se puede deshacer]");
				System.out.println("|\n");
				System.out.println("Introduzca un número: ");
				String opcionmesa = sc.nextLine();
				
				if(opcionmesa.equals("1")) {
					System.out.println("\n\n");
					System.out.println("---------- MESAS ----------\n|  ");
					LinkedList<Mesas> mesas = Mesas.find(null, null);
					for(Mesas m: mesas) {
						System.out.println("| Mesa "+m.getIdMesa()+" | "+m.getNombre()+" | "+m.getEstado());
					}
					System.out.println("|");
				}
				
				if(opcionmesa.equals("2")) {
					System.out.println("\n\n");
					System.out.println("Nombre del juego: ");
					String nomjuego = sc.nextLine();
					LinkedList<Mesas> mesaporjuego = Mesas.find(nomjuego, null);
					System.out.println("---------- MESAS DE "+nomjuego+" ----------");
					for(Mesas m : mesaporjuego) {
						System.out.println("| Mesa " + m.getIdMesa()+": " +m.getEstado());
					}
					
				}
				
				if(opcionmesa.equals("3")) {
					System.out.println("\n\n");
					System.out.println("---------- MESAS ----------");
					System.out.println("ID de la mesa: ");
					int idm = Integer.parseInt(sc.nextLine());
					Mesas.delete(idm);
				}
				
				if(opcionmesa.equals("4")) {
					System.out.println("\n\n");
					System.out.println("---------- MESAS ----------\n|  ");
					System.out.println("| ID De la mesa a modificar: ");
					int idm = Integer.parseInt(sc.nextLine());
					System.out.println("| 1) Cambiar juego");
					System.out.println("| 2) Cambiar estado");
					
					String opcimodi = sc.nextLine();
					if(opcimodi.equals("1")) {
						System.out.println("Nombre del juego: ");
						String njueg = sc.nextLine();
						Mesas.update(idm, njueg, null);
					}
					
					if(opcimodi.equals("2")) {
						boolean err=false;
						String es="";
						System.out.println("Estado (Libre, Cerrada, Ocupada): ");
						do {
							es = sc.nextLine();
						
							if((!es.equalsIgnoreCase("libre") && !es.equalsIgnoreCase("Cerrada") && !es.equalsIgnoreCase("ocupada"))) {
								System.out.println("Error! El estado debe ser Libre, Cerrada u Ocupada");
								err = true;
							}else {
								err=false;
							}
						}while(err==true);
						Mesas.update(idm, null, es);
					}
				}
				
				if(opcionmesa.equals("5")) {
					System.out.println("\n\n");
					System.out.println("Nombre del juego: ");
					String nommesa = sc.nextLine();
					Mesas.insert(nommesa);
					
				}
				
				if(opcionmesa.equals("6")) {
					System.out.println("\n\n");
					Mesas.clear();
				}
			}
			
			if(opcion.equals("2")) {
				System.out.println("---------- TRABAJADORES ----------\n|  ");
				System.out.println("| 1) Mostrar todos");
				System.out.println("| 2) Buscar por nombre");
				System.out.println("| 3) Dar de baja");
				System.out.println("| 4) Dar de alta");
				System.out.println("| 5) Editar trabajador");
				System.out.println("| 6) Borrar todas los trabajadores [No se puede deshacer]");
				System.out.println("|\n");
				System.out.println("Introduzca un número: ");
				String opciontra = sc.nextLine();
				
				if(opciontra.equals("1")) {
					System.out.println("\n\n");
					System.out.println("---------- TRABAJADORES ----------\n|");
					LinkedList<Trabajadores> trabajadores = Trabajadores.find(null, null);
					for(Trabajadores t:trabajadores) {
						System.out.println("| ID: "+t.getIdTrabajdor()+" | Nombre: "+t.getNombre()+" | Cargo: "+t.getCargo());
					}
				}
				
				if(opciontra.equals("2")) {
					System.out.println("\n\n");
					System.out.println("Nombre del trabajador: ");
					String nomtra = sc.nextLine();
					LinkedList<Trabajadores> trabajadorpornombre  = Trabajadores.find(nomtra, null);
					for(Trabajadores t: trabajadorpornombre) {
						System.out.println("| ID Trabajador: " + t.getIdTrabajdor());
					}
					
				}
				
				if(opciontra.equals("3")) {
					System.out.println("\n ID Del trabajador: ");
					int idtr = Integer.parseInt(sc.nextLine());
					Trabajadores.delete(idtr);
				}
				
				if(opciontra.equals("4")) {
					System.out.println("\n Nombre del trabajador: ");
					String nomt = sc.nextLine();
					System.out.println("\n Cargo del trabajador: ");
					String cartr = sc.nextLine();
					
					Trabajadores.insert(nomt, cartr);
				}
				
				if(opciontra.equals("5")) {
					System.out.println("\n ID Trabajador a editar: ");
					int idtedit = Integer.parseInt(sc.nextLine());
					
					System.out.println("\n | 1) Editar nombre");
					
					System.out.println("\n | 2) Editar cargo");
					String editop = sc.nextLine();
					
					if(editop.equals("1")) {
						System.out.println("Nombre nuevo: ");
						String nunomt = sc.nextLine();
						Trabajadores.update(idtedit, nunomt,null );
						
					}
					
					if(editop.equals("2")) {
						System.out.println("Cargo nuevo: ");
						String nucart = sc.nextLine();
						Trabajadores.update(idtedit, null,nucart );
					}
				}
				
				if(opciontra.equals("6")) {
					Trabajadores.clear();
					System.out.println("Todos los trabajadores han sido borrados. \n");
				}
			}
			
			if(opcion.equals("3")) {
				System.out.println("---------- TURNOS ----------\n|  ");				
				System.out.println("| 1) Mostrar todos");
				System.out.println("| 2) Borrar turno");
				System.out.println("| 3) Modificar turno");
				System.out.println("| 4) Crear turno");
				System.out.println("| 5) Borrar todos los turnos [No se puede deshacer]");
				System.out.println("|\n");
				System.out.println("Introduzca un número: ");
				String opcionmesa = sc.nextLine();
				
				if(opcionmesa.equals("1")) {
					System.out.println("\n\n");
					System.out.println("---------- TURNOS ----------\n|");
					LinkedList<Turnos> turnos = Turnos.find(null, null);
					for(Turnos t:turnos) {
						System.out.println("| "+t.getIdTurno()+" | "+t.getHoraIni()+" -> "+t.getHoraFin());
					}
				}
				
				if(opcionmesa.equals("2")) {
					System.out.println("ID Del turno a borrar: ");
					int idtbor = Integer.parseInt(sc.nextLine());
					Turnos.delete(idtbor);
				}
				
				if(opcionmesa.equals("3")) {
					System.out.println("ID Del turno a modificar");
					int idtmod = Integer.parseInt(sc.nextLine());
					System.out.println("\n\n");
					System.out.println("1) Cambiar Hora Inicio");
					System.out.println("2) Cambiar Hora Fin");
					String opcimodtur = sc.nextLine();
					
					if(opcimodtur.equals("1")) {
						Time ti = Turnos.alterhora();
						Turnos.update(idtmod, ti, null);
					}
					if(opcimodtur.equals("2")) {
						Time ti = Turnos.alterhora();
						Turnos.update(idtmod, null, ti);
					}
				}
				
				if(opcionmesa.equals("4")) {
					System.out.println("Hora de inicio del turno: ");
					Time hoini = Turnos.alterhora();
					
					System.out.println("Hora fin del turno: ");
					Time hofin = Turnos.alterhora();
					
					Turnos.insert(hoini, hofin);
				}
				
				if(opcionmesa.equals("5")) {
					Turnos.clear();
					System.out.println("Todos los turnos eliminados");
				}
			}
			
			if(opcion.equals("4")) {
				System.out.println("---------- DESCANSOS ----------\n|  ");				
				System.out.println("| 1) Mostrar todos");
				System.out.println("| 2) Buscar por trabajador");
				System.out.println("| 3) Borrar descanso");
				System.out.println("| 4) Modificar descanso");
				System.out.println("| 5) Crear descanso");
				System.out.println("| 6) Borrar todos los descansos [No se puede deshacer]");
				System.out.println("|\n");
				System.out.println("Introduzca un número: ");
				String opciondescans = sc.nextLine();
				
				if(opciondescans.equals("1")) {
					System.out.println("\n\n");
					System.out.println("---------- DESCANSOS ----------\n|  ");
					LinkedList<Descansos> descansos = Descansos.find(null, null);
					for(Descansos d:descansos) {
						System.out.println("| "+d.getIdDescanso()+" | ID Trabajador: "+d.getIdTrabajador()+" | "+d.getFecha()+" | "+d.getHoraIni()+" -> "+d.getHoraFin());
					}
				}
				
				if(opciondescans.equals("2")) {
					System.out.println("\n\n");
					System.out.println("ID Del trabajador : ");
					String idtd = sc.nextLine();
					LinkedList<Descansos> descansostr = Descansos.find(idtd, null);
					System.out.println("---------- DESCANSOS TRABAJADOR "+idtd+"----------\n|");
					for(Descansos d:descansostr) {
						System.out.println("| "+d.getIdDescanso()+" | "+d.getFecha()+" | "+d.getHoraIni()+" -> "+d.getHoraFin());
					}
				}
				
				if(opciondescans.equals("3")) {
					System.out.println("ID Del descanso a borrar: ");
					int iddbor = Integer.parseInt(sc.nextLine());
					Descansos.delete(iddbor);
				}
				
				if(opciondescans.equals("4")) {
					System.out.println("ID Del descanso a modificar: ");
					int iddmod = Integer.parseInt(sc.nextLine());
					
					System.out.println("1) Cambiar fecha");
					System.out.println("2) Cambiar hora inicio");
					System.out.println("3) Cambiar hora fin");
					
					String opmodd=sc.nextLine();
					
					if(opmodd.equals("1")) {
						Date dot = Descansos.alterdate();
						Descansos.update(iddmod, dot, null, null);
					}
					
					if(opmodd.equals("2")) {
						Time tin = Descansos.alterhora();
						Descansos.update(iddmod, null, tin, null);
					}
					
					if(opmodd.equals("3")) {
						Time tfn = Descansos.alterhora();
						Descansos.update(iddmod, null, null, tfn);
					}
				}
				
				if(opciondescans.equals("5")) {
					System.out.println("ID Trabajador: ");
					int idt = Integer.parseInt(sc.nextLine());
					System.out.println("Fecha: ");
					Date dat = Descansos.alterdate();
					System.out.println("Hora inicio: ");
					Time fini = Descansos.alterhora();
					System.out.println("Hora fin; ");
					Time ffin = Descansos.alterhora();
					
					Descansos.insert(idt, dat, fini, ffin);
				}
				
				if(opciondescans.equals("6")) {
					Descansos.clear();
					System.out.println("Todos los descansos borrados: ");
				}
				
				
			}
			if(opcion.equals("5")) {
				System.out.println("---------- ASIGNACIONES ----------\n|  ");				
				System.out.println("| 1) Mostrar todas");
				System.out.println("| 2) Buscar por trabajador");
				System.out.println("| 3) Buscar por mesa");
				System.out.println("| 4) Buscar por turno");
				System.out.println("| 5) Borrar asignacion");
				System.out.println("| 6) Modificar descanso [NO TERMINADO]");
				System.out.println("| 7) Crear asignación");
				System.out.println("| 8) Borrar todas las asignaciones [No se puede deshacer]");
				System.out.println("|\n");
				System.out.println("Introduzca un número: ");
				String opcionasig = sc.nextLine();
				
				if(opcionasig.equals("1")) {
					System.out.println("\n\n");
					System.out.println("---------- ASIGNACIONES ----------\n|  ");	
					LinkedList<Asignaciones> asignaciones = Asignaciones.find(null, null, null, null,null);
					for(Asignaciones a:asignaciones) {
						System.out.println("| ID: "+a.getIdAsignacion()+" | ID Mesa: "+a.getIdMesa()+" | ID Trabajador "+a.getIdTrabajador()+" | ID Turno: "+ a.getIdTurno());
					}
				}
				
				if(opcionasig.equals("2")) {
					System.out.println("ID Del trabajador: ");
					int idt = Integer.parseInt(sc.nextLine());
					LinkedList<Asignaciones> asigtra = Asignaciones.find(null, idt+"", null, null,null);
					System.out.println("---------- ASIGNACIONES TRABAJADOR "+idt+"----------\n|  ");
					for(Asignaciones a:asigtra) {
						System.out.println("| ID: "+a.getIdAsignacion()+" | ID Mesa: "+a.getIdMesa()+" | ID Turno: "+ a.getIdTurno());
					}
				}
				
				if(opcionasig.equals("3")) {
					System.out.println("ID De la mesa: ");
					int idt = Integer.parseInt(sc.nextLine());
					LinkedList<Asignaciones> asigtra = Asignaciones.find(idt+"",null,null , null, null);
					System.out.println("---------- ASIGNACIONES MESA "+idt+"----------\n|  ");
					for(Asignaciones a:asigtra) {
						System.out.println("| ID: "+a.getIdAsignacion()+" | ID Trabajador: "+a.getIdTrabajador()+" | ID Turno: "+ a.getIdTurno());
					}
				}
				
				if(opcionasig.equals("4")) {
					System.out.println("ID Del turno: ");
					int idt = Integer.parseInt(sc.nextLine());
					LinkedList<Asignaciones> asigtra = Asignaciones.find(null, null,idt+"", null, null);
					System.out.println("---------- ASIGNACIONES TURNO "+idt+"----------\n|  ");
					for(Asignaciones a:asigtra) {
						System.out.println("| ID: "+a.getIdAsignacion()+" | ID Mesa: "+a.getIdMesa()+" | ID Trabajador: "+ a.getIdTrabajador());
					}
				}
				
				if(opcionasig.equals("5")) {
					System.out.println("ID De la asignación a borrar: ");
					int idab = Integer.parseInt(sc.nextLine());
					
					Asignaciones.delete(idab);
				}
				
				if(opcionasig.equals("7")) {
					System.out.println("ID De la mesa: ");
					int idm=Integer.parseInt(sc.nextLine());
					
					System.out.println("ID Del trabajador: ");
					int idt=Integer.parseInt(sc.nextLine());
					
					System.out.println("ID Del turno: ");
					int idtt=Integer.parseInt(sc.nextLine());
					
					System.out.println("ID Del día: ");
					int idd = Integer.parseInt(sc.nextLine());
					
					Asignaciones.insert(idm, idt, idtt, idd);
				}
				
				if(opcionasig.equals("8")) {
					Asignaciones.clear();
					System.out.println("Todas las asignaciones borradas");
				}
			}
			
			if(opcion.equals("6")) {
				System.out.println("1) Crear día");
				System.out.println("2) Ver días y sus asignaciones");
				String opsies = sc.nextLine();
				if(opsies.equals("1")) {
					Dias.insert(Descansos.alterdate());
				}
				
				if(opsies.equals("2")) {
					LinkedList<Dias> dias = Dias.find(null);
					System.out.println("---------- DIAS ----------");
					for(Dias d:dias) {
						System.out.println("| ID DIA:  "+d.getIdDia()+" | "+d.getDia());
					};
					
					System.out.println("Seleccionar id día: ");
					int idea = Integer.parseInt(sc.nextLine());
					
					LinkedList<Asignaciones> asigdi = Asignaciones.find(null, null, null, idea+"", null);
					for(Asignaciones a:asigdi) {
						System.out.println("| ID Asignación: "+ a.getIdAsignacion()+" | ID Mesa: "+a.getIdMesa()+" | ID Trabajador" + a.getIdTrabajador()+" | ID Turno : " + a.getIdTurno());
					}
				}
				
			}
			
			if(opcion.equals("7")) {
				counter=0;
				System.out.println("Fecha a automatizar: ");
				Date moddate = Descansos.alterdate();
				Dias d = Dias.insert(moddate);
				LinkedList<Trabajadores> personaljornada = new LinkedList<Trabajadores>();
				LinkedList<Mesas> mesasdispo = Mesas.find(null, null);
				int mesasdisspo = mesasdispo.size();
				int requeridos = Math.round(mesasdisspo + (mesasdisspo/2));
				do {
					for(Trabajadores t:Trabajadores.find(null, null)) {
						System.out.println("| ID Trabajador: "+t.getIdTrabajdor()+" | Cargo: "+t.getCargo()+" | Nombre: "+t.getNombre());
					}
					System.out.println("ID Del trabajador a añadir: ");
					int idele = Integer.parseInt(sc.nextLine());
					
					for(Trabajadores t:Trabajadores.find(null, null)) {
						if(t.getIdTrabajdor()==idele) {
							personaljornada.add(t);
							counter++;
						}
					}
				
						System.out.println("Llevas "+counter+"trabajadores de "+requeridos +" requeridos");
						
					
					}while(counter < requeridos);
				
				Collections.shuffle(personaljornada);
				
			}
		}while(1==1);
	}

}
