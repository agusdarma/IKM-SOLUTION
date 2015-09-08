package com.myproject.parking.trx.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.CommonUtil;
import com.myproject.parking.trx.logic.BaseQueryLogic;
import com.myproject.parking.trx.logic.LogicFactory;

public class ParkingServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ParkingServiceServlet.class);

	private ObjectMapper mapper;
	
	@Autowired
	private LogicFactory logicFactory;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
		mapper = new ObjectMapper();
		// faster this way, not default
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);		
		LOG.debug("Servlet Initialized");
	}
	  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long startTime = (int)System.currentTimeMillis();
		
		String pathInfo = request.getPathInfo();
		LOG.info("{} START", new String[] {pathInfo});
		LOG.debug("POST PathInfo: {}", new String[] {pathInfo});
		
		BaseQueryLogic logic = logicFactory.getLogic().get(pathInfo);
		if(logic == null){
			LOG.warn("PathInfo {} is not supported.", new String[] {pathInfo});
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line + "\n");
			line = reader.readLine();
		}
		reader.close();
		String data = sb.toString();
//		if(!"/receiveTrxFromVeriTrans".equalsIgnoreCase(pathInfo)){
			data = CipherUtil.decryptTripleDES(data, CipherUtil.PASSWORD);
//		}		
		LOG.debug("RequestData: {}", new String[] { data });	
	
		String respData = logic.process(request,response,data, mapper, pathInfo);
		
		if (respData == null) {
			LOG.warn("PathInfo {} is not supported.", new String[] {pathInfo});
			response.sendError(HttpServletResponse.SC_NOT_FOUND);			
			return;
		}
		if("/userActivate".equalsIgnoreCase(pathInfo)){
//			response.setContentType("text/html;charset=UTF-8");	
		    request.setAttribute("message", respData);
		    request.getRequestDispatcher("/response_activated.jsp").forward(request, response);
		}else{
			response.setContentType("application/json");
			response.setContentLength(respData.length());
			response.getWriter().write(respData);
			response.getWriter().flush();
		}		
		int delta = (int) (System.currentTimeMillis() - startTime);
		LOG.info("{} FINISH in {}ms", new String[] {pathInfo, 
				CommonUtil.displayNumberNoDecimal(delta) });
	 }
	
	
}
