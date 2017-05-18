package com.jx372.emaillist.action;

import com.jx372.web.action.Action;
import com.jx372.web.action.ActionFactory;

public class EmaillistActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("form".equals(actionName)){
			action = new FormAction();
		}
		else if("insert".equals(actionName)){
			action = new InsertAction();
		}else{
			action = new ListAction();
		}
		return action;
	}

}
