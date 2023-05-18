package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Trabajadores {
	private int id_trabajador;
	private String nombre;
	private String cargo;
	
	private Trabajadores(int id_trabajador, String nombre, String cargo) {
		this.id_trabajador=id_trabajador;
		this.nombre = nombre;
		this.cargo= cargo;
	}
	
	public int getIdTrabajdor() {
		return this.id_trabajador;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getCargo() {
		return this.cargo;
	}
	
	public static LinkedList<Trabajadores> find(String nom, String campo_orden){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Trabajadores> lista = new LinkedList<Trabajadores>();
		String sql="Select * from trabajadores where ";
		if(nom!=null) sql+="nombre like ? and ";
		sql+="1=1";
		
		if(campo_orden!=null) sql+="order by "+campo_orden;
				
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(nom!=null) stm.setString(1, "%"+nom+"%");
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Trabajadores t = new Trabajadores(respuesta.getInt("ID_Trabajador"),
						respuesta.getString("Nombre"),
						respuesta.getString("Cargo"));
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
			stm=Conexion.con.prepareStatement("delete from trabajadores where ID_Trabajador=?");
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
		return Trabajadores.delete(this.getIdTrabajdor());
	}
	
	public static boolean update(int id, String nombre, String cargo) {
		Conexion.open();
		String sql="";
		PreparedStatement stm;
		
		try {
			if(cargo!=null || nombre !=null) {
				sql="Update trabajadores set ";
				if(nombre!=null) sql+="nombre="+"\""+nombre+"\"";
				if(nombre!=null && cargo!=null) sql+=" and ";
				if(cargo!=null) sql+="cargo="+"\""+cargo+"\"";
				sql+=" where ID_Trabajador= ? ";
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
	
	public static Trabajadores load(int id) {
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Select * from trabajadores where ID_trabajadores=?");
			stm.setInt(1, id);
			
			respuesta=stm.executeQuery();
			
			if(respuesta.next()) {
				Trabajadores m = new Trabajadores(respuesta.getInt("ID_Trabajadores"),
						respuesta.getString("Nombre"),
						respuesta.getString("Cargo"));
				stm.close();
				return m;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Trabajadores insert(String nombre, String cargo) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into trabajadores (nombre,cargo) values (?,?)", Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, nombre);
			stm.setString(2, cargo);
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);

			
			Trabajadores m=new Trabajadores(auto,nombre,cargo);
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
			stm=Conexion.con.prepareStatement("delete from trabajadores where 1=1");
			stm.executeUpdate();
			stm.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}

















