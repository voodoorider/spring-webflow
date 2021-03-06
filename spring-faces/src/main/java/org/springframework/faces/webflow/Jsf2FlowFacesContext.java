/*
 * Copyright 2004-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.faces.webflow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextFactory;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.webflow.execution.RequestContext;

/**
 * Extends FlowFacesContext in order to provide JSF 2 delegation method.
 * 
 * @author Rossen Stoyanchev
 */
public class Jsf2FlowFacesContext extends FlowFacesContext {

	private ExternalContext externalContext;

	private PartialViewContext partialViewContext;

	public Jsf2FlowFacesContext(RequestContext context, FacesContext delegate) {
		super(context, delegate);

		this.externalContext = new Jsf2FlowExternalContext(getDelegate().getExternalContext());

		PartialViewContextFactory factory = (PartialViewContextFactory) FactoryFinder
				.getFactory(FactoryFinder.PARTIAL_VIEW_CONTEXT_FACTORY);
		PartialViewContext partialViewContextDelegate = factory.getPartialViewContext(this);
		this.partialViewContext = new FlowPartialViewContext(partialViewContextDelegate);
	}

	public ExternalContext getExternalContext() {
		return externalContext;
	}

	// --------------- JSF 2.0 Pass-through delegate methods ------------------//

	public Map<Object, Object> getAttributes() {
		return getDelegate().getAttributes();
	}

	public PartialViewContext getPartialViewContext() {
		return partialViewContext;
	}

	/**
	 * Returns a List for all Messages in the current MessageContext that does translation to FacesMessages.
	 */
	public List<FacesMessage> getMessageList() {
		return getMessageDelegate().getMessageList();
	}

	/**
	 * Returns a List for all Messages with the given clientId in the current MessageContext that does translation to
	 * FacesMessages.
	 */
	public List<FacesMessage> getMessageList(String clientId) {
		return getMessageDelegate().getMessageList(clientId);
	}

	public boolean isPostback() {
		return getDelegate().isPostback();
	}

	public PhaseId getCurrentPhaseId() {
		return getDelegate().getCurrentPhaseId();
	}

	public void setCurrentPhaseId(PhaseId currentPhaseId) {
		getDelegate().setCurrentPhaseId(currentPhaseId);
	}

	public ExceptionHandler getExceptionHandler() {
		return getDelegate().getExceptionHandler();
	}

	public boolean isProcessingEvents() {
		return getDelegate().isProcessingEvents();
	}

	public boolean isProjectStage(ProjectStage stage) {
		return getDelegate().isProjectStage(stage);
	}

	public boolean isValidationFailed() {
		if (getMessageDelegate().hasErrorMessages()) {
			return true;
		} else {
			return getDelegate().isValidationFailed();
		}
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		getDelegate().setExceptionHandler(exceptionHandler);
	}

	public void setProcessingEvents(boolean processingEvents) {
		getDelegate().setProcessingEvents(processingEvents);
	}

	public void validationFailed() {
		getDelegate().validationFailed();
	}

	// --------------- JSF 2.1 Pass-through delegate methods ------------------//

	public boolean isReleased() {
		return getDelegate().isReleased();
	}

	protected class Jsf2FlowExternalContext extends FlowExternalContext {

		Log logger = LogFactory.getLog(FlowExternalContext.class);

		public Jsf2FlowExternalContext(ExternalContext delegate) {
			super(delegate);
		}

		public void responseSendError(int statusCode, String message) throws IOException {
			logger.debug("Sending error HTTP status code " + statusCode + " with message '" + message + "'");
			delegate.responseSendError(statusCode, message);
		}

		// --------------- JSF 2.0 Pass-through delegate methods ------------------//

		public String getContextName() {
			return delegate.getContextName();
		}

		public void addResponseCookie(String name, String value, Map<String, Object> properties) {
			delegate.addResponseCookie(name, value, properties);
		}

		public Flash getFlash() {
			return delegate.getFlash();
		}

		public String getMimeType(String file) {
			return delegate.getMimeType(file);
		}

		public String getRequestScheme() {
			return delegate.getRequestScheme();
		}

		public String getRequestServerName() {
			return delegate.getRequestServerName();
		}

		public int getRequestServerPort() {
			return delegate.getRequestServerPort();
		}

		public String getRealPath(String path) {
			return delegate.getRealPath(path);
		}

		public int getRequestContentLength() {
			return delegate.getRequestContentLength();
		}

		public OutputStream getResponseOutputStream() throws IOException {
			return delegate.getResponseOutputStream();
		}

		public Writer getResponseOutputWriter() throws IOException {
			return delegate.getResponseOutputWriter();
		}

		public void setResponseContentType(String contentType) {
			delegate.setResponseContentType(contentType);
		}

		public void invalidateSession() {
			delegate.invalidateSession();
		}

		public void setResponseHeader(String name, String value) {
			delegate.setResponseHeader(name, value);
		}

		public void addResponseHeader(String name, String value) {
			delegate.addResponseHeader(name, value);
		}

		public void setResponseBufferSize(int size) {
			delegate.setResponseBufferSize(size);
		}

		public int getResponseBufferSize() {
			return delegate.getResponseBufferSize();
		}

		public boolean isResponseCommitted() {
			return delegate.isResponseCommitted();
		}

		public void responseReset() {
			delegate.responseReset();
		}

		public void setResponseStatus(int statusCode) {
			delegate.setResponseStatus(statusCode);
		}

		public void responseFlushBuffer() throws IOException {
			delegate.responseFlushBuffer();
		}

		public void setResponseContentLength(int length) {
			delegate.setResponseContentLength(length);
		}

		public String encodeBookmarkableURL(String baseUrl, Map<String, List<String>> parameters) {
			return delegate.encodeBookmarkableURL(baseUrl, parameters);
		}

		public String encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
			return delegate.encodeRedirectURL(baseUrl, parameters);
		}

		public String encodePartialActionURL(String url) {
			return delegate.encodePartialActionURL(url);
		}

		// --------------- JSF 2.1 Pass-through delegate methods ------------------//

		public int getSessionMaxInactiveInterval() {
			return delegate.getSessionMaxInactiveInterval();
		}

		public boolean isSecure() {
			return delegate.isSecure();
		}

		public void setSessionMaxInactiveInterval(int interval) {
			delegate.setSessionMaxInactiveInterval(interval);
		}
	}
}
