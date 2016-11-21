package edu.ubb.cwmdEjb.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.cwmdEjb.assemblers.TokenAssembler;
import edu.ubb.cwmdEjb.assemblers.UserAssembler;
import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.TokenDAO;
import edu.ubb.cwmdEjb.dao.UserDAO;
import edu.ubb.cwmdEjb.model.Token;
import edu.ubb.cwmdEjb.model.User;
import edu.ubb.cwmdEjbClient.dtos.TokenDTO;
import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TokenBeanInterface;

@Stateless
@LocalBean
public class TokenBean implements Serializable, TokenBeanInterface {

	private static Logger logger = LoggerFactory.getLogger(TokenBean.class);
	private static final long serialVersionUID = -3179218356134823502L;
	private static final int NUMBER_OF_DAYS_UNTIL_THE_TOKEN_EXPIRES = 2;

	@EJB
	private TokenDAO tokenDao;

	@EJB
	private UserDAO userDao;

	private TokenAssembler tokenAssembler = new TokenAssembler();
	private UserAssembler userAssembler = new UserAssembler();

	@Override
	public UserDTO validateToken(String value) throws RemoteException {
		try {
			Token foundToken = tokenDao.getByValue(value);
			LocalDate creationDate = foundToken.getCreationDate();
			LocalDate currentDate = LocalDate.now();
			long numberOfDays = ChronoUnit.DAYS.between(currentDate, creationDate);

			if (numberOfDays > NUMBER_OF_DAYS_UNTIL_THE_TOKEN_EXPIRES) {
				throw new RemoteException("The token has expired");
			}
			return userAssembler.modelToDto(foundToken.getUser());
		} catch (NoResultException e) {
			logger.error("The token is not valid " + e);
			throw new RemoteException("The token is not valid");
		} catch (DaoException e) {
			logger.error("Token retrieval failed " + e);
			throw new RemoteException("Token retrieval failed");
		}
	}

	@Override
	public TokenDTO generateToken(String userName, String email) throws RemoteException {
		try {
			User tempUser = userDao.getByUserName(userName);

			if (tempUser.getEmail().equals(email)) {
				Token token = new Token();
				token.setCreationDate(LocalDate.now());
				token.setUser(tempUser);
				token.setValue(UUID.randomUUID().toString());
				token = tokenDao.insertToken(token);
				return tokenAssembler.modelToDto(token);
			} else {
				logger.error("The user with the given user name does not have the given email");
				throw new RemoteException("The user with the given user name does not have the given email");
			}
		} catch (DaoException e) {
			logger.error("The user with the given user name was not found");
			throw new RemoteException("The user with the given user name was not found");
		}
	}

}
