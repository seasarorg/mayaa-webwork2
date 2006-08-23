package org.seasar.mayaa.webwork;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.mayaa.engine.Engine;
import org.seasar.mayaa.impl.MayaaServlet;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.provider.ProviderUtil;
import org.seasar.mayaa.impl.util.StringUtil;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

/**
 * Mayaa�ŏ������s��Result�ł��B
 */
public class MayaaResult extends WebWorkResultSupport {
	/** �V���A���o�[�W����ID */
	private static final long serialVersionUID = -7752483670104351387L;

	/**
	 * �o�͏������s���܂��B
	 * 
	 * @param location
	 *            �o�͂Ɏg�p����t�@�C���p�X
	 * @param actionInvocation
	 *            ActionInvocation
	 */
	protected void doExecute(String location, ActionInvocation actionInvocation)
			throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getServletPath();
		if (path.lastIndexOf('/') > 0) {
			path = path.substring(1, path.lastIndexOf('/') + 1);
		} else {
			path = "";
		}

		if (location != null) {
			if (location.startsWith("/")) {
				// ��΃p�X�w��
				request = new MayaResultRequest(request, location);
			} else {
				// ���΃p�X�w��
				request = new MayaResultRequest(request, path + location);
			}
		} else {
			// location�w�肪�����ꍇ�́uaction��+".html"�v
			request = new MayaResultRequest(request, path
					+ ActionContext.getContext().getName() + ".html");
		}

		CycleUtil.initialize(request, ServletActionContext.getResponse());
		Engine engine = ProviderUtil.getEngine();
		setupCharacterEncoding(ServletActionContext.getRequest(), engine
				.getParameter("requestCharacterEncoding"));
		engine.doService(null, true);
	}

	protected void setupCharacterEncoding(HttpServletRequest request,
			String encoding) {
		if (request.getCharacterEncoding() == null) {
			try {
				request.setCharacterEncoding(encoding);
			} catch (UnsupportedEncodingException e) {
				String message = StringUtil.getMessage(MayaaServlet.class, 0,
						encoding);
				Log log = LogFactory.getLog(MayaaServlet.class);
				log.warn(message, e);
			}
		}
	}

	/**
	 * PathInfo���U������HttpServletRequest
	 */
	public static class MayaResultRequest extends HttpServletRequestWrapper {
		private String location;

		public MayaResultRequest(HttpServletRequest request, String location) {
			super(request);
			this.location = location;
		}

		public String getPathInfo() {
			return location;
		}

		public String getServletPath() {
			return "";
		}
	}
}
