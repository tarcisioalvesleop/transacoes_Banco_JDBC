 package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement st = null;
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);//confirma��o false caso ocorrar uma exce��o
			
			st = conn.createStatement();			
			
			//atualizando o salario do department 1 e 2
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId =1");
			//criando uma exce��o no caminho (simu��o)
			/*int x = 1;
			if( x < 2 ) {
				throw new SQLException("Fake error.");
			}*/
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId =2");
			
			conn.commit();//confirma��o de transa��o (executa os comando acima
			
			System.out.println("rows1 " + rows1);
			System.out.println("rows2 " + rows2);
		}
		catch(SQLException e) {//erro na transa��o (Exception personalizada)
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {//erro no rollback
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}//retorna ao estado da transa��o
		}
		finally {//fechando conex�o e statement
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
