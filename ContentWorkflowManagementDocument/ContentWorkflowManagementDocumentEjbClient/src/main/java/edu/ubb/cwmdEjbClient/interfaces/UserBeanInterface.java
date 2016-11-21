package edu.ubb.cwmdEjbClient.interfaces;

import java.util.List;

import javax.ejb.Remote;

import edu.ubb.cwmdEjbClient.dtos.UserDTO;

@Remote
public interface UserBeanInterface {

	String validateUserNameAndPassword(String userName, String password);

	void insertUser(UserDTO userDTO) throws RemoteException;

	UserDTO updateUser(UserDTO userDTO, boolean withPassword) throws RemoteException;

	UserDTO getByUserName(String userName) throws RemoteException;

	UserDTO findById(Long userId) throws RemoteException;

	void deleteUser(UserDTO userDTO) throws RemoteException;

	List<UserDTO> getUsers() throws RemoteException;

	List<UserDTO> getUsersByRoles(String role) throws RemoteException;
	
	void changePassword(UserDTO userDto) throws RemoteException;
	
	 List<UserDTO> getUserFromDepartment(Long departmentid) throws RemoteException;

}
