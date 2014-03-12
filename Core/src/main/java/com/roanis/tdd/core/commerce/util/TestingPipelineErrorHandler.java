package com.roanis.tdd.core.commerce.util;

import atg.commerce.util.PipelineErrorHandler;

public class TestingPipelineErrorHandler implements PipelineErrorHandler {

	@Override
	public void handlePipelineError(Object pObject, String pString) {
		System.out.println(new StringBuilder(pObject.toString()).append(":").append(pString).toString());
	}

}
