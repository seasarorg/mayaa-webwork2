package org.seasar.mayaa.webwork;

import java.util.Iterator;

import org.seasar.mayaa.impl.cycle.scope.AbstractReadOnlyAttributeScope;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * OgnlValueStackから値を取得するAttributeScopeです。
 */
public class WebworkValueStackScope extends AbstractReadOnlyAttributeScope {
	/** シリアルバージョンID */
	private static final long serialVersionUID = -8692027295257276932L;

	/** スコープ名 */
	public static final String SCOPE_NAME = "ww";

	/**
	 * @see org.seasar.mayaa.impl.cycle.scope.AbstractReadOnlyAttributeScope#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		OgnlValueStack valueStack = ActionContext.getContext().getValueStack();

		if (name != null) {
			Object value = valueStack.findValue(name);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * @see org.seasar.mayaa.impl.cycle.scope.AbstractReadOnlyAttributeScope#hasAttribute(java.lang.String)
	 */
	public boolean hasAttribute(String name) {
		OgnlValueStack valueStack = ActionContext.getContext().getValueStack();

		if (name != null) {
			Object value = valueStack.findValue(name);
			if (value != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 実装していません。
	 * 
	 * @see org.seasar.mayaa.impl.cycle.scope.AbstractReadOnlyAttributeScope#iterateAttributeNames()
	 */
	public Iterator iterateAttributeNames() {
		return null;
	}

	/**
	 * @see org.seasar.mayaa.impl.cycle.scope.AbstractReadOnlyAttributeScope#getScopeName()
	 */
	public String getScopeName() {
		return SCOPE_NAME;
	}
}
