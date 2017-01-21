package edu.ubb.cwmdEjb.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import edu.ubb.cwmdEjb.dao.DaoException;
import edu.ubb.cwmdEjb.dao.DocumentDAO;
import edu.ubb.cwmdEjb.dao.LogDAO;
import edu.ubb.cwmdEjb.dao.VersionDAO;
import edu.ubb.cwmdEjb.model.Document;
import edu.ubb.cwmdEjb.model.Log;
import edu.ubb.cwmdEjb.model.LogActionType;
import edu.ubb.cwmdEjb.model.Version;
import edu.ubb.cwmdEjb.util.EmailHelper;

@Singleton
@Startup
public class DocumentDeletionTimedTask {

	/*
	 * Documentele create avand o vechime de maxim 30 zile de la ultima
	 * modificare, si care nu sunt incluse in niciun flux de documente sunt
	 * automat programate pentru stergere, iar stergerea efectiva se va realiza
	 * dupa 30 zile de cand a fost considerat eligibil pentru stergere
	 */

	// get all documents from db with last modification date > 30
	// check documents to see if the last modification date is > 30
	// check if documents are not in the active flows
	// => get all active flows...get all documents for each active flow
	// set a flag(date) for deletion
	// check if the flag date for deletion is current date and delte from db

	@EJB
	private DocumentDAO documentDao;

	@EJB
	private VersionDAO versionDao;

	@EJB
	private LogDAO logDao;

	@Resource
	private TimerService timerService;

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Resource
	private SessionContext ctx;

	private LocalDateTime localDate;

	public LocalDateTime getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDateTime localDate) {
		this.localDate = localDate;
	}

	@PostConstruct
	public void init() {
		logger.info("ProgrammaticalTimerEJB initialized");
		localDate = LocalDateTime.MIN;
		ScheduleExpression everyTenSeconds = new ScheduleExpression().hour("*").minute("*").second("13,34,57");
		timerService.createCalendarTimer(everyTenSeconds, new TimerConfig("Date" + localDate, false));
	}

	@Timeout
	public void processFlights(Timer timer) {
		deleteDocuments();
		removeFromDeletion();
		scheduleForDeletion();
	}

	public void removeFromDeletion() {
		List<Document> documents = documentDao.getAllDocuments();
		for (Document doc : documents) {
			if (doc.getDeletionDate() != null) {
				if (Math.abs(ChronoUnit.DAYS.between(doc.getDeletionDate(), doc.getLastModficationDate())) < 30) {
					doc.setDeletionDate(null);
					try {
						logger.info("Document " + doc.getName() + " was removed from deletion schedule");
						documentDao.updateDocument(doc);
						// send the email
						String message = "Dear " + doc.getAuthor().getUserName() + ",\n\n"
								+ "Your document with the title " + doc.getName()
								+ " has been removed from deletion schedule because you have modified it.\n";
						String subject = "Remove document from deletion schedule";
						EmailHelper.sendEmail(doc.getAuthor().getEmail(), subject, message);

						// insert in the log table
						Log log = new Log();
						log.setDate(LocalDate.now());
						log.setLogActionType(LogActionType.SYSTEM_REMOVE_DOCUMENT_FROM_DELETION_SCHEDULE);
						log.setUser(null);
						logDao.insertLog(log);
					} catch (DaoException e) {
						logger.info("Could not delete document " + doc.getId());
					}
				}
			}
		}
	}

	public void scheduleForDeletion() {
		List<Document> documents = documentDao.getAllDocuments();
		for (Document doc : documents) {
			if (doc.getDeletionDate() == null
					&& Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), doc.getLastModficationDate())) >= 30) {
				doc.setDeletionDate(LocalDate.now().plusDays(30L));
				try {
					logger.info("Document " + doc.getName() + " was scheduled for deletion");
					documentDao.updateDocument(doc);
					// send the email
					String message = "Dear " + doc.getAuthor().getUserName() + ",\n\n" + "Your document with the title "
							+ doc.getName() + " has been scheduled for deletion in 30 days time.\n";
					String subject = "Schedule document deletion";
					EmailHelper.sendEmail(doc.getAuthor().getEmail(), subject, message);

					// insert in the log table
					Log log = new Log();
					log.setDate(LocalDate.now());
					log.setLogActionType(LogActionType.SYSTEM_SCHEDULE_DOCUMENT_DELETION);
					log.setUser(null);
					logDao.insertLog(log);
				} catch (DaoException e) {
					logger.info("Could not scheedule the document " + doc.getId() + " for deletion");
				}
			}

		}
	}

	public void deleteDocuments() {
		List<Document> documents = documentDao.getAllDocuments();
		for (Document doc : documents) {
			if (doc.getDeletionDate() != null) {
				if (LocalDate.now().isEqual(doc.getDeletionDate())) {
					try {
						// prepare the data needed for the email
						String message = "Dear " + doc.getAuthor().getUserName() + ",\n\n"
								+ "Your document with the title " + doc.getName() + " has been deleted.\n";
						String subject = "Document deletion by system";
						String authorEmail = doc.getAuthor().getEmail();

						// delete all the versions
						List<Version> versions = versionDao.getAllVersionsOfDocument(doc.getId());
						for (Version version : versions) {
							versionDao.deleteVersion(version);
						}
						logger.info("Document " + doc.getName() + " was deleted");
						documentDao.deleteDocument(doc);

						// send the email
						EmailHelper.sendEmail(authorEmail, subject, message);

						//// insert in the log table
						Log log = new Log();
						log.setDate(LocalDate.now());
						log.setLogActionType(LogActionType.SYSTEM_DELETE_DOCUMENT);
						log.setUser(null);
						logDao.insertLog(log);
					} catch (DaoException e) {
						logger.info("Could not delete the document " + doc.getId());
					}
				}
			}
		}
	}
}
