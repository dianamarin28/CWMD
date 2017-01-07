package edu.ubb.cwmdEjbClient.interfaces;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.TokenDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;

@Remote
public interface TokenBeanInterface {

	UserDTO validateToken(String value) throws RemoteException;

	TokenDTO generateToken(String userName, String email) throws RemoteException;

}