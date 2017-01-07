package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.UserAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.TokenDAO;
import edu.ubb.cwmdEjb.dao.UserDAO;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjb.util.PasswordEncrypter;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;

@Stateless
@LocalBean
public class UserBean implements Serializable, UserBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(UserBean.class);
	private static final long serialVersionUID = 6215916780406838825L;

	@EJB
	private UserDAO userDao;
	
	@EJB
	private TokenDAO tokenDao;

	private UserAssembler userAssembler = new UserAssembler();

	@Override
	public String validateUserNameAndPassword(String userName, String password) {
		try {
			return userDao.validateUserNameAndPassword(userName, password);
		} catch (DaoException e) {
			logger.error("Username and password validation error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void insertUser(UserDTO userDTO) throws RemoteException {
		User user = userAssembler.dtoToModelSimple(userDTO);
		//user.setPassword(PasswordEncrypter.getGeneratedHashedPassword(user.getPassword(), ""));
		try {
			userDao.insertUser(user);

		} catch (DaoException e) {
			logger.error("insert user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, boolean withPassword) throws RemoteException {
		User user = userAssembler.dtoToModel(userDTO);
		if (withPassword) {
			//user.setPassword(PasswordEncrypter.getGeneratedHashedPassword(user.getPassword(), ""));
		}
		try {
			UserDTO updatedDTO = userAssembler.modelToDto(userDao.updateUser(user));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("update user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public UserDTO getByUserName(String userName) throws RemoteException {
		try {
			UserDTO getUser = userAssembler.modelToDto(userDao.getByUserName(userName));
			return getUser;
		} catch (DaoException e) {
			logger.error("getByUserName error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public UserDTO findById(Long userId) throws RemoteException {
		UserDTO user = new UserDTO();
		try {
			user = userAssembler.modelToDto(userDao.findById(userId));
			return user;
		} catch (DaoException e) {
			logger.error("findById error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteUser(UserDTO userDTO) throws RemoteException {
		User user = userAssembler.dtoToModel(userDTO);
		try {
			userDao.deleteUser(user);
		} catch (DaoException e) {
			logger.error("delete user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}

	}

	@Override
	public List<UserDTO> getUsers() throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getUsers();
			for (User user : usersList) {
				UserDTO userDto = userAssembler.modelToDto(user);
				usersDTOlist.add(userDto);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("get all users error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserDTO> getUsersByRoles(String role) throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getUsersByRoles(role);
			for (User user : usersList) {
				UserDTO userDto = userAssembler.modelToDto(user);
				usersDTOlist.add(userDto);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("getUsersByType error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	
	@Override
		public void changePassword(UserDTO userDto) throws RemoteException {
			User user = userAssembler.dtoToModel(userDto);
			try {
				System.out.println("parola: " + user.getPassword() );
				user = userDao.findById(user.getId());
				user.setPassword(userDto.getPassword());
				// user.setPassword(PasswordEncrypter.getGeneratedHashedPassword(userDto.getPassword(),
				// ""));
				user = userDao.updateUser(user);
				tokenDao.deleteAllHavingUser(user);
			} catch (DaoException e) {
				logger.error("The user password change failed" + e);
				throw new RemoteException("The user change password failed", e);
			}
		}

	@Override
	public List<UserDTO> getUserFromDepartment(Long departmentid) throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getUserFromDepartment(departmentid);
			for (User user : usersList) {
				UserDTO userDto = userAssembler.modelToDto(user);
				usersDTOlist.add(userDto);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("getUserFromDepartment error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
