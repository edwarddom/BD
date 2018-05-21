
/**
 * Clase basica que gestiona una conexion JDBC a MySQL ModeloDB here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Iterator;
import java.sql.*;
import java.util.ArrayList;

public class ModeloDB extends ModeloAbs
{
    // instance variables - replace the example below with your own
   
    /**7
     * Constructor for objects of class ModeloDB
     * Establece la conexion a la base de datos
     */
	
	
	ResultSet rset=null;
	Connection conexion=null;
	Statement stmt=null;
	
    public ModeloDB()
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    	}
    	catch(ClassNotFoundException e) {
    		System.out.println("ERROR AL REGISTRAR EL DRIVER");
    		System.exit(0);
    		
    	}
    	
    	try {
    	conexion = DriverManager.getConnection("jdbc:mysql://192.168.7.200:3306/ProductosDB","alumno","alumno");
    	
    	stmt= conexion.createStatement();
    	}
    	catch(SQLException e) {
    		System.err.println("ERROR AL CONECTAR CON EL SERVIDOR");
    		System.exit(0);
    	}
    	System.out.println("Usted esta conectado a la base de datos");
        
    }

    // INSERT
    public boolean insertarProducto ( Producto p){
    	PreparedStatement preparado = null;
    	int nfilas=0;
    	try {
    		preparado=conexion.prepareStatement("INSERT INTO `Productos` (`CODIGO`, `NOMBRE`, `STOCK`, `STOCK_MIN`, `PRECIO`) VALUES (?,?,?,?,?);");
   
    		}catch(SQLException ex) {
    			System.err.println("ERROR EN LA INSTRUCCION SQL");
    			return false;
    	    }
    	
    	try {
    		preparado.setInt(1, p.getCodigo());
    		preparado.setString(2, p.getNombre());
    		preparado.setInt(3, p.getStock());
    		preparado.setInt(4, p.getStock_min());
    		preparado.setFloat(5, p.getPrecio());	
    	    nfilas=preparado.executeUpdate();
    	}
    	catch(Exception ex) {
    		return false;
    	
    	}
    return (nfilas== 1);    
    }
    
    
    // DELETE
    boolean borrarProducto ( int codigo ){
    	try {
        	rset=stmt.executeQuery("delete from productos where codigo ="+codigo);
        	}
        	catch(SQLException ex) {
        		ex.printStackTrace();
        	}
        return true;   
    }
    
    // SELECT
    public Producto buscarProducto ( int codigo){
    	Producto aux=null;
    	
    	try {
    		rset= stmt.executeQuery("select * from Productos where codigo= " +codigo);
    		
    		if(rset.next()) {
    		aux=new Producto();
    		aux.setCodigo(rset.getInt(1));
    		aux.setNombre(rset.getString(2));
    		aux.setStock(rset.getInt(3));
    		aux.setStock_min(rset.getInt(4));
    		aux.setPrecio(rset.getFloat(5));
    		}
    	}
    		
    	catch(SQLException e) {
    		
    	}
    return aux;    
    }
    
    
    //SELECT
    void listarProductos (){
    	
    	
    	try {
    		rset=stmt.executeQuery("select * from Productos");
    		
    		while(rset.next()) {
    			
    			System.out.print("Código: " + rset.getInt(1)+" ");
    			System.out.print("Nombre: "+ rset.getFloat(2)+" ");
    			System.out.print("Precio: "+ rset.getFloat(5)+" ");
    			System.out.println(" ");
    			
    		}
    		
    		
    	}
    	catch(SQLException e) {
    		System.err.println("ERROR AL LISTAR PRODUCTOS");
    		
    	}
        
    }
    
    //UPDATE
    boolean modificarProducto (Producto nuevo){  
		PreparedStatement preparado=null; 
		int nfilas=0;
    	
    	try {
			preparado = conexion.prepareStatement( "UPDATE `Productos` SET CODIGO = ?, NOMBRE = ?, STOCK = ?, STOCK_MIN = ?, PRECIO = ? " + 
					                       " WHERE CODIGO = ?");
		} catch (Exception ex) {
			System.out.println("ERROR EN LA INSTRUCCION DE SQL");
			return false;
		}
    	try {
    		preparado.setInt(1,nuevo.getCodigo());
    		preparado.setString(2,nuevo.getNombre());
    		preparado.setInt(3,nuevo.getStock());
    		preparado.setInt(4, nuevo.getStock_min());
    		preparado.setFloat(5, nuevo.getPrecio());
    		nfilas=preparado.executeUpdate();
    	}
    	catch(Exception ex) {
    		System.out.println("ERROR EN LA INSTRUCCION SQL");
    	    return false; 
    	}
    	
    return (nfilas==1); 
    }
    
    // Devuelvo un Iterador de una ArrayList con los resultados
    // copiados de Rset al ArrayList
     Iterator <Producto> getIterator(){
       ArrayList <Producto> lista = new ArrayList<Producto>();
       // Relleno el array list con los resultados de al consulta
       try {
    	 ResultSet rset =stmt.executeQuery("select* from productos;");
    	   
    	   while(rset.next()) {
    		   Producto p =new Producto();
    		   p.setCodigo(rset.getInt("Codigo"));
    		   p.setNombre(rset.getString("Nombre"));
    		   p.setStock(rset.getInt("Stock"));
    		   p.setStock_min(rset.getInt("Stock_min"));
    		   p.setPrecio(rset.getFloat("Precio"));
    		   lista.add(p);
    	   }
       }
       catch(Exception e){
    	   System.out.println("ERROR EN EL ARRAYLIST");
    	   
       }
       return lista.iterator();
     }
}
