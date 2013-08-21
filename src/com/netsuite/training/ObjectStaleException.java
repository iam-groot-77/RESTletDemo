package com.netsuite.training;

@SuppressWarnings("serial")
public class ObjectStaleException extends Exception
{

	public ObjectStaleException(String message)
	{
		super(message);
	}
}
