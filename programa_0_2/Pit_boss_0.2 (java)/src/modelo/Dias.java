package modelo;
import java.util.Scanner;

import controlador.Principal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;

public class Dias {
	static Scanner sc = new Scanner(System.in);
	private int id_dia;
	private Date dia;
	
	private Dias(int id, Date dia) {
		this.id_dia = id;
		this.dia=dia;
	}
	
	public int getIdDia() {
		return this.id_dia;
	}
	
	public Date getDia() {
		return this.dia;
	}
	
	public static Dias insert(Date d) {
		Conexion.open();
		
		PreparedStatement stm;
		ResultSet respuesta;
		
		try {
			stm=Conexion.con.prepareStatement("Insert into Dias (dia) values (?)", Statement.RETURN_GENERATED_KEYS);
			stm.setDate(1, d);
			
			stm.executeUpdate();
			
			respuesta = stm.getGeneratedKeys();
			int auto = respuesta.getInt(1);

			
			Dias a=new Dias(auto,d);
			stm.close();
			return a;
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static LinkedList<Dias> find(String id){
		Conexion.open();
		PreparedStatement stm;
		ResultSet respuesta;
		
		LinkedList<Dias> lista = new LinkedList<Dias>();
		String sql="Select * from dias";
		if(id!=null) sql+= "where id_dia like ?";
		
		
		try {
			stm=Conexion.con.prepareStatement(sql);
			if(id!=null)   stm.setString(1, "%"+id+"%");
			
			
			respuesta=stm.executeQuery();
			
			while(respuesta.next()) {
				Dias d = new Dias(respuesta.getInt("ID_Dia"),
						respuesta.getDate("dia"));
				lista.add(d);
			}
			
			stm.close();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	
}
