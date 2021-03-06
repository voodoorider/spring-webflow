/*
 * Copyright 2004-2010 the original author or authors.
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.js.ajax.AjaxHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;

/**
 * An extension of {@link FlowHandlerAdapter} that replaces the default {@link AjaxHandler} instance with a
 * {@link JsfAjaxHandler} assuming JSF 2 is the runtime environment.
 * 
 * @author Rossen Stoyanchev
 * @since 2.2.0
 */
public class JsfFlowHandlerAdapter extends FlowHandlerAdapter {

	public void afterPropertiesSet() throws Exception {
		boolean initializeAjaxHandler = getAjaxHandler() == null;
		super.afterPropertiesSet();
		if (initializeAjaxHandler) {
			if (JsfRuntimeInformation.isAtLeastJsf20()) {
				JsfAjaxHandler ajaxHandler = new JsfAjaxHandler();
				ajaxHandler.setApplicationContext(getApplicationContext());
				setAjaxHandler(ajaxHandler);
			}
		}
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return super.handle(request, response, handler);
	}

}
