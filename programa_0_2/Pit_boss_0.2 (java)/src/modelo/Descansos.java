package modelo;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Descansos {
	static Scanner sc = new Scanner(System.in);
	private int id_descanso;
	private int id_trabajador;
	private Date fecha;
	private Time hora_inicio;
	private Time hora_fin;
	
	private Descansos(int id_descanso, int id_trabajador, Date fecha, Time hora_inicio, Time hora_fin) {
		this.id_descanso=id_descanso;
		this.id_trabajador=id_trabajador;
		this.fecha=fecha;
		this.hora_inicio=hora_inicio;
		this.hora_fin = hora_fin;
	}
	
	public int getIdDescanso() {
		return this.id_descanso;
	}
	
	public int getIdTrabajador() {
		return this.id_trabajador;
	}
	
	public Date getFecha() {
		return this.fecha;
	}
	
	public Time getHoraIni() {
		return this.hora_inicio;
	}
	
	public Time getHoraFin() {
		return this.hora_fin;
	}
	
	public static LinkedList<Descansos> find(String nom, String campo_orden){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Descansos> lista = new LinkedList<Descansos>();
		String sql="Select * from descansos where ";
		if(nom!=null) sql+="id_trabajador like ? and ";
		sql+="1=1";
		
		if(campo_orden!=null) sql+="order by "+campo_orden;
		
		
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(nom!=null) stm.setString(1, "%"+nom+"%");
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Descansos d = new Descansos(respuesta.getInt("ID_Descanso"),
						respuesta.getInt("ID_Trabajador"),
						respuesta.getDate("Fecha"),
						respuesta.getTime("Hora_Inicio"),
						respuesta.getTime("Hora_Fin"));
				lista.add(d);
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
			stm=Conexion.con.prepareStatement("delete from descansos where ID_Descanso=?");
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
		return Descansos.delete(this.getIdDescanso());
	}
	
	
	public static boolean update(int id, Date dat, Time hini, Time hfin) {
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
	}
	
	public static Descansos load(int id) {
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Select * from Descansos where ID_Descansos=?");
			stm.setInt(1, id);
			
			respuesta=stm.executeQuery();
			
			if(respuesta.next()) {
				Descansos d = new Descansos(respuesta.getInt("ID_Descanso"),
						respuesta.getInt("ID_Trabajador"),
						respuesta.getDate("Fecha"),
						respuesta.getTime("Hora_Inicio"),
						respuesta.getTime("Hora_Fin"));
				return d;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Descansos insert(int idtr, Date dat, Time hini, Time hfin) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into Descansos (ID_Trabajador,Fecha, Hora_Inicio, Hora_Fin) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stm.setInt(1, idtr);
			stm.setDate(2, dat);
			stm.setTime(3, hini);
			stm.setTime(4, hfin);
			
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);

			
			Descansos d=new Descansos(auto,idtr,dat,hini,hfin);
			stm.close();
			return d;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static void clear() {
		Conexion.open();
		PreparedStatement stm;
		
		try {
			stm=Conexion.con.prepareStatement("delete from Descansos where 1=1");
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

















