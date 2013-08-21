package com.netsuite.training;

import java.util.Scanner;

public class RESTLetDemoJava
{

	public static void main(String[] args) throws Exception
	{
		doSetup();

		print("Press Enter to begin processing Sales Order");
		pause();
		processSalesOrder();

		print("Press Enter to begin processing Employee");
		pause();
		processEmployee();

		print("Press Enter to terminate execution");
		pause();

		print("Demo completed");
	}

	private static void processSalesOrder() throws Exception
	{
		print("Press Enter to see JSON received as part of JObject constructor");
		pause();

		JObject recSalesOrder = new JObject("salesorder", "1524");

		print("\nPress Enter to see values of selected fields in the recSalesOrder JObject");
		pause();

		getAndDisplaySalesOrderFields(recSalesOrder);

		print("\nValues will be set on fields in the recSalesOrder JObject; press Enter to continue");
		pause();

		recSalesOrder.setFieldValue("salesrep", "19");
		recSalesOrder.setFieldValue("shipdate", "9/3/2012");
		recSalesOrder.setFieldValue("excludecommission", "T");

		getAndDisplaySalesOrderFields(recSalesOrder);

		print("\nObject will now be submitted to update NetSuite record; press Enter to coninue");
		pause();

		String submitReturn = recSalesOrder.submit();

		print("\nReturn from submit = " + submitReturn);

		print("\nSetting a value on a field in the recSalesOrder JObject will now be attempted; press Enter to continue");
		pause();
		try
		{
			recSalesOrder.setFieldValue("tobeprinted", "T");
		}
		catch (Exception ex)
		{
			print(ex.getMessage());
		}
	}

	private static void getAndDisplaySalesOrderFields(JObject recSalesOrder)
			throws Exception
	{
		String salesRep = recSalesOrder.getFieldValue("salesrep");
		String salesRepName = recSalesOrder.getFieldText("salesrep");
		String shipDate = recSalesOrder.getFieldValue("shipdate");
		String excludeCommission = recSalesOrder
				.getFieldValue("excludecommission");

		// @formatter:off
		print("salesrep = " + salesRep + "\n" +
		      "salesrep name = " + salesRepName + "\n" +
			  "shipdate = " + shipDate + "\n" +
		      "excludecommission = " + excludeCommission);
		// @formatter:on
	}

	private static void processEmployee() throws Exception
	{
		print("Press Enter to see JSON received as part of JObject constructor");
		pause();

		JObject recEmployee = new JObject("employee", "6");

		print("\nPress Enter to see values of selected fields in the recEmployee JObject");
		pause();

		getAndDisplayEmployeeFields(recEmployee);

		print("\nValues will be set on fields in the recEmployee JObject; press Enter to continue");
		pause();

		recEmployee.setFieldValue("phone", "(123) 456-7890");
		recEmployee.setFieldValue("department", "3");
		recEmployee.setFieldValue("title", "Manager");
		recEmployee.setFieldValue("supervisor", "15");

		getAndDisplayEmployeeFields(recEmployee);

		print("\nObject will now be submitted to update NetSuite record; press Enter to coninue");
		pause();

		String submitReturn = recEmployee.submit();

		print("\nReturn from submit = " + submitReturn);

		print("\nSetting a value on a field in the recEmployee JObject will now be attempted; press Enter to continue");
		pause();
		try
		{
			recEmployee.setFieldValue("sendemail", "T");
		}
		catch (Exception ex)
		{
			print(ex.getMessage());
		}
	}

	private static void getAndDisplayEmployeeFields(JObject recEmployee)
			throws Exception
	{
		String phone = recEmployee.getFieldValue("phone");
		String department = recEmployee.getFieldValue("department");
		String departmentName = recEmployee.getFieldText("department");
		String title = recEmployee.getFieldValue("title");
		String supervisor = recEmployee.getFieldValue("supervisor");
		String supervisorName = recEmployee.getFieldText("supervisor");

		// @formatter:off
		print("phone = " + phone + "\n" +
		      "department = " + department + "\n" +
			  "department name = " + departmentName + "\n" +
		      "title = " + title + "\n" +
		      "supervisor = " + supervisor + "\n" +
		      "supervisor name = " + supervisorName);
		// @formatter:on
	}

	private static void doSetup()
	{
		HTTPMethods.restletURL = "";
		HTTPMethods.account = "";
		HTTPMethods.email = "";
		HTTPMethods.password = "";
	}

	private static void print(String in)
	{
		System.out.println(in);
	}

	private static void pause() throws Exception
	{
		Scanner keyIn = new Scanner(System.in);
		keyIn.nextLine();
	}
}
