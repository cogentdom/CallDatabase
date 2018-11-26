import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


class project {


private static StringBuilder CallStoredProcedure(String[] args, Connection connection) {
	StringBuilder message = new StringBuilder();
	PreparedStatement statement;
	CallableStatement stmnt;
	ResultSet resultSet = null;
	
	try {
		
		switch(args[0].toLowerCase()) {
		case "createitem":
			if(args.length != 4) {
				return message.append("Usage: CreateItem <ItemCode> <ItemDescription> <Price>");
			}
			
			statement = connection.prepareStatement(String.format("Call CreateItem(\'%s\', \'%s\', \'%s\')", args[1], args[2], args[3]));	
      		statement.execute();    
  			message.append(String.format("Successfully added item with code: %s, description: %s, and price: %s", args[1], args[2], args[3]));
      		break;
		case "createpurchase":
			if(args.length != 3) {
				return message.append("Usage: CreatePurchase <ItemCode> <PurchaseQuantity> ");
			}
			
			statement = connection.prepareStatement(String.format("Call CreatePurchase(\'%s\', \'%s\')", args[1], args[2]));	
      		statement.execute();    
  			message.append(String.format("Successfully added purchase with code: %s, quantity: %s", args[1], args[2]));
      		break;
		case "createshipment":
			if(args.length != 4) {
				return message.append("Usage: CreateShipment <ItemCode> <ShipmentQuantity> <shipmentDate>");
			}
			
			statement = connection.prepareStatement(String.format("Call CreateShipment(\'%s\', \'%s\')", args[1], args[2], args[3]));	
      		statement.execute();    
  			message.append(String.format("Successfully added shipment with code: %s and quantity: %s and date: %s", args[1], args[2], args[3]));
      		break;
		case "getitems":
			if(args.length != 2) {
				return message.append("Usage: GetItems <ItemCode>");
			}
			statement = connection.prepareStatement(String.format("Call GetItems(\'%s\')", args[1]));				
			statement.execute();    

  			resultSet = statement.getResultSet();
      		
      		if(!resultSet.next()) {
      			message.append("No results found.");
      			break;
      		}
      		resultSet.beforeFirst();
      		message.append("ID : ItemCode : ItemDescription : Price\n");
      		message.append("---------------------------------------\n");
			while (resultSet.next()) {
				message.append(String.format("%s : %s : %s : %s\n", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
			}
      		break;
		case "getshipments":
			if(args.length != 2) {
				return message.append("Usage: GetShipments <ItemCode>");
			}
			statement = connection.prepareStatement(String.format("Call GetShipments(\'%s\')", args[1]));				
			statement.execute();    

  			resultSet = statement.getResultSet();
      		
      		if(!resultSet.next()) {
      			message.append("No results found.");
      			break;
      		}
      		resultSet.beforeFirst();
      		message.append("ID : ItemID : Quantity : ShipmentDate\n");
      		message.append("-------------------------------------\n");
			while (resultSet.next()) {
				message.append(String.format("%s : %s : %s : %s\n", resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4)));
			}
      		break;
		case "getpurchases":
			if(args.length != 2) {
				return message.append("Usage: GetPurchases <ItemCode>");
			}
			statement = connection.prepareStatement(String.format("Call GetPurchases(\'%s\')", args[1]));				
			statement.execute();    

  			resultSet = statement.getResultSet();	      		
      		if(!resultSet.next()) {
      			message.append("No results found.");
      			break;
      		}
      		resultSet.beforeFirst();
      		message.append("ID : ItemID : Quantity : PurchaseDate\n");
      		message.append("-------------------------------------\n");
			while (resultSet.next()) {
				message.append(String.format("%s : %s : %s : %s\n", resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getString(4)));
			}
      		break;
		case "itemsavailable":
			if(args.length != 2) {
				return message.append("Usage: ItemsAvailable <ItemCode>");
			}
//			stmnt = connection.prepareCall("Call ItemsAvailable(" + args[1] + ")");
			statement = connection.prepareStatement(String.format("Call ItemsAvailable(\'%s\')", args[1]));				
			statement.execute();
//			stmnt.execute();

  			resultSet = statement.getResultSet();
      		if(!resultSet.next()) {
      			message.append("No results found.");
      			break;
      		}
      		resultSet.beforeFirst();
      		message.append("ID : Code : Name : QuantityAvailabe\n");
      		message.append("----------------------------------\n");
			while (resultSet.next()) {
				message.append(String.format("%s : %s : %s : %s\n", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
			}
      		break;
		case "updateitem":
			if(args.length != 3) {
				return message.append("Usage: UpdateItem <ItemCode> <Price>");
			}
			
			statement = connection.prepareStatement(String.format("Call UpdateItem(\'%s\', \'%s\')", args[1], args[2]));	
      		statement.execute();    
  			message.append(String.format("Successfully updated item with code: %s, and price: %s", args[1], args[2]));
      		break;
		
		case "deleteitem":
			if(args.length != 2) {
				return message.append("Usage: DeleteItem <ItemCode>");
			}
			
			statement = connection.prepareStatement(String.format("Call DeleteItem(\'%s\')", args[1]));	
      		statement.execute();    
  			message.append(String.format("Successfully deleted item with code: %s", args[1]));
      		break;
		case "deleteshipment":
			if(args.length != 2) {
				return message.append("Usage: DeleteShipment <ItemCode>");
			}
			
			statement = connection.prepareStatement(String.format("Call DeleteShipment(\'%s\')", args[1]));	
      		statement.execute();    
  			message.append(String.format("Successfully deleted shipment with code: %s", args[1]));
      		break;
		case "deletepurchase":
			if(args.length != 2) {
				return message.append("Usage: DeletePurchase <ItemCode>");
			}
			
			statement = connection.prepareStatement(String.format("Call DeletePurchase(\'%s\')", args[1]));	
      		statement.execute();    
  			message.append(String.format("Successfully deleted purchase with code: %s", args[1]));
      		break;
		default:
			message.append("Listed are the stored procedures usage: \n");
			message.append("CreateItem <ItemCode> <ItemDescription> <Price> \n");
			message.append("CreatePurchase <ItemCode> <PurchaseQuantity> \n");
			message.append("CreateShipment <ItemCode> <ShipmentQuantity> <shipmentDate\n");
			message.append("GetItems <ItemCode> \n");
			message.append("GetShipments <ItemCode> \n");
			message.append("GetPurchases <ItemCode> \n");
			message.append("ItemsAvailable <ItemCode> \n");
			message.append("UpdateItem <ItemCode> <Price> \n");
			message.append("DeleteItem <ItemCode> \n");
			message.append("DeleteShipment <ItemCode> \n");
			message.append("DeletePurchase <ItemCode \n");
		}
		
		return message;
	
	} catch (SQLException ex) {
		System.err.println("SQLException: " + ex.getMessage());
		System.err.println("SQLState: " + ex.getSQLState());
		System.err.println("VendorError: " + ex.getErrorCode());
		return message;
	}
}

public static void main(String[] args) {
	try {
		StringBuilder result = new StringBuilder();
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection connection = makeConnection("5753", "FinalBase", "mango");

		if (args.length < 1) {
			result.append("Please provide an argument for the desired stored procedure, or type \\? for a list of procedures.");
		}
		else {
			result.append(CallStoredProcedure(args, connection));
		}
		
		connection.close();
		System.out.println();
		System.out.println(result);
		System.out.println();
	} catch (Exception ex) {
		System.err.println(ex);
	}
}

public static Connection makeConnection(String port, String database, String password) {
	try {
		Connection conn = null;
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:" + port+ "/" + database+
		"?verifyServerCertificate=false&useSSL=true", "msandbox",
		password);
		// Do something with the Connection
		System.out.println("Database " + database +" connection succeeded!");
		System.out.println();
		return conn;
		} catch (SQLException ex) {
		// handle any errors
		System.err.println("SQLException: " + ex.getMessage());
		System.err.println("SQLState: " + ex.getSQLState());
		System.err.println("VendorError: " + ex.getErrorCode());
		}
		return null;
		}
}




