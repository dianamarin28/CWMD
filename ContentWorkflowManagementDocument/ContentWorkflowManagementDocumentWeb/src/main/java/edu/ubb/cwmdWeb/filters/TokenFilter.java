package edu.ubb.cwmdWeb.filters;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ubb.cwmdEjbClient.dtos.UserDTO;
import edu.ubb.cwmdEjbClient.interfaces.RemoteException;
import edu.ubb.cwmdEjbClient.interfaces.TokenBeanInterface;

@WebFilter(filterName = "TokenFilter", urlPatterns = { "/changePassword.xhtml" })
public class TokenFilter implements Filter {

	@EJB
	private TokenBeanInterface tokenBeanInterface;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String token = httpRequest.getParameter("token");
		if (token != null) {
			try {
				UserDTO userDto = tokenBeanInterface.validateToken(token);
				String userName = userDto.getUserName();
				// request.setAttribute("userForChangePassword", userDto);
				request.setAttribute("userForChangePassword", userName);
				chain.doFilter(request, response);
			} catch (RemoteException e) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}