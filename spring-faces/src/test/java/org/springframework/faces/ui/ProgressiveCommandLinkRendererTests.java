package org.springframework.faces.ui;

import javax.faces.component.UIForm;
import javax.faces.component.UIParameter;

import junit.framework.TestCase;

import org.springframework.faces.webflow.JSFMockHelper;

public class ProgressiveCommandLinkRendererTests extends TestCase {

	JSFMockHelper jsf = new JSFMockHelper();

	ProgressiveCommandLinkRenderer renderer = new ProgressiveCommandLinkRenderer();

	public void setUp() throws Exception {
		jsf.setUp();
	}

	public void tearDown() throws Exception {
		jsf.tearDown();
	}

	public void testRenderOnClick_AjaxEnabled_NoParams() throws Exception {
		String expected = "<a onclick=\"Spring.remoting.submitForm(&apos;myForm:foo&apos;, &apos;myForm&apos;, "
				+ "{processIds: &apos;myForm:foo&apos;}); return false;\"/>";

		UIForm form = new UIForm();
		form.setId("myForm");
		ProgressiveUICommand link = new ProgressiveUICommand();
		link.setId("foo");
		form.getChildren().add(link);

		RenderAttributeCallback callback = renderer.getAttributeCallbacks(link).get("onclick");

		jsf.facesContext().getResponseWriter().startElement("a", link);

		callback.doRender(jsf.facesContext(), jsf.facesContext().getResponseWriter(), link, "onclick", null, "onclick");

		jsf.facesContext().getResponseWriter().endElement("a");

		assertEquals(expected, jsf.contentAsString());
	}

	public void testRenderOnClick_AjaxEnabled_WithParams() throws Exception {
		String expected = "<a onclick=\"Spring.remoting.submitForm(&apos;myForm:foo&apos;, &apos;myForm&apos;, "
				+ "{processIds: &apos;myForm:foo&apos;, foo : &apos;bar&apos;, zoo : &apos;baz&apos;}"
				+ "); return false;\"/>";

		UIForm form = new UIForm();
		form.setId("myForm");
		ProgressiveUICommand link = new ProgressiveUICommand();
		link.setId("foo");
		form.getChildren().add(link);
		UIParameter param1 = new UIParameter();
		param1.setName("foo");
		param1.setValue("bar");
		UIParameter param2 = new UIParameter();
		param2.setName("zoo");
		param2.setValue("baz");
		link.getChildren().add(param1);
		link.getChildren().add(param2);

		RenderAttributeCallback callback = renderer.getAttributeCallbacks(link).get("onclick");

		jsf.facesContext().getResponseWriter().startElement("a", link);

		callback.doRender(jsf.facesContext(), jsf.facesContext().getResponseWriter(), link, "onclick", null, "onclick");

		jsf.facesContext().getResponseWriter().endElement("a");

		assertEquals(expected, jsf.contentAsString());
	}

	public void testRenderOnClick_AjaxDisabled_NoParams() throws Exception {
		String expected = "<a onclick=\"this.submitFormFromLink(&apos;myForm&apos;,&apos;myForm:foo&apos;, []); return false;\"/>";

		UIForm form = new UIForm();
		form.setId("myForm");
		ProgressiveUICommand link = new ProgressiveUICommand();
		link.setId("foo");
		link.setAjaxEnabled(false);
		form.getChildren().add(link);

		RenderAttributeCallback callback = renderer.getAttributeCallbacks(link).get("onclick");

		jsf.facesContext().getResponseWriter().startElement("a", link);

		callback.doRender(jsf.facesContext(), jsf.facesContext().getResponseWriter(), link, "onclick", null, "onclick");

		jsf.facesContext().getResponseWriter().endElement("a");

		assertEquals(expected, jsf.contentAsString());
	}

	public void testRenderOnClick_AjaxDisabled_WithParams() throws Exception {
		String expected = "<a onclick=\"this.submitFormFromLink(&apos;myForm&apos;,&apos;myForm:foo&apos;, ["
				+ "{name : &apos;foo&apos;, value : &apos;bar&apos;}, {name : &apos;zoo&apos;, value : &apos;baz&apos;}"
				+ "]); return false;\"/>";

		UIForm form = new UIForm();
		form.setId("myForm");
		ProgressiveUICommand link = new ProgressiveUICommand();
		link.setId("foo");
		link.setAjaxEnabled(false);
		form.getChildren().add(link);
		UIParameter param1 = new UIParameter();
		param1.setName("foo");
		param1.setValue("bar");
		UIParameter param2 = new UIParameter();
		param2.setName("zoo");
		param2.setValue("baz");
		link.getChildren().add(param1);
		link.getChildren().add(param2);

		RenderAttributeCallback callback = renderer.getAttributeCallbacks(link).get("onclick");

		jsf.facesContext().getResponseWriter().startElement("a", link);

		callback.doRender(jsf.facesContext(), jsf.facesContext().getResponseWriter(), link, "onclick", null, "onclick");

		jsf.facesContext().getResponseWriter().endElement("a");

		assertEquals(expected, jsf.contentAsString());
	}
}
