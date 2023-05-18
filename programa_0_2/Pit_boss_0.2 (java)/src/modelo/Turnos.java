package modelo;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Turnos {
	static Scanner sc = new Scanner(System.in);
	private int id_turno;
	private String nombre;
	private Time hora_Inicio;
	private Time hora_fin;
	
	private Turnos(int id_turno, Time hora_inicio, Time hora_fin) {
		this.id_turno = id_turno;
		this.hora_Inicio=hora_inicio;
		this.hora_fin = hora_fin;
	}
	
	public int getIdTurno() {
		return this.id_turno;
	}
	
	
	public Time getHoraIni() {
		return this.hora_Inicio;
	}
	
	public Time getHoraFin() {
		return this.hora_fin;
	}
	
	public static LinkedList<Turnos> find(String nom, String campo_orden){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Turnos> lista = new LinkedList<Turnos>();
		String sql="Select * from turnos where ";
		if(nom!=null) sql+="id_turno like ? and ";
		sql+="1=1";
		
		if(campo_orden!=null) sql+="order by "+campo_orden;
		
		
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(nom!=null) stm.setString(1, "%"+nom+"%");
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Turnos t = new Turnos(respuesta.getInt("ID_Turno"),
						respuesta.getTime("Hora_Inicio"),
						respuesta.getTime("Hora_Fin"));
				lista.add(t);
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
			stm=Conexion.con.prepareStatement("delete from turnos where ID_Turno=?");
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
		return Descansos.delete(this.getIdTurno());
	}
	
	
	public static boolean update(int id, Time hini, Time hfin) {
		Conexion.open();
		String sql="";
		PreparedStatement stm;
		
		try {
			if(hini!=null || hfin !=null) {
				sql="Update turnos set ";
				if(hini!=null) sql+="Hora_Inicio="+"\""+hini+"\"";
				if(hini!=null && hfin!=null) sql+=" and ";
				if(hfin!=null) sql+="Hora_Fin="+"\""+hfin+"\"";
				sql+=" where ID_Turno= ? ";
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
	}
	
	public static Turnos load(int id) {
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Select * from Turnos where ID_Turnos=?");
			stm.setInt(1, id);
			
			respuesta=stm.executeQuery();
			
			if(respuesta.next()) {
				Turnos t = new Turnos(respuesta.getInt("ID_Turno"),
						respuesta.getTime("Hora_Inicio"),
						respuesta.getTime("Hora_Fin"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Turnos insert(Time hini, Time hfin) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into Turnos (Hora_Inicio, Hora_Fin) values (?,?)", Statement.RETURN_GENERATED_KEYS);
			stm.setTime(1, hini);
			stm.setTime(2, hfin);
			
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);

			
			Turnos m=new Turnos(auto,hini,hfin);
			stm.close();
			return m;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static void clear() {
		Conexion.open();
		PreparedStatement stm;
		
		try {
			stm=Conexion.con.prepareStatement("delete from Turnos where 1=1");
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
	
}

















