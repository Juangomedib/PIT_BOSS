package modelo;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Asignaciones{
	static Scanner sc = new Scanner(System.in);
	private int id_asignacion;
	private int id_mesa;
	private int id_trabajador;
	private int id_turno;
	private int id_dia;
	
	private Asignaciones(int ida, int idm, int idt, int idtt,int idd) {
		this.id_asignacion=ida;
		this.id_mesa=idm;
		this.id_trabajador=idt;
		this.id_turno=idtt;
		this.id_dia=idd;
	}
	
	public int getIdAsignacion() {
		return this.id_asignacion;
	}
	
	public int getIdMesa() {
		return this.id_mesa;
	}
	
	public int getIdTrabajador() {
		return this.id_trabajador;
	}
	
	public int getIdTurno() {
		return this.id_turno;
	}
	
	public static LinkedList<Asignaciones> find(String idm, String idt, String idtt, String idd,String campo_orden){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Asignaciones> lista = new LinkedList<Asignaciones>();
		String sql="Select * from asignaciones where ";
		if(idm!=null) sql+="id_mesa like ? and";
		if(idt!=null) sql+= "id_trabajador like ? and ";
		if(idtt!=null) sql+="id_turno like ? and ";
		if(idd!=null) sql+="id_dia like ? and ";
		sql+="1=1";
		if(campo_orden!=null) sql+="order by "+campo_orden;
		
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(idm!=null) stm.setString(1, "%"+idm+"%");
			if(idt!=null) stm.setString(1, "%"+idt+"%");
			if(idtt!=null) stm.setString(1, "%"+idtt+"%");
			if(idd!=null) stm.setString(1, "%"+idd+"%");
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Asignaciones a = new Asignaciones(respuesta.getInt("ID_Asignacion"),
						respuesta.getInt("ID_Mesa"),
						respuesta.getInt("ID_Trabajador"),
						respuesta.getInt("ID_Turno"),
						respuesta.getInt("id_dia"));
				lista.add(a);
			}
			
			stm.close();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	public static boolean delete(int id) {
		Conexion.open();
		PreparedStatement stm;
		
		try {
			stm=Conexion.con.prepareStatement("delete from asignaciones where ID_Asignacion=?");
			stm.setInt(1, id);
			int num=stm.executeUpdate();
			stm.close();
			if(num>0) return true;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean delete() {
		return Asignaciones.delete(this.getIdAsignacion());
	}
	
	
	/*public static boolean update(int id, Date dat, Time hini, Time hfin) {
		Conexion.open();
		String sql="";
		PreparedStatement stm;
		
		try {
			if(hini!=null || hfin !=null || dat!=null) {
				sql="Update descansos set ";
				if(dat!=null)sql+="Fecha="+"\""+dat+"\"";
				if(dat!=null && hini!=null) sql+=" and ";
				if(hini!=null) sql+="Hora_Inicio="+"\""+hini+"\"";
				if(hini!=null && hfin!=null) sql+=" and ";
				if(hfin!=null) sql+="Hora_Fin="+"\""+hfin+"\"";
				sql+=" where ID_Descanso= ? ";
				stm=Conexion.con.prepareStatement(sql);
				
				stm.setInt(1, id);
				
				int num=stm.executeUpdate();
				stm.close();
				if(num>0) return true;
			}
			
			
		}catch(SQLException e) {
			System.out.println(sql);
			System.out.println(e.getMessage());
		}
		return false;
	}*/
	
	public static Asignaciones load(int id) {
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Select * from Asignaciones where ID_Asignacion=?");
			stm.setInt(1, id);
			
			respuesta=stm.executeQuery();
			
			if(respuesta.next()) {
				Asignaciones a = new Asignaciones(respuesta.getInt("ID_Asignacion"),
						respuesta.getInt("ID_Mesa"),
						respuesta.getInt("ID_Trabajador"),
						respuesta.getInt("ID_Turno"),
						respuesta.getInt("id_dia"));
				return a;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Asignaciones insert(int idm, int idt, int idtt, int idd) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into Asignaciones (ID_Mesa, ID_Trabajador, ID_Turno, id_dia) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, idm);
			stm.setInt(2, idt);
			stm.setInt(3, idtt);
			stm.setInt(4, idd);
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);

			
			Asignaciones a=new Asignaciones(auto,idm,idt,idtt,idd);
			stm.close();
			return a;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static void clear() {
		Conexion.open();
		PreparedStatement stm;
		
		try {
			stm=Conexion.con.prepareStatement("delete from Asignaciones where 1=1");
			stm.executeUpdate();
			stm.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static Time alterhora() {
		int hh;
		int mm;
		int ss;
		
		System.out.println("Hora (HH:MM:SS): \n");
		
		do {
			System.out.println("HH: ");
			hh = Integer.parseInt(sc.nextLine());
			if(hh>24 || hh<0) {
				System.out.println("Hora inválida!!!");
			}
		}while(hh>24 || hh<0);
		
		
		do {
			System.out.println("MM: ");
			mm = Integer.parseInt(sc.nextLine());
			if(mm>60 || mm<0) {
				System.out.println("Minutos inválidos!!!");
			}
		}while(mm>60 || mm<0);
		
		
		do {
			System.out.println("SS: ");
			ss = Integer.parseInt(sc.nextLine());
			if(ss>60 || ss<0) {
				System.out.println("Segundos inválidos!!!");
			}
		}while(hh>60 || hh<0);
		
		@SuppressWarnings("deprecation")
		Time tim = new Time(hh,mm,ss);
		return tim;
	}
	
	public static Date alterdate() {
		int mm;
		int dd;
		
		System.out.println("Fecha (MM:DD)");
		
		do {
			System.out.println("MM: ");
			mm = Integer.parseInt(sc.nextLine());
			if(mm>12 || mm<1) {
				System.out.println("Mes inválido!!!");
			}
		}while(mm>12 || mm<1);
		
		do {
			System.out.println("DD: ");
			dd = Integer.parseInt(sc.nextLine());
			if(dd>31 || dd<1) {
				System.out.println("Día inválido!!!");
			}
		}while(dd>31 || dd<1);
		String daat = "2023-"+mm+"-"+dd;

		Date dat = Date.valueOf(daat);
		return dat;
	}
	
}

















