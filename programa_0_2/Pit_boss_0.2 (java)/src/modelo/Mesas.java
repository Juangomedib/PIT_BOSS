package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Mesas {
	private int id_mesa;
	private String nombre;
	private String estado;
	
	private Mesas(int id_mesa, String nombre, String estado) {
		this.id_mesa=id_mesa;
		this.nombre = nombre;
		this.estado = estado;
	}
	
	public int getIdMesa() {
		return this.id_mesa;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public static LinkedList<Mesas> find(String nom, String campo_orden){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Mesas> lista = new LinkedList<Mesas>();
		String sql="Select * from mesas where ";
		if(nom!=null) sql+="nombre like ? and ";
		sql+="1=1";
		
		if(campo_orden!=null) sql+="order by "+campo_orden;

		
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(nom!=null) stm.setString(1, "%"+nom+"%");
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Mesas m = new Mesas(respuesta.getInt("ID_Mesa"),
						respuesta.getString("Nombre"),
						respuesta.getString("Estado"));
				lista.add(m);
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
			stm=Conexion.con.prepareStatement("delete from mesas where ID_Mesa=?");
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
		return Mesas.delete(this.id_mesa);
	}
	
	public static boolean update(int id, String nombre, String estado) {
		Conexion.open();
		String sql="";
		PreparedStatement stm;
		
		try {
			if(estado!=null || nombre !=null) {
				sql="Update mesas set ";
				if(nombre!=null) sql+="nombre="+"\""+nombre+"\"";
				if(nombre!=null && estado!=null) sql+=" and ";
				if(estado!=null) sql+="estado="+"\""+estado+"\"";
				sql+=" where ID_Mesa= ? ";
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
	
	public static Mesas load(int id) {
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Select * from mesas where ID_Mesa=?");
			stm.setInt(1, id);
			
			respuesta=stm.executeQuery();
			
			if(respuesta.next()) {
				Mesas m = new Mesas(respuesta.getInt("ID_Mesas"),
						respuesta.getString("Nombre"),
						respuesta.getString("Estado"));
				stm.close();
				return m;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static Mesas insert(String nombre) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into mesas (nombre) values (?)", Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, nombre);
			
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);
			String estado = respuesta.getString(1);
			
			Mesas m=new Mesas(auto,nombre,estado);
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
			stm=Conexion.con.prepareStatement("delete from mesas where 1=1");
			stm.executeUpdate();
			stm.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}

















